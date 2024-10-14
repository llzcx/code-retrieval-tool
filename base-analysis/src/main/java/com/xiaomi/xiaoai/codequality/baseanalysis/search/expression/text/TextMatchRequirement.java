package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.text;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断多行字符串的每个行是否匹配某个模式串，可以传入正则模式串，也可以传入包含的文本串
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMatchRequirement extends TextExp {

    private String pattern;
    private boolean isRegex;

    public TextMatchRequirement(String pattern, boolean isRegex) {
        this.pattern = pattern;
        this.isRegex = isRegex;
    }

    @Override
    public boolean eva(ExpContext<TextMatchEntity> context, String content) {
        int cnt = 0;
        if (isRegex) {
            Pattern compiledPattern = Pattern.compile(pattern);
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i++) {
                Matcher matcher = compiledPattern.matcher(lines[i]);
                if (matcher.find()) {
                    cnt += 1;
                    TextMatchEntity entity = new TextMatchEntity();
                    entity.setLineNumber(i + 1);
                    entity.setMatch(matcher.group());
                    context.getMatchEntities().add(entity);
                }
            }
        } else {
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String str = lines[i];
                if (str.contains(pattern)) {
                    cnt += 1;
                    TextMatchEntity entity = new TextMatchEntity();
                    entity.setLineNumber(i + 1);
                    entity.setMatch(str);
                    context.getMatchEntities().add(entity);
                }
            }
        }
        return cnt != 0;
    }

    @Override
    public String toString() {
        if (isRegex) {
            return String.format("TextRegex[%s]", pattern);
        } else {
            return String.format("TextContain[%s]", pattern);
        }
    }
}