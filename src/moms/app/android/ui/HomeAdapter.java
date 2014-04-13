package moms.app.android.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.login.Login;
import moms.app.android.model.testing.Poll;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import moms.app.android.login.Login.*;
import java.io.IOException;
import java.util.List;

/**
 * ListView adapter for the polls page
 */
public class HomeAdapter extends ArrayAdapter<Poll> {

    private static final String TAG = HomeAdapter.class.getName();

    private Context context;
    private int resourceId;
    private List<Poll> list;
    int mVote;
    final String URL = "http://107.170.50.231/polls";
    public HomeAdapter(Context context, int resourceId, List<Poll> list){
        super(context, resourceId, list);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .defaultDisplayImageOptions(defaultOptions)
        .build();


        ImageLoader.getInstance().init(config);

        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Poll currentPoll = list.get(position);
        View currentView = convertView;
        final PollViewHolder holder;

        //if not recyclable view, create new
        if(currentView == null){
            Log.d(TAG, "No recyclable custom view found. New view created.");
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            currentView = inflater.inflate(resourceId, parent, false);

            //get references to two images
            ImageView leftImage = (ImageView) currentView.findViewById(R.id.iv_poll_left);
            ImageView rightImage = (ImageView) currentView.findViewById(R.id.iv_poll_right);

            //get references to layouts with heart and vote
            final RelativeLayout leftHeartVote = (RelativeLayout) currentView
                    .findViewById(R.id.poll_left_heart_vote_layout);
            final RelativeLayout rightHeartVote = (RelativeLayout) currentView
                    .findViewById(R.id.poll_right_heart_vote_layout);

            //set click listener for left image
            leftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Left Image Click Received", Toast.LENGTH_SHORT).show();
                    mVote = 1;
                    VotingTask votingTask = new VotingTask(getContext());
                    //votingTask.setMessageLoading("Casting Vote");
                    votingTask.execute(URL+"/"+currentPoll.getId()+"/vote");
                    leftHeartVote.setVisibility(View.VISIBLE);
                    rightHeartVote.setVisibility(View.VISIBLE);
                }
            });

            //set click listener for right image
            rightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Right Image Click Received", Toast.LENGTH_SHORT).show();
                    mVote = 2;
                    VotingTask votingTask = new VotingTask(getContext());
                    //votingTask.setMessageLoading("Casting Vote");
                    votingTask.execute(URL+"/"+currentPoll.getId()+"/vote");
                    leftHeartVote.setVisibility(View.VISIBLE);
                    rightHeartVote.setVisibility(View.VISIBLE);
                }
            });

            //create holder and  set up references to TextView's
            holder = new PollViewHolder();
            holder.mainTitle = (TextView) currentView.findViewById(R.id.tv_poll_main_title);
            holder.subTitle = (TextView) currentView.findViewById(R.id.tv_poll_sub_title);
            holder.leftImage = (ImageView) currentView.findViewById(R.id.iv_poll_left);
            holder.rightImage = (ImageView) currentView.findViewById(R.id.iv_poll_right);
            holder.leftVotes = (TextView) currentView.findViewById(R.id.tv_poll_left_votes);
            holder.rightVotes = (TextView) currentView.findViewById(R.id.tv_poll_right_votes);
            holder.leftVotesHeart = leftHeartVote;
            holder.rightVotesHeart = rightHeartVote;
            currentView.setTag(holder);
        }

        // get the recycled view (stored in tag)
        else {
            Log.d(TAG, "View recycled");
            holder = (PollViewHolder)currentView.getTag();
            holder.leftVotesHeart.setVisibility(View.GONE);
            holder.rightVotesHeart.setVisibility(View.GONE);
        }

        //set click listener for left image
        holder.leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Left Image Click Received", Toast.LENGTH_SHORT).show();
                mVote = 1;
                VotingTask votingTask = new VotingTask(getContext());
                //votingTask.setMessageLoading("Casting Vote");
                votingTask.execute(URL+"/"+currentPoll.getId()+"/vote");
                holder.leftVotes.setText((currentPoll.getLeftVotes()+1) + "");
                holder.leftVotesHeart.setVisibility(View.VISIBLE);
                holder.rightVotesHeart.setVisibility(View.VISIBLE);
            }
        });

        //set click listener for right image
        holder.rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Right Image Click Received", Toast.LENGTH_SHORT).show();
                mVote = 2;
                VotingTask votingTask = new VotingTask(getContext());
                //votingTask.setMessageLoading("Casting Vote");
                votingTask.execute(URL+"/"+currentPoll.getId()+"/vote");
                holder.rightVotes.setText((currentPoll.getRightVotes()+1) + "");
                holder.leftVotesHeart.setVisibility(View.VISIBLE);
                holder.rightVotesHeart.setVisibility(View.VISIBLE);
            }
        });

        //reset any variables in holder if view can be recycled

        holder.mainTitle.setText(currentPoll.getMainTitle());
        holder.subTitle.setText(currentPoll.getSubTitle());
        ImageLoader.getInstance().displayImage(currentPoll.getLeftImage(),holder.leftImage);
        ImageLoader.getInstance().displayImage(currentPoll.getRightImage(),holder.rightImage);

        holder.leftVotes.setText(currentPoll.getLeftVotes().toString());
        holder.rightVotes.setText(currentPoll.getRightVotes().toString());

        return currentView;
    }

    private class VotingTask  extends AsyncTask<String, Void, JSONObject> {
        public VotingTask(Context context) {

        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            String response;
            JSONObject json = null;
            try {
                try {
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    json = new JSONObject();
                    json.put("_method","post");
                    json.put("authenticity","");
                    json.put("auth_token", Login.getSharedPreferences().getString("AuthToken",""));
                    if(mVote == 1)
                        json.put("type","one");
                    else
                        json.put("type","two");
                    StringEntity se = new StringEntity(json.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Failed to Cast Vote. Retry!");
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
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }




    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    //holder class to store references to listView sub views
    private class PollViewHolder {
        TextView mainTitle;
        TextView subTitle;
        ImageView leftImage;
        ImageView rightImage;
        TextView leftVotes;
        TextView rightVotes;
        RelativeLayout leftVotesHeart;
        RelativeLayout rightVotesHeart;
    }
}
