package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
public abstract class XiaoAiServiceQueryHandler<T>{

    protected static ExecutorService handlerExecutor;

    protected static volatile  boolean isStarted = false;
    public XiaoAiServiceQueryHandler(){}

    public abstract CompletableFuture<T> handle(XiaoAiService xiaoAiService);

    public static synchronized void start() {
       if(!isStarted) {
           handlerExecutor = ExecutorBuilder.create()
                   .setCorePoolSize(16)
                   .setMaxPoolSize(16)
                   .setThreadFactory(new NamedThreadFactory("QueryHandler-Task-",true))
                   .setWorkQueue(new ArrayBlockingQueue<>(5000))
                   .build();
           isStarted = true;
       }
    }
    public static synchronized void close() throws IOException {
        if(isStarted) {
            handlerExecutor.shutdown();
            isStarted = false;
        }
    }
}
