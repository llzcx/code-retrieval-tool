package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.service;

import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.dependency.DependencyMatchEntity;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.XiaoAiServiceQueryHandler;
import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
public class ServiceHandlerXiaoAiService extends XiaoAiServiceQueryHandler<ServiceHandlerResult> {

    private final Exp<Set<DependentCoordinate>, DependentCoordinate> exp;

    public ServiceHandlerXiaoAiService(Exp exp) {
        this.exp = exp;
    }
    @Override
    public CompletableFuture<ServiceHandlerResult> handle(XiaoAiService xiaoAiService) {
        if(!isStarted) throw new RuntimeException("handler is not started");
        CompletableFuture<ServiceHandlerResult> cf = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
                    ExpContext<DependentCoordinate> expContext = new ExpContext<>();
                    if (exp.eva(expContext, xiaoAiService.getSbtCompileResult().getDependentCoordinates())) {
                        return expContext.getMatchEntities();
                    } else {
                        return new ArrayList<DependentCoordinate>();
                    }
                }, handlerExecutor)
                .thenAccept(matchEntities -> {
                    if (matchEntities != null) {
                        cf.complete(new ServiceHandlerResult(xiaoAiService.getName(),matchEntities));
                    }else{
                        cf.completeExceptionally(new NullPointerException());
                    }
                });
        return cf;
    }
}
