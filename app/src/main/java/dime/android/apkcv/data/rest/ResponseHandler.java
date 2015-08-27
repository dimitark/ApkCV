package dime.android.apkcv.data.rest;

/**
 * Created by dime on 27/08/15.
 */
public interface ResponseHandler<ResponseType> {
    /**
     * Successfully fetched the response
     *
     * @param responseType
     */
    void success(ResponseType responseType);
    /**
     * Error while fetching the response
     */
    void error();
}
