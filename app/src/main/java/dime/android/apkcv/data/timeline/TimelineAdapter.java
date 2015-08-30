package dime.android.apkcv.data.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // The years [position : year]
    private Map<Integer, Integer> years = new HashMap<>();
    // The actual items [position : item]
    private Map<Integer, TimelineItem> items = new HashMap<>();
    // The total number of rows
    private int count = 0;

    /**
     * Default constructor
     *
     * @param data
     */
    public TimelineAdapter(List<TimelineItem> data) {
        // Sort it descending by year
        Collections.sort(data, new Comparator<TimelineItem>() {
            @Override
            public int compare(TimelineItem lhs, TimelineItem rhs) {
                return rhs.getYear() - lhs.getYear();
            }
        });

        // Calculate the positions
        // The year that was added last in the years map
        int lastAddedYear = -1;
        // The position index that needs to be added
        int position = 0;
        for (TimelineItem item : data) {
            // Check if we already have the year
            if (item.getYear() != lastAddedYear) {
                years.put(position++, item.getYear());
                lastAddedYear = item.getYear();
                count++;
            }
            // Add the item
            items.put(position++, item);
            count++;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // The layout inflater
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // The view holder that is being created
        RecyclerView.ViewHolder viewHolder;

        // Create the cells
        if (viewType == TimelineCellType.YEAR.ordinal()) {
            // Create the YEAR cell
            viewHolder = new TimelineYearHolder(inflater.inflate(R.layout.cell_timeline_year, parent, false));
        } else {
            // Create the LIFE EVENT cell
            viewHolder = new TimelineLifeEventHolder(inflater.inflate(R.layout.cell_timeline_life_event, parent, false));
        }

        // Return the view holder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof TimelineYearHolder) {
            // Bind the data in the years cell
            ((TimelineYearHolder) viewHolder).bindData(years.get(position));
        } else {
            // Bind the data in the life event cell
            ((TimelineLifeEventHolder) viewHolder).bindData(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return years.containsKey(position) ? TimelineCellType.YEAR.ordinal() : TimelineCellType.LIFE_EVENT.ordinal();
    }
}
