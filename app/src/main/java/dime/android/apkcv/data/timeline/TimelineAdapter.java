package dime.android.apkcv.data.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.timeline.TimelineItem;

/**
 * Created by dime on 29/08/15.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineLifeEventHolder> {
    // The data (already sorted)
    private List<TimelineItem> items;
    // A list of indexes of the cells that need to display the year
    private List<Integer> cellsWithYear = new ArrayList<>();

    /**
     * Default constructor
     *
     * @param items
     */
    public TimelineAdapter(List<TimelineItem> items) {
        // Save the data
        this.items = items;

        // Sort it descending by year
        Collections.sort(this.items, new Comparator<TimelineItem>() {
            @Override
            public int compare(TimelineItem lhs, TimelineItem rhs) {
                return rhs.getYear() - lhs.getYear();
            }
        });

        // See which cells need to display the year
        int lastYear = -1;
        for (int i = 0; i < this.items.size(); i++) {
            if (lastYear != this.items.get(i).getYear()) {
                lastYear = this.items.get(i).getYear();
                cellsWithYear.add(i);
            }
        }
    }

    @Override
    public TimelineLifeEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // The layout inflater
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // The view holder that is being created
        TimelineLifeEventHolder viewHolder = new TimelineLifeEventHolder(inflater.inflate(R.layout.cell_timeline_life_event, parent, false));;

        // Return the view holder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimelineLifeEventHolder viewHolder, int position) {
        // Get the item
        TimelineItem item = items.get(position);
        // Check the year visibility
        if (cellsWithYear.indexOf(position) != -1) {
            viewHolder.year.setVisibility(View.VISIBLE);
        } else {
            viewHolder.year.setVisibility(View.INVISIBLE);
        }

        // Bind the data
        viewHolder.year.setText(item.getYear() + "");
        viewHolder.info.setText(item.getInfo());
        viewHolder.desc.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
