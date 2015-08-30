package dime.android.apkcv.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * View pager that doesn't response to touch events
 *
 * Created by dime on 30/08/15.
 */
public class InsensitiveViewPager extends ViewPager {
    /**
     * Default constructor
     *
     * @param context
     */
    public InsensitiveViewPager(Context context) {
        super(context);
    }

    /**
     * XML constructor
     *
     * @param context
     * @param attrs
     */
    public InsensitiveViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Ignore everything and everyone
        return false;
    }
}
