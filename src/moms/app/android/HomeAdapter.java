package moms.app.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import moms.app.android.model.Poll;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Steve on 3/29/14.
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

        else {
            // get the recycled view (stored in tag)
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

        //set onClickListener for poll item
        LinearLayout pollItem = (LinearLayout) currentView.findViewById(R.id.custom_poll_item);
        pollItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PollItemActivity.class);
                //TODO: add extra to intent with info on which poll was selected
                context.startActivity(intent);

            }
        });

        return currentView;
    }

    private class PollViewHolder {
        TextView mainTitle;
        TextView subTitle;
        PollImageView leftImage;
        PollImageView rightImage;
        TextView leftVotes;
        TextView rightVotes;
    }

}
