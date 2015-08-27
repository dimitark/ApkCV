package dime.android.apkcv.data.rest.skills;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by dime on 27/08/15.
 */
public interface SkillsService {
    @GET("/skills")
    List<Skill> listSkills();
}
