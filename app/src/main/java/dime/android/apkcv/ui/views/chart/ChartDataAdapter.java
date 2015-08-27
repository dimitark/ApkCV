package dime.android.apkcv.ui.views.chart;

/**
 * Created by dime on 27/08/15.
 */
public abstract class ChartDataAdapter<T extends ChartDataItem> {
    // The parent view
    private ChartView chartView;

    /**
     * Returns the item at the given position
     *
     * @param position
     * @return
     */
    public abstract T getItemAtPosition(int position);

    /**
     * Returns the number of items
     *
     * @return
     */
    public abstract int getItemsCount();

    /**
     * Returns the title of the X axis
     *
     * @return
     */
    public abstract String getXAxisTitle();

    /**
     * Returns the maximum value of the items' values
     *
     * @return
     */
    public abstract int getMaxValue();

    /**
     * Returns true if there are no items in the adapter
     *
     * @return
     */
    protected boolean isEmpty() {
        return getItemsCount() == 0;
    }

    /**
     * Set's the parent view
     *
     * @param chartView
     */
    protected void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }

    /**
     * Notifies the view that the data has changed.
     */
    @SuppressWarnings("unused")
    protected void notifyDataChanged() {
        // Sanity check
        if (chartView == null) return;

        // Notify
        chartView.notifyDataChanged();
    }
}
