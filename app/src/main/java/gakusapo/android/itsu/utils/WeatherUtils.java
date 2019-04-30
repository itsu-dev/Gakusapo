package gakusapo.android.itsu.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gakusapo.android.itsu.R;
import gakusapo.android.itsu.MainApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherUtils {
    public static Bitmap getImage(String weather) {
        Bitmap result;
        if (weather.startsWith("猛暑") || weather.startsWith("晴れ")) result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.clear);
        else if (weather.startsWith("くもり")) result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.cloudy);
        else if (weather.startsWith("大雨")) result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.thunder);
        else if (weather.startsWith("小雨") || weather.startsWith("雨")) result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.rainy);
        else if (weather.startsWith("みぞれ") || weather.startsWith("大雪") || weather.startsWith("雪")) result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.snowy);
        else result = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), R.drawable.mist);
        return result;
    }
}
