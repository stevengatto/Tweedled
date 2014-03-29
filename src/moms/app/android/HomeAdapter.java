package moms.app.android;

import android.app.Activity;
import android.content.Context;
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
public class HomeAdapter extends ArrayAdapter<String> {

    private static final String TAG = HomeAdapter.class.getName();

    private Context context;
    private int resourceId;

    public HomeAdapter(Context context, int resourceId, List<String> list){
        super(context, resourceId, list);

        this.context = context;
        this.resourceId = resourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View currentView = convertView;

        if(currentView == null){
            Log.d(TAG, "No recyclable custom view found. New view created.");
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            currentView = inflater.inflate(resourceId, parent, false);

            //create holder and  set up references to TextView's
            //holder = new PollViewHolder();
            //currentView.setTag(holder);
        }

        else {
            // get the recycled view (stored in tag)
            Log.d(TAG, "View recycled");
            //holder = (PollViewHolder)currentView.getTag();
        }

        //reset any variables in holder if view can be recycled

        return currentView;
    }

    private class PollViewHolder {
        //put variables for storage here
    }

}
