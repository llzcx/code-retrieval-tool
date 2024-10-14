package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Not<T,V> extends Exp<T,V> {
    private Exp<T,V> child;

    public Not(Exp<T,V> child) {
        this.child = child;
    }

    @Override
    public boolean eva(ExpContext<V> context, T content) {
        return !child.eva(context, content);
    }

    @Override
    public String toString() {
        return "!" + child;
    }
}
