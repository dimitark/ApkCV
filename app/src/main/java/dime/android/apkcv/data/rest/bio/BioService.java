package dime.android.apkcv.data.rest.bio;

import retrofit.http.GET;

/**
 * Created by dime on 27/08/15.
 */
public interface BioService {
    /**
     * Returns my BIO
     *
     * @return
     */
    @GET("/bio")
    Bio getBio();
}
