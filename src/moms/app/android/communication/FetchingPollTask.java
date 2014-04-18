package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.model.testing.Poll;
import moms.app.android.ui.HomeAdapter;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by klam on 4/17/14.
 */
public class FetchingPollTask {

    private Activity mActivity;
    JSONObject json = new JSONObject();
    private List<Poll> list = new ArrayList<Poll>();
    private ListView mListView;



    public FetchingPollTask(Activity activity, ListView listView)
    {
        this.mActivity = activity;
        mListView = listView;
    }

    public void submitRequest()
    {
        FetchingPollAsyncTask pollTask = new FetchingPollAsyncTask(mActivity);
        pollTask.setMessageLoading("Fetching Polls...");
        pollTask.execute(WebGeneral.FETCHING_POLL_URL);
    }

    private void createListView()
    {
        try {
            JSONArray polls_array = json.getJSONArray("polls");
            int poll_count = json.getInt("poll_count");
            Random random = new Random();
            for(int i = poll_count - 1; i >= 0 ;i--)
            {

                JSONObject poll_json = polls_array.getJSONObject(i);
                String image_1_url = WebGeneral.BASE_URL + poll_json.getString("attachment_1_url");
                String image_2_url = WebGeneral.BASE_URL + poll_json.getString("attachment_2_url");

                String question = poll_json.getString("question");
                String subtitle = poll_json.getString("title_one") + " Or " + poll_json.getString("title_two");
                Poll poll = new Poll(question,subtitle,null,null,null,null);

                poll.setLeftVotes(poll_json.getInt("vote_one"));
                poll.setRightVotes(poll_json.getInt("vote_two"));
                poll.setLeftImageUrl(image_1_url);
                poll.setRightImageUrl(image_2_url);
                poll.setId(poll_json.getInt("id"));

                list.add(poll);

            }

            //set up listView adapter and onItemClick listener
            HomeAdapter adapter = new HomeAdapter(mActivity, R.layout.poll_item, list);
            mListView.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }





    private class FetchingPollAsyncTask extends UrlJsonAsyncTask {
        public FetchingPollAsyncTask(Context context) {
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
                    json.put("success",false);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Failed fetching polls. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {

                    Toast.makeText(context, json.getString("info"),
                            Toast.LENGTH_LONG).show();
                    createListView();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }
}
