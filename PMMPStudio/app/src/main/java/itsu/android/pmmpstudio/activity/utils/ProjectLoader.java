package jp.mirm.pmmpstudio.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectLoader {

    public static Map<String, String> getProjectSoft(final @NonNull File file) {
        Map<String, String> result = new HashMap<>();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            return null;
        }

        result.put("id", properties.getProperty("id"));
        result.put("name", properties.getProperty("name"));
        result.put("date", properties.getProperty("date"));

        return result;
    }

    public static Map<String, String> getProjectHard(final @NonNull File file) {
        Map<String, String> result = new HashMap<>();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            return null;
        }

        result.put("id", properties.getProperty("id"));
        result.put("name", properties.getProperty("name"));
        result.put("date", properties.getProperty("date"));
        result.put("root", properties.getProperty("root"));
        result.put("mode", properties.getProperty("mode"));
        result.put("config", properties.getProperty("config"));
        result.put("mainDir", properties.getProperty("mainDir"));
        result.put("mainClass", properties.getProperty("mainClass"));
        result.put("resourceDir", properties.getProperty("resourceDir"));
        result.put("pluginConfig", properties.getProperty("pluginConfig"));

        return result;
    }
}
