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

        View currentView = convertView;
        PollViewHolder holder;

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
                    leftHeartVote.setVisibility(View.VISIBLE);
                    rightHeartVote.setVisibility(View.VISIBLE);
                }
            });

            //set click listener for right image
            rightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Right Image Click Received", Toast.LENGTH_SHORT).show();
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
            holder.leftProgBar = (ProgressBar) currentView.findViewById(R.id.pb_poll_left);
            holder.rightProgBar = (ProgressBar) currentView.findViewById(R.id.pb_poll_right);
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

        //reset any variables in holder if view can be recycled
        final Poll currentPoll = list.get(position);
        holder.mainTitle.setText(currentPoll.getMainTitle());
        holder.subTitle.setText(currentPoll.getSubTitle());
        ImageLoader.getInstance().displayImage(currentPoll.getLeftImageUrl(), holder.leftImage,
                null, new ImageLoadingListener(holder.leftProgBar));
        ImageLoader.getInstance().displayImage(currentPoll.getRightImageUrl(), holder.rightImage,
                null, new ImageLoadingListener(holder.rightProgBar));

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
        TextView leftVotes;
        TextView rightVotes;
        RelativeLayout leftVotesHeart;
        RelativeLayout rightVotesHeart;
        ProgressBar leftProgBar;
        ProgressBar rightProgBar;
    }
}
