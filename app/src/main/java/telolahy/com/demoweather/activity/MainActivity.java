package telolahy.com.demoweather.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import telolahy.com.demoweather.DAL.ModelNetworkTask;
import telolahy.com.demoweather.R;
import telolahy.com.demoweather.model.Weather;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ModelNetworkTask getWeatherTask = new ModelNetworkTask(null, new ModelNetworkTask.ModelNetworkTaskListener() {
            @Override
            public void modelNetworkTaskDidSucceed(Object model) {
                Weather weather = (Weather) model;
                Log.i("", weather.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(weather.name);
                builder.setMessage(weather.toString());
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }

            @Override
            public void modelNetworkTaskDidFail(Exception error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        getWeatherTask.execute();

    }
}
