package telolahy.com.demoweather.manager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by stephano on 30/11/16.
 */
public class UserLocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Context context;
    private UserLocationManagerListener listener;
    private GoogleApiClient googleApiClient;

    // ===========================================================
    // Constructors
    // ===========================================================

    public UserLocationManager(UserLocationManagerListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            this.listener.userLocationManagerDidReceiveLocation(lastLocation);
        } else {
            this.listener.userLocationManagerDidFail();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.listener.userLocationManagerDidFail();
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    public void requestLocation() {

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void handleActivityStart() {
        googleApiClient.connect();
    }

    public void handleActivityStop() {
        googleApiClient.disconnect();
    }


    // ===========================================================
    // Private Methods
    // ===========================================================

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

    public interface UserLocationManagerListener {

        void userLocationManagerDidReceiveLocation(Location location);

        void userLocationManagerDidFail();
    }
}
