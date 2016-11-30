package telolahy.com.demoweather.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import telolahy.com.demoweather.DAL.ServiceAtlas;
import telolahy.com.demoweather.DAL.ServiceTask;
import telolahy.com.demoweather.R;
import telolahy.com.demoweather.adapter.WeatherListAdapter;
import telolahy.com.demoweather.manager.UserLocationManager;
import telolahy.com.demoweather.model.Weather;

public class MainActivity extends AppCompatActivity implements UserLocationManager.UserLocationManagerListener, ServiceTask.ServiceTaskListener {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private ListView listView;
    private TextView infoTextView;
    private ProgressBar progressBar;

    private UserLocationManager locationManager;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.list_view);
        this.infoTextView = (TextView) findViewById(R.id.info_text_view);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        this.locationManager = new UserLocationManager(this, this);
        this.locationManager.requestLocation();
    }

    protected void onStart() {
        this.locationManager.handleActivityStart();
        super.onStart();
    }

    protected void onStop() {
        this.locationManager.handleActivityStop();
        super.onStop();
    }


    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    @Override
    public void userLocationManagerDidReceiveLocation(Location location) {

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", location.getLatitude() + "");
        params.put("lon", location.getLongitude() + "");
        params.put("APPID", "18c77339b0fcdff43a5bdd2e583ee950");
        ServiceTask getWeatherTask = new ServiceTask(ServiceAtlas.ServiceType.ServiceWeather, params, this);
        getWeatherTask.execute();
    }

    @Override
    public void userLocationManagerDidFail() {
        infoTextView.setText(getString(R.string.location_unavailable_message));
    }

    @Override
    public void serviceTaskDidStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void serviceTaskDidSucceed(Object model) {
        progressBar.setVisibility(View.GONE);

        Weather weather = (Weather) model;
        weather.name = getString(R.string.current_location);

        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(weather);
        WeatherListAdapter adapter = new WeatherListAdapter(weathers, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void serviceTaskDidFail(Exception error) {
        progressBar.setVisibility(View.GONE);

        infoTextView.setText(error.getLocalizedMessage());
        infoTextView.setVisibility(View.VISIBLE);
    }

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
