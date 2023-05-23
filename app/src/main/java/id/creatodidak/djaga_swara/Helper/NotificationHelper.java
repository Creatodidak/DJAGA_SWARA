package id.creatodidak.djaga_swara.Helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.creatodidak.djaga_swara.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "channel_name";

    @SuppressLint("MissingPermission")
    public static void showNotification(Context context, String title, String message) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logosmall)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        // Mengatur tampilan kustom notifikasi
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notifikasi);
        notificationLayout.setTextViewText(R.id.notifJudul, title);
        notificationLayout.setTextViewText(R.id.notifText, message);
        notificationLayout.setImageViewResource(R.id.notifImg, R.drawable.logosmall);

        builder.setContent(notificationLayout);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}