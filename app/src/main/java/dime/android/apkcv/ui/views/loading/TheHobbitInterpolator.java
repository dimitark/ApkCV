package dime.android.apkcv.ui.views.loading;

import android.view.animation.Interpolator;

/**
 * There and back again interpolator
 *
 * Created by dime on 28/08/15.
 */
public class TheHobbitInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return input > 0.5f ? (1.0f - input) / 0.5f : (input / 0.5f);
    }
}
