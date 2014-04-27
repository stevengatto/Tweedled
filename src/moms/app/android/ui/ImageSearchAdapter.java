package moms.app.android.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import moms.app.android.R;
import moms.app.android.model.testing.ImageResult;
import moms.app.android.utils.ImageLoadingListener;

import java.util.List;

/**
 * Created by Steve on 4/26/14.
 */
public class ImageSearchAdapter extends ArrayAdapter<ImageResult> {

    private static final String TAG = ImageSearchAdapter.class.getName();

    private Context context;
    private int resourceId;
    private List<ImageResult> list;

    public ImageSearchAdapter(Context context, int resourceId, List<ImageResult> list){
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

        Log.d(null, list.toString());
    }

    public View getView(int position, View convertView, ViewGroup parent){

        ImageResult currentResult = list.get(position);
        ImageSearchViewHolder holder;
        View currentView = convertView;

        //if not recyclable view, create new
        if(currentView == null){
            Log.d(TAG, "No recyclable custom view found. New view created.");
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            currentView = inflater.inflate(resourceId, parent, false);

            //create holder and  set up references to TextView's
            holder = new ImageSearchViewHolder();
            holder.imageView = (ImageView) currentView.findViewById(R.id.iv_image_search);
            holder.progressBar = (ProgressBar) currentView.findViewById(R.id.pb_image_search);

            currentView.setTag(holder);
        }

        // get the recycled view (stored in tag)
        else {
            Log.d(TAG, "View recycled");
            holder = (ImageSearchViewHolder)currentView.getTag();
        }

        ImageLoader.getInstance().displayImage(currentResult.getThumbUrl(),holder.imageView,
                new ImageLoadingListener(holder.progressBar));

        return currentView;
    }

    //holder class to store references to gridView sub views
    private class ImageSearchViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}
