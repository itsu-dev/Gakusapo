package jp.mirm.pmmpstudio.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import jp.mirm.pmmpstudio.RuntimeData;
import jp.mirm.pmmpstudio.project.Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectBuilder {

    public static Project createProject(final @NonNull Activity activity, final @NonNull String name, final @NonNull String author, int mode) {
        final long id = Long.parseLong(Config.getString("id"));
        Config.set("id", String.valueOf(id + 1));

        String mainDirName = author + "/" + name + "/";

        File root = new File(Config.getString("project_root") + "/" + name + "/");
        File config = new File(Config.getString("project_root") + "/" + name + "/project.properties");
        File mainDir = new File(root.getPath() + "/src/" + author + "/" + name + "/");
        File resourceDir = new File(root.getPath() + "/resources/");
        File mainClass = new File(mainDir.getPath() + "/Main.php");
        File pluginConfig = new File(root.getPath() + "/plugin.yml");

        root.mkdirs();
        mainDir.mkdirs();
        resourceDir.mkdirs();

        Properties properties = new Properties();
        properties.put("id", String.valueOf(id));
        properties.put("name", name);
        properties.put("author", author);
        properties.put("date", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        properties.put("mode", String.valueOf(mode));
        properties.put("root", root.getPath());
        properties.put("config", config.getPath());
        properties.put("mainDir", mainDir.getPath());
        properties.put("mainClass", mainClass.getPath());
        properties.put("resourceDir", resourceDir.getPath());
        properties.put("pluginConfig", pluginConfig.getPath());

        try {
            FileOutputStream out;
            properties.store(out = new FileOutputStream(config), "");
            out.close();

            String classTemp =
                            "<?php\n\n" +
                            "namespace " + mainDirName.substring(0, mainDirName.length() - 1).replaceAll("/", "\\\\") + ";" +
                            "\n\nuse pocketmine\\plugin\\PluginBase;\n\n" +
                            "class Main extends PluginBase {\n" +
                            "\n}";

            Map<String, String> data = new HashMap<>();
            data.put("project.namespace", mainDirName.substring(0, mainDirName.length() - 1).replaceAll("/", "\\\\"));
            data.put("project.mainclass", "Main");

            IOUtils.writeFile(mainClass, IOUtils.decodeTemplate(IOUtils.readFile(activity.getResources().getAssets().open("php.html")), data));

            String configTemp =
                            "name: " + name + "\n" +
                            "main: " + mainDirName.replaceAll("/", "\\\\") + "Main\n" +
                            "author: " + author + "\n" +
                            "api: \n" +
                            "version: 1.0.0\n" +
                            "description: " + name;

            IOUtils.writeFile(pluginConfig, configTemp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Project(name);
    }

}
