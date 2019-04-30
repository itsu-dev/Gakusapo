package gakusapo.android.itsu.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class TrainInfoUtils {

    public static List<Map<String, Object>> getData(String json) {
        Gson gson = new Gson();
        List<Map<String, Object>> data = gson.fromJson(json, new TypeToken<List>(){}.getType());
        return data;
    }

}
