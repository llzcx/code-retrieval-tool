package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import static com.xiaomi.codequality.util.PrintUtil.appendWithPadding;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileHandleResult {

    private FileType fileType;
    private String fileTypeString;
    private Exp exp;
    private String serviceName;
    private String servicePath;

    List<FileMatchResult> fileMatchResults;

    @Data
    @AllArgsConstructor
    public static class FileMatchResult {
        private String filePath;
        private List<TextMatchEntity> textMatchEntity;
    }
    public static String trimAfterFirstMatch(String stringA, String stringB) {
        int index = stringA.indexOf(stringB);
        if (index >= 0) {
            // 索引位置加上子串长度
            index += stringB.length();
            return stringA.substring(index);
        } else {
            // 如果没有找到匹配项，则返回空字符串
            return "";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendWithPadding(sb, "ProjectName: " + serviceName, 0);
        appendWithPadding(sb, "FileType: " + fileType, 0);
        appendWithPadding(sb, "The total number of files is: " + fileMatchResults.size(), 0);
        AtomicInteger count = new AtomicInteger();
        fileMatchResults.forEach(fileMatchResult -> {
            count.addAndGet(1);
            appendWithPadding(sb, trimAfterFirstMatch(fileMatchResult.getFilePath(), serviceName), 2);
            appendFileMatches(sb, fileMatchResult.getTextMatchEntity(), 4);
        });
        return sb.toString();
    }

    private void appendFileMatches(StringBuilder sb, List<TextMatchEntity> matches, int paddingLevel) {
        matches.forEach(textMatchEntity -> {
            appendWithPadding(sb, "--Line " + textMatchEntity.getLineNumber() + ": " + textMatchEntity.getMatch(), paddingLevel);
        });
    }

}
