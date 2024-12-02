package com.xiaomi.serviceaggregate.codesearch;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.or;
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
        Exp confCondition
                = or(new TextMatchRequirement("^(?!.*cluster_01_proxy)(?=.*cluster_01(?:_with_password)?).*", true), new TextMatchRequirement("RedisConfig()", false));
        //conf
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.CONF));
        //scala
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.SCALA));
        //Java
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.JAVA));
        //python
        list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.PY));
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list);
        handler.search().get();
        handler.close();
    }
}
