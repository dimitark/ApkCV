package dime.android.apkcv.data.rest;

import dime.android.apkcv.BuildConfig;
import dime.android.apkcv.data.rest.bio.BioService;
import dime.android.apkcv.data.rest.projects.ProjectsService;
import dime.android.apkcv.data.rest.skills.SkillsService;
import dime.android.apkcv.data.rest.timeline.TimelineService;
import retrofit.RestAdapter;

/**
 * A simple container for all the REST services
 *
 * Created by dime on 28/08/15.
 */
public class RestServices {
    // The Skills Service
    private SkillsService skillsService;
    // The Bio Service
    private BioService bioService;
    // The Timeline service
    private TimelineService timelineService;
    // The projects service
    private ProjectsService projectsService;

    /**
     * Default constructor
     */
    public RestServices() {

        // Create the REST services
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.REST_URL)
                .build();
        // Create the services
        skillsService = restAdapter.create(SkillsService.class);
        bioService = restAdapter.create(BioService.class);
        timelineService = restAdapter.create(TimelineService.class);
        projectsService = restAdapter.create(ProjectsService.class);
    }

    /**
     * Returns the skills REST service
     * @return
     */
    public SkillsService getSkillsService() {
        return skillsService;
    }

    /**
     * Returns the BIO service
     *
     * @return
     */
    public BioService getBioService() {
        return bioService;
    }

    /**
     * Returns the timeline service
     *
     * @return
     */
    public TimelineService getTimelineService() {
        return timelineService;
    }

    /**
     * Returns the projects service
     *
     * @return
     */
    public ProjectsService getProjectsService() {
        return projectsService;
    }
}
