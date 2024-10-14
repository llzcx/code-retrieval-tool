package com.xiaomi.xiaoai.codequality.baseanalysis.analyzer.impl;

import com.xiaomi.xiaoai.codequality.baseanalysis.analyzer.XiaoAiServiceAnalyzer;
import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;
import com.xiaomi.xiaoai.codequality.basecode.compile.SbtCompiler;

import java.nio.file.Path;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class XiaoAiServiceAnalyzerImpl implements XiaoAiServiceAnalyzer {

    private final SbtCompiler sbtCompiler;

    public XiaoAiServiceAnalyzerImpl(SbtCompiler sbtCompiler) {
        this.sbtCompiler = sbtCompiler;
    }
    @Override
    public XiaoAiService analyze(Path serviceDir) throws Exception {
        SbtCompiler.SbtCompileResult compileResult = (SbtCompiler.SbtCompileResult) sbtCompiler.compile(serviceDir.toString()).get();
        return new XiaoAiService(serviceDir, compileResult);
    }
}
