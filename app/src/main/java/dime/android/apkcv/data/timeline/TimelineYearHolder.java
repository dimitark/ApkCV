package dime.android.apkcv.data.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dime.android.apkcv.R;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineYearHolder extends RecyclerView.ViewHolder {
    // The year text view
    private TextView yearTextView;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public TimelineYearHolder(View itemView) {
        super(itemView);

        // Get the UI components
        yearTextView = (TextView) itemView.findViewById(R.id.timeline_year);
    }

    /**
     * Binds the data
     *
     * @param year
     */
    protected void bindData(int year) {
        yearTextView.setText(year + "");
    }
}
