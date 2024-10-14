package com.xiaomi.serviceaggregate;

import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public interface QueryXiaoAiService {
    CompletableFuture<List<XiaoAiService>> query(String serviceName);
}
