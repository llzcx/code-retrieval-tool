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
public class CodeSearchTask3 {

    public static void main(String[] args) throws Exception {
        String resultPath = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data\\result";

        List<FileTypeAndExp> list = new ArrayList<>();
        String[] methods = new String[]{
                "getCustomSettingsV2",
                "getValidInstsWithDetailV2",
                "getValidInstsV2",
                "getAppAndIntentSupportedSkills",
                "getAppSupportedProviders",
                "batchGetAppSetting",
                "getAppSkillsById",
                "getAllAppSkillsByClientId",
        };
        for (String method : methods) {
            list.add(new FileTypeAndExp(new TextMatchRequirement(method, false) , FileType.PROGRAMMING_LANGUAGE));
            list.add(new FileTypeAndExp(new TextMatchRequirement(method, false) , FileType.CONF));
        }
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list, false, resultPath, SaveType.CSV);
        handler.search().get();
        handler.close();
    }
}
