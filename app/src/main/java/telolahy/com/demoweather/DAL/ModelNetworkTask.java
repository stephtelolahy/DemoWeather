package telolahy.com.demoweather.DAL;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by telolahy on 29/11/16.
 */

public class ModelNetworkTask extends AsyncTask<Void, Void, Boolean> {

    private final ServiceAtlas.ServiceType serviceType;
    private final Map<String, String> params;

    private ModelNetworkTaskListener listener;

    private Object model;
    private Exception error;

    public ModelNetworkTask(ServiceAtlas.ServiceType serviceType, Map<String, String> params, ModelNetworkTaskListener listener) {
        super();
        this.serviceType = serviceType;
        this.params = params;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            String url = ServiceAtlas.urlForService(this.serviceType, this.params);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();

            Log.i("ModelNetworkTask", url);
            Log.i("ModelNetworkTask", jsonString);

            this.model = ServiceAtlas.parseModelForService(this.serviceType, jsonString);
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (null != this.listener) {
            this.listener.modelNetworkTaskDidStart();
        }
    }

    public interface ModelNetworkTaskListener {

        void modelNetworkTaskDidStart();

        void modelNetworkTaskDidSucceed(Object model);

        void modelNetworkTaskDidFail(Exception error);
    }
}
