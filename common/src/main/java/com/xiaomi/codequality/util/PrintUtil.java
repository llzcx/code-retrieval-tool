package com.xiaomi.codequality.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
public class PrintUtil {
    public static void appendWithPadding(StringBuilder sb, String text, int paddingLevel) {
        sb.append(IntStream.range(0, paddingLevel)
                        .mapToObj(i -> "  ")
                        .collect(Collectors.joining()))
                .append(text)
                .append(System.lineSeparator());
    }

    public static String extractRepoName(String url) {
        String[] parts = url.split("/");
        String name = parts[parts.length - 1];
        return name.substring(0, name.lastIndexOf('.'));
    }

}
