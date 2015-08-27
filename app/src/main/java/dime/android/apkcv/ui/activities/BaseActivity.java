package dime.android.apkcv.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import dime.android.apkcv.R;
import dime.android.apkcv.Utils;

/**
 * Created by dime on 25/08/15.
 */
public class BaseActivity<App> extends ActionBarActivity {
    // Reference to the app
    protected App app;

    // The Window object. Not null only if the version is >= LOLLIPOP
    protected Window window;

    // The color of the status bar
    protected int statusBarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the reference to the app
        app = (App) getApplication();

        // Get the window object only for LOLLIPOP+ devices.
        // Because we need the Window only for changing the
        // status bar color, and that is available only for 5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        // Defaults to the themes primaryColorDark
        statusBarColor = Utils.getThemeColor(R.attr.colorPrimaryDark, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set back the status bar color (if LOLLIPOP+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(statusBarColor);
        }
    }
}
