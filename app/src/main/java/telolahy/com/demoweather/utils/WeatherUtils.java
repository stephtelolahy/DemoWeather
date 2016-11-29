package telolahy.com.demoweather.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by telolahy on 29/11/16.
 */

public class WeatherUtils {

    public static String kelvinToCelsiusTemperature(double temperature) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(temperature - 273.15) + "°C";
    }

    public static String timestampToDate(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(timeStamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateString = sdf.format(calendar.getTime());
        return dateString;
    }
}
