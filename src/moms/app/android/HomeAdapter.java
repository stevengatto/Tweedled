package moms.app.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            holder.imageLeft = (PollImageView) currentView.findViewById(R.id.iv_poll_left);
            holder.imageRight = (PollImageView) currentView.findViewById(R.id.iv_poll_right);
            currentView.setTag(holder);
        }

        else {
            // get the recycled view (stored in tag)
            Log.d(TAG, "View recycled");
            holder = (PollViewHolder)currentView.getTag();
        }

        //reset any variables in holder if view can be recycled
        holder.imageLeft.setImageDrawable(list.get(position));
        holder.imageRight.setImageDrawable(list.get(position));

        return currentView;
    }

    private class PollViewHolder {
        PollImageView imageLeft;
        PollImageView imageRight;
    }

}
