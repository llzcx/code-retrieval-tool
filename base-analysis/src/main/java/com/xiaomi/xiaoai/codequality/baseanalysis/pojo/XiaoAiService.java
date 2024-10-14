package com.xiaomi.xiaoai.codequality.baseanalysis.pojo;
import com.xiaomi.xiaoai.codequality.basecode.compile.SbtCompiler;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
@Data
@AllArgsConstructor
public class XiaoAiService {
    private String name;
    private Path dir;
    private SbtCompiler.SbtCompileResult sbtCompileResult;

    public XiaoAiService(Path dir, SbtCompiler.SbtCompileResult sbtCompileResult) {
        this.dir = dir;
        this.name = dir.getFileName().toString();
        this.sbtCompileResult = sbtCompileResult;
    }
}
