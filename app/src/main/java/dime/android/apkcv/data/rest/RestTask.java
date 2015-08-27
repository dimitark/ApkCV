package dime.android.apkcv.data.rest;

import android.os.AsyncTask;

import retrofit.RetrofitError;

/**
 * Created by dime on 27/08/15.
 */
public class RestTask<ResponseType> extends AsyncTask<RestTaskRunnable<ResponseType>, Void, ResponseType> {
    // Did we get an error?
    private boolean error = false;
    // The response handler
    private ResponseHandler<ResponseType> responseHandler;

    /**
     * Default constructor
     *
     * @param responseHandler
     */
    public RestTask(ResponseHandler<ResponseType> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    protected ResponseType doInBackground(RestTaskRunnable<ResponseType>... params) {
        // We must have only one runnable
        if (params.length != 1) {
            error = true;
            return null;
        }

        // Start the runnable
        try {
            return params[0].run();
        } catch (RetrofitError e) {
            error = true;
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseType response) {
        if (error) {
            responseHandler.error();
        } else {
            responseHandler.success(response);
        }
    }
}
