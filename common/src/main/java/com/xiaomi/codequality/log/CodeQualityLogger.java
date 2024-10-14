package com.xiaomi.codequality.log;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/8
 */
public interface CodeQualityLogger {
    Logger LOGGER = LoggerFactory.getLogger("com.xiaomi.xiaoai.CodeQuality");
    static void configureLogging() {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            ContextInitializer ci = new ContextInitializer(context);
            ci.configureByResource(new URL("./logback.xml"));

            // 假设 logback.xml 中的 appender 名称为 "fileAppender"
            // 更新文件名
            String fileName = "./data/log/app-" + getCurrentTimestamp() + ".log";
            context.reset();
            context.putProperty("logFileName", fileName);

            ci.autoConfig();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }
}
