package dime.android.apkcv.ui.views.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;

import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.BaseView;
import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 27/08/15.
 */
public class ChartView extends BaseView implements ValueAnimator.AnimatorUpdateListener {
    // The desired height of a single cell
    private final int DESIRED_CELL_HEIGHT = ViewUtils.dipsToPixels(getContext(), 23f);
    private final int TIMELINE_TEXT_HEIGHT = ViewUtils.dipsToPixels(getContext(), 20f);
    private final int TIMELINE_HIGH_BAR_HEIGHT = ViewUtils.dipsToPixels(getContext(), 10f);
    private final int TIMELINE_LOW_BAR_HEIGHT = ViewUtils.dipsToPixels(getContext(), 5f);
    private final float TIMELINE_STROKE_WIDTH = 5f;
    private final float BARS_STROKE_WIDTH = 5f;
    private float BARS_TEXT_SIZE_IN_SP;
    private float TIMELINE_TEXT_SIZE_IN_SP;

    // Should we animate the into?
    private boolean animateIntro;
    private ValueAnimator introAnimation;

    // The width of a single unit
    private int unitWidth;

    // The bars' locations
    private int barsLocationLeft;
    private int[] barsLocationY;
    private int[] barsLocationRight;
    private int[] barsLocationRightAnimated;
    private int barCircleRadius;
    private int[] barTextY;

    // The timeline's Y location
    private int timelineY;
    private int timelineMaxX;
    private int timelineMod; // Which bars should be HIGH
    private int timelineTextY;
    private int timelineTitleX;

    // The data adapter
    private ChartDataAdapter adapter;

    // The paints
    private Paint _pBars;
    private Paint _pBarsText;
    private Paint _pTimelineMain;
    private Paint _pTimelineText;

    /**
     * Default constructor
     *
     * @param context
     */
    public ChartView(Context context) {
        super(context);
    }

    /**
     * Default XML constructor
     *
     * @param context
     * @param attrs
     */
    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void calculateDimensions() {
        // Nothing to do, if we don't have any data
        if (adapter == null || adapter.isEmpty()) return;

        // The width of the title space
        int titleSpace = (int) (0.2 * widthP);

        // What is the width of one unit?
        unitWidth = (widthP - titleSpace - 5) / adapter.getMaxValue();

        // The timeline's location
        timelineY = getPaddingTop() + TIMELINE_TEXT_HEIGHT + TIMELINE_HIGH_BAR_HEIGHT / 2;
        timelineMaxX = getPaddingLeft() + (adapter.getMaxValue() * unitWidth);
        timelineMod = adapter.getMaxValue() / 3;
        timelineTextY = timelineY - (int)(TIMELINE_HIGH_BAR_HEIGHT / 1.5) + (int)((_pTimelineText.descent() + _pTimelineText.ascent()) / 2);
        timelineTitleX = getPaddingLeft() + widthP - (titleSpace / 2);

        // Calculate the locations of the bars
        barsLocationLeft = -10;
        barsLocationY = new int[adapter.getItemsCount()];
        barsLocationRight = new int[adapter.getItemsCount()];
        barsLocationRightAnimated = new int[adapter.getItemsCount()];
        barTextY = new int[adapter.getItemsCount()];
        // The radius of the circle at the end of the bars
        barCircleRadius = DESIRED_CELL_HEIGHT / 8;

        // Loop through all of the items in the adapter, and calculate their specific dimensions
        for (int i=0; i<adapter.getItemsCount(); i++) {
            barsLocationY[i] = i * DESIRED_CELL_HEIGHT + timelineY + (int)(1.5 * DESIRED_CELL_HEIGHT);
            barsLocationRight[i] = getPaddingLeft() + (unitWidth * adapter.getItemAtPosition(i).getValue());
            barsLocationRightAnimated[i] = animateIntro ? 0 : barsLocationRight[i];
            barTextY[i] = barsLocationY[i] - (int)((_pBarsText.descent() + _pBarsText.ascent()) / 2);
        }

        // Should we animate?
        if (animateIntro) {
            introAnimation.start();
        }
    }

    @Override
    protected void init(@Nullable AttributeSet attrs) {
        // Read the attributes from the XML layout
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChartView);

        // Read the colors
        int barsColor = a.getColor(R.styleable.ChartView_chart_bars_color, Color.WHITE);
        int timelineColor = a.getColor(R.styleable.ChartView_chart_timeline_color, Color.GRAY);
        animateIntro = a.getBoolean(R.styleable.ChartView_chart_animate_intro, true);

        // Recycle the typed array
        a.recycle();

        // Set the text sizes
        TIMELINE_TEXT_SIZE_IN_SP = ViewUtils.spToPixels(getContext(), 10f);
        BARS_TEXT_SIZE_IN_SP = ViewUtils.spToPixels(getContext(), 10f);

        // Init the animation (if needed)
        if (animateIntro) {
            introAnimation = ValueAnimator.ofFloat(0f, 1f);
            introAnimation.setDuration(1500);
            introAnimation.setInterpolator(new BounceInterpolator());
            introAnimation.addUpdateListener(this);
        }

        // Init the paints
        _pBars = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pBars.setColor(barsColor);
        _pBars.setStyle(Paint.Style.FILL_AND_STROKE);
        _pBars.setStrokeWidth(BARS_STROKE_WIDTH);
        _pBars.setStrokeJoin(Paint.Join.ROUND);
        _pBars.setStrokeCap(Paint.Cap.ROUND);

        _pBarsText = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pBarsText.setStyle(Paint.Style.FILL);
        _pBarsText.setTypeface(Typeface.DEFAULT);
        _pBarsText.setTextAlign(Paint.Align.LEFT);
        _pBarsText.setTextSize(BARS_TEXT_SIZE_IN_SP);
        _pBarsText.setColor(barsColor);

        _pTimelineMain = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pTimelineMain.setColor(timelineColor);
        _pTimelineMain.setStyle(Paint.Style.FILL_AND_STROKE);
        _pTimelineMain.setStrokeWidth(TIMELINE_STROKE_WIDTH);
        _pTimelineMain.setStrokeJoin(Paint.Join.ROUND);
        _pTimelineMain.setStrokeCap(Paint.Cap.ROUND);

        _pTimelineText = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pTimelineText.setColor(timelineColor);
        _pTimelineText.setStyle(Paint.Style.FILL);
        _pTimelineText.setTypeface(Typeface.DEFAULT);
        _pTimelineText.setTextAlign(Paint.Align.CENTER);
        _pTimelineText.setTextSize(TIMELINE_TEXT_SIZE_IN_SP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Nothing to draw, if we don't have any data
        if (adapter == null || adapter.isEmpty()) return;

        // Draw the timeline
        canvas.drawLine(getPaddingLeft(), timelineY, timelineMaxX, timelineY, _pTimelineMain);
        canvas.drawText(adapter.getXAxisTitle(), timelineTitleX, timelineTextY, _pTimelineText);
        for (int i = adapter.getMaxValue(); i > 0; i--) {
            // Calculate the X of the bar
            float x = getPaddingLeft() + i*unitWidth;

            int barHalfHeight;
            if (((adapter.getMaxValue() - i) % timelineMod) == 0) {
                // Draw the text
                canvas.drawText(i + "", x, timelineTextY, _pTimelineText);
                canvas.drawCircle(x, timelineY, barCircleRadius, _pTimelineMain);
            } else {
                // It's a LOW bar
                barHalfHeight = TIMELINE_LOW_BAR_HEIGHT / 2;
                // Draw the bar
                canvas.drawLine(x, timelineY - barHalfHeight, x, timelineY + barHalfHeight, _pTimelineMain);
            }
        }

        // Draw the bars
        for (int i=0; i<adapter.getItemsCount(); i++) {
            canvas.drawLine(barsLocationLeft, barsLocationY[i], barsLocationRightAnimated[i], barsLocationY[i], _pBars);
//            canvas.drawRect(barsLocationRightAnimated[i] - barCircleRadius, barsLocationY[i] - barCircleRadius, barsLocationRightAnimated[i] + barCircleRadius, barsLocationY[i] + barCircleRadius, _pBars);
            canvas.drawCircle(barsLocationRightAnimated[i], barsLocationY[i], barCircleRadius, _pBars);
            canvas.drawText(adapter.getItemAtPosition(i).getTitle(), barsLocationRightAnimated[i] + 2 * barCircleRadius, barTextY[i], _pBarsText);
//            canvas.drawLine(barsLocationRightAnimated[i], barsLocationY[i], barsLocationRightAnimated[i], 0, _pBars);
        }
    }

    /**
     * Sets the data of this chart. After that this method request re-layout.
     */
    public void setAdapter(ChartDataAdapter adapter) {
        // Save the reference
        this.adapter = adapter;
        // Set self as the parent view
        adapter.setChartView(this);
        // Notify data change
        notifyDataChanged();
    }

    /**
     * Called when the underlying data has changed. Request re-layout
     */
    protected void notifyDataChanged() {
        // Set the desired dimensions, if we have any data at all
        if (adapter != null) {
            desiredHeight = (adapter.getItemsCount() + 2) * DESIRED_CELL_HEIGHT + timelineY + TIMELINE_HIGH_BAR_HEIGHT + (DESIRED_CELL_HEIGHT / 2);
        }

        // Initiate the re-measure
        requestLayout();
        // Redraw
        invalidate();
    }

    //
    // Animation callbacks
    //
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // Sanity check
        if (adapter == null || adapter.isEmpty()) return;

        for (int i=0; i<adapter.getItemsCount(); i++) {
            barsLocationRightAnimated[i] = (int)((float) animation.getAnimatedValue() * barsLocationRight[i]);
        }
        postInvalidate();
    }
}
