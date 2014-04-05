package moms.app.android.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import moms.app.android.R;
import moms.app.android.model.Poll;

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

            //create holder and  set up references to TextView's
            holder = new PollViewHolder();
            holder.mainTitle = (TextView) currentView.findViewById(R.id.tv_poll_main_title);
            holder.subTitle = (TextView) currentView.findViewById(R.id.tv_poll_sub_title);
            holder.leftImage = (PollImageView) currentView.findViewById(R.id.iv_poll_left);
            holder.rightImage = (PollImageView) currentView.findViewById(R.id.iv_poll_right);
            holder.leftVotes = (TextView) currentView.findViewById(R.id.tv_poll_left_votes);
            holder.rightVotes = (TextView) currentView.findViewById(R.id.tv_poll_right_votes);
            currentView.setTag(holder);
        }

        // get the recycled view (stored in tag)
        else {
            Log.d(TAG, "View recycled");
            holder = (PollViewHolder)currentView.getTag();
        }

        //reset any variables in holder if view can be recycled
        final Poll currentPoll = list.get(position);
        holder.mainTitle.setText(currentPoll.getMainTitle());
        holder.subTitle.setText(currentPoll.getSubTitle());
        holder.leftImage.setImageDrawable(currentPoll.getLeftImage());
        holder.rightImage.setImageDrawable(currentPoll.getRightImage());
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
        PollImageView leftImage;
        PollImageView rightImage;
        TextView leftVotes;
        TextView rightVotes;
    }

}
