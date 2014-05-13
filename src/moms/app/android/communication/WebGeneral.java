package moms.app.android.communication;

import android.content.SharedPreferences;

/**
 * Created by klam on 4/16/14.
 */
public class WebGeneral {
    public static final String BASE_URL = "http://107.170.50.231/";
  //  public static final String BASE_URL = "http://5e04b4bc.ngrok.com/";
    public static final String LOGIN_URL = BASE_URL + "api/v1/sessions";
    public static final String FETCHING_POLL_URL = BASE_URL + "polls.json";
    public static final String CREATING_NEW_POLL_URL = BASE_URL + "polls/new";
    public static final String REGISTER_URL = BASE_URL + "api/v1/registrations";
    public static final String IMAGE_SEARCH_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?" +
            "v=1.0&as_filetype=jpg&rsz=8";

    public static String generateCommentURL(int poll_id){ return BASE_URL + "/polls/" + poll_id + "/comments.json"; }

    public static String generateVoteURL(int id)
    {
        return BASE_URL + "/polls/"+ id +"/vote";
    }

    public static String encodeString(String query)
    {
        if(query.matches("^[A-Za-z0-9 ]*$")){
            return query.replace(" ","%20");
        }
        else
            return null;
    }

    private static SharedPreferences sPreferences = null;

    public static SharedPreferences getsPreferences() {
        return sPreferences;
    }

    public static void setsPreferences(SharedPreferences sPreferences) {
        WebGeneral.sPreferences = sPreferences;
    }

}
