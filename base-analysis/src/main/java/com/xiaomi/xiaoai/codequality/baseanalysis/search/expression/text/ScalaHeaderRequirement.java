package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;

import java.util.List;

/**
 * 判断scala文件是否导入了目标类 com.xiaomi.ai.redis.RedisConfig
 * 前提要求 1.是符合小爱开发规范的scala代码 2.能够通过编译
 * 能覆盖以下情况:
 * import com.xiaomi.ai.redis.RedisConfig
 * import com.xiaomi.ai.redis._
 * import com.xiaomi.ai.redis.{RedisConfig, RedisKeyPrefix}
 * import com.xiaomi.ai.redis.RedisConfig as MyRedisConfig
 *
 * import com.xiaomi.ai.redis.{RedisConfig => RC, _}
 * import com.xiaomi.ai.redis.RedisConfig._
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public class ScalaHeaderRequirement extends TextExp {

    private String header;

    public ScalaHeaderRequirement(String header) {
        this.header = header;
    }

    @Override
    public boolean eva(ExpContext<TextMatchEntity> context, String content) {
        String importPrefix = "import " + header.substring(0, header.lastIndexOf(".") + 1);
        String className = header.substring(header.lastIndexOf(".") + 1);
        String[] lines = content.split("\\R");
        List<TextMatchEntity> matches = context.getMatchEntities();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith(importPrefix)) {
                String suffix = line.substring(importPrefix.length()).trim();
                if (suffix.equals("_") || suffix.contains(className)) {
                    TextMatchEntity entity = new TextMatchEntity();
                    entity.setLineNumber(i + 1);
                    entity.setMatch(lines[i]);
                    matches.add(entity);
                }
            }
        }

        if (!matches.isEmpty()) {
            context.setMatchEntities(matches);
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("ScalaHeader[%s]", header);
    }
}
