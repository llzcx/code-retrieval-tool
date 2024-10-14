package com.xiaomi.xiaoai.codequality.basecode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
@Data
@AllArgsConstructor
public class DependentCoordinate {
    private String groupId;
    private String artifactId;
    private String version;
}
