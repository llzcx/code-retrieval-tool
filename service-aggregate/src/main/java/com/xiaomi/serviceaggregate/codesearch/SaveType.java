package com.xiaomi.serviceaggregate.codesearch;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public enum SaveType  {
    CSV("CSV"),
    NOR("CSV"),
    ;

    private final String type;

    public String getType() {
        return type;
    }

    SaveType(String type) {
        this.type = type;
    }
}
