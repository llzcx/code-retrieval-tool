package com.xiaomi.xiaoai.codequality.basecode.pull;

import com.xiaomi.codequality.util.CommandUtil;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/8
 */
public abstract class ServiceBasePath {
    protected CommandUtil commandUtil;
    public ServiceBasePath(CommandUtil commandUtil) {
        this.commandUtil = commandUtil;
    }
    public abstract String getBasePath(String URL);
}
