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

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private ArrayList<Weather> items;
    private Context context;

    // ===========================================================
    // Constructors
    // ===========================================================

    public WeatherListAdapter(ArrayList<Weather> items, Context context) {
        this.items = items;
        this.context = context;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

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

        WeatherViewHolder holder = null;
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) this.context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weather_item, null);
            holder = new WeatherViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (WeatherViewHolder) view.getTag();
        }


        Weather item = this.items.get(i);

        holder.nameTextView.setText(item.name);
        holder.descriptionTextView.setText(item.description);
        Picasso.with(this.context).load(item.iconUrl).into(holder.iconImageView);
        holder.temperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.temperature));
        holder.sunriseTextView.setText(context.getString(R.string.sunrise) + " " + WeatherUtils.timestampToDate(item.sunrise));
        holder.sunsetTextView.setText(context.getString(R.string.sunset) + " " + WeatherUtils.timestampToDate(item.sunset));
        holder.humidityTextView.setText(item.humidity + "%");
        holder.windTextView.setText(item.windSpeed + "mps");
        holder.minTemperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.tempMin));
        holder.maxTemperatureTextView.setText(WeatherUtils.kelvinToCelsiusTemperature(item.tempMax));

        return view;
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

    static class WeatherViewHolder {

        TextView nameTextView;
        ImageView iconImageView;
        TextView descriptionTextView;
        TextView temperatureTextView;
        TextView sunriseTextView;
        TextView sunsetTextView;
        TextView humidityTextView;
        TextView windTextView;
        TextView minTemperatureTextView;
        TextView maxTemperatureTextView;

        public WeatherViewHolder(View view) {
            nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            iconImageView = (ImageView) view.findViewById(R.id.icon_image_view);
            descriptionTextView = (TextView) view.findViewById(R.id.description_text_view);
            temperatureTextView = (TextView) view.findViewById(R.id.temperature_text_view);
            sunriseTextView = (TextView) view.findViewById(R.id.sunrise_text_view);
            sunsetTextView = (TextView) view.findViewById(R.id.sunset_text_view);
            humidityTextView = (TextView) view.findViewById(R.id.humidity_text_view);
            windTextView = (TextView) view.findViewById(R.id.wind_text_view);
            minTemperatureTextView = (TextView) view.findViewById(R.id.temperature_min_text_view);
            maxTemperatureTextView = (TextView) view.findViewById(R.id.temperature_max_text_view);
        }
    }

}
