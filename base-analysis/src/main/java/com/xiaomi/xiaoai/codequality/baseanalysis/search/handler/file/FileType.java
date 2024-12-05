package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public enum FileType {
    CPP("cpp"),
    JAVA("java"),
    SCALA("scala"),
    XML("xml"),
    PROPERTIES("properties"),
    YAML("yaml"),
    JSON("json"),
    PY("py"),
    SH("sh"),
    MD("md"),
    TXT("txt"),
    SBT("sbt"),
    CONF("conf"),
    ANY("All files"),
    APPLICATION_AK_CONF("application-ak.conf"),
    APPLICATION_C4_CONF("application-c4.conf"),
    PROGRAMMING_LANGUAGE(".java .scala .py .js .cpp .c .cs .php .rb .go .rs"),
    ;

    private final String type;

    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
}
