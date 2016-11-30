package telolahy.com.demoweather;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import telolahy.com.demoweather.model.Weather;
import telolahy.com.demoweather.utils.WeatherUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by stephano on 30/11/16.
 */
public class WeatherUtilsTest {

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
    public void testKelvinToCelsius() throws Exception {

        assertEquals("7°C", WeatherUtils.kelvinToCelsiusTemperature(280.15));
        assertEquals("-5°C", WeatherUtils.kelvinToCelsiusTemperature(268.15));
    }

    @Test
    public void testTimestampToDate() throws  Exception {

        assertEquals("11:27", WeatherUtils.timestampToDate(1480501624));
        assertEquals("11:28", WeatherUtils.timestampToDate(1480501685));
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
