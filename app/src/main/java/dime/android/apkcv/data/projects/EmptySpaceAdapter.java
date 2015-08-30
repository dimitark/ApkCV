package dime.android.apkcv.data.projects;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dime.android.apkcv.ui.fragments.EmptySpaceFragment;

/**
 * Created by dime on 30/08/15.
 */
public class EmptySpaceAdapter extends FragmentPagerAdapter {
    // Just save the number of items
    private int count;

    /**
     * Default constructor
     *
     * @param fm
     * @param count
     */
    public EmptySpaceAdapter(FragmentManager fm, int count) {
        super(fm);
        // Save the data
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return new EmptySpaceFragment();
    }

    @Override
    public int getCount() {
        return count;
    }
}
