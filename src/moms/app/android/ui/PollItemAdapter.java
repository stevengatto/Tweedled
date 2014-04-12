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
import moms.app.android.model.testing.Comment;

import java.util.List;

/**
 * Created by Steve on 4/5/14.
 */
public class PollItemAdapter extends ArrayAdapter<Comment> {

    private static final String TAG = PollItemAdapter.class.getName();

    private List<Comment> list;
    private Context context;
    private int resource;

    public PollItemAdapter(Context context, int resource, List<Comment> list){
        super(context, resource, list);
        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View currentView = convertView;
        CommentViewHolder holder;

        //if not recyclable view, create new
        if(currentView == null){
            Log.d(TAG, "No recyclable custom view found. New view created.");
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            currentView = inflater.inflate(resource, parent, false);

            //create holder and  set up references to TextView's
            holder = new CommentViewHolder();
            holder.username = (TextView) currentView.findViewById(R.id.tv_comment_username);
            holder.body = (TextView) currentView.findViewById(R.id.tv_comment_body);
            currentView.setTag(holder);
        }

        // get the recycled view (stored in tag)
        else {
            Log.d(TAG, "View recycled");
            holder = (CommentViewHolder)currentView.getTag();
        }

        //reset any variables in holder if view can be recycled
        final Comment currentComment = list.get(position);
        holder.username.setText(currentComment.getUsername());
        holder.body.setText(currentComment.getBody());

        return currentView;
    }

    //method necessary only if comments will be clickable
    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    //holder class to store references to listView sub views
    private class CommentViewHolder {
        TextView username;
        TextView body;
    }

}
