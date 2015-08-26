package dime.android.apkcv.ui.views.buble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.BaseView;
import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 25/08/15.
 */
public class BubbleView extends BaseView implements ViewAnimationListener, View.OnTouchListener {
    // The different types of bubble buttons
    public enum BubbleButtonType {
        TIMELINE, PROJECTS, SKILLS
    }

    // The bubbles' color schemes
    private Map<BubbleButtonType, BubbleColorScheme> colorSchemes;

    // The paints
    private Paint _pBackground;
    private Paint _pText;

    // The main image bubble
    private ImageBubble imageBubble;
    // The other bubbles
    private Map<BubbleButtonType, ColorBubble> colorBubbles;
    // The bubble that needs to be on top
    private ColorBubble bubbleOnTop;

    // The data
    // The text
    private String mainText;

    // The dimensions
    // The text
    private int textX;
    private int textY;

    // The object interested in my clicks
    private BubbleClickListener bubbleClickListener;

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

        // Get the data
        mainText = a.getString(R.styleable.BubbleView_text);
        if (mainText == null) {
            mainText = "";
        }

        //
        // Init the paints
        //
        // The background paint
        _pBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pBackground.setColor(a.getColor(R.styleable.BubbleView_background_color, Color.TRANSPARENT));
        _pBackground.setStyle(Paint.Style.FILL);
        // The text paint
        _pText = new Paint(Paint.ANTI_ALIAS_FLAG);
        _pText.setColor(a.getColor(R.styleable.BubbleView_text_color, Color.WHITE));
        _pText.setTextAlign(Paint.Align.CENTER);
        _pText.setStyle(Paint.Style.FILL);
        _pText.setTypeface(Typeface.MONOSPACE);

        // Init the bubbles
        imageBubble = new ImageBubble();
        // The color bubbles
        colorBubbles = new HashMap<>();
        colorSchemes = new HashMap<>();
        int timelinePrimaryColor = a.getColor(R.styleable.BubbleView_bubble_timeline_color, Color.RED);
        int timelineSecondaryColor = a.getColor(R.styleable.BubbleView_bubble_timeline_secondary_color, Color.RED);
        colorBubbles.put(BubbleButtonType.TIMELINE, new ColorBubble(BubbleButtonType.TIMELINE, timelinePrimaryColor, "T", this));
        colorSchemes.put(BubbleButtonType.TIMELINE, new BubbleColorScheme(timelinePrimaryColor, timelineSecondaryColor));

        int projectsPrimaryColor = a.getColor(R.styleable.BubbleView_bubble_projects_color, Color.GREEN);
        int projectsSecondaryColor = a.getColor(R.styleable.BubbleView_bubble_projects_secondary_color, Color.GREEN);
        colorBubbles.put(BubbleButtonType.PROJECTS, new ColorBubble(BubbleButtonType.PROJECTS, projectsPrimaryColor, "P", this));
        colorSchemes.put(BubbleButtonType.PROJECTS, new BubbleColorScheme(projectsPrimaryColor, projectsSecondaryColor));

        int skillsPrimaryColor = a.getColor(R.styleable.BubbleView_bubble_skills_color, Color.BLUE);
        int skillsSecondaryColor = a.getColor(R.styleable.BubbleView_bubble_skills_secondary_color, Color.BLUE);
        colorBubbles.put(BubbleButtonType.SKILLS, new ColorBubble(BubbleButtonType.SKILLS, skillsPrimaryColor, "S", this));
        colorSchemes.put(BubbleButtonType.SKILLS, new BubbleColorScheme(skillsPrimaryColor, skillsSecondaryColor));

        // Register a click listener
        setOnTouchListener(this);
    }

    /**
     * Sets the image in the main bubble
     *
     * @param bitmap
     */
    public void setMainImage(Bitmap bitmap) {
        imageBubble.setBitmap(bitmap);
        invalidate();
    }

    /**
     * Sets the bubble click listener
     *
     * @param bubbleClickListener
     */
    public void setBubbleClickListener(BubbleClickListener bubbleClickListener) {
        this.bubbleClickListener = bubbleClickListener;
    }

    /**
     * Returns the color scheme for the given bubble type
     *
     * @param type
     * @return
     */
    public @NonNull BubbleColorScheme getColorSchemeForType(@NonNull BubbleButtonType type) {
        BubbleColorScheme colorScheme = colorSchemes.get(type);
        // Return TRANSPARENT if null
        if (colorScheme == null) {
            colorScheme = new BubbleColorScheme(Color.TRANSPARENT, Color.TRANSPARENT);
        }
        // Return the scheme
        return colorScheme;
    }

    /**
     * Calculates the dimensions of all child elements
     *
     */
    @Override
    protected void calculateDimensions() {
        // The image bubble
        int bubbleX = getPaddingLeft() + widthP / 2;
        int bubbleY = getPaddingTop() + heightP / 2 - heightP / 5;
        int bubbleRadius = widthP / 4;
        imageBubble.setDimensions(bubbleX, bubbleY, bubbleRadius);
        // The text
        textX = bubbleX;
        textY = bubbleY + (int) (1.5 * bubbleRadius);
        ViewUtils.adjustTextSizeForWidth(mainText, _pText, (int)(widthP / 1.5));
        // The color bubbles
        int colorBubblesRadius = bubbleRadius / 3;
        int colorBubblesY = textY + 3*colorBubblesRadius;
        int ySpacing = (widthP - 6*colorBubblesRadius) / 3;
        colorBubbles.get(BubbleButtonType.TIMELINE).setDimensions(ySpacing, colorBubblesY, colorBubblesRadius, widthP, heightP);
        colorBubbles.get(BubbleButtonType.PROJECTS).setDimensions(2*ySpacing + 2*colorBubblesRadius, colorBubblesY, colorBubblesRadius, widthP, heightP);
        colorBubbles.get(BubbleButtonType.SKILLS).setDimensions(3*ySpacing + 4*colorBubblesRadius, colorBubblesY, colorBubblesRadius, widthP, heightP);
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
        // Draw the image bubble
        imageBubble.draw(canvas);
        // The text
        canvas.drawText(mainText, textX, textY, _pText);
        // The color bubbles
        for (ColorBubble cb : colorBubbles.values()) {
            // If we have a expanding bubble - don't draw it at all.
            if (cb == bubbleOnTop) continue;
            cb.draw(canvas);
        }
        // Draw the expanding bubble
        if (bubbleOnTop != null) {
            bubbleOnTop.draw(canvas);
        }
    }

    /**
     * Resets the state of the view
     */
    public void reset() {
        for (ColorBubble cb : colorBubbles.values()) {
            cb.reset();
        }
    }

    /**
     * Called from a child of the view, when the parent view needs to redraw.
     *
     * @param bubbleOnTop
     *          The given bubble will be last on the draw queue - and on top of everything else.
     */
    @Override
    public void invalidateView(ColorBubble bubbleOnTop) {
        // Save the top bubble
        this.bubbleOnTop = bubbleOnTop;
        // A child bubble changed, so we need to redraw
        postInvalidate();
    }

    @Override
    public void tapAnimationFinished(ColorBubble bubble) {
        // Just delegate the call on the BubbleClickListener
        if (bubbleClickListener != null) {
            bubbleClickListener.bubbleClicked(bubble.type);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // We listen just for ACTION_UP events
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (ColorBubble cb : colorBubbles.values()) {
                if (cb.onTouch((int) event.getX(), (int) event.getY())) {
                    return true;
                }
            }
        }
        // Everything else is out of our reach
        return false;
    }
}
