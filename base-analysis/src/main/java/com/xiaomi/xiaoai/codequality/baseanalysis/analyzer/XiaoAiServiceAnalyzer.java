package com.xiaomi.xiaoai.codequality.baseanalysis.analyzer;

import com.xiaomi.xiaoai.codequality.baseanalysis.pojo.XiaoAiService;

import java.nio.file.Path;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public interface XiaoAiServiceAnalyzer {
	XiaoAiService analyze(Path serviceDir) throws Exception;
}
