package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.Set;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class ApplicationC4Conf extends FileHandlerXiaoAiService {

    public ApplicationC4Conf(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
    }

    @Override
    public boolean filter(File file) {
        return file.getName().equals("application-c4.conf");
    }

    @Override
    public String toString() {
        return "application-c4.conf";
    }
}
