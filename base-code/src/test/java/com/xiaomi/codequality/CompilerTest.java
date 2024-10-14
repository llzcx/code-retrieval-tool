package com.xiaomi.codequality;

import cn.hutool.json.JSONUtil;
import com.xiaomi.codequality.util.CommandUtil;
import com.xiaomi.xiaoai.codequality.basecode.compile.Compiler;
import com.xiaomi.xiaoai.codequality.basecode.compile.SbtCompiler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class CompilerTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {
        CommandUtil commandUtil = new CommandUtil();
        Compiler compiler = new SbtCompiler(commandUtil);
        String proj = "D:\\project\\dev-project\\xiaoai-code-quality-information-platform\\data\\ai-open-platform\\Migrating\\ai-open-service";
        Compiler.CompileResult compileResult = compiler.compile(proj).get();
        System.out.println(JSONUtil.toJsonPrettyStr(new ArrayList<>(compileResult.getDependentCoordinates())));

    }
}
