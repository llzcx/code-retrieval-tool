import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.not;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.or;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.ScalaHeaderRequirement;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text.TextMatchRequirement;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandlerXiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandlerFactory;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandleResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public class SearchFileTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Exp exp = or(new TextMatchRequirement("RedisConfig()",false), new ScalaHeaderRequirement("com.xiaomi.ai.redis.RedisConfig"));
        FileHandlerXiaoAiService fileHandler = FileHandlerFactory.create(FileType.SCALA, exp);
        String servicePath = "..\\data\\ai-open-platform\\Migrating\\ai-open-skill-store";
        XiaoAiService xiaoAiService = new XiaoAiService(Paths.get(servicePath), null);
        FileHandleResult fileHandleResult = fileHandler.handle(xiaoAiService).get();
        System.out.println(fileHandleResult);
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Exp exp = not(new TextMatchRequirement("cluster_01",false));
        FileHandlerXiaoAiService fileHandler = FileHandlerFactory.create(FileType.SCALA, exp);
        String servicePath = "..\\data\\ai-open-platform\\Migrating\\ai-open-skill-store";
        XiaoAiService xiaoAiService = new XiaoAiService(Paths.get(servicePath), null);
        FileHandleResult fileHandleResult = fileHandler.handle(xiaoAiService).get();
        System.out.println(fileHandleResult);
        assert fileHandleResult.getFileMatchResults().size() > 0;
    }
}
