package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import moms.app.android.R;
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

    private static final String TAG = HomeFragment.class.getName();

    private List<Poll> list = new ArrayList<Poll>();
    private View layout;
    private ListView listView;
    private Activity thisActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisActivity = getActivity();

        //create references
        layout = inflater.inflate(R.layout.home_fragment, container, false);
        listView = (ListView) layout.findViewById(R.id.home_list_view);

        //make web call for kitten pictures
        new DownloadImageTask().execute("http://www.zwaldtransport.com/images/placeholders/placeholder1.jpg");

        return layout;
    }

    //terrible class to download placeholder image off for listview
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

            //create mock list of Poll objects to show in list
            Random random = new Random();
            for(int i=1; i<100; i++){
                //set params one by one for clarity
                Poll poll = new Poll(null,null,null,null,null,null);
                poll.setMainTitle("Main Title #"+i);
                poll.setSubTitle("Subtitle #" + i);
                poll.setLeftImage(result);
                poll.setRightImage(result);
                poll.setLeftVotes(random.nextInt(10000));
                poll.setRightVotes(random.nextInt(10000));
                list.add(poll);
            }

            //set up listView adapter and onItemClick listener
            HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    Log.d(TAG, "Entering onItemClick method in Polls ListView");
                    Intent intent = new Intent(thisActivity, PollItemActivity.class);
                    //TODO: add extra to intent with info on which poll was selected
                    startActivity(intent);
                    thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
        }
    }
}