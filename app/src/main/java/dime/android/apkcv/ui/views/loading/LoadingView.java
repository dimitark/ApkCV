package dime.android.apkcv.ui.views.loading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;

import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.BaseView;
import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 28/08/15.
 */
public class LoadingView extends BaseView implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {
    // The desired radius of a single circle
    private int DESIRED_CIRCLE_RADIUS;
    private float DESIRED_TEXT_SIZE_IN_SP;

    // The Paints
    private Paint[] _paints;
    private Paint _pText;
    // The colors
    private int[] colors;
    private int textColor;

    // The actual radius of the circles
    private int[] circlesRadiusAnimated;
    private int maxCirclesRadius;
    private int minCirclesRadius;

    // The locations of the circles
    private int circlesY;
    private int[] circlesX;

    // The main animation
    private ValueAnimator valueAnimator;
    private int animatedCircle = 0;

    // The text
    private String text;

    // The height of the text
    private int textHeight;
    // The location
    private int textY;

    /**
     * Default constructor
     *
     * @param context
     */
    public LoadingView(Context context) {
        super(context);
    }

    /**
     * Default XML constructor
     *
     * @param context
     * @param attrs
     */
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void calculateDimensions() {
        // Calculate the radii of the circles
        maxCirclesRadius = (heightP - (textHeight * 2)) / 2;
        minCirclesRadius = maxCirclesRadius / 2;

        // Set the current radius to the min radius
        for (int i = 0; i < circlesRadiusAnimated.length; i++) {
            circlesRadiusAnimated[i] = minCirclesRadius;
        }

        // Calculate the positions
        circlesY = (heightP - (textHeight * 2)) / 2;
        for (int i = 0; i < circlesX.length; i++) {
            circlesX[i] = (i * 3 * minCirclesRadius) + ((i+1) * 2 * minCirclesRadius);
        }

        // The text's location
        textY = getPaddingTop() + heightP - (int)((_pText.descent() - _pText.ascent()) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Just draw the circles
        for (int i = 0; i < circlesX.length; i++) {
            canvas.drawCircle(circlesX[i], circlesY, circlesRadiusAnimated[i], _paints[i]);
        }
        // Draw the text
        canvas.drawText(text, widthP / 2, textY, _pText);
    }

    @Override
    protected void init(@Nullable AttributeSet attrs) {
        // Read the attributes from the XML layout
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView);

        // The colors
        colors = new int[3];
        // Read the colors
        colors[0] = a.getColor(R.styleable.LoadingView_loading_first_color, Color.RED);
        colors[1] = a.getColor(R.styleable.LoadingView_loading_second_color, Color.GREEN);
        colors[2] = a.getColor(R.styleable.LoadingView_loading_third_color, Color.BLUE);
        textColor = a.getColor(R.styleable.LoadingView_loading_text_color, Color.WHITE);

        // Get the text
        text = a.getString(R.styleable.LoadingView_loading_text);
        if (text == null) text = "";

        // Recycle the typed array
        a.recycle();

        // Setup the paints
        _paints = new Paint[colors.length];
        for (int i = 0; i < _paints.length; i++) {
            _paints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            _paints[i].setColor(colors[i]);
            _paints[i].setStyle(Paint.Style.FILL);
        }

        DESIRED_TEXT_SIZE_IN_SP = ViewUtils.spToPixels(getContext(), 8f);
        _pText = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pText.setTypeface(Typeface.DEFAULT_BOLD);
        _pText.setColor(textColor);
        _pText.setTextAlign(Paint.Align.CENTER);
        _pText.setStyle(Paint.Style.FILL);
        _pText.setTextSize(DESIRED_TEXT_SIZE_IN_SP);
        textHeight = (int) (_pText.descent() - _pText.ascent());

        // Init the arrays
        circlesRadiusAnimated = new int[colors.length];
        circlesX = new int[colors.length];

        // Calculate the desired width and height
        // We will want to use the MAX radius (because of the animation)
        DESIRED_CIRCLE_RADIUS = ViewUtils.dipsToPixels(getContext(), 5f);
        desiredHeight = 4 * DESIRED_CIRCLE_RADIUS + textHeight * 2;
        desiredWidth = (colors.length * 4 * DESIRED_CIRCLE_RADIUS) + (colors.length - 1) * DESIRED_CIRCLE_RADIUS; // We've added the spacing between the circles

        // Setup the animations
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(600);
        valueAnimator.setInterpolator(new TheHobbitInterpolator());
        valueAnimator.setIntValues(minCirclesRadius, maxCirclesRadius);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setRepeatMode(Animation.RESTART);
        valueAnimator.addListener(this);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }

    /**
     * Sets the text
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    //
    // Animator callbacks
    //

    @Override
    public void onAnimationStart(Animator animation) {}

    @Override
    public void onAnimationEnd(Animator animation) {}

    @Override
    public void onAnimationCancel(Animator animation) {}

    @Override
    public void onAnimationRepeat(Animator animation) {
        animatedCircle = (animatedCircle + 1) % (circlesX.length + 1);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // We do this, because we want pause between the animation cycles.
        // It looks better.
        if (animatedCircle >= circlesX.length) return;

        // Animate
        circlesRadiusAnimated[animatedCircle] = (int) (((maxCirclesRadius - minCirclesRadius) * (float)animation.getAnimatedValue()) + minCirclesRadius);
        postInvalidate();
    }
}
