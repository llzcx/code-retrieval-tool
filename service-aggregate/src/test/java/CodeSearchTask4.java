import com.xiaomi.serviceaggregate.codesearch.CodeSearchRequestHandler;
import com.xiaomi.serviceaggregate.codesearch.FileTypeAndExp;
import com.xiaomi.serviceaggregate.codesearch.SaveType;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchRequirement;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/10
 */
public class CodeSearchTask4 {
    public static void main(String[] args) throws Exception {
        String resultPath = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data\\result";
        List<FileTypeAndExp> list = new ArrayList<>();
        list.add(new FileTypeAndExp(new TextMatchRequirement("c4.report.gsched.srv", false) , FileType.APPLICATION_AK_CONF));
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list, false, resultPath, SaveType.CSV);
        handler.search().get();
        handler.close();
    }
}
