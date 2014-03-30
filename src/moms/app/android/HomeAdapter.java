package moms.app.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Steve on 3/29/14.
 */
public class HomeAdapter extends ArrayAdapter<Drawable> {

    private static final String TAG = HomeAdapter.class.getName();

    private Context context;
    private int resourceId;
    private List<Drawable> list;

    public HomeAdapter(Context context, int resourceId, List<Drawable> list){
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
            holder.leftTitle = (TextView) currentView.findViewById(R.id.tv_poll_left_title);
            holder.rightTitle = (TextView) currentView.findViewById(R.id.tv_poll_right_title);
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
        holder.leftImage.setImageDrawable(list.get(position));
        holder.rightImage.setImageDrawable(list.get(position));

        //get all data from list so we can Bundle it for click event
        final String mainTitle = (String)holder.mainTitle.getText();
        final String leftTitle = (String)holder.leftTitle.getText();
        final String rightTitle = (String)holder.rightTitle.getText();
        final Drawable leftImage = holder.leftImage.getDrawable();
        final Drawable rightImage = holder.rightImage.getDrawable();
        final String leftVotes = (String)holder.leftVotes.getText();
        final String rightVotes = (String)holder.rightVotes.getText();

        //set onClickListener for poll item
        RelativeLayout pollItem = (RelativeLayout) currentView.findViewById(R.id.custom_poll_item);
        pollItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to poll item fragment
                FragmentManager fm = ((Activity) context).getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                PollItemFragment pollItemFragment = new PollItemFragment();

                Bundle bundle = new Bundle();
                bundle.putString("mainTitle", mainTitle);
                bundle.putString("leftTitle", leftTitle);
                bundle.putString("rightTitle", rightTitle);
                bundle.putString("leftVotes", leftVotes);
                bundle.putString("rightVotes", rightVotes);
                pollItemFragment.setArguments(bundle);

                ft.replace(R.id.main_fragment, pollItemFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return currentView;
    }

    private class PollViewHolder {
        TextView mainTitle;
        TextView leftTitle;
        TextView rightTitle;
        PollImageView leftImage;
        PollImageView rightImage;
        TextView leftVotes;
        TextView rightVotes;
    }

}
