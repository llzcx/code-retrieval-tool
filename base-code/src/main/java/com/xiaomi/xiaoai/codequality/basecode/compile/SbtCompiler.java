package com.xiaomi.xiaoai.codequality.basecode.compile;
import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;
import com.xiaomi.codequality.util.CommandUtil;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class SbtCompiler extends Compiler {

    public static final String ENV = "-Xmx2g -Dsbt.ivy.home=D:/env/.ivy2 -Dsbt.repository.config=D:/env/.sbt/repositories -Dsbt.override.build.repos=true";
    public static final String SHOW_FULL_CLASSPATH = "sbt 'show fullClasspath'";
    public SbtCompiler(CommandUtil commandUtil) {
        super(commandUtil);
    }

    @Override
    public CompletableFuture<CompileResult> compile(String projectPath) {
        CompletableFuture<CompileResult> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            HashMap<String, String> env = new HashMap<>();
            env.put("SBT_OPTS", ENV);
            String result = commandUtil.runCmd(SHOW_FULL_CLASSPATH, env, Paths.get(projectPath));
            completableFuture.complete(new SbtCompileResult(result, SbtParser.parse(result)));
        }, compilerExecutor);

        return completableFuture;
    }

    public static class SbtCompileResult extends CompileResult {
        public SbtCompileResult(String output, Set<DependentCoordinate> dependentCoordinates) {
            super(output, dependentCoordinates);
        }
    }

    private static class SbtParser {
        public static Set<DependentCoordinate> parse(String result) {
            Set<DependentCoordinate> set = new HashSet<>();
            String regex = "public\\\\([\\w\\\\]+)\\\\([\\w-]+)\\\\([\\d\\.]+)\\\\\\2-\\3\\.jar";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                String groupId = matcher.group(1).replace("\\", ".");
                String artifactId = matcher.group(2);
                String version = matcher.group(3);
                DependentCoordinate dependentCoordinate = new DependentCoordinate(groupId, artifactId, version);
                set.add(dependentCoordinate);
            }
            return set;
        }
    }
}
