package dime.android.apkcv.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.Utils;
import dime.android.apkcv.ui.views.buble.BubbleView;

/**
 * Created by dime on 26/08/15.
 */
public class DetailsActivity extends BaseActivity<App> {
    // The Extra key names
    public static final String EXTRA_TYPE_KEY = "type";
    public static final String EXTRA_PRIMARY_COLOR_KEY = "primaryColor";
    public static final String EXTRA_SECONDARY_COLOR_KEY = "secondaryColor";

    // The type of the activity
    private BubbleView.BubbleButtonType type;
    // The colors
    private int primaryColor;
    private int secondaryColor;

    // The root view of this activity
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            BubbleView.BubbleButtonType.valueOf(extras.getString(EXTRA_TYPE_KEY));
            primaryColor = extras.getInt(EXTRA_PRIMARY_COLOR_KEY, Utils.getThemeColor(R.attr.colorPrimary, this));
            secondaryColor = extras.getInt(EXTRA_SECONDARY_COLOR_KEY, Utils.getThemeColor(R.attr.colorPrimaryDark, this));
            statusBarColor = secondaryColor;
        } else {
            // Default to timeline with the theme's colors
            type = BubbleView.BubbleButtonType.TIMELINE;
            primaryColor = Utils.getThemeColor(R.attr.colorPrimary, this);
            secondaryColor = Utils.getThemeColor(R.attr.colorPrimary, this);
        }

        // Get the UI components
        rootView = findViewById(android.R.id.content);

        // Set the colors
        rootView.setBackgroundColor(primaryColor);
    }
}
