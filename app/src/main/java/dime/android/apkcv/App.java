package dime.android.apkcv;

import android.app.Application;

import dime.android.apkcv.data.rest.RestServices;

/**
 * Created by dime on 25/08/15.
 */
public class App extends Application {
    // The Log TAG
    public static final String LOG_TAG = "ApkCV";

    // The REST client services
    private RestServices restServices;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init the REST services
        restServices = new RestServices();
    }

    /**
     * Returns the REST services container
     *
     * @return
     */
    public RestServices getRestServices() {
        return restServices;
    }
}
