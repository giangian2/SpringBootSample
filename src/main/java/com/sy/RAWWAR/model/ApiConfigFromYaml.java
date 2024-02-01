package com.sy.RAWWAR.model;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ApiConfigFromYaml implements ApiConfig {
    private static String YAML_PATH = "auth.yml";

    private String apiKey;

    public ApiConfigFromYaml() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(ApiConfigFromYaml.YAML_PATH);
        Map<String, String> data = new Yaml().load(inputStream);
        this.apiKey = data.get("api-key");

    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

}
