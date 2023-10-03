package id.creatodidak.djaga_swara.plugin;

import android.content.Context;
import android.location.LocationManager;

public class GPSChecker {

    public static boolean isGPSActive(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }
}
