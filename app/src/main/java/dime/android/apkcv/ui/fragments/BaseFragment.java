package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

    // References to the loadingLayout and the loadingView
    private ViewGroup loadingLayout;
    private LoadingView loadingView;

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

        // Let the fragment customize the loading view
        if (loadingLayout != null && loadingView != null) {
            customizeLoadingView(loadingLayout, loadingView);
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
