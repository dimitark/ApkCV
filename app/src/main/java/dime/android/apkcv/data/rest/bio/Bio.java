package dime.android.apkcv.data.rest.bio;

import java.util.List;

/**
 * Created by dime on 27/08/15.
 */
public class Bio {
    // The image URL
    private String image;
    // The title
    private String title;
    // Education
    private String edu;
    // The spoken languages
    private List<String> languages;

    /**
     * Returns the image url
     *
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image url
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }
}
