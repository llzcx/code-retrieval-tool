package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.Set;

public class ConfFileHandlerXiaoAiService extends FileHandlerXiaoAiService {
    public ConfFileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
    }

    @Override
    public boolean filter(File file) {
        return file.getName().endsWith(".conf") || file.getName().endsWith(".yml") || file.getName().endsWith(".yaml")
                || file.getName().endsWith(".properties");
    }

}