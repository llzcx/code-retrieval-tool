package com.xiaomi.codequality;


import com.xiaomi.codequality.util.CommandUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.io.IOException;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class CommandUtilTest {
    private static final CommandUtil commandUtil = new CommandUtil();

    @Test
    public  void test() {
        String result = commandUtil.runCmd("ping www.baidu.com");
        System.out.println(result);
        assertTrue(result!=null && !StringUtils.isBlank(result));
    }

    @Test
    public void testLs() {
        if(SystemUtils.IS_OS_WINDOWS) {
            System.out.println(commandUtil.runCmd("cd"));
        }else{
            System.out.println(commandUtil.runCmd("ls"));
        }
    }

    @AfterAll
    public static void clear() {
        try {
            commandUtil.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
