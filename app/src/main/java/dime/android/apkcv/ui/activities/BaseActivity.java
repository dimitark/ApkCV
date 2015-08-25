package dime.android.apkcv.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by dime on 25/08/15.
 */
public class BaseActivity<App> extends ActionBarActivity {
    // Reference to the app
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the reference to the app
        app = (App) getApplication();
    }
}
