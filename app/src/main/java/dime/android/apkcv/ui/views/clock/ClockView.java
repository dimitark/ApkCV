package dime.android.apkcv.ui.views.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import dime.android.apkcv.ui.views.BaseView;
import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 30/08/15.
 */
public class ClockView extends BaseView {
    // The dimensions
    private int OUTLINE_STROKE_WIDTH;

    // The paints
    private Paint _pOutline;

    // The center of the clock
    private int x;
    private int y;
    // The radius of the clock
    private int radius;

    /**
     * Constructor
     *
     * @param context
     */
    public ClockView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void calculateDimensions() {
        // The center of the clock
        x = getPaddingLeft() + (widthP / 2);
        y = getPaddingTop() + (widthP / 2);
        // The radius
        radius = (widthP / 2) - OUTLINE_STROKE_WIDTH;
    }

    @Override
    protected void init(@Nullable AttributeSet attrs) {
        // This clock needs to be square
        forceSquare = true;

        // The dimensions
        OUTLINE_STROKE_WIDTH = ViewUtils.dipsToPixels(getContext(), 2f);

        // Init the paints
        _pOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pOutline.setColor(Color.BLACK);
        _pOutline.setStrokeWidth(OUTLINE_STROKE_WIDTH);
        _pOutline.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the outline of the clock
        canvas.drawCircle(x, y, radius, _pOutline);
    }
}
