package moms.app.android.communication;

import org.json.JSONObject;

/**
 * Created by klam on 4/16/14.
 */
public interface AsyncTaskInterface {
    JSONObject respond = null;
    public JSONObject submitRequest(String... params);
}
