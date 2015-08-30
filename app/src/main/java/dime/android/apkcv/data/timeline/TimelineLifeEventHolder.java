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
    private TextView info;
    private TextView desc;

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

    /**
     * Binds the data
     *
     * @param item
     */
    public void bindData(TimelineItem item) {
        info.setText(item.getInfo());
        desc.setText(item.getDesc());
    }
}
