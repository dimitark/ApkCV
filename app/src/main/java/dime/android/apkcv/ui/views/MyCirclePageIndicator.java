package dime.android.apkcv.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by dime on 30/08/15.
 */
public class MyCirclePageIndicator extends CirclePageIndicator {
    // The additional OnPageChangeListener
    private ViewPager.OnPageChangeListener additionalOnPageChangeListener;

    /**
     * Default constructor
     *
     * @param context
     */
    public MyCirclePageIndicator(Context context) {
        super(context);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     */
    public MyCirclePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyCirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the additional on page change listener
     *
     * @param additionalOnPageChangeListener
     */
    public void setAdditionalOnPageChangeListener(ViewPager.OnPageChangeListener additionalOnPageChangeListener) {
        this.additionalOnPageChangeListener = additionalOnPageChangeListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        if (additionalOnPageChangeListener != null) {
            additionalOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        if (additionalOnPageChangeListener != null) {
            additionalOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);

        if (additionalOnPageChangeListener != null) {
            additionalOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
