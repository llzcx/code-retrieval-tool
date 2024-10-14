package com.xiaomi.codequality.constant;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class Const {
    public static final Path BASE_REPOSITORIES = Paths.get("D:/cache/repositories").toAbsolutePath();
    public static final Path BASE_RESULT = Paths.get("data/result").toAbsolutePath();
    public static final Set<String> DEFAULT_WHITE_LIST = new HashSet<>();
    static {
        if(!BASE_REPOSITORIES.toFile().exists()){
            BASE_REPOSITORIES.toFile().mkdirs();
        }
        if(!BASE_RESULT.toFile().exists()){
            BASE_RESULT.toFile().mkdirs();
        }
        DEFAULT_WHITE_LIST.add("target");
    }
}
