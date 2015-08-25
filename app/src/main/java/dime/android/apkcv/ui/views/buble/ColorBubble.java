package dime.android.apkcv.ui.views.buble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 25/08/15.
 */
public class ColorBubble {
    // The pains
    private Paint _pBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint _pText = new Paint(Paint.ANTI_ALIAS_FLAG);

    // The dimensions of this bubble in the canvas
    private int x;
    private int y;
    private int radius;
    private int textY;

    // The text
    private String text;

    /**
     * Default constructor
     *
     * @param bgColor
     */
    public ColorBubble(int bgColor, String text) {
        // Save the text
        this.text = text;

        // Init the paints
        _pBg.setColor(bgColor);
        _pBg.setStyle(Paint.Style.FILL);

        _pText.setColor(Color.WHITE);
        _pText.setTypeface(Typeface.MONOSPACE);
        _pText.setStyle(Paint.Style.FILL);
        _pText.setTextAlign(Paint.Align.CENTER);
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
        ViewUtils.adjustTextSizeForWidth(text, _pText, radius / 2);
        this.textY = this.y - (int)((_pText.descent() + _pText.ascent()) / 2);
    }

    /**
     * The magical method
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        // Draw the bg
        canvas.drawCircle(x, y, radius, _pBg);
        // Draw the text
        canvas.drawText(text, x, textY, _pText);
    }
}
