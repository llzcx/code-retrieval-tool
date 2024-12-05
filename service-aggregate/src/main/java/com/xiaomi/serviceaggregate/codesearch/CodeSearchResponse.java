package com.xiaomi.serviceaggregate.codesearch;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileHandleResult;
import lombok.Data;

import java.util.List;

@Data
public class CodeSearchResponse {
    private CodeSearchRequest request;
    private String serviceName;
    private List<FileHandleResult> fileHandleResults;
    private boolean isEmpty = true;

    public CodeSearchResponse(CodeSearchRequest request, List<FileHandleResult> fileHandleResults) {
        this.request = request;
        this.fileHandleResults = fileHandleResults;
        for (FileHandleResult fileHandleResult : fileHandleResults) {
            if(!fileHandleResult.getFileMatchResults().isEmpty()) {
                serviceName = fileHandleResult.getServiceName();
                isEmpty = false;
                break;
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(FileHandleResult fileHandleResult : fileHandleResults) sb.append(fileHandleResult);
        return sb.toString();
    }
}