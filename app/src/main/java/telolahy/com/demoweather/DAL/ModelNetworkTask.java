package telolahy.com.demoweather.DAL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import telolahy.com.demoweather.utils.JSONWeatherParser;

/**
 * Created by telolahy on 29/11/16.
 */

public class ModelNetworkTask extends AsyncTask<Void, Void, Boolean> {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private final Map<String, String> params;
    private ModelNetworkTaskListener listener;

    private Object model;
    private Exception error;

    public ModelNetworkTask(Map<String, String> params, ModelNetworkTaskListener listener) {
        super();
        this.params = params;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

//        ConnectivityManager connMgr = (ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo == null || !networkInfo.isConnected()) {
//            Toast.makeText(this, "Not connected to internet", Toast.LENGTH_LONG).show();
//        }

        try {
            this.model = this.downloadModel();
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            this.error = e;
            return Boolean.FALSE;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (null != this.listener) {
            if (aBoolean.booleanValue()) {
                this.listener.modelNetworkTaskDidSucceed(this.model);
            } else {
                this.listener.modelNetworkTaskDidFail(this.error);
            }
        }
    }

    private Object downloadModel() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        String url = BASE_URL + "?q=" + "London,UK" + "&APPID=" + "18c77339b0fcdff43a5bdd2e583ee950";

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        Log.i("ModelNetworkTask", jsonString);

        Object model = JSONWeatherParser.getWeather(jsonString);

        return model;
    }

    public interface ModelNetworkTaskListener {
        void modelNetworkTaskDidSucceed(Object model);

        void modelNetworkTaskDidFail(Exception error);
    }
}
