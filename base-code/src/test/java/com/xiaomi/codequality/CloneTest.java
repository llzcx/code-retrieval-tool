package com.xiaomi.codequality;

import com.xiaomi.codequality.util.CommandUtil;
import com.xiaomi.xiaoai.codequality.basecode.pull.GitLabImpl;
import com.xiaomi.xiaoai.codequality.basecode.pull.ServiceBasePath;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class CloneTest {
    @Test
    public void test() {
        ServiceBasePath serviceBasePath = new GitLabImpl(new CommandUtil());
        System.out.println(serviceBasePath.getBasePath("git@git.n.xiaomi.com:ai-service/ai-open-platform.git"));
    }
}
