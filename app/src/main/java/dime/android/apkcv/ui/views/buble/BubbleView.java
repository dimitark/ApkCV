package dime.android.apkcv.ui.views.buble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.BaseView;
import dime.android.apkcv.ui.views.ViewUtils;

/**
 * Created by dime on 25/08/15.
 */
public class BubbleView extends BaseView {
    // The paints
    private Paint _pBackground;
    private Paint _pText;

    // The main image bubble
    private ImageBubble imageBubble;
    // The other bubbles
    private ColorBubble timelineBubble;

    // The data
    // The text
    private String mainText;

    // The dimensions
    // The text
    private int textX;
    private int textY;

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
        timelineBubble = new ColorBubble(a.getColor(R.styleable.BubbleView_bubble_timeline_color, Color.BLACK), "T");
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
        ViewUtils.adjustTextSizeForWidth(mainText, _pText, widthP / 2);
        // The color bubbles
        int colorBubblesRadius = bubbleRadius / 4;
        int colorBubblesY = textY + 2*colorBubblesRadius;
        timelineBubble.setDimensions(100, colorBubblesY, colorBubblesRadius);
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
        timelineBubble.draw(canvas);
    }
}
