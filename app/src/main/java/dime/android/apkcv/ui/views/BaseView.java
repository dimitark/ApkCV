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

    //
    // If set to true, the height will be the same
    // as the calculated width IF the height is set to Wrap Content
    //
    protected boolean forceSquare;

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

        if (forceSquare) {
            desiredHeight = width;
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
}
