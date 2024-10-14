package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
@Data
public class ExpContext<T> {
    public ExpContext() {
        matchEntities = new ArrayList<>();
    }
    private List<T> matchEntities;


}
