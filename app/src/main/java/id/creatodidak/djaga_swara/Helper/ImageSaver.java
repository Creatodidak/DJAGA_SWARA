package id.creatodidak.djaga_swara.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageSaver {

    private static final String IMAGE_DIRECTORY = "/CameraWatermark";
    private static final String IMAGE_FORMAT = ".jpg";

    private final Context context;
    private OnImageSavedListener listener;

    public ImageSaver(Context context) {
        this.context = context;
    }

    public void setOnImageSavedListener(OnImageSavedListener listener) {
        this.listener = listener;
    }

    public void saveImage(Bitmap bitmap) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageName = "IMG_" + timeStamp + IMAGE_FORMAT;

        File storageDir = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File imageFile = new File(storageDir, imageName);
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.flush();
        outputStream.close();

        // Refresh the gallery to display the saved image
        MediaScannerConnection.scanFile(context, new String[]{imageFile.getPath()}, null, null);

        if (listener != null) {
            listener.onImageSaved(bitmap);
        }
    }

    public interface OnImageSavedListener {
        void onImageSaved(Bitmap bitmap);
    }
}
