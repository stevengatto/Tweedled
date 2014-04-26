package moms.app.android.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import moms.app.android.R;
import moms.app.android.communication.VotingTask;
import moms.app.android.login.Login;
import moms.app.android.model.testing.Poll;
import moms.app.android.utils.ImageLoadingListener;

import java.util.List;

/**
 * ListView adapter for the polls page
 */
public class HomeAdapter extends ArrayAdapter<Poll> {

    private static final String TAG = HomeAdapter.class.getName();

    private Context context;
    private int resourceId;
    private List<Poll> list;

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



            //get references to layouts with heart and vote
            final RelativeLayout leftHeartVote = (RelativeLayout) currentView
                    .findViewById(R.id.poll_left_heart_vote_layout);
            final RelativeLayout rightHeartVote = (RelativeLayout) currentView
                    .findViewById(R.id.poll_right_heart_vote_layout);


            //create holder and  set up references to TextView's
            holder = new PollViewHolder();
            holder.mainTitle = (TextView) currentView.findViewById(R.id.tv_poll_main_title);
            holder.subTitle = (TextView) currentView.findViewById(R.id.tv_poll_sub_title);
            holder.leftImage = (ImageView) currentView.findViewById(R.id.iv_poll_left);
            holder.rightImage = (ImageView) currentView.findViewById(R.id.iv_poll_right);
            holder.leftProgressBar = (ProgressBar) currentView.findViewById(R.id.pb_poll_left);
            holder.rightProgressBar = (ProgressBar) currentView.findViewById(R.id.pb_poll_right);
            holder.leftVotes = (TextView) currentView.findViewById(R.id.tv_poll_left_votes);
            holder.rightVotes = (TextView) currentView.findViewById(R.id.tv_poll_right_votes);
            holder.leftVotesHeart = leftHeartVote;
            holder.rightVotesHeart = rightHeartVote;
            holder.vote1text = (TextView) currentView.findViewById(R.id.tv_poll_left_votes);
            holder.vote2text = (TextView) currentView.findViewById(R.id.tv_poll_right_votes);

            currentView.setTag(holder);
        }

        // get the recycled view (stored in tag)
        else {
            Log.d(TAG, "View recycled");
            holder = (PollViewHolder)currentView.getTag();
            holder.leftVotesHeart.setVisibility(View.GONE);
            holder.rightVotesHeart.setVisibility(View.GONE);
        }
        //setting listeners for voting
        VotingTask votingTask = new VotingTask(this.context, holder.leftImage, holder.rightImage, holder.leftVotesHeart,
                holder.rightVotesHeart, holder.vote1text, holder.vote2text,currentPoll);
        votingTask.setOnClickListenerForImages();

        //reset any variables in holder if view can be recycled

        holder.mainTitle.setText(currentPoll.getMainTitle());
        holder.subTitle.setText(currentPoll.getSubTitle());
        ImageLoader.getInstance().displayImage(currentPoll.getLeftImageUrl(),holder.leftImage,
                new ImageLoadingListener(holder.leftProgressBar));
        ImageLoader.getInstance().displayImage(currentPoll.getRightImageUrl(),holder.rightImage,
                new ImageLoadingListener(holder.rightProgressBar));
        holder.leftVotes.setText(currentPoll.getLeftVotes().toString());
        holder.rightVotes.setText(currentPoll.getRightVotes().toString());

        return currentView;
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
        ProgressBar leftProgressBar;
        ProgressBar rightProgressBar;
        TextView leftVotes;
        TextView rightVotes;
        RelativeLayout leftVotesHeart;
        RelativeLayout rightVotesHeart;
        TextView vote1text;
        TextView vote2text;
    }
}
