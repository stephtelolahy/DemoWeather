package telolahy.com.demoweather.DAL;

import android.os.AsyncTask;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by telolahy on 29/11/16.
 * Model fetching task based on a service type defined in ServiceAtlas
 */

public class ServiceTask extends AsyncTask<Void, Void, Boolean> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private final ServiceAtlas.ServiceType serviceType;
    private final Map<String, String> params;
    private ServiceTaskListener listener;
    private Object model;
    private Exception error;

    // ===========================================================
    // Constructors
    // ===========================================================

    public ServiceTask(ServiceAtlas.ServiceType serviceType, Map<String, String> params, ServiceTaskListener listener) {
        super();
        this.serviceType = serviceType;
        this.params = params;
        this.listener = listener;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (null != this.listener) {
            this.listener.serviceTaskDidStart();
        }
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
                this.listener.serviceTaskDidSucceed(this.model);
            } else {
                this.listener.serviceTaskDidFail(this.error);
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

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

    public interface ServiceTaskListener {

        void serviceTaskDidStart();

        void serviceTaskDidSucceed(Object model);

        void serviceTaskDidFail(Exception error);
    }
}
