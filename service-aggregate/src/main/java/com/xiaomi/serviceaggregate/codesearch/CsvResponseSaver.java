package com.xiaomi.serviceaggregate.codesearch;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchEntity;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandleResult;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandleResult.trimAfterFirstMatch;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class CsvResponseSaver extends AbstractResponseSaver {

    private static final String FILE_PATH_NAME = "output.csv";
    private static final List<String> HEADERS = Arrays.asList("文件筛选条件", "查询条件", "ai-workbench", "文件路径", "行号", "代码内容");

    private final CsvWriter writer;
    private final List<List<String>> res = new ArrayList<>();
    public CsvResponseSaver(String dir) {
        super(dir);
        writer = CsvUtil.getWriter(Paths.get(dir,FILE_PATH_NAME).toString(), CharsetUtil.CHARSET_UTF_8);
        res.add(HEADERS);
    }

    @Override
    public void addItem(CodeSearchResponse response) {
        for (FileHandleResult fileHandleResult : response.getFileHandleResults()) {
            for (FileHandleResult.FileMatchResult matchResult : fileHandleResult.getFileMatchResults()) {
                for (TextMatchEntity textMatchEntity : matchResult.getTextMatchEntity()) {
                    List<String> list = new ArrayList<>();
                    list.add(fileHandleResult.getFileTypeString());
                    list.add(fileHandleResult.getExp().toString());
                    list.add(fileHandleResult.getServiceName());
                    list.add(trimAfterFirstMatch(matchResult.getFilePath(), fileHandleResult.getServiceName()));
                    list.add(textMatchEntity.getLineNumber().toString());
                    list.add(textMatchEntity.getMatch());
                    res.add(list);
                }
            }
        }
    }

    @Override
    public void close() {
        writer.write(res);
        res.clear();
        writer.flush();
        writer.close();
    }
}
