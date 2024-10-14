package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.dependency;

import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */

@AllArgsConstructor
@Data
public class DependencyMatchEntity {
    /**
     * 找到的依赖
     */
    private List<DependentCoordinate> dependentCoordinates;
}
