package id.creatodidak.djaga_swara.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

public class BorderTransformation implements Transformation<Bitmap> {
    private final int borderWidth;
    private final int borderColor;

    public BorderTransformation(int borderWidth, int borderColor) {
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(
            @NonNull Context context,
            @NonNull Resource<Bitmap> resource,
            int outWidth,
            int outHeight
    ) {
        Bitmap source = resource.get();
        int width = source.getWidth();
        int height = source.getHeight();

        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        Bitmap bitmap = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        canvas.drawBitmap(source, 0, 0, null);
        canvas.drawRect(0, 0, width, height, borderPaint);

        return BitmapResource.obtain(bitmap, bitmapPool);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        // No-op
    }
}