package gakusapo.android.itsu.api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WeatherService {

    public Map<String, Object> getWeather(double latitude, double longitude) {
        URL url;

        try {
            url = new URL(String.format("https://weathernews.jp/onebox/%s/%s/temp=c", latitude, longitude));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Language", "ja,en-US;q=0.9,en;q=0.8");
            connection.setDoOutput(false);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String temp;

            while ((temp = reader.readLine()) != null) {
                buffer.append(temp + "\n");
            }

            reader.close();

            Document document = Jsoup.parse(buffer.toString());

            Map<String, Object> result = new HashMap<>();

            //city data
            Map<String, Object> city = new HashMap<>();
            city.put("latitude", latitude);
            city.put("longitude", longitude);
            city.put("name", document.select(".title_now").text().trim().replaceAll("付近の天気", ""));
            city.put("url", connection.getURL().toString());
            result.put("city", city);

            //latest weather data
            Map<String, Object> latest = new HashMap<>();
            String[] timeAndWeather = document.select(".sub").text().trim().split(",");
            latest.put("time", timeAndWeather[0].trim());
            latest.put("name", timeAndWeather[1].trim());
            latest.put("icon", getIcon(String.valueOf(latest.get("name"))));
            latest.put("temp", Double.parseDouble(document.select(".obs_temp_main").text().trim()));
            latest.put("temp_unit", document.select(".obs_temp_sub").text().trim());

            Elements elements = document.select(".table-obs_sub tbody tr");
            for (Element element : elements) {
                String title = element.select(".obs_sub_title").text().trim();
                String value = element.select(".obs_sub_value").text().trim();
                switch (title) {
                    case "湿度": {
                        latest.put("humidity", Integer.parseInt(value.replaceAll("[^0-9]", "")));
                        break;
                    }
                    case "気圧": {
                        latest.put("pressure", Integer.parseInt(value.replaceAll("[^0-9]", "")));
                        break;
                    }
                    case "風": {
                        latest.put("wind", value.substring(2));
                        break;
                    }
                    case "日の出": {
                        latest.put("sunrise", value.substring(1).trim());
                        break;
                    }
                    case "日の入": {
                        latest.put("sunset", value.substring(1).trim());
                    }
                }
            }

            result.put("latest", latest);

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private String getIcon(String text) {
        switch (text) {
            case "晴れ": return "https://weathernews.jp/s/topics/img/wxicon/100.png";
            case "猛暑": return "https://weathernews.jp/s/topics/img/wxicon/550.png";
            case "晴れ時々くもり": return "https://weathernews.jp/s/topics/img/wxicon/101.png";
            case "晴れ一時雨": return "https://weathernews.jp/s/topics/img/wxicon/102.png";
            case "晴れ一時雪": return "https://weathernews.jp/s/topics/img/wxicon/104.png";
            case "晴れのち時々くもり": return "https://weathernews.jp/s/topics/img/wxicon/110.png";
            case "晴れのち一時雨": return "https://weathernews.jp/s/topics/img/wxicon/112.png";
            case "晴れのち一時雪": return "https://weathernews.jp/s/topics/img/wxicon/115.png";
            case "くもり": return "https://weathernews.jp/s/topics/img/wxicon/200.png";
            case "くもり時々晴れ": return "https://weathernews.jp/s/topics/img/wxicon/201.png";
            case "くもり一時雨": return "https://weathernews.jp/s/topics/img/wxicon/202.png";
            case "くもり一時雪": return "https://weathernews.jp/s/topics/img/wxicon/204.png";
            case "くもりのち時々晴れ": return "https://weathernews.jp/s/topics/img/wxicon/210.png";
            case "くもりのち一時雨": return "https://weathernews.jp/s/topics/img/wxicon/212.png";
            case "くもりのち一時雪": return "https://weathernews.jp/s/topics/img/wxicon/215.png";
            case "小雨": return "https://weathernews.jp/s/topics/img/wxicon/650.png";
            case "雨": return "https://weathernews.jp/s/topics/img/wxicon/300.png";
            case "大雨・嵐": return "https://weathernews.jp/s/topics/img/wxicon/850.png";
            case "雨時々晴れ": return "https://weathernews.jp/s/topics/img/wxicon/301.png";
            case "雨時々止む": return "https://weathernews.jp/s/topics/img/wxicon/302.png";
            case "雨時々雪": return "https://weathernews.jp/s/topics/img/wxicon/303.png";
            case "雨のち晴れ": return "https://weathernews.jp/s/topics/img/wxicon/311.png";
            case "雨のちくもり": return "https://weathernews.jp/s/topics/img/wxicon/313.png";
            case "雨のち時々雪": return "https://weathernews.jp/s/topics/img/wxicon/314.png";
            case "みぞれ": return "https://weathernews.jp/s/topics/img/wxicon/430.png";
            case "雪": return "https://weathernews.jp/s/topics/img/wxicon/400.png";
            case "大雪・吹雪": return "https://weathernews.jp/s/topics/img/wxicon/950.png";
            case "雪時々晴れ": return "https://weathernews.jp/s/topics/img/wxicon/401.png";
            case "雪時々止む": return "https://weathernews.jp/s/topics/img/wxicon/402.png";
            case "雪時々雨": return "https://weathernews.jp/s/topics/img/wxicon/403.png";
            case "雪のち晴れ": return "https://weathernews.jp/s/topics/img/wxicon/411.png";
            case "雪のちくもり": return "https://weathernews.jp/s/topics/img/wxicon/413.png";
            case "雪のち雨": return "https://weathernews.jp/s/topics/img/wxicon/414.png";
            default: return null;
        }
    }
}
