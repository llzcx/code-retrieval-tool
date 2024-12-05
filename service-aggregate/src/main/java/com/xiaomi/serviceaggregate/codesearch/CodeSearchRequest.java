package com.xiaomi.serviceaggregate.codesearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class CodeSearchRequest {
        private String basePath;
        private Set<String> filePathWhiteList;
        private List<FileTypeAndExp> fileTypeAndExpList;
    }