package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.data.projects.ContentAdapter;
import dime.android.apkcv.data.rest.projects.Project;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.views.loading.LoadingView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dime on 30/08/15.
 */
public class ProjectsFragment extends BaseFragment<App, BaseActivity> {
    // The UI components
    private ViewPager contentViewPager;
    private CirclePageIndicator pageIndicator;

    // The data callback
    private Callback<List<Project>> callback = new Callback<List<Project>>() {
        @Override
        public void success(List<Project> projects, Response response) {
            // Set the adapters
            contentViewPager.setAdapter(new ContentAdapter(baseActivity.getSupportFragmentManager(), projects));

            // Setup the page indicator
            pageIndicator.setViewPager(contentViewPager);

            // Hide the loading view
            hideLoadingScreen();
        }

        @Override
        public void failure(RetrofitError error) {
            // Write to the log
            Log.e(App.LOG_TAG, "RetrofitError: " + error.getMessage());
            // Show the error screen
            showErrorScreen();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_projects, container, false);

        // Get the UI components
        contentViewPager = (ViewPager) rootLayout.findViewById(R.id.content_view_pager);
        pageIndicator = (CirclePageIndicator) rootLayout.findViewById(R.id.page_indicator);

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
        // Fetch the data
        app.getRestServices().getProjectsService().listProjects(callback);
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Show the loading screen
        showLoadingScreen();
    }

    @Override
    protected void handleTryAgain() {
        // Show the loading screen again
        showLoadingScreen();
        // Try to fetch the data again
        app.getRestServices().getProjectsService().listProjects(callback);
    }
}
