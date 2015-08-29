package dime.android.apkcv.data.rest.skills;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dime on 27/08/15.
 */
public interface SkillsService {
    /**
     * Returns my skills
     *
     * @return
     */
    @GET("/skills")
    void listSkills(Callback<List<Skill>> listCallback);
}
