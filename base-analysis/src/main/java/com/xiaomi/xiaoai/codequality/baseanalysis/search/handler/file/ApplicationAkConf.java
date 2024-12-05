package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.Set;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class ApplicationAkConf extends FileHandlerXiaoAiService {

    public ApplicationAkConf(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
    }

    @Override
    public boolean filter(File file) {
        return file.getName().equals("application-ak.conf");
    }

    @Override
    public String toString() {
        return "application-ak.conf";
    }
}
