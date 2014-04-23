package moms.app.android.communication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.model.testing.Poll;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by klam on 4/17/14.
 * class for voting web calls
 */
public class VotingTask implements TaskInterface{
    private Context mContext;
    ImageView mLeftImage;
    ImageView mRightImage;
    RelativeLayout mLeftVotesHeart;
    RelativeLayout mRightVotesHeart;
    TextView mVote1;
    TextView mVote2;
    Poll mPoll;
    JSONObject respond = new JSONObject();
    public VotingTask(Context context, ImageView leftImage, ImageView rightImage, RelativeLayout leftVotesHeart,
                      RelativeLayout rightVotesHeart,TextView vote1, TextView vote2, Poll poll)
    {
        this.mContext = context;
        this.mLeftImage = leftImage;
        this.mRightImage = rightImage;
        this.mLeftVotesHeart = leftVotesHeart;
        this.mRightVotesHeart = rightVotesHeart;
        this.mVote1 = vote1;
        this.mVote2 = vote2;
        this.mPoll = poll;

    }

    public void setOnClickListenerForImages()
    {
        mLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Left Image Click Received", Toast.LENGTH_SHORT).show();
                try {
                    respond.put("type", "one");
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON", "" + e);
                }
                VotingAsyncTask votingTask = new VotingAsyncTask(mContext);
                votingTask.execute(WebGeneral.generateVoteURL(mPoll.getId()));
                mLeftVotesHeart.setVisibility(View.VISIBLE);
                mRightVotesHeart.setVisibility(View.VISIBLE);
            }
        });

        mRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    respond.put("type", "two");
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON", "" + e);
                }
                VotingAsyncTask votingTask = new VotingAsyncTask(mContext);
                votingTask.execute(WebGeneral.generateVoteURL(mPoll.getId()));
                mLeftVotesHeart.setVisibility(View.VISIBLE);
                mRightVotesHeart.setVisibility(View.VISIBLE);
            }
        });

    }

    public void onPostExecuteAction(JSONObject respond)
    {
        try {
            if (respond.getBoolean("success")) {
                Toast.makeText(mContext, respond.getString("info"),
                        Toast.LENGTH_LONG).show();
                //Log.e("PostExecute", ""+ respond.getInt("vote_one") + respond.getInt("vote_two"));
                mVote1.setText("" + respond.getInt("vote_one"));
                mVote2.setText(""+respond.getInt("vote_two"));
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }



    private class VotingAsyncTask  extends UrlJsonAsyncTask {
        public VotingAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            String response;
            try {
                try {
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    respond.put("_method", "post");
                    respond.put("authenticity", "");
                    respond.put("auth_token", WebGeneral.getsPreferences().getString("auth_token", ""));
                    StringEntity se = new StringEntity(respond.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");
                    response = client.execute(post, responseHandler);
                    respond = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e + " " + urls[0]);
                    respond.put("info",
                            "Failed to Cast Vote. Retry!");
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
