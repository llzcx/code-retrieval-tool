package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.Set;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/14
 */
public class PythonFileHandlerXiaoAiService extends FileHandlerXiaoAiService {
    public PythonFileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
    }

    @Override
    public boolean filter(File file) {
        return file.getName().endsWith(".py");
    }
}
