package dime.android.apkcv.data.rest.projects;

import java.util.List;

/**
 * Created by dime on 30/08/15.
 */
public class Project {
    // The name
    private String title;
    // Description
    private String desc;
    // The position
    private ProjectPosition position;
    // The platforms
    private List<ProjectPlatform> platforms;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ProjectPosition getPosition() {
        return position;
    }

    public void setPosition(ProjectPosition position) {
        this.position = position;
    }

    public List<ProjectPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<ProjectPlatform> platforms) {
        this.platforms = platforms;
    }
}
