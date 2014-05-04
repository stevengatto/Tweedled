package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.model.testing.Poll;
import moms.app.android.outdated.HomeAdapter;
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
 * Created by klam on 4/17/14.
 * Class for fetching polls
 */
public class FetchingPollTask {

    private Activity mActivity;
    JSONObject respond = new JSONObject();
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
            JSONArray polls_array = respond.getJSONArray("polls");
            int poll_count = respond.getInt("poll_count");

            for(int i = poll_count - 1; i >= 0 ;i--)
            {

                JSONObject poll_json = polls_array.getJSONObject(i);
                String image_1_url = WebGeneral.BASE_URL + poll_json.getString("attachment_1_url");
                String image_2_url = WebGeneral.BASE_URL + poll_json.getString("attachment_2_url");

                String question = poll_json.getString("question");
                String subTitleLeft = poll_json.getString("title_one");
                String subTitleRight = poll_json.getString("title_two");
                Poll poll = new Poll(question,subTitleLeft,subTitleRight,null,null,null,null);

                poll.setLeftVotes(poll_json.getInt("vote_one"));
                poll.setRightVotes(poll_json.getInt("vote_two"));

                if(poll_json.getBoolean("is_picture1_url"))
                    poll.setLeftImageUrl(poll_json.getString("attachment_1_url"));
                else
                    poll.setLeftImageUrl(image_1_url);

                if(poll_json.getBoolean("is_picture2_url"))
                    poll.setRightImageUrl(poll_json.getString("attachment_2_url"));
                else
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

    }


    public void onPostExecuteAction(JSONObject respond)
    {
        try {
            if (respond.getBoolean("success")) {

                Toast.makeText(mActivity, respond.getString("info"),
                        Toast.LENGTH_LONG).show();
                createListView();
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
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
                    respond.put("success", false);
                    respond = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    respond.put("info",
                            "Failed fetching polls. Retry!");
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
