package com.xiaomi.xiaoai.codequality.baseanalysis.search.handler.file;

import com.xiaomi.xiaoai.codequality.baseanalysis.search.expression.base.Exp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfFileHandlerXiaoAiService extends FileHandlerXiaoAiService {
    private final List<String> confs = new ArrayList<>();

    public ConfFileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
        confs.add(".conf");
        confs.add(".yml");
        confs.add(".yaml");
        confs.add(".properties");
    }


    @Override
    public boolean filter(File file) {
        for (String extension : confs) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("endWith: ");
        for (String conf : confs) {
            sb.append(conf).append(" ");
        }
        return sb.toString();
    }
}