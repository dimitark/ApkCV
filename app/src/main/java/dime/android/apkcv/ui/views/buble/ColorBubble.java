package dime.android.apkcv.ui.views.buble;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.animation.AnticipateInterpolator;

import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 25/08/15.
 */
public class ColorBubble implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {
    // Which type am I?
    public final BubbleView.BubbleButtonType type;

    // The pains
    private Paint _pBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint _pText = new Paint(Paint.ANTI_ALIAS_FLAG);

    // The animator for the Zoom and the text fade
    private ValueAnimator _aFade;
    private ValueAnimator _aZoom;

    // The dimensions of this bubble in the canvas
    private int x;
    private int y;
    private int initialRadius;
    private int textY;
    // The radius we need to get to when zooming in
    private int zoomRadius;
    // The radius used for drawing
    private int radius;

    // The text
    private String text;

    // Should we response to touch events?
    private boolean respondToTouchEvents = true;

    // Called when the parent view needs to redraw
    private ViewAnimationListener viewAnimationListener;

    /**
     * Default constructor
     *
     * @param bgColor
     * @param text
     * @param viewAnimationListener
     */
    public ColorBubble(@NonNull BubbleView.BubbleButtonType type, int bgColor, @NonNull String text, @NonNull ViewAnimationListener viewAnimationListener) {
        // Save the references
        this.type = type;
        this.text = text;
        this.viewAnimationListener = viewAnimationListener;

        // Init the paints
        _pBg.setColor(bgColor);
        _pBg.setStyle(Paint.Style.FILL);

        _pText.setColor(Color.WHITE);
        _pText.setTypeface(Typeface.MONOSPACE);
        _pText.setStyle(Paint.Style.FILL);
        _pText.setTextAlign(Paint.Align.CENTER);

        // Init the animators
        _aFade = ValueAnimator.ofInt(255, 0);
        _aFade.addUpdateListener(this);
        _aFade.setDuration(300);
        _aZoom = ValueAnimator.ofInt(radius, zoomRadius);
        _aZoom.addUpdateListener(this);
        _aZoom.addListener(this);
        _aZoom.setDuration(500);
        _aZoom.setInterpolator(new AnticipateInterpolator(0.5f));
    }

    /**
     * Sets the dimensions of this bubble
     *
     * @param x
     * @param y
     * @param radius
     * @param canvasWidth
     * @param canvasHeight
     */
    public void setDimensions(int x, int y, int radius, int canvasWidth, int canvasHeight) {
        this.x = x;
        this.y = y;
        this.initialRadius = radius;
        this.radius = radius;
        ViewUtils.adjustTextSizeForWidth(text, _pText, radius / 2);
        this.textY = this.y - (int)((_pText.descent() + _pText.ascent()) / 2);
        // The zoomRadius should cover the while screen
        this.zoomRadius = Math.max(canvasWidth, canvasHeight);

        // Set the animator. Cancel any previous active one
        if (_aZoom != null && _aZoom.isRunning()) {
            _aZoom.cancel();
        }
        _aZoom.setIntValues(radius, zoomRadius);
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

    /**
     * Resets the state of the bubble
     */
    public void reset() {
        radius = initialRadius;
        _pText.setAlpha(255);
    }

    /**
     * Click listener. If the click is inside the area of this bubble, it handles the click.
     *
     * Returns true if the click has been handled. Otherwise returns false.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean onTouch(int x, int y) {
        // Should we response at all?
        if (!respondToTouchEvents) return false;

        // Maybe the touch was outside this
        if (x < this.x - radius || x > this.x + radius || y < this.y - radius || y > this.y + radius) {
            // Nothing to do here
            return false;
        }

        // Start the animations
        _aFade.start();
        _aZoom.start();

        return true;
    }

    /**
     * Called on every animation tick
     *
     * @param animation
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation == _aZoom) {
            // Just change the radius
            radius = (int) animation.getAnimatedValue();
            // Redraw
            viewAnimationListener.invalidateView(this);
        } else {
            // Change the alpha of the text
            _pText.setAlpha((int) animation.getAnimatedValue());
            // Redraw
            viewAnimationListener.invalidateView(this);
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
        // Disable touch respond
        respondToTouchEvents = false;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // Enable back the touch respond
        respondToTouchEvents = true;
        // Tell the listener, I'm done
        viewAnimationListener.tapAnimationFinished(this);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // Enable back the touch respond
        respondToTouchEvents = true;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {}
}
