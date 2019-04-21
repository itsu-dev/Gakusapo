package jp.mirm.pmmpstudio.utils;

import android.app.Activity;
import android.os.Environment;
import jp.mirm.pmmpstudio.RuntimeData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Config {

    private static Properties properties;

    public static void init(Activity activity) {
        properties = new Properties();
        try {
            if (!RuntimeData.configFile.exists()) {
                FileOutputStream out;
                properties.put("project_root", Environment.getExternalStorageDirectory().getPath() + "/PMMPStudio/projects/");
                properties.put("id", "0");
                properties.store(out = new FileOutputStream(RuntimeData.configFile), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                out.close();
            } else {
                properties.load(new FileInputStream(RuntimeData.configFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, String value) {
        try {
            FileOutputStream out;
            properties.setProperty(key, value);
            properties.store(out = new FileOutputStream(RuntimeData.configFile), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
