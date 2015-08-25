package dime.android.apkcv.ui.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dime on 25/08/15.
 */
public abstract class BaseView extends View {
    // The sizing properties
    protected int desiredWidth;
    protected int desiredHeight;
    //
    // The width of the widget - not counting in the padding.
    // The variable is set in the onSizeChanged() method.
    //
    protected int width;
    //
    // The height of the widget - not counting in the padding.
    // The variable is set in the onSizeChanged() method.
    //
    protected int height;

    //
    // The width of the widget - padding counted in.
    // The variable is set in the onSizeChanged() method.
    //
    protected int widthP;

    //
    // The height of the widget - padding counted in.
    // The variable is set in the onSizeChanged() method.
    //
    protected int heightP;

    /**
     * This methods needs to calculate the dimensions for it's children
     */
    protected abstract void calculateDimensions();

    /**
     * Initializes everything
     */
    protected abstract void init(@Nullable AttributeSet attrs);

    /**
     * Default constructor
     *
     * @param context
     */
    public BaseView(Context context) {
        super(context);
        init(null);
    }

    /**
     * Default XML constructor
     *
     * @param context
     * @param attrs
     */
    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        /* Measure the width */
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        /* Measure the height */
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        /* Mandatory call */
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /* Store the class variables */
        this.width = w;
        this.height = h;
        this.widthP = width - getPaddingLeft() - getPaddingRight();
        this.heightP = height - getPaddingTop() - getPaddingBottom();
        /* Calculate all the needed dimensions for all the elements */
        calculateDimensions();
    }

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
    protected void adjustTextSizeToWidthAndHeight(String text, Paint textPaint, int width, int height) {
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
    private void adjustTextScale(String text, Paint textPaint, int width) {
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
    protected void adjustTextSizeForHeight(String text, Paint textPaint, int height) {
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
    protected void adjustTextSizeForWidth(String text, Paint textPaint, int width) {
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
}
