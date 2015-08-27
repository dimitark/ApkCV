package dime.android.apkcv.data.rest.skills;

import dime.android.apkcv.ui.views.chart.ChartDataItem;

/**
 * Created by dime on 27/08/15.
 */
public class Skill implements ChartDataItem {
    // The name of the skill
    public String name;
    // the experience (in years)
    public int experience;

    /**
     * Returns the name of the skill
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the skill
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the experience (in years)
     *
     * @return
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Sets the experience (in years)
     *
     * @param experience
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Returns the name as the title (for the chart view)
     *
     * @return
     */
    @Override
    public String getTitle() {
        return getName();
    }

    /**
     * Returns the experience as value (for the chart view)
     *
     * @return
     */
    @Override
    public int getValue() {
        return getExperience();
    }
}
