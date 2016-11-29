package telolahy.com.demoweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import telolahy.com.demoweather.R;
import telolahy.com.demoweather.model.Weather;
import telolahy.com.demoweather.utils.WeatherUtils;

/**
 * Created by stephano on 29/11/16.
 */

public class WeatherListAdapter extends BaseAdapter {


    private ArrayList<Weather> items;
    private Context context;

    public WeatherListAdapter(ArrayList<Weather> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) this.context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weather_item, null);
        }

        /**
         - température max - température min
         */

        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon_image_view);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description_text_view);
        TextView temperatureTextView = (TextView) view.findViewById(R.id.temperature_text_view);
        TextView sunriseTextView = (TextView) view.findViewById(R.id.sunrise_text_view);
        TextView sunsetTextView = (TextView) view.findViewById(R.id.sunset_text_view);
        TextView humidityTextView = (TextView) view.findViewById(R.id.humidity_text_view);
        TextView windTextView = (TextView) view.findViewById(R.id.wind_text_view);
        TextView minTemperatureTextView = (TextView) view.findViewById(R.id.temperature_min_text_view);
        TextView maxTemperatureTextView = (TextView) view.findViewById(R.id.temperature_max_text_view);

        Weather item = this.items.get(i);

        nameTextView.setText(item.name);
        descriptionTextView.setText(item.description);
        Picasso.with(this.context).load(item.iconUrl).into(iconImageView);
        temperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.temperature));
        sunriseTextView.setText(context.getString(R.string.sunrise) + " " + WeatherUtils.timestampToDate(item.sunrise));
        sunsetTextView.setText(context.getString(R.string.sunset) + " " + WeatherUtils.timestampToDate(item.sunset));
        humidityTextView.setText(item.humidity + "%");
        windTextView.setText(item.windSpeed + "mps");
        minTemperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.tempMin));
        maxTemperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.tempMax));

        return view;
    }
}
