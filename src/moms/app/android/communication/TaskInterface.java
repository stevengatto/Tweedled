package moms.app.android.communication;

import org.json.JSONObject;

/**
 * Created by klam on 4/16/14.
 * interface for all the task classes
 */
public interface TaskInterface {
    public void onPostExecuteAction(JSONObject respond);
}
