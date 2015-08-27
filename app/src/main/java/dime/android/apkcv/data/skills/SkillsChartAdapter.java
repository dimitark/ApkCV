package dime.android.apkcv.data.skills;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dime.android.apkcv.data.rest.skills.Skill;
import dime.android.apkcv.ui.views.chart.ChartDataAdapter;

/**
 * Created by dime on 27/08/15.
 */
public class SkillsChartAdapter extends ChartDataAdapter<Skill> {
    // The title of the chart
    private String title;
    // The data
    private List<Skill> data;
    // The max value
    private int max;

    /**
     * Default constructor
     *
     * @param data
     */
    public SkillsChartAdapter(List<Skill> data, String title) {
        this.title = title;
        this.data = data;

        // Sort the data and find the MAX
        Collections.sort(this.data, new Comparator<Skill>() {
            @Override
            public int compare(Skill lhs, Skill rhs) {
                // Save also the maximum
                max = Math.max(max, Math.max(lhs.getValue(), rhs.getValue()));
                return rhs.getValue() - lhs.getValue();
            }
        });
    }

    /**
     * Returns the item at the given position
     *
     * @param position
     * @return
     */
    @Override
    public Skill getItemAtPosition(int position) {
        return data.get(position);
    }

    /**
     * Returns the number of items
     *
     * @return
     */
    @Override
    public int getItemsCount() {
        return data.size();
    }

    /**
     * Returns the title of the X axis
     *
     * @return
     */
    @Override
    public String getXAxisTitle() {
        return title;
    }

    /**
     * Returns the max value
     *
     * @return
     */
    @Override
    public int getMaxValue() {
        return max;
    }
}
