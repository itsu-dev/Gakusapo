package jp.mirm.pmmpstudio;

import android.os.Environment;

import java.io.File;

public class RuntimeData {

    public static final File configFile = new File(Environment.getExternalStorageDirectory().getPath() + "/PMMPStudio/Config.properties");

}
