package com.xiaomi.serviceaggregate.load;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;

/**
 * @Description
 * @Author Chen Xiang
 * @Date 2024/10/9
 */
public class YamlConfigLoader {

    private static final String RESOURCE_PATH = "./config.yml";


    @Data
    public static class YamlConfig {
        Service service;
    }

    @Data
    public static class Service {
        String token;
        String gitlab;
        String repository;
        String group;
        List<String> repositoryWhiteList;
    }

    private static volatile YamlConfig yamlConfig;

    public static YamlConfig getConfig() {
        if (yamlConfig == null) {
            synchronized (YamlConfigLoader.class) {
                if (yamlConfig == null) {
                    yamlConfig = read(RESOURCE_PATH);
                }
            }
        }
        return yamlConfig;
    }

    private static YamlConfig read(String resourcePath) {
        ClassLoader classLoader = YamlConfigLoader.class.getClassLoader();
        try {
            InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
            if (inputStream != null) {
                Yaml yaml = new Yaml(new Constructor(YamlConfig.class));
                return yaml.load(inputStream);
            } else {
                throw new RuntimeException("Resource not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Resource not found.");
        }
    }
}
