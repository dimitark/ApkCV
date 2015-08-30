package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.skills.Skill;
import dime.android.apkcv.data.skills.SkillsChartAdapter;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.views.chart.ChartView;
import dime.android.apkcv.ui.views.loading.LoadingView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dime on 29/08/15.
 */
public class SkillsFragment extends BaseFragment<App, BaseActivity> {
    // The chart view
    private ChartView chartView;

    // The Skills callback
    private Callback<List<Skill>> skillsCallback = new Callback<List<Skill>>() {
        @Override
        public void success(List<Skill> skills, Response response) {
            // Set the ChartView's adapter
            chartView.setAdapter(new SkillsChartAdapter(skills, getResources().getString(R.string.years)));
            // Hide the loading screen
            hideLoadingScreen();
        }
        @Override
        public void failure(RetrofitError error) {
            // Write to the log
            Log.e(App.LOG_TAG, error.getMessage());
            // Show the error screen
            showErrorScreen();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_skills, container, false);

        // Get the UI components
        chartView = (ChartView) rootLayout.findViewById(R.id.chart);

        // Call the super
        postOnCreateView(rootLayout);
        // Return the layout
        return rootLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set the toolbar's data
        baseActivity.getToolbar().setTitle(getResources().getString(R.string.skills_title));
        baseActivity.getToolbar().setSubtitle(getResources().getString(R.string.skills_subtitle));
        // Fetch the skills data
        app.getRestServices().getSkillsService().listSkills(skillsCallback);
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Show the loading screen
        showLoadingScreen();
    }
}
