package id.creatodidak.djaga_swara.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageUtils {

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data) {
        // Mengatur opsi pembacaan bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // Mengubah ukuran gambar sesuai kebutuhan

        // Membaca byte array dan menghasilkan bitmap
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        // Membuat matrix rotasi
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        // Mengubah bitmap dengan matrix rotasi
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
