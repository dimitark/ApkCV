package dime.android.apkcv.data.rest.projects;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by dime on 30/08/15.
 */
public class Project {
    // The name
    private String title;
    // Description
    @Expose
    private String desc;
    // The size of the team
    @Expose
    private Integer teamSize;
    // The position
    @Expose
    private List<ProjectPosition> position;
    // The platforms
    @Expose
    private List<ProjectPlatform> platforms;
    // The URL
    private String url;

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
        return position;
    }

    public void setPosition(List<ProjectPosition> positions) {
        this.position = positions;
    }

    public List<ProjectPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<ProjectPlatform> platforms) {
        this.platforms = platforms;
    }

    public Integer getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
