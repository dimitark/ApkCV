package dime.android.apkcv.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.projects.Project;

/**
 * Created by dime on 30/08/15.
 */
public class ProjectContentFragment extends Fragment {
    // The pretty print GSON
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    // The data
    private Project project;

    // The UI components
    private TextView title;
    private TextView content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_project_content, container, false);

        // Get the UI components
        title = (TextView) rootLayout.findViewById(R.id.title);
        content = (TextView) rootLayout.findViewById(R.id.project_content_text);

        // Bind the data (we should have data always, at this point)
        if (project != null) {
            title.setText(project.getTitle());
            content.setText(gson.toJson(project));
        }

        // Return the root layout
        return rootLayout;
    }

    /**
     * Sets the data
     *
     * @param project
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
