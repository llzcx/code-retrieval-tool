package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base;

/**
 * bool表达式顶层抽象
 * T: 待匹配的实体类型
 * V: 返回的值类型
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public abstract class Exp<T,V> {
    /**
     * 计算content是否符合要求，返回true或false
     *
     * @param context
     * @param content
     * @return
     */
    public abstract boolean eva(ExpContext<V> context,T content);

    public static <T,V> Exp<T,V> and(Exp<T,V> left, Exp<T,V> right) {
        return new And<>(left, right);
    }

    public static <T,V> Exp<T,V> or(Exp<T,V> left, Exp<T,V> right) {
        return new Or<>(left, right);
    }

    public static <T,V> Exp<T,V> not(Exp<T,V> left) {
        return new Not<>(left);
    }
}
