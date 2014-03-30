package moms.app.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 3/29/14.
 */
public class HomeFragment extends Fragment {

    //mock list of bitmaps for testing
    private List<Drawable> list = new ArrayList<Drawable>();
    private View listView;
    private Activity thisActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //create references
        listView = inflater.inflate(R.layout.home_fragment, container, false);

        thisActivity = getActivity();

        //make web call for kitten pictures
        new DownloadImageTask().execute("http://www.zwaldtransport.com/images/placeholders/placeholder1.jpg");

        return listView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            String url = urls[0];
            try{
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            }catch (Exception e) {
                System.out.println("Exc=" + e);
                return null;
            }
        }

        protected void onPostExecute(Drawable result) {
            for(int i=0; i<100; i++)
                list.add(result);
            HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
            ((ListView) listView).setAdapter(adapter);
        }
    }
}