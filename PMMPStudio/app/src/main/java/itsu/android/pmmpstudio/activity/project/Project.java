package jp.mirm.pmmpstudio.project;

import jp.mirm.pmmpstudio.utils.Config;
import jp.mirm.pmmpstudio.utils.ProjectLoader;

import java.io.File;
import java.util.Map;

public class Project {

    public static final int MODE_BEGINNER = 0;
    public static final int MODE_PROFESSIONAL = 1;

    private Map<String, String> data;
    private int mode;

    public Project(String name) {
        data = ProjectLoader.getProjectHard(new File(Config.getString("project_root") + "/" + name + "/project.properties"));
        mode = Integer.parseInt(data.get("mode"));
    }

    public String getData(String key) {
        return data.get(key);
    }

}
