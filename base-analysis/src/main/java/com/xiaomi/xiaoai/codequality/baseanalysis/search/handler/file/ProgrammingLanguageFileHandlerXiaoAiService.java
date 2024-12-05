package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class ProgrammingLanguageFileHandlerXiaoAiService extends FileHandlerXiaoAiService {
    private final List<String> languages = new ArrayList<>();

    public ProgrammingLanguageFileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
        languages.add(".java");
        languages.add(".scala");
        languages.add(".py");
        languages.add(".js");
        languages.add(".cpp");
        languages.add(".c");
        languages.add(".cs");
        languages.add(".php");
        languages.add(".rb");
        languages.add(".go");
        languages.add(".rs");
    }

    @Override
    public boolean filter(File file) {
        for (String extension : languages) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("endWith: ");
        for (String lag : languages) {
            sb.append(lag).append(" ");
        }
        return sb.toString();
    }
}
