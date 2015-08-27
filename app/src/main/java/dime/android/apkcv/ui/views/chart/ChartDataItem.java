package dime.android.apkcv.ui.views.chart;

/**
 * A single data item of the Chart
 *
 * Created by dime on 27/08/15.
 */
public interface ChartDataItem {
    /**
     * Returns the title of the item, which is display on the side of each item.
     *
     * @return
     */
    String getTitle();
    /**
     * Returns the value of the item on the X axis
     *
     * @return
     */
    int getValue();
}
