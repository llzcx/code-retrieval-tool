package com.xiaomi.xiaoai.codequality.basecode.pull;

import com.xiaomi.codequality.constant.Const;
import com.xiaomi.codequality.log.CodeQualityLogger;
import com.xiaomi.codequality.util.CommandUtil;
import com.xiaomi.codequality.util.PrintUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/8
 */
public class GitLabImpl extends ServiceBasePath implements CodeQualityLogger {
    CommandUtil commandUtil = new CommandUtil();

    public GitLabImpl(CommandUtil commandUtil) {
        super(commandUtil);
    }

    @Override
    public String getBasePath(String URL) {
        Path absolutePath = Const.BASE_REPOSITORIES.toAbsolutePath();
        String repoName = PrintUtil.extractRepoName(URL);
        Path path = Paths.get(absolutePath.toString(), repoName);
        String cmd = "git clone " + URL;
        if(!path.toFile().exists()){
            String res = commandUtil.runCmd(cmd, null, absolutePath);
            LOGGER.info("clone {} success\n{}", repoName, res);
        }
        return path.toString();
    }

}
