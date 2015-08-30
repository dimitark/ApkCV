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
 * Created by dime on 30/08/15.
 */
public class ProjectsFragment extends BaseFragment<App, BaseActivity> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_projects, container, false);

        // Call the super
        postOnCreateView(rootLayout);

        // Return the layout
        return rootLayout;
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Show the loading screen
//        showLoadingScreen();
    }
}
