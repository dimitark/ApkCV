package dime.android.apkcv.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.Utils;
import dime.android.apkcv.ui.fragments.BaseFragment;
import dime.android.apkcv.ui.fragments.ProjectsFragment;
import dime.android.apkcv.ui.fragments.SkillsFragment;
import dime.android.apkcv.ui.fragments.TimelineFragment;
import dime.android.apkcv.ui.views.buble.BubbleView;

/**
 * Created by dime on 26/08/15.
 */
public class DetailsActivity extends BaseActivity<App> {
    // Default behaviour
    private static final boolean ANIMATE_COLOR_OVERLAY_BY_DEFAULT = false;

    // The Extra key names
    public static final String EXTRA_TYPE_KEY = "type";
    public static final String EXTRA_PRIMARY_COLOR_KEY = "primaryColor";
    public static final String EXTRA_SECONDARY_COLOR_KEY = "secondaryColor";
    // The saved state bundle keys
    private static final String SAVED_STATE_SHOULD_ANIMATE_COLOR_OVERLAY = "shouldAnimateColorOverlay";

    // The type of the activity
    private BubbleView.BubbleButtonType type;
    // The colors
    private int primaryColor;
    private int secondaryColor;

    // Indicating if we need to animate the colorOverlay.
    // The animation should happen only once (on the first initialisation)
    // and should not happen if the activity is killed and then recreated.
    private boolean shouldAnimateColorOverlay;

    // The colorOverlay animation
    private ScaleAnimation colorOverlayAnimation = new ScaleAnimation(1, 1, 1, 0);

    // The root view/layout
    private View root;
    // The color overlay
    private View colorOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = BubbleView.BubbleButtonType.valueOf(extras.getString(EXTRA_TYPE_KEY));
            primaryColor = extras.getInt(EXTRA_PRIMARY_COLOR_KEY, Utils.getThemeColor(R.attr.colorPrimary, this));
            secondaryColor = extras.getInt(EXTRA_SECONDARY_COLOR_KEY, Utils.getThemeColor(R.attr.colorPrimaryDark, this));
            statusBarColor = secondaryColor;
        } else {
            // Default to timeline with the theme's colors
            type = BubbleView.BubbleButtonType.TIMELINE;
            primaryColor = Utils.getThemeColor(R.attr.colorPrimary, this);
            secondaryColor = Utils.getThemeColor(R.attr.colorPrimary, this);
        }

        // Set the default behaviour
        // If we are being re-created, the onRestoreInstantState()
        // will override this value.
        shouldAnimateColorOverlay = ANIMATE_COLOR_OVERLAY_BY_DEFAULT;

        // Get the UI components
        root = findViewById(android.R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        colorOverlay = findViewById(R.id.color_overlay);

        // Set the toolbar & colorOverlay backgrounds
        root.setBackgroundColor(primaryColor);
        toolbar.setBackgroundColor(primaryColor);
        colorOverlay.setBackgroundColor(primaryColor);

        // Setup the correct fragment
        setupFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set-up the colorOverlay animation - if we need it
        if (shouldAnimateColorOverlay) {
            // Set-up the animation
            colorOverlayAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    colorOverlay.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            colorOverlayAnimation.setDuration(300);
            colorOverlayAnimation.setInterpolator(new DecelerateInterpolator());
            shouldAnimateColorOverlay = false;
            colorOverlay.startAnimation(colorOverlayAnimation);
        } else {
            // If we don't need to animate - we definitely need to hide the overlay
            colorOverlay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the shouldAnimateColorOverlay
        outState.putBoolean(SAVED_STATE_SHOULD_ANIMATE_COLOR_OVERLAY, shouldAnimateColorOverlay);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get the shouldAnimateColorOverlay
        shouldAnimateColorOverlay = savedInstanceState.getBoolean(SAVED_STATE_SHOULD_ANIMATE_COLOR_OVERLAY, ANIMATE_COLOR_OVERLAY_BY_DEFAULT);
    }

    /**
     * Set's up the correct fragment
     */
    private void setupFragment() {
        // The fragment that need to be placed in the container
        BaseFragment fragment;

        switch (type) {
            case TIMELINE:
                fragment = new TimelineFragment();
                break;
            case PROJECTS:
                fragment = new ProjectsFragment();
                break;
            case SKILLS:
                fragment = new SkillsFragment();
                break;
            default:
                // Should not come to this!
                fragment = new SkillsFragment();
                break;
        }
        // Setup the colors of the fragment
        fragment.setColors(primaryColor, secondaryColor);
        // Add the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
