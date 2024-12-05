package com.xiaomi.serviceaggregate.codesearch;

import com.xiaomi.codequality.util.FileUtil;
import static com.xiaomi.codequality.util.PrintUtil.extractRepoName;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/12/5
 */
public class NorResponseSaver extends AbstractResponseSaver{
    public NorResponseSaver(String dir) {
        super(dir);
    }

    @Override
    public void addItem(CodeSearchResponse response) {
        FileUtil.writeTextToFile(dir, extractRepoName(response.getServiceName()) ,response.toString());
    }

    @Override
    public void close() {
    }
}
