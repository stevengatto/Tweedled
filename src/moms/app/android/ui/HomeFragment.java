package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.savagelook.android.UrlJsonAsyncTask;
import com.wilson.android.library.DrawableManager;
import moms.app.android.R;
import moms.app.android.model.testing.Poll;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private Drawable mImage1;
    private Drawable mImage2;
    private String URL = "http://10.0.0.18/polls";//getString(R.string.url) + "/polls";
    private String IMAGE_URL_PREFIX = "http://10.0.0.18/system/polls/";//getString(R.string.url) + "/system/polls/";
    private String URL_MISSING_IMAGE = "http://10.0.0.18/images/missing.png";//getString(R.string.url) + "/images/missing.png";
    private JSONObject json = new JSONObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisActivity = getActivity();

        //create references
        layout = inflater.inflate(R.layout.home_fragment, container, false);
        listView = (ListView) layout.findViewById(R.id.home_list_view);

        fetchingPolls();

        //set up listView adapter and onItemClick listener
//       HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
//        listView.setAdapter(adapter);
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
                //intent.putExtra("leftImage", list.get(position).getLeftImage());
                //intent.putExtra("rightImage", list.get(position).getRightImage());
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
               mImage1 = result;
        }
   }
    private void fetchingPolls()
    {
        FetchingPollTask pollTask = new FetchingPollTask(getActivity());
        pollTask.setMessageLoading("Fetching Polls...");
        pollTask.execute(URL);
    }

    private void createListView()
    {
        try {
            JSONArray polls_array = json.getJSONArray("polls");
            int poll_count = json.getInt("poll_count");
            Random random = new Random();
            DrawableManager drawableManager = new DrawableManager();
            for(int i = 0; i < poll_count;i++)
            {

                JSONObject poll_json = polls_array.getJSONObject(i);
                String image_1_url = (!poll_json.getString("attachment_1_file_name").equals(""))
                                    ? IMAGE_URL_PREFIX + poll_json.getString("id") + "/original/" + poll_json.getString("attachment_1_file_name")
                                    : URL_MISSING_IMAGE;
                String image_2_url = (!poll_json.getString("attachment_1_file_name").equals(""))
                        ? IMAGE_URL_PREFIX + poll_json.getString("id") + "/original/" + poll_json.getString("attachment_2_file_name")
                        : URL_MISSING_IMAGE;


                String question = poll_json.getString("question");
                String subtitle = poll_json.getString("title_one") + " Or " + poll_json.getString("title_two");
                Poll poll = new Poll(question,subtitle,null,null,null,null);
                poll.setLeftVotes(random.nextInt(10000));
                poll.setRightVotes(random.nextInt(10000));
                poll.setLeftImage(image_1_url);
                poll.setRightImage(image_2_url);
               // downloadImage1.execute(image_1_url);
               // poll.setLeftImage(mImage1);
                //downloadImage2.execute(image_2_url);
               // poll.setLeftImage(mImage1);
               // poll.setRightImage(drawableManager.fetchDrawable(image_2_url));
                list.add(poll);

            }

            //set up listView adapter and onItemClick listener
            HomeAdapter adapter = new HomeAdapter(thisActivity, R.layout.poll_item, list);
            listView.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }


    private class FetchingPollTask extends UrlJsonAsyncTask {
        public FetchingPollTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            String response;

            try {
                try {
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Failed fetching polls. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {

                    Toast.makeText(context, json.getString("info"),
                            Toast.LENGTH_LONG).show();
                    createListView();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }




}