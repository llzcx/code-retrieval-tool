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
public class And<T,V> extends Exp<T,V> {
    private Exp<T,V> left;
    private Exp<T,V> right;

    public And(Exp<T,V> left, Exp<T,V> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean eva(ExpContext<V> context, T content) {
        return left.eva(context, content) && right.eva(context, content);
    }

    @Override
    public String toString() {
        return "(" + left + " && " + right + ")";
    }


}
