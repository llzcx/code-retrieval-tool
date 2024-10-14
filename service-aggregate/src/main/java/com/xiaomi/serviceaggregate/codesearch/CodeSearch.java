package com.xiaomi.serviceaggregate.codesearch;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.or;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchEntity;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchRequirement;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
public class CodeSearch {

    public static void main(String[] args) throws Exception {
        List<CodeSearchRequestHandler.FileTypeAndExp> list = new ArrayList<>();
        //conf
        Exp confCondition
                = or(new TextMatchRequirement("cluster_01", false), new TextMatchRequirement("cluster_02", false));
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition,FileType.CONF));
        //scala
        Exp scalaCondition
                = or(new TextMatchRequirement("RedisConfig()", false), confCondition);
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(scalaCondition,FileType.SCALA));
        //Java
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(scalaCondition,FileType.JAVA));
        //python
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(scalaCondition,FileType.PY));
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list);
        handler.quickStart().get();
        handler.close();
    }
}
