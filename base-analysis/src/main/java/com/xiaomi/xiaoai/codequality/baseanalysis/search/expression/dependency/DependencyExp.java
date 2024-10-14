package com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.dependency;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.ExpContext;
import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/10
 */
public class DependencyExp extends Exp<Set<DependentCoordinate>,DependentCoordinate> {

    private final DependentCoordinate condition;
    public DependencyExp(DependentCoordinate condition) {
        super();
        this.condition = condition;
    }
    @Override
    public boolean eva(ExpContext<DependentCoordinate> context, Set<DependentCoordinate> content) {
        List<DependentCoordinate> list = new ArrayList<>();
        for (DependentCoordinate item : content) {
            if(condition.getArtifactId() != null && item.getArtifactId().contains(condition.getArtifactId())) {
                list.add(item);
            }else if(condition.getGroupId() != null && item.getGroupId().contains(condition.getGroupId())) {
                list.add(item);
            }else if(condition.getVersion() != null && item.getVersion().contains(condition.getVersion())) {
                list.add(item);
            }
        }
        if(!list.isEmpty()) {
            context.getMatchEntities().addAll(list);
        }
        return !context.getMatchEntities().isEmpty();
    }

}
