package dime.android.apkcv.data.rest.gson;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import dime.android.apkcv.App;

/**
 * Created by dime on 30/08/15.
 */
public class DateConverter implements JsonDeserializer<Date> {
    // The date format
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return format.parse(json.getAsString());
        } catch (Exception e) {
            Log.e(App.LOG_TAG, "Oh no! The date format is not OK. Returning now()");
        }

        // An error occurred, so just return now()
        return new Date();
    }
}
