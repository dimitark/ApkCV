package dime.android.apkcv.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import dime.android.apkcv.R;

/**
 * Created by dime on 25/08/15.
 */
public class BubbleView extends BaseView {
    // The paints
    private Paint _pBackground;

    /**
     * Default constructor
     *
     * @param context
     */
    public BubbleView(Context context) {
        super(context);
    }

    /**
     * Default XML constructor
     *
     * @param context
     * @param attrs
     */
    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Initializes everything
     *
     * @param attrs
     */
    @Override
    protected void init(@Nullable AttributeSet attrs) {
        // Read the attributes from the XML layout
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);

        //
        // Init the paints
        //
        // The background paint
        _pBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pBackground.setColor(a.getColor(R.styleable.BubbleView_background_color, Color.TRANSPARENT));
        _pBackground.setStyle(Paint.Style.FILL);
    }

    /**
     * Calculates the dimensions of all child elements
     *
     */
    @Override
    protected void calculateDimensions() {
        // TODO
    }

    /**
     * This is where the magic happens
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), width - getPaddingLeft(), height - getPaddingRight(), _pBackground);
    }
}
