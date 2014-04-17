package moms.app.android.communication;

import android.content.SharedPreferences;

/**
 * Created by klam on 4/16/14.
 */
public class WebGeneral {
    public static final String BASE_URL = "http://192.168.1.6";
    public static final String LOGIN_URL = BASE_URL + "/api/v1/sessions";
    private static SharedPreferences mPreferences;

    public static SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public static void setmPreferences(SharedPreferences mPreferences) {
        WebGeneral.mPreferences = mPreferences;
    }




}
