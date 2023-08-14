package id.creatodidak.djaga_swara;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import id.creatodidak.djaga_swara.Helper.NotificationHelper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationHelper.showNotification(this, Objects.requireNonNull(remoteMessage.getNotification()).getTitle(), remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Implement your logic to send the token to your server
    }

    private void scheduleJob() {
        // Implement your logic for scheduling a task (if needed)
    }

    private void handleNow() {
        // Implement your logic for handling the message immediately (if needed)
    }
}