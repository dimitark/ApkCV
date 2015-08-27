package dime.android.apkcv.data.rest;

/**
 * The runnable, that actually calls the rest service
 *
 * Created by dime on 27/08/15.
 */
public interface RestTaskRunnable<ResponseType> {
    /**
     * Calls the rest service, and returns the data
     *
     * @return
     */
    ResponseType run();
}
