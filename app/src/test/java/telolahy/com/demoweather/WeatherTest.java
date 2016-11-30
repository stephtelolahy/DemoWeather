package telolahy.com.demoweather;

import org.json.JSONObject;
import org.junit.Test;

import telolahy.com.demoweather.model.Weather;

import static org.junit.Assert.assertEquals;

/**
 * Created by stephano on 30/11/16.
 */
public class WeatherTest {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    // ===========================================================
    // Public Methods
    // ===========================================================

    @Test
    public void testParsingWeatherFromJson() throws Exception {

        String jsonString = "{\"coord\":{\"lon\":2.23,\"lat\":48.83},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":274.32,\"pressure\":1035,\"humidity\":69,\"temp_min\":273.15,\"temp_max\":275.15},\"visibility\":10000,\"wind\":{\"speed\":1.97,\"deg\":92.0005},\"clouds\":{\"all\":0},\"dt\":1480500000,\"sys\":{\"type\":1,\"id\":5614,\"message\":0.0573,\"country\":\"FR\",\"sunrise\":1480490563,\"sunset\":1480521425},\"id\":6457370,\"name\":\"Arrondissement de Boulogne-Billancourt\",\"cod\":200}";
        JSONObject jsonObject = new JSONObject(jsonString);
        Weather weather = new Weather(jsonObject);
        assertEquals(weather.name, "Arrondissement de Boulogne-Billancourt");
        assertEquals(weather.description, "clear sky");
        assertEquals(weather.iconUrl, "http://openweathermap.org/img/w/01d.png");
        assertEquals(weather.temperature, 274.32, 0.0);
        assertEquals(weather.sunrise, 1480490563);
        assertEquals(weather.sunset, 1480521425);
        assertEquals(weather.humidity, 69);
        assertEquals(weather.windSpeed, 1.97, 0.0);
        assertEquals(weather.tempMin, 273.15, 0.0);
        assertEquals(weather.tempMax, 275.15, 0.0);
    }


    // ===========================================================
    // Private Methods
    // ===========================================================

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
