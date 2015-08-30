package dime.android.apkcv.data.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.timeline.TimelineItem;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineLifeEventHolder extends RecyclerView.ViewHolder {
    // The UI components
    public final TextView info;
    public final TextView desc;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public TimelineLifeEventHolder(View itemView) {
        super(itemView);

        // Get the UI components
        info = (TextView) itemView.findViewById(R.id.timeline_info);
        desc = (TextView) itemView.findViewById(R.id.timeline_desc);
    }
}
