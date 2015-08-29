package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.views.loading.LoadingView;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineFragment extends BaseFragment<App, BaseActivity> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_timeline, container, false);

        // Call the super
        postOnCreateView(rootLayout);
        // Return the layout
        return rootLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set the toolbar's data
        baseActivity.getToolbar().setTitle(getResources().getString(R.string.timeline_title));
        baseActivity.getToolbar().setSubtitle(getResources().getString(R.string.timeline_subtitle));
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Show the loading screen
        showLoadingScreen();
    }
}
