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
import moms.app.android.model.Poll;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Steve on 3/29/14.
 */
public class HomeFragment extends Fragment {

    //mock list of bitmaps for testing
    private List<Poll> list = new ArrayList<Poll>();
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

            Random random = new Random();
            //create mock list of Poll objects to show in list
            for(int i=1; i<100; i++){
                //set params one by one for clarity
                Poll poll = new Poll(null,null,null,null,null,null,null);
                poll.setMainTitle("Poll Title #"+i);
                poll.setLeftTitle("Left Sub #" + i);
                poll.setRightTitle("Right Sub #" + i);
                poll.setLeftImage(result);
                poll.setRightImage(result);
                poll.setLeftVotes(random.nextInt(100));
                poll.setRightVotes(random.nextInt(100));
                list.add(poll);
            }
            HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
            ((ListView) listView).setAdapter(adapter);
        }
    }
}