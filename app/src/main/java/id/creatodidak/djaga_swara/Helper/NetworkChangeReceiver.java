package id.creatodidak.djaga_swara.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import id.creatodidak.djaga_swara.Dashboard.Sprin;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Log.d(TAG, "Internet connected");
            // Panggil metode fetchFromServer() dari activity Sprin
            Sprin activity = (Sprin) context;
            activity.fetchFromServer();
        } else {
            Log.d(TAG, "Internet disconnected");
        }
    }
}
