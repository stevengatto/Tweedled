package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.model.testing.ImageResult;
import moms.app.android.ui.ImageSearchAdapter;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 4/26/14.
 */
public class ImageSearchTask {

    private Activity mActivity;
    private JSONObject respond = new JSONObject();
    private List<ImageResult> list;
    private GridView mGridView;

    public ImageSearchTask(Activity activity, GridView gridView)
    {
        this.mActivity = activity;
        mGridView = gridView;
    }

    public void submitRequest(String query)
    {
        ImageSearchAsyncTask imageTask = new ImageSearchAsyncTask(mActivity);
        imageTask.setMessageLoading("Fetching Images...");
        imageTask.execute(WebGeneral.IMAGE_SEARCH_BASE_URL + "&q=" + query.replace(" ", "%20"));
    }

    private void createGridView()
    {
        list = new ArrayList<ImageResult>();

        try {
            //pull results array from google api response
            JSONArray results_array = respond.getJSONObject("responseData").getJSONArray("results");

            //remove thumbnail url and full image url from response data
            for(int i = 0; i < results_array.length(); i++)
            {
                JSONObject result_json = results_array.getJSONObject(i);

                ImageResult imageResult = new ImageResult();
                imageResult.setThumbUrl(result_json.getString("tbUrl"));
//                Log.d(null, "Thumb url: " + result_json.getString("tbUrl"));
                imageResult.setUrl(result_json.getString("url"));
//                Log.d(null, "Url: " + result_json.getString("url"));
                list.add(imageResult);
            }

            //set up gridView adapter
            ImageSearchAdapter adapter = new ImageSearchAdapter(mActivity, R.layout.image_search_item, list);
            mGridView.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d(null, "No array found");
        }

    }

    public void onPostExecuteAction(JSONObject respond)
    {
        try {
            if (respond.getString("responseDetails").equals("null")) {

                Toast.makeText(mActivity, "Successful Image Query",
                        Toast.LENGTH_LONG).show();
                createGridView();
            }
            else
                Toast.makeText(mActivity, respond.getString("responseDetails"),
                        Toast.LENGTH_LONG).show();
            createGridView();
        } catch (Exception e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private class ImageSearchAsyncTask extends UrlJsonAsyncTask{

        public ImageSearchAsyncTask(Context context){
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(urls[0]);
            String response;

            try {
                try {
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(get, responseHandler);
                    respond = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return respond;
        }

        @Override
        protected void onPostExecute(JSONObject respond) {
            onPostExecuteAction(respond);
            super.onPostExecute(respond);
        }
    }
}
