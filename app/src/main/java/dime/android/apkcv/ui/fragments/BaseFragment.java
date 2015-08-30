package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.loading.LoadingView;

/**
 * Created by dime on 29/08/15.
 */
public abstract class BaseFragment<App, BaseActivity> extends Fragment {
    // The reference to the App
    protected App app;
    // A reference to the parent BaseActivity
    protected BaseActivity baseActivity;

    // The primary and secondary colors
    protected int primaryColor = -1;
    protected int secondaryColor = -1;

    // References to the loadingLayout and the loadingView
    private ViewGroup loadingLayout;
    private LoadingView loadingView;
    private View errorText;

    /**
     * Called from the postOnCreateView. This method should setup the loading view (e.g. colors).
     * Does not get called if there isn't a loading view in the fragment's layout.
     *
     * @param loadingLayout
     * @param loadingView
     */
    protected abstract void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get a reference to the Base activity
        this.baseActivity = (BaseActivity) getActivity();

        // Get the reference to the App
        this.app = (App) getActivity().getApplication();
    }

    /**
     * Set's up the loading screen (if exists).
     * Should be called after onCreateView.
     *
     * @param rootLayout
     */
    protected void postOnCreateView(ViewGroup rootLayout) {
        // Get the loading layout
        loadingLayout = (ViewGroup) rootLayout.findViewById(R.id.loading_layout);
        loadingView = (LoadingView) rootLayout.findViewById(R.id.loading_view);
        errorText = rootLayout.findViewById(R.id.error_text);

        // Let the fragment customize the loading view
        if (loadingLayout != null && loadingView != null) {
            // Set the background color of the loading layout
            // the same as the primary color of this fragment
            if (primaryColor != -1) {
                loadingLayout.setBackgroundColor(primaryColor);
                // Check if a some of the loading colors is the same
                // as the loading background, and change it if it is
                int[] colors = loadingView.getColors();
                int[] newColors = new int[colors.length];
                boolean colorsChanges = false;
                for (int i = 0; i < colors.length; i++) {
                    if (colors[i] == primaryColor) {
                        newColors[i] = secondaryColor;
                        colorsChanges = true;
                    } else {
                        newColors[i] = colors[i];
                    }
                }
                // Set the colors back
                if (colorsChanges) {
                    loadingView.setColors(newColors);
                }
            }
            customizeLoadingView(loadingLayout, loadingView);
        }
    }

    /**
     * Sets the main colors of the fragment
     *
     * @param primaryColor
     * @param secondaryColor
     */
    public void setColors(int primaryColor, int secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    /**
     * Shows the error screen
     */
    protected void showErrorScreen() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows the loading screen
     */
    protected void showLoadingScreen() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides the loading screen
     */
    protected void hideLoadingScreen() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
    }
}
