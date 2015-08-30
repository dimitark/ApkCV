package dime.android.apkcv.data.rest.timeline;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dime on 29/08/15.
 */
public interface TimelineService {
    /**
     * Returns the timeline
     *
     * @param timelineCallback
     */
    @GET("/timeline")
    void timeline(Callback<List<TimelineItem>> timelineCallback);
}
