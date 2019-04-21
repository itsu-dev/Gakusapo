package jp.mirm.pmmpstudio.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import jp.mirm.pmmpstudio.model.list.ProjectListCell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectGetter {

    public static List<ProjectListCell> getProjects() {
        List<ProjectListCell> result = new ArrayList<>();

        File projectDir = new File(Config.getString("project_root"));
        if (!projectDir.exists()) projectDir.mkdirs();

        for (File file : projectDir.listFiles()) {
            File config = new File(file.getPath() + "/project.properties");
            if (!file.isDirectory() || !config.exists()) continue;

            Map<String, String> data = ProjectLoader.getProjectSoft(config);
            if (data != null) result.add(new ProjectListCell(Integer.parseInt(data.get("id")), data.get("name"), data.get("date")));
        }

        return result;
    }

    public static List<String> getProjectNames() {
        List<String> result = new ArrayList<>();
        File projectDir = new File(Config.getString("project_root"));

        for (File file : projectDir.listFiles()) {
            File config = new File(file.getPath() + "project.properties");
            if (!file.isDirectory() || !config.exists()) continue;
            result.add(file.getName());
        }

        return result;
    }

}
