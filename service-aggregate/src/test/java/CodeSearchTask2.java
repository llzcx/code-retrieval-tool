import com.xiaomi.serviceaggregate.codesearch.CodeSearchRequestHandler;
import com.xiaomi.serviceaggregate.codesearch.FileTypeAndExp;
import com.xiaomi.serviceaggregate.codesearch.SaveType;
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
public class CodeSearchTask2 {

    public static void main(String[] args) throws Exception {
        List<FileTypeAndExp> list = new ArrayList<>();
        String REGEX1 = "^\\s*cluster\\s*=\\s*c4\\s*$";
        String REGEX2 = "^\\s*cluster\\s*=\\s*\"c4\"\\s*$";
        String pattern = "application-c4.conf";
        String resultPath = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data\\result";
        Exp regCondition = or(new TextMatchRequirement(REGEX1, true), new TextMatchRequirement(REGEX2, true));
        list.add(new FileTypeAndExp(new TextMatchRequirement(pattern, false) , FileType.APPLICATION_AK_CONF));
        list.add(new FileTypeAndExp(regCondition , FileType.APPLICATION_AK_CONF));
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list, false, resultPath, SaveType.CSV);
        handler.search().get();
        handler.close();
    }
}
