package com.xiaomi.codequality.util;

import com.xiaomi.codequality.constant.Const;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/12
 */
public class FileUtil {
    public static String writeTextToFile(String directory, String filename, String content) {
        Path path = Paths.get(directory, filename);
        try (FileWriter writer = new FileWriter(path.toString())) {
            writer.write(content);
            return path.toFile().getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + e.getMessage());
            return null;
        }
    }

    public static String getResultPath(String repositoryName) {
        return Paths.get(String.valueOf(Const.BASE_RESULT),repositoryName).toString();
    }

}
