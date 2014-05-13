package moms.app.android.communication;

/**
 * Created by klam on 5/12/14.
 */
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.model.testing.Comment;
import moms.app.android.ui.PollItemAdapter;
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
public class FetchingCommentTask {

    private Context mContext;
    private List<Comment> list = new ArrayList<Comment>();
    private ListView mListView;



    public FetchingCommentTask(Context context, ListView listView)
    {
        this.mContext = context;
        mListView = listView;
    }

    public void submitRequest(int id)
    {
        FetchingCommentAsyncTask pollTask = new FetchingCommentAsyncTask(mContext);
        pollTask.setMessageLoading("Fetching Comments...");
        pollTask.execute(WebGeneral.generateCommentURL(id));
    }

    private void createListView(JSONObject respond)
    {
        try {
            JSONArray comments_array = respond.getJSONArray("comments");

            for(int i = comments_array.length() - 1; i >= 0 ;i--)
            {
                JSONObject currentComment = comments_array.getJSONObject(i);
                Comment comment = new Comment(null,null);
                comment.setUsername(currentComment.getString("username"));
                comment.setBody(currentComment.getString("body"));
                list.add(comment);

            }

            //set up listView adapter and onItemClick listener
            PollItemAdapter adapter = new PollItemAdapter(mContext, R.layout.comment_list_item, list);
            mListView.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void onPostExecuteAction(JSONObject respond)
    {
        try {
            if (respond.getBoolean("success")) {

//                Toast.makeText(mActivity, respond.getString("info"),
//                        Toast.LENGTH_LONG).show();
                createListView(respond);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }


    private class FetchingCommentAsyncTask extends UrlJsonAsyncTask {
        public FetchingCommentAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(urls[0]);
            String response;
            JSONObject respond = new JSONObject();
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
                            "Failed fetching comments. Retry!");
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
