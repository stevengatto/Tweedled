package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
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
    private ProgressBar mProgressBar;
    private String query;
    private final int TOTAL_IMAGES = 63;
    private boolean adapterSet = false;

    public ImageSearchTask(Activity activity, GridView gridView, ProgressBar progressBar)
    {
        this.mActivity = activity;
        mGridView = gridView;
        mProgressBar = progressBar;
        list = new ArrayList<ImageResult>();
    }

    public void submitRequest(String query)
    {
        //encode query
        this.query = WebGeneral.encodeString(query);

        if(this.query == null){
            Toast.makeText(mActivity, "Please use only letters, numbers, and spaces", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d(null, this.query);

        ImageSearchAsyncTask imageTask = new ImageSearchAsyncTask(mActivity);
        imageTask.execute(WebGeneral.IMAGE_SEARCH_BASE_URL + "&q=" + this.query);
    }

    public void submitRequestFromInt(int start)
    {
        ImageSearchAsyncTask imageTask = new ImageSearchAsyncTask(mActivity);
        imageTask.execute(WebGeneral.IMAGE_SEARCH_BASE_URL + "&q=" + query
                + "&start=" + Integer.toString(start));
    }

    private void createGridView()
    {
        if(list.size() >= TOTAL_IMAGES && adapterSet == false){
            //set up gridView adapter
            setCurrentListToAdapter();
            return;
        }

        //keep making web calls with new start if we have not downloaded 100 images yet
        if(list.size() < TOTAL_IMAGES)
        {
            try {
                //pull results array from google api response
                JSONArray results_array = respond.getJSONObject("responseData").getJSONArray("results");

                Log.d(null, "************Array Length: " + results_array.length());
                //remove thumbnail url and full image url from response data
                for(int i = 0; i < results_array.length(); i++)
                {
                    if(list.size() < TOTAL_IMAGES){
                        JSONObject result_json = results_array.getJSONObject(i);

                        ImageResult imageResult = new ImageResult();
                        imageResult.setThumbUrl(result_json.getString("tbUrl"));
                        imageResult.setUrl(result_json.getString("url"));
                        list.add(imageResult);
                        Log.d(null, "Image " + list.size() + " added to list as " + result_json.getString("title"));
                    }
                }
                submitRequestFromInt(list.size());

            } catch (JSONException e) {
                Log.d(null, "No array found");
            }
        }
}
    
    public void setCurrentListToAdapter(){
        mProgressBar.setVisibility(View.GONE);
        ImageSearchAdapter adapter = new ImageSearchAdapter(mActivity, R.layout.image_search_item, list);
        mGridView.setAdapter(adapter);
        adapterSet = true;
//        mActivity.setProgressBarIndeterminateVisibility(false);
    }


    public void onPostExecuteAction(JSONObject respond)
    {
        try {

            //if no error, spawn new web call
            if (respond.getInt("responseStatus") == 200 ) {
                createGridView();
            }

            //if error occurs, set current list of images to grid view
            else
                setCurrentListToAdapter();

        } catch (Exception e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
            mActivity.setProgressBarIndeterminateVisibility(false);
        }
    }

    private class ImageSearchAsyncTask extends UrlJsonAsyncTask{

        public ImageSearchAsyncTask(Context context){
            super(context);
        }

        //override on pre execute so that progress dialog doesn't show up recursively
        @Override
        protected void onPreExecute() {
            Log.d(null, "New Web Call Spawned, imageCount = " + list.size() + "\n");
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
