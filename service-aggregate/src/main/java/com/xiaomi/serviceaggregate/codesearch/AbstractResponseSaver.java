package com.xiaomi.serviceaggregate.codesearch;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public abstract class AbstractResponseSaver {

    protected String dir;
    public AbstractResponseSaver(String dir) {
        this.dir = dir;
    }

    public abstract void addItem(CodeSearchResponse response);

    public abstract void close();

}
