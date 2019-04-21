package gakusapo.android.itsu.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gakusapo.andoid.itsu.R;
import gakusapo.android.itsu.MainApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherFormatter {

    public static long KtoC(double kelvin) {
        return Math.round(kelvin - 273.15);
    }

    public static Map<String, Long> getTemps(String json) {
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(json, new TypeToken<Map>(){}.getType());
        Map<String, Double> main = (Map<String, Double>) data.get("main");
        Map<String, Long> result = new HashMap<>();
        result.put("temp", KtoC(main.get("temp")));
        result.put("temp_max", KtoC(main.get("temp_max")));
        result.put("temp_min", KtoC(main.get("temp_min")));

        return result;
    }

    public static Map<String, Object> getWeather(String json) {
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(json, new TypeToken<Map>(){}.getType());
        return ((List<Map<String, Object>>) data.get("weather")).get(0);
    }

    public static String getCity(String json) {
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(json, new TypeToken<Map>(){}.getType());
        Map<String, Object> cityData = ((List<Map<String, Object>>) ((Map<String, Object>) data.get("response")).get("location")).get(0);
        return cityData.get("prefecture") + " " + cityData.get("city") + " " + cityData.get("town");
    }

    public static Bitmap getImage(String weather) {
        Bitmap result;
        switch (weather.toLowerCase()) {
            case "clear": {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.clear);
                break;
            }

            case "clouds": {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.cloudy);
                break;
            }

            case "drizzle":
            case "rain": {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.rainy);
                break;
            }

            case "snow": {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.snowy);
                break;
            }

            case "thunderstorm": {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.thunder);
                break;
            }

            default: {
                result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.mist);
            }
        }
        return result;
    }
}
