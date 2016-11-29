package telolahy.com.demoweather.activity;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;

import telolahy.com.demoweather.DAL.ModelNetworkTask;
import telolahy.com.demoweather.DAL.ServiceAtlas;
import telolahy.com.demoweather.R;
import telolahy.com.demoweather.adapter.WeatherListAdapter;
import telolahy.com.demoweather.model.Weather;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private ListView listView;
    private TextView infoTextView;
    private ProgressBar progressBar;

    private GoogleApiClient googleApiClient;

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

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        this.listView = (ListView) findViewById(R.id.list_view);
        this.infoTextView = (TextView) findViewById(R.id.info_text_view);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            requestWeatherAtLocation(lastLocation);
        } else {
            infoTextView.setText(getString(R.string.location_unavailable_message));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    // ===========================================================
    // Private Methods
    // ===========================================================

    private void requestWeatherAtLocation(Location location) {

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", location.getLatitude() + "");
        params.put("lon", location.getLongitude() + "");
        params.put("APPID", "18c77339b0fcdff43a5bdd2e583ee950");
        ModelNetworkTask getWeatherTask = new ModelNetworkTask(ServiceAtlas.ServiceType.ServiceWeather, params, new ModelNetworkTask.ModelNetworkTaskListener() {

            @Override
            public void modelNetworkTaskDidStart() {

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void modelNetworkTaskDidSucceed(Object model) {

                progressBar.setVisibility(View.GONE);

                Weather weather = (Weather) model;

                ArrayList<Weather> weathers = new ArrayList<>();
                weathers.add(weather);
                WeatherListAdapter adapter = new WeatherListAdapter(weathers, MainActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void modelNetworkTaskDidFail(Exception error) {

                progressBar.setVisibility(View.GONE);

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
