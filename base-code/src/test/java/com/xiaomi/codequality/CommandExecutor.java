package com.xiaomi.codequality;

import cn.hutool.core.io.IoUtil;

import java.io.File;
import java.io.IOException;

public class CommandExecutor {
    public static void main(String[] args) {
        String env = "-Xmx2g -Dsbt.ivy.home=D:/env/.ivy2 -Dsbt.repository.config=D:/env/.sbt/repositories -Dsbt.override.build.repos=true";
        String dir = "D:/project/dev-project/xiaoai-code-quality-information-platform/data/ai-open-platform/Migrating/ai-open-service";
        String sbtCmd = "sbt 'show fullClasspath'";
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("powershell", "-Command", sbtCmd);
            pb.directory(new File(dir));
            pb.environment().put("SBT_OPTS", env);

            Process process = pb.start();
            System.out.println("getInputStream:");
            System.out.println(IoUtil.read(process.getInputStream(), "GBK"));
            System.out.println("getErrorStream:");
            System.out.println(IoUtil.read(process.getErrorStream(), "GBK"));

            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}