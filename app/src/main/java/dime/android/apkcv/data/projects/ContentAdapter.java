package dime.android.apkcv.data.projects;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import dime.android.apkcv.data.rest.projects.Project;
import dime.android.apkcv.ui.fragments.ProjectContentFragment;

/**
 * Created by dime on 30/08/15.
 */
public class ContentAdapter extends FragmentPagerAdapter {
    // The data
    private List<Project> data;

    /**
     * Default constructor
     *
     * @param fm
     * @param data
     */
    public ContentAdapter(FragmentManager fm, List<Project> data) {
        super(fm);
        // Save the data
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        // Create the fragment
        ProjectContentFragment fragment = new ProjectContentFragment();
        // Set the data
        fragment.setProject(data.get(position));
        // Return the fragment
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
