package telolahy.com.demoweather.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import telolahy.com.demoweather.DAL.ModelNetworkTask;
import telolahy.com.demoweather.DAL.ServiceAtlas;
import telolahy.com.demoweather.R;
import telolahy.com.demoweather.adapter.WeatherListAdapter;
import telolahy.com.demoweather.model.Weather;

public class MainActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int REQUEST_LOCATION_CODE = 1;

    // ===========================================================
    // Fields
    // ===========================================================

    private ListView listView;
    private TextView infoTextView;

    private LocationManager locationManager;
    private final String provider = LocationManager.GPS_PROVIDER;

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

        // Get the location manager
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        // Check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            return;
        }

        // permission has been granted, continue as usual
        Location location = locationManager.getLastKnownLocation(provider);
        requestWeatherAtLocation(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to

                // permission has been granted, continue as usual
                try {
                    Location location = locationManager.getLastKnownLocation(provider);
                    requestWeatherAtLocation(location);

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private void requestWeatherAtLocation(Location location) {

        if (null == location) {
            this.infoTextView.setText(getString(R.string.location_unavailable_message));
            this.infoTextView.setVisibility(View.VISIBLE);
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", location.getLatitude() + "");
        params.put("lon", location.getLongitude() + "");
        params.put("APPID", "18c77339b0fcdff43a5bdd2e583ee950");

        ModelNetworkTask getWeatherTask = new ModelNetworkTask(ServiceAtlas.ServiceType.ServiceWeather, params, new ModelNetworkTask.ModelNetworkTaskListener() {
            @Override
            public void modelNetworkTaskDidSucceed(Object model) {

                Weather weather = (Weather) model;

                Log.i("", weather.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(weather.name);
                builder.setMessage(weather.toString());
                builder.setPositiveButton("OK", null);
                builder.create().show();

                ArrayList<Weather> weathers = new ArrayList<>();
                weathers.add(weather);
                WeatherListAdapter adapter = new WeatherListAdapter(weathers, MainActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void modelNetworkTaskDidFail(Exception error) {
                infoTextView.setText(error.getLocalizedMessage());
                infoTextView.setVisibility(View.VISIBLE);
            }
        });
        getWeatherTask.execute();
    }

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
