package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import cn.hutool.core.io.file.FileReader;
import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchEntity;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.XiaoAiServiceQueryHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
public abstract class FileHandlerXiaoAiService extends XiaoAiServiceQueryHandler<FileHandleResult> {

    protected Exp exp;

    private Set<String> whiteList;

    public FileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        this.exp = exp;
        this.whiteList = whiteList;
    }


    @Override
    public CompletableFuture<FileHandleResult> handle(XiaoAiService xiaoAiService) {
        if(!isStarted) throw new RuntimeException("handler is not started");
        String servicePath = xiaoAiService.getDir().toString();
        CompletableFuture<FileHandleResult> cf = new CompletableFuture<>();
        File serviceFile = new File(servicePath);
        if (!serviceFile.isDirectory()) {
            throw new RuntimeException("servicePath must be a directory: " + servicePath);
        }
        List<File> fileList = getFileList(serviceFile,whiteList);
        CopyOnWriteArrayList<FileHandleResult.FileMatchResult> list = new CopyOnWriteArrayList<>();
        CompletableFuture[] futures = fileList.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> {
                            FileReader fileReader = new FileReader(file);
                            String content = fileReader.readString();
                            ExpContext<TextMatchEntity> expContext = new ExpContext<>();
                            if (exp.eva(expContext, content)) {
                                return new FileHandleResult.FileMatchResult(file.getPath(), expContext.getMatchEntities());
                            } else {
                                return null;
                            }
                        }, handlerExecutor)
                        .thenApply(fileMatchResult -> {
                            if (fileMatchResult != null) {
                                list.add(fileMatchResult);
                            }
                            return null;
                        }))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                cf.completeExceptionally(throwable);
            } else {
                FileHandleResult fileHandleResult = FileHandleResult.builder()
                        .servicePath(serviceFile.getPath())
                        .serviceName(serviceFile.getName())
                        .fileMatchResults(list)
                        .build();
                cf.complete(fileHandleResult);
            }
        });

        return cf;
    }
    private List<File> getFileList(File root, Set<String> whiteList) {
        List<File> fileList = new ArrayList<>();
        if (root.exists()) {
            getFileList(root, fileList, whiteList);
        }
        return fileList;
    }

    private void getFileList(File root, List<File> fileList, Set<String> whiteList) {
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    boolean flag = true;
                    for (String  path : whiteList) {
                        if(file.getAbsolutePath().contains(path)) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        getFileList(file, fileList, whiteList);
                    }
                } else if (filter(file)) {
                    fileList.add(file);
                }
            }
        }
    }
    public abstract boolean filter(File file);



}