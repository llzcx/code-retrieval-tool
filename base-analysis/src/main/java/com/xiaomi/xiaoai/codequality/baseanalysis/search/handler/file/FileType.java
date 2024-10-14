package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public enum FileType {
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
    ANY("any")
    ;

    private final String type;

    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
}
