package com.xiaomi.serviceaggregate.codesearch;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.xiaomi.codequality.constant.Const;
import com.xiaomi.codequality.log.CodeQualityLogger;
import com.xiaomi.codequality.util.CommandUtil;
import com.xiaomi.codequality.util.CustomizableBitmap;
import com.xiaomi.codequality.util.FileUtil;
import com.xiaomi.codequality.util.PrintUtil;
import static com.xiaomi.codequality.util.PrintUtil.extractRepoName;
import com.xiaomi.serviceaggregate.load.YamlConfigLoader;
import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.XiaoAiServiceQueryHandler;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandleResult;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandlerFactory;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandlerXiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;
import com.xiaomi.xiaoai.codequality.basecode.pull.GitLabImpl;
import com.xiaomi.xiaoai.codequality.basecode.pull.GitlabProjectsFetcherWithPagination;
import com.xiaomi.xiaoai.codequality.basecode.pull.ServiceBasePath;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */

public class CodeSearchRequestHandler implements CodeQualityLogger, Closeable {
    private final CommandUtil commandUtil = new CommandUtil(16);
    private final List<FileTypeAndExp> fileTypeAndExpList;
    private final Set<String> reposWhiteList;
    private final Set<String> handing = new ConcurrentSkipListSet<>();
    private final CustomizableBitmap customizableBitmap;
    private final ServiceBasePath serviceBasePath;
    private final List<String> repositories;
    private final Set<String> problemsExist = new HashSet<>();
    private boolean reset = false;
    private AbstractResponseSaver responseSaver;
    public static final Set<String> pathWhiteList = new HashSet<>();

    static {
        pathWhiteList.add("target");
    }

    public CodeSearchRequestHandler(List<FileTypeAndExp> fileTypeAndExpList, boolean reset, String dir) {
        this(fileTypeAndExpList, dir);
        this.reset = reset;
        this.responseSaver = SaveFactory.createFileSaver(SaveType.NOR, dir);
    }

    public CodeSearchRequestHandler(List<FileTypeAndExp> fileTypeAndExpList, boolean reset, String dir, SaveType saveType) {
        this(fileTypeAndExpList, dir);
        this.reset = reset;
        this.responseSaver = SaveFactory.createFileSaver(saveType, dir);
    }
    private CodeSearchRequestHandler(List<FileTypeAndExp> fileTypeAndExpList, String dir) {
        this.fileTypeAndExpList = fileTypeAndExpList;
        YamlConfigLoader.Service service = YamlConfigLoader.getConfig().getService();
        String group = service.getGroup();
        String gitlab = service.getGitlab();
        String token = service.getToken();
        String repositoryPath = service.getRepository();
        if(!FileUtil.createAndCheckDir(repositoryPath) || !FileUtil.createAndCheckDir(dir)) {
            throw new RuntimeException("file initialization failed, Please check.");
        }
        reposWhiteList = new HashSet<>(service.getRepositoryWhiteList());
        //拉取所有仓库地址
        repositories = GitlabProjectsFetcherWithPagination.fetchAllGitlabProjects(gitlab, group, token);
        LOGGER.info("Total git repositories: {}", repositories.size());

        serviceBasePath = new GitLabImpl(commandUtil, repositoryPath);
        try {
            Path records = Paths.get(dir, Const.RECORDS_SER);
            if(reset) {
                cn.hutool.core.io.FileUtil.del(records.toString());
                cn.hutool.core.io.FileUtil.del(dir);
            }
            customizableBitmap = CustomizableBitmap.loadFromFile(records.toString(), repositories.size() * 2);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        solved.set(customizableBitmap.countSetBits());
        XiaoAiServiceQueryHandler.start();
    }
    private final ExecutorService  executorService = ExecutorBuilder.create()
            .setCorePoolSize(3)
            .setMaxPoolSize(3)
            .setKeepAliveTime(0)
            .setThreadFactory(new NamedThreadFactory("CodeSearch-Task-",true))
            .setWorkQueue(new ArrayBlockingQueue<>(500))
            .build();

    private final AtomicInteger solved = new AtomicInteger(0);
    ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1, new NamedThreadFactory("Monitor-Task-",true));

    private CodeSearchResponse handle(CodeSearchRequest request) {
        try {
            List<FileTypeAndExp> fileTypeAndExpList = request.getFileTypeAndExpList();
            Path basePath = Paths.get(request.getBasePath());
            List<CompletableFuture<FileHandleResult>> list = new ArrayList<>();
            HashMap<CompletableFuture<FileHandleResult>,FileType> mp = new HashMap<>();
            HashMap<CompletableFuture<FileHandleResult>,FileHandlerXiaoAiService> mp2 = new HashMap<>();
            for (FileTypeAndExp fileTypeAndExp : fileTypeAndExpList) {
                Exp exp = fileTypeAndExp.getExp();
                FileType fileTypes = fileTypeAndExp.getFileTypes();
                FileHandlerXiaoAiService fileHandlerXiaoAiService = FileHandlerFactory.create(fileTypes, exp, request.getFilePathWhiteList());
                XiaoAiService xiaoAiService = new XiaoAiService(basePath, null);
                CompletableFuture<FileHandleResult> cf = fileHandlerXiaoAiService.handle(xiaoAiService);
                mp.put(cf, fileTypes);
                mp2.put(cf, fileHandlerXiaoAiService);
                list.add(cf);
            }
            List<FileHandleResult> ans = new ArrayList<>();
            for (CompletableFuture<FileHandleResult> cf : list) {
                FileHandleResult fileHandleResult = cf.get();
                fileHandleResult.setFileType(mp.get(cf));
                fileHandleResult.setFileTypeString(mp2.get(cf).toString());
                ans.add(fileHandleResult);
            }
            return new CodeSearchResponse(request, ans);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public CompletableFuture<Void> search() {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        //监视器线程将记录进行持久化，并打印实时进度
        scheduled.scheduleAtFixedRate(() -> {
            try {
                customizableBitmap.saveToFile();
                LOGGER.info("Remaining task size is {}. The progress is {}/{}, The git repository being processed is: {}", repositories.size() - solved.intValue(), solved.intValue(), repositories.size(), handing);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 10, TimeUnit.SECONDS);
        for (int i = 0; i < repositories.size(); i++) {
            String repository = repositories.get(i);
            // 仓库不在白名单内且处于未完成状态
            if(!reposWhiteList.contains(PrintUtil.extractRepoName(repository)) && customizableBitmap.getBit(i) == 0) {
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new SearchTask(repository), executorService);
                final int finalI = i;
                completableFuture.thenRunAsync(()-> {
                    solved.addAndGet(1);
                    customizableBitmap.setBit(finalI, 1);
                });
                futures.add(completableFuture);
            }else {
                LOGGER.info("Skip repository task: {}", repository);
            }
        }
        try {
            long startTime = System.currentTimeMillis();
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRunAsync(() -> {
                long endTime = System.currentTimeMillis();
                LOGGER.info("Total time: {} ,min", (endTime - startTime) / 1000 / 60);
                LOGGER.info("Problems exist repository: {}", problemsExist);
                try {
                    responseSaver.close();
                    customizableBitmap.saveToFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws IOException {
        executorService.shutdown();
        scheduled.shutdown();
        commandUtil.close();
        XiaoAiServiceQueryHandler.close();
    }





    class SearchTask implements Runnable {
        private String repositoryUrl;

        public SearchTask(String repositoryUrl) {
            this.repositoryUrl = repositoryUrl;
        }
        @Override
        public void run() {
            LOGGER.info("Pulling in {}", repositoryUrl);
            handing.add(repositoryUrl);
            String basePath = serviceBasePath.getBasePath(repositoryUrl);
            LOGGER.info("Pulling success basePath:{}", basePath);
            CodeSearchRequest.CodeSearchRequestBuilder requestBuilder = CodeSearchRequest.builder();
            CodeSearchRequest request =
                    requestBuilder.basePath(basePath)
                            .filePathWhiteList(pathWhiteList)
                            .fileTypeAndExpList(fileTypeAndExpList)
                            .build();
            CodeSearchResponse response = handle(request);
            if (response==null) {
                LOGGER.error("Analysis fail. Repository:[{}]", repositoryUrl);
                problemsExist.add(repositoryUrl);
            }else if(!response.isEmpty()) {
                responseSaver.addItem(response);
                LOGGER.info("Analysis success. Find matching items. Repository:[{}]", repositoryUrl);
            } else {
                LOGGER.info("Analysis success. No matches found. Repository:[{}]", repositoryUrl);
            }
            handing.remove(repositoryUrl);
            LOGGER.info("Repository: {} handle success.", repositoryUrl);
        }
    }



}
