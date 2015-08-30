package dime.android.apkcv.data.rest.projects;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dime on 30/08/15.
 */
public interface ProjectsService {
    /**
     * Lists the projects
     *
     * @param callback
     */
    @GET("/projects")
    void listProjects(Callback<List<Project>> callback);
}
