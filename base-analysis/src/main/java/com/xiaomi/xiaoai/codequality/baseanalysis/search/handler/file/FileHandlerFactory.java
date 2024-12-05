package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.util.HashSet;
import java.util.Set;

public class FileHandlerFactory {

    public static FileHandlerXiaoAiService create(FileType fileType, Exp exp) {
        return create(fileType, exp, new HashSet<>());
    }
    public static FileHandlerXiaoAiService create(FileType fileType, Exp exp, Set<String> whiteList) {
        if(fileType == null) throw new NullPointerException();
        switch (fileType) {
            case SCALA:
                return new ScalaFileHandlerXiaoAiService(exp, whiteList);
            case CONF:
                return new ConfFileHandlerXiaoAiService(exp,whiteList);
            case JAVA:
                return new JavaFileHanlderXiaoAiService(exp, whiteList);
            case PY:
                return new PythonFileHandlerXiaoAiService(exp, whiteList);
            case ANY:
                return new AnyFileHandlerXiaoAiService(exp, whiteList);
            case APPLICATION_AK_CONF:
                return new ApplicationAkConf(exp, whiteList);
            case APPLICATION_C4_CONF:
                return new ApplicationC4Conf(exp, whiteList);
            case PROGRAMMING_LANGUAGE:
                return new ProgrammingLanguageFileHandlerXiaoAiService(exp, whiteList);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}