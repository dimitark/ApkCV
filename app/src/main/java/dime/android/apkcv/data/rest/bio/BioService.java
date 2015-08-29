package dime.android.apkcv.data.rest.bio;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dime on 27/08/15.
 */
public interface BioService {
    /**
     * Returns my BIO
     *
     * @param bioCallback
     */
    @GET("/bio")
    void getBio(Callback<Bio> bioCallback);
}
