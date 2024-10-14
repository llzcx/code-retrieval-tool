import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.not;
import static com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp.or;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.dependency.DependencyExp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.service.ServiceHandlerResult;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.service.ServiceHandlerXiaoAiService;
import com.xiaomi.xiaoai.codequality.basecode.compile.SbtCompiler;
import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
public class SearchDepTest {

    public SbtCompiler.SbtCompileResult constructor(){
        DependentCoordinate dependentCoordinate1 = new DependentCoordinate("com.xiaomi","common-api","1.0");
        DependentCoordinate dependentCoordinate2 = new DependentCoordinate("com.xiaomi","common-service","1.0");
        Set<DependentCoordinate> set = new HashSet<>();
        set.add(dependentCoordinate1);
        set.add(dependentCoordinate2);
        return new SbtCompiler.SbtCompileResult(".", set);
    }
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        DependentCoordinate condition1 = new DependentCoordinate(null,"common-a","1.0");
        Exp exp = new DependencyExp(condition1);
        System.out.println(new ServiceHandlerXiaoAiService(exp).handle(new XiaoAiService(Paths.get("../"), constructor())).get());
    }

    @Test
    public void testNot() throws ExecutionException, InterruptedException {
        DependentCoordinate condition1 = new DependentCoordinate(null,"common-a",null);
        Exp exp = not(new DependencyExp(condition1));
        System.out.println(new ServiceHandlerXiaoAiService(exp).handle(new XiaoAiService(Paths.get("../"), constructor())).get());
    }
}
