package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.service;

import com.xiaomi.xiaoai.codequality.basecode.pojo.DependentCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/11
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceHandlerResult {
    private String serviceName;
    private List<DependentCoordinate> dependencies;
}
