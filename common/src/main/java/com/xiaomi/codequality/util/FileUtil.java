package com.xiaomi.codequality.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static boolean createAndCheckDir(String path) {
        File file = Paths.get(path).toFile();
        if(file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("invalid path:" + path);
        }
        if(!file.exists()) {
            return file.mkdirs();
        }
        return file.isDirectory();
    }

}
