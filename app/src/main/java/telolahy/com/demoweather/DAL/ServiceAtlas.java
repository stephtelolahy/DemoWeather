package telolahy.com.demoweather.DAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import telolahy.com.demoweather.model.Weather;

/**
 * Created by stephano on 29/11/16.
 */

public class ServiceAtlas {

    public enum ServiceType {
        ServiceWeather
    }

    public static String urlForService(ServiceType serviceType, Map<String, String> params) throws UnsupportedEncodingException {
        String url = null;
        switch (serviceType) {
            case ServiceWeather:
                url = "http://api.openweathermap.org/data/2.5/weather";
                break;

            default:
                throw new IllegalStateException("Unsupported service " + serviceType);
        }

        // append parameters
        boolean firstEntry = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (firstEntry) {
                firstEntry = false;
                url += "?";
            } else {
                url += "&";
            }
            url += key + "=" + URLEncoder.encode(value, "UTF-8");
        }

        return url;
    }

    public static Object parseModelForService(ServiceType serviceType, String jsonString) throws JSONException {

        // Create JSONObject
        JSONObject jsonObject = new JSONObject(jsonString);

        switch (serviceType) {
            case ServiceWeather:
                return new Weather(jsonObject);

        }

        return null;
    }
}
