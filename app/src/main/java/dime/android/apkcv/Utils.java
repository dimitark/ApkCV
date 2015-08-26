package dime.android.apkcv;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by dime on 26/08/15.
 */
public class Utils {
    /**
     * Returns the theme color with the given colorID
     *
     * @param colorID
     * @param context
     * @return
     */
    public static int getThemeColor(final int colorID, final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme ().resolveAttribute(colorID, value, true);
        return value.data;
    }
}
