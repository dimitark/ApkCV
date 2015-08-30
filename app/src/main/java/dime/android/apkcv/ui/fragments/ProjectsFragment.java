package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.views.loading.LoadingView;

/**
 * Created by dime on 30/08/15.
 */
public class ProjectsFragment extends BaseFragment<App, BaseActivity> {
    // The UI components
    private ViewPager parentViewPager;
    private ViewPager contentViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_projects, container, false);

        // Get the UI components
        parentViewPager = (ViewPager) rootLayout.findViewById(R.id.parent_view_pager);
        contentViewPager = (ViewPager) rootLayout.findViewById(R.id.content_view_pager);

        // Call the super
        postOnCreateView(rootLayout);

        // Return the layout
        return rootLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set the toolbar data
        baseActivity.getToolbar().setTitle(getResources().getString(R.string.projects_title));
        baseActivity.getToolbar().setSubtitle(getResources().getString(R.string.projects_subtitle));
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Show the loading screen
//        showLoadingScreen();
    }
}
