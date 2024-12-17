import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.xiaomi.serviceaggregate.codesearch.CodeSearchRequestHandler;
import com.xiaomi.serviceaggregate.codesearch.FileTypeAndExp;
import com.xiaomi.serviceaggregate.codesearch.SaveType;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchRequirement;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/17
 */
public class CodeSearchTask6 {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\cache\\log\\需要调整的接口.xlsx";
        String resultPath = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data\\result";

        ExcelReader reader = ExcelUtil.getReader(filePath);

        List<List<Object>> allRows = reader.read(1); // 1 表示从第二行开始读取（跳过标题行），如果不需要跳过则可以传0

        List<Object> firstColumn = allRows.stream()
                .map(row -> row.size() > 0 ? row.get(0) : null)
                .collect(Collectors.toList());

        List<String> firstColumnAsString = firstColumn.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toList());

        reader.close();
        List<FileTypeAndExp> list = new ArrayList<>();
        for (String str : firstColumnAsString) {
            System.out.println("target: " + str);
            list.add(new FileTypeAndExp(new TextMatchRequirement(str, false) , FileType.PROGRAMMING_LANGUAGE));
        }

        System.out.println("list size is:" + list.size());
        CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list, false, resultPath, SaveType.CSV);
        handler.search().get();
        handler.close();
    }
}
