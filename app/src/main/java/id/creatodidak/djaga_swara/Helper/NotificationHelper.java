package id.creatodidak.djaga_swara.Helper;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
    private static final int NOTIFICATION_ID = 1;

    public static void showNotification(Context context, String title, String message) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logosmall)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        // Set custom notification layout
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notifikasi);
        notificationLayout.setTextViewText(R.id.notifJudul, title);
        notificationLayout.setTextViewText(R.id.notifText, message);
        notificationLayout.setImageViewResource(R.id.notifImg, R.drawable.logosmall);

        builder.setContent(notificationLayout);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Handle permission not granted
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void showNotificationWithButton(final Context context, String title, String message) {
        createNotificationChannel(context);

        // Check if permission to show notification is granted
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission not granted
            return;
        }

        // Create intent to handle button action
        Intent buttonIntent = new Intent(context, NotificationButtonReceiver.class);
        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(context, 0, buttonIntent, PendingIntent.FLAG_IMMUTABLE); // Set FLAG_IMMUTABLE

        // Create notification with button
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logosmall)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .addAction(R.drawable.logosmall, "AKAN SAYA LAPORKAN", buttonPendingIntent);

        // Set custom notification layout
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notifikasi);
        notificationLayout.setTextViewText(R.id.notifJudul, title);
        notificationLayout.setTextViewText(R.id.notifText, message);
        notificationLayout.setImageViewResource(R.id.notifImg, R.drawable.logosmall);

        builder.setContent(notificationLayout);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static class NotificationButtonReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Stop repeated notification
            stopRepeatedNotification(context);

            // Handle button action here
        }
    }

    public static void stopRepeatedNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
