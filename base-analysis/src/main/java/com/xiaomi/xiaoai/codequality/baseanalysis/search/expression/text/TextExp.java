package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public class TextExp extends Exp<String,TextMatchEntity> {

    @Override
    public boolean eva(ExpContext<TextMatchEntity> context, String content) {
        return false;
    }
}
