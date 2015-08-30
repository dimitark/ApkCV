package dime.android.apkcv.data.rest.timeline;

import java.util.Date;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineItem {
    // The year
    private int year;
    // The data
    private Date date;
    // The info
    private String info;
    // The desc (can be empty)
    private String desc;
    // The type
    private TimelineItemType type;

    /**
     * Returns the year
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the date
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the info
     *
     * @return
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the info
     *
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Returns the desc
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the desc
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Returns the type
     *
     * @return
     */
    public TimelineItemType getType() {
        return type;
    }

    /**
     * Sets the type
     *
     * @param type
     */
    public void setType(TimelineItemType type) {
        this.type = type;
    }
}
