package com.xiaomi.xiaoai.codequality.basecode.compile;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;
import com.xiaomi.codequality.util.CommandUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public abstract class Compiler {
    protected final ExecutorService compilerExecutor;
    protected final CommandUtil commandUtil;
    public Compiler(CommandUtil commandUtil) {
        this.commandUtil = commandUtil;
        compilerExecutor = ExecutorBuilder.create()
                .setCorePoolSize(2)
                .setMaxPoolSize(2)
                .setKeepAliveTime(0)
                .setThreadFactory(new NamedThreadFactory("Compiler-Task-",true))
                .setWorkQueue(new ArrayBlockingQueue<>(200))
                .build();
    }
    public abstract CompletableFuture<CompileResult> compile(String projectPath);

    @Data
    @AllArgsConstructor
    public abstract static class CompileResult {
        protected String output;
        protected Set<DependentCoordinate> dependentCoordinates;
    }
}
