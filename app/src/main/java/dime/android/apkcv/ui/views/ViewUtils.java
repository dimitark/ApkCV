package dime.android.apkcv.ui.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by dime on 25/08/15.
 */
public class ViewUtils {
    /**
     * Adjusts the font size of the given paint for the given string,
     * to match the given width AND height.
     *
     * The original ratio of the font is NOT saved! The text is stretched horizontally to fill the whole width.
     *
     * @param text
     * @param textPaint
     * @param width
     * @param height
     */
    public static void adjustTextSizeToWidthAndHeight(String text, Paint textPaint, int width, int height) {
        adjustTextSizeForHeight(text, textPaint, height);
        adjustTextScale(text, textPaint, width);
    }

    /**
     * Adjusts the text scale X for the given parameters. Called from adjustTextSize().
     *
     * @param text
     * @param textPaint
     * @param width
     */
    public static void adjustTextScale(String text, Paint textPaint, int width) {
        // do calculation with scale of 1.0 (no scale)
        textPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();
        // ask the paint for the bounding rect if it were to draw this text.
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        // determine the width
        int w = bounds.right - bounds.left;

        // determine how much to scale the width to fit the view
        float xscale = ((float) width) / w;

        textPaint.setTextScaleX(xscale);
    }

    /**
     * Adjusts the text size for the given parameters. Called from adjustTextSize().
     *
     * @param text
     * @param textPaint
     * @param height
     */
    public static void adjustTextSizeForHeight(String text, Paint textPaint, int height) {
        textPaint.setTextSize(100);
        textPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();
        // ask the paint for the bounding rect if it were to draw this text
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        // get the height that would have been produced
        int h = bounds.bottom - bounds.top;

        // make the text text up 100% of the height
        float target = (float) height * 1.0f;

        // figure out what textSize setting would create that height of text
        float size  = ((target / h) * 100f);

        // and set it into the paint
        textPaint.setTextSize(size);
    }

    /**
     * Adjusts the text size for the given parameters. Called from adjustTextSize().
     *
     * @param text
     * @param textPaint
     * @param width
     */
    public static void adjustTextSizeForWidth(String text, Paint textPaint, int width) {
        textPaint.setTextSize(100);
        textPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();
        // ask the paint for the bounding rect if it were to draw this text
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        // get the height that would have been produced
        int w = bounds.right - bounds.left;

        // make the text text up 100% of the height
        float target = (float) width * 1.0f;

        // figure out what textSize setting would create that height of text
        float size  = ((target / w) * 100f);

        // and set it into the paint
        textPaint.setTextSize(size);
    }

    /**
     * Dips to Pixels
     *
     * @param context
     * @param dips
     * @return
     */
    public static int dipsToPixels(Context context, float dips) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dips * scale);
    }

    /**
     * Pixels to Dips
     *
     * @param context
     * @param pixels
     * @return
     */
    public static float pixelsToDips(Context context, int pixels) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pixels / scale;
    }
}
