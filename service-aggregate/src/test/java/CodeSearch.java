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
public class CodeSearch {

    public static void main(String[] args) throws Exception {
        List<FileTypeAndExp> list = new ArrayList<>();
        String path = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data";
        Exp confCondition
                = or(new TextMatchRequirement("^(?!.*cluster_01_proxy)(?=.*cluster_01(?:_with_password)?).*", true), new TextMatchRequirement("RedisConfig()", false));
        //conf
        list.add(new FileTypeAndExp(confCondition, FileType.PROGRAMMING_LANGUAGE));
        list.add(new FileTypeAndExp(confCondition, FileType.CONF));
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list, false, path, SaveType.CSV);
        handler.search().get();
        handler.close();
    }
}
