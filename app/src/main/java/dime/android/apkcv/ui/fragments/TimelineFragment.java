package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.timeline.TimelineItem;
import dime.android.apkcv.data.timeline.TimelineAdapter;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.views.loading.LoadingView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineFragment extends BaseFragment<App, BaseActivity> {
    // The main recycler view
    private RecyclerView recyclerView;

    // The data callback
    private Callback<List<TimelineItem>> timelineCallback = new Callback<List<TimelineItem>>() {
        @Override
        public void success(List<TimelineItem> timelineItems, Response response) {
            // Set the data to the adapter
            recyclerView.setAdapter(new TimelineAdapter(timelineItems));
            // Hide the loading screen
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
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_timeline, container, false);

        // Get the UI components
        recyclerView = (RecyclerView) rootLayout.findViewById(R.id.timeline_recycler_view);

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

        // Setup the recycler view
        // The layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Load the data
        app.getRestServices().getTimelineService().timeline(timelineCallback);
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
        app.getRestServices().getTimelineService().timeline(timelineCallback);
    }
}
