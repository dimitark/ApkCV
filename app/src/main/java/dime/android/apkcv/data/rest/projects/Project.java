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
    private List<ProjectPosition> positions;
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

    public List<ProjectPosition> getPosition() {
        return positions;
    }

    public void setPosition(List<ProjectPosition> positions) {
        this.positions = positions;
    }

    public List<ProjectPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<ProjectPlatform> platforms) {
        this.platforms = platforms;
    }
}
