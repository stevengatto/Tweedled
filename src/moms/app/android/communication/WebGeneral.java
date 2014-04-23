package moms.app.android.communication;

import android.content.SharedPreferences;

/**
 * Created by klam on 4/16/14.
 */
public class WebGeneral {
    public static final String BASE_URL = "http://107.170.50.231/";
    public static final String LOGIN_URL = BASE_URL + "api/v1/sessions";
    public static final String FETCHING_POLL_URL = BASE_URL + "polls.json";
    public static final String CREATING_NEW_POLL_URL = BASE_URL + "polls/new";
    public static final String REGISTER_URL = BASE_URL + "api/v1/registrations";

    public static String generateVoteURL(int id)
    {
        return BASE_URL + "/polls/"+ id +"/vote";
    }
    private static SharedPreferences sPreferences = null;

    public static SharedPreferences getsPreferences() {
        return sPreferences;
    }

    public static void setsPreferences(SharedPreferences sPreferences) {
        WebGeneral.sPreferences = sPreferences;
    }

}
