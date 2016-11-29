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

        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon_image_view);

        Weather weather = this.items.get(i);
        nameTextView.setText(weather.name);
        Picasso.with(this.context)
                .load(weather.iconUrl)
                .into(iconImageView);


        return view;
    }
}
