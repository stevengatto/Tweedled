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

        //create mock list of Poll objects to show in list
        Random random = new Random();
        for(int i=0; i<=23; i++){
            //set params one by one for clarity
            Poll poll = new Poll(null,null,null,null,null,null);
            poll.setLeftVotes(random.nextInt(10000));
            poll.setRightVotes(random.nextInt(10000));
            list.add(poll);
        }

        //set static images
        list.get(0).setLeftImage(R.drawable.t1);
        list.get(0).setRightImage(R.drawable.t2);
        list.get(1).setLeftImage(R.drawable.t3);
        list.get(1).setRightImage(R.drawable.t4);
        list.get(2).setLeftImage(R.drawable.t5);
        list.get(2).setRightImage(R.drawable.t6);
        list.get(3).setLeftImage(R.drawable.t7);
        list.get(3).setRightImage(R.drawable.t8);
        list.get(4).setLeftImage(R.drawable.t9);
        list.get(4).setRightImage(R.drawable.t10);
        list.get(5).setLeftImage(R.drawable.t11);
        list.get(5).setRightImage(R.drawable.t12);
        list.get(6).setLeftImage(R.drawable.t13);
        list.get(6).setRightImage(R.drawable.t14);
        list.get(7).setLeftImage(R.drawable.t15);
        list.get(7).setRightImage(R.drawable.t16);
        list.get(8).setLeftImage(R.drawable.t17);
        list.get(8).setRightImage(R.drawable.t18);
        list.get(9).setLeftImage(R.drawable.t19);
        list.get(9).setRightImage(R.drawable.t20);
        list.get(10).setLeftImage(R.drawable.t21);
        list.get(10).setRightImage(R.drawable.t22);
        list.get(11).setLeftImage(R.drawable.t23);
        list.get(11).setRightImage(R.drawable.t24);
        list.get(12).setLeftImage(R.drawable.t25);
        list.get(12).setRightImage(R.drawable.t26);
        list.get(13).setLeftImage(R.drawable.t27);
        list.get(13).setRightImage(R.drawable.t28);
        list.get(14).setLeftImage(R.drawable.t29);
        list.get(14).setRightImage(R.drawable.t30);
        list.get(15).setLeftImage(R.drawable.t31);
        list.get(15).setRightImage(R.drawable.t32);
        list.get(16).setLeftImage(R.drawable.t33);
        list.get(16).setRightImage(R.drawable.t34);
        list.get(17).setLeftImage(R.drawable.t35);
        list.get(17).setRightImage(R.drawable.t36);
        list.get(18).setLeftImage(R.drawable.t37);
        list.get(18).setRightImage(R.drawable.t38);
        list.get(19).setLeftImage(R.drawable.t39);
        list.get(19).setRightImage(R.drawable.t40);
        list.get(20).setLeftImage(R.drawable.t41);
        list.get(20).setRightImage(R.drawable.t42);
        list.get(21).setLeftImage(R.drawable.t43);
        list.get(21).setRightImage(R.drawable.t44);
        list.get(22).setLeftImage(R.drawable.t45);
        list.get(22).setRightImage(R.drawable.t46);
        list.get(23).setLeftImage(R.drawable.t47);
        list.get(23).setRightImage(R.drawable.t48);

        //set static main titles
        list.get(0).setMainTitle("Which hat is better?");
        list.get(1).setMainTitle("Better way to put infant to sleep?");
        list.get(2).setMainTitle("Which costume is cuter?");
        list.get(3).setMainTitle("Which costume is cuter?");
        list.get(4).setMainTitle("Which costume is cuter");
        list.get(5).setMainTitle("Which costume is cuter?");
        list.get(6).setMainTitle("Which costume is cuter?");
        list.get(7).setMainTitle("Which food is better?");
        list.get(8).setMainTitle("Which animal is better for infants?");
        list.get(9).setMainTitle("Which toy is more engaging?");
        list.get(10).setMainTitle("Which toy is more engaging?");
        list.get(11).setMainTitle("Which costume is cuter?");
        list.get(12).setMainTitle("Which doll is better?");
        list.get(13).setMainTitle("Which action figure is better?");
        list.get(14).setMainTitle("Which action figure is better?");
        list.get(15).setMainTitle("Which technology is better for infants?");
        list.get(16).setMainTitle("Which name do you prefer?");
        list.get(17).setMainTitle("Which name do you prefer?");
        list.get(18).setMainTitle("Which name do you prefer?");
        list.get(19).setMainTitle("Which name do you prefer?");
        list.get(20).setMainTitle("Which name do you prefer?");
        list.get(21).setMainTitle("Which name do you prefer?");
        list.get(22).setMainTitle("Which name do you prefer?");
        list.get(23).setMainTitle("Which form of transportation?");

        //set static sub titles
        list.get(0).setSubTitle("blue or pink");
        list.get(1).setSubTitle("reading or rocking");
        list.get(2).setSubTitle("lion or chef");
        list.get(3).setSubTitle("superwoman or ketchup");
        list.get(4).setSubTitle("monkey or joker");
        list.get(5).setSubTitle("skunk or bee");
        list.get(6).setSubTitle("flamingo or bear");
        list.get(7).setSubTitle("porridge or fruit");
        list.get(8).setSubTitle("dogs or cats");
        list.get(9).setSubTitle("farm toy or car toy");
        list.get(10).setSubTitle("phone toy or legos");
        list.get(11).setSubTitle("kermit or miss piggy");
        list.get(12).setSubTitle("barbie or polly pocket");
        list.get(13).setSubTitle("power rangers or transformers");
        list.get(14).setSubTitle("sonic the hedgehog or ninja turtles");
        list.get(15).setSubTitle("ipad or computer");
        list.get(16).setSubTitle("Sophia or Emma");
        list.get(17).setSubTitle("Olivia or Isabella");
        list.get(18).setSubTitle("Ava or Emily");
        list.get(19).setSubTitle("Abigail or Mia");
        list.get(20).setSubTitle("Madison or Elizabeth");
        list.get(21).setSubTitle("Jacob or Mason");
        list.get(22).setSubTitle("Ethan or Noah");
        list.get(23).setSubTitle("Baby Backpack or Baby Stroller");

        //set up listView adapter and onItemClick listener
        HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//                Log.d(TAG, "Entering onItemClick method in Polls ListView");
//                Intent intent = new Intent(thisActivity, PollItemActivity.class);
//                intent.putExtra("mainTitle", list.get(position).getMainTitle());
//                intent.putExtra("subTitle", list.get(position).getSubTitle());
//                intent.putExtra("leftImage", list.get(position).getLeftImage());
//                intent.putExtra("rightImage", list.get(position).getRightImage());
//                intent.putExtra("leftVotes", list.get(position).getLeftVotes());
//                intent.putExtra("rightVotes", list.get(position).getRightVotes());
//                startActivity(intent);
//                thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
//            }
//        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "Entering onItemLongClick method in Polls ListView");
                Toast.makeText(getActivity(), "Poll Long Click Received", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thisActivity, PollItemActivity.class);
                intent.putExtra("mainTitle", list.get(position).getMainTitle());
                intent.putExtra("subTitle", list.get(position).getSubTitle());
                intent.putExtra("leftImage", list.get(position).getLeftImage());
                intent.putExtra("rightImage", list.get(position).getRightImage());
                intent.putExtra("leftVotes", list.get(position).getLeftVotes());
                intent.putExtra("rightVotes", list.get(position).getRightVotes());
                startActivity(intent);
                thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                return true;
            }
        });

        //make web call for kitten pictures
        //new DownloadImageTask().execute("http://www.zwaldtransport.com/images/placeholders/placeholder1.jpg");

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
                //poll.setLeftImage(result);
                //poll.setRightImage(result);
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
                    thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                }
            });
        }
    }
}