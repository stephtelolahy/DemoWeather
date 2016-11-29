package telolahy.com.demoweather.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import telolahy.com.demoweather.utils.WeatherUtils;

/**
 * Created by telolahy on 29/11/16.
 * <p>
 * Parsed Model openweathermap
 * http://openweathermap.org/current
 * https://openweathermap.org/weather-conditions
 */

public class Weather {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    public String name;         // City name

    public double latitude;     // City geo location, longitude
    public double longitude;    // City geo location, latitude

    public String description;  // Weather condition within the group
    public String iconUrl;      // Weather icon Url

    public double temperature;  // Temperature. Unit Default: Kelvin

    public long sunrise;        // Sunrise time, unix, UTC
    public long sunset;         // Sunset time, unix, UTC

    public int humidity;        // Humidity %
    public double windSpeed;    // Wind speed. Unit Default: meter/sec

    public double tempMin;      // Minimum temperature at the moment.
    public double tempMax;      // Maximum temperature at the moment.

    // ===========================================================
    // Constructors
    // ===========================================================

    public Weather(JSONObject json) throws JSONException {

        JSONObject coordObj = json.getJSONObject("coord");
        if (null != coordObj) {
            latitude = coordObj.getDouble("lat");
            longitude = coordObj.getDouble("lon");
        }

        JSONArray weatherObjects = json.getJSONArray("weather");
        if (weatherObjects.length() >= 1) {
            JSONObject weatherObj = weatherObjects.getJSONObject(0);
            description = weatherObj.getString("description");
            iconUrl = "http://openweathermap.org/img/w/" + weatherObj.getString("icon") + ".png";
        }

        name = json.getString("name");

        JSONObject mainObj = json.getJSONObject("main");
        if (null != mainObj) {
            temperature = mainObj.getDouble("temp");
            humidity = mainObj.getInt("humidity");
            tempMin = mainObj.getDouble("temp_min");
            tempMax = mainObj.getDouble("temp_max");
        }

        JSONObject sysObject = json.getJSONObject("sys");
        if (null != sysObject) {
            sunrise = sysObject.getLong("sunrise");
            sunset = sysObject.getLong("sunset");
        }

        JSONObject windObject = json.getJSONObject("wind");
        if (null != windObject) {
            windSpeed = windObject.getDouble("speed");
        }
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    @Override
    public String toString() {
        String result = "";
        result += "description:" + description;
        result += "\ncity:" + name;
        result += "\nlatitude:" + latitude;
        result += "\nlongitude" + longitude;
        result += "\nicon:" + iconUrl;
        result += "\ntemperature:" + WeatherUtils.kelvinToCelsiusTemperature(this.temperature);
        result += "\nsunrise:" + WeatherUtils.timestampToDate(sunrise);
        result += "\nsunset:" + WeatherUtils.timestampToDate(sunset);
        result += "\nhumidity:" + humidity + "%";
        result += "\nwindSpeed:" + windSpeed + "mps";
        result += "\ntempMin:" + WeatherUtils.kelvinToCelsiusTemperature(this.tempMin);
        result += "\ntempMax:" + WeatherUtils.kelvinToCelsiusTemperature(this.tempMax);
        return result;
    }

    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    // ===========================================================
    // Public Methods
    // ===========================================================

    // ===========================================================
    // Private Methods
    // ===========================================================

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
