package dime.android.apkcv;

import android.app.Application;

import dime.android.apkcv.data.rest.bio.BioService;
import dime.android.apkcv.data.rest.skills.SkillsService;
import retrofit.RestAdapter;

/**
 * Created by dime on 25/08/15.
 */
public class App extends Application {
    // The REST client services
    private SkillsService skillsService;
    private BioService bioService;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create the REST services
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BuildConfig.REST_URL).build();
        // Create the services
        skillsService = restAdapter.create(SkillsService.class);
        bioService = restAdapter.create(BioService.class);
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
}
