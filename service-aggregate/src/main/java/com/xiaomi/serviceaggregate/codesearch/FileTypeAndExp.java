package com.xiaomi.serviceaggregate.codesearch;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;
import com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileTypeAndExp {
    private Exp exp;
    private FileType fileTypes;
}
