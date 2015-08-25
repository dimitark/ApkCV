package dime.android.apkcv.ui.views.buble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Represents one image bubble in the Bubble View
 *
 * Created by dime on 25/08/15.
 */
public class ImageBubble {
    // The cropping paint
    private final Paint _pCrop = new Paint(Paint.ANTI_ALIAS_FLAG);

    // The bitmap inside the circle
    private Bitmap bitmap;
    private Rect bitmapRect = new Rect(0, 0, 0, 0);

    // The bitmap rect on the canvas
    private Rect canvasRect = new Rect(0, 0, 0, 0);

    // The dimensions of this bubble in the canvas
    private int x;
    private int y;
    private int radius;

    /**
     * Default empty constructor
     */
    public ImageBubble() {}

    /**
     * Default constructor
     *
     * @param bitmap
     */
    public ImageBubble(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Sets the dimensions of this bubble
     *
     * @param x
     * @param y
     * @param radius
     */
    public void setDimensions(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.canvasRect.set(x - radius, y - radius, x + radius, y + radius);
    }

    /**
     * Sets the bitmap of this image bubble
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        // First release the old memory
        if (this.bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        // Crop the image
        this.bitmap = cropBitmap(bitmap);
        this.bitmapRect.set(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    /**
     * The magical method
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        // Do nothing if the image is not set yet
        if (bitmap == null) {
            return;
        }

        // Draw the image
        canvas.drawBitmap(bitmap, bitmapRect, canvasRect, _pCrop);
    }

    /**
     * Crops the bitmap in circle
     *
     * @param original
     * @return
     */
    private Bitmap cropBitmap(Bitmap original) {
        Bitmap output = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, original.getWidth(), original.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.RED);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(original, rect, rect, paint);

        return output;
    }
}
