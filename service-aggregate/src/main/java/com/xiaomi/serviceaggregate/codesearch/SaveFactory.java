package com.xiaomi.serviceaggregate.codesearch;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class SaveFactory {
    public static AbstractResponseSaver createFileSaver(SaveType type, String filePath) {
        switch (type) {
            case CSV:
                return new CsvResponseSaver(filePath);
            case NOR:
                return new NorResponseSaver(filePath);
            default:
                throw new IllegalArgumentException("Unknown file saver type: " + type);
        }
    }
}
