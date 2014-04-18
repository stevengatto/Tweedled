package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import moms.app.android.R;
import moms.app.android.communication.FetchingPollTask;


/**
 * Created by Steve on 3/29/14.
 */
public class HomeFragment extends Fragment {

    private View layout;
    private ListView listView;
    private Activity thisActivity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisActivity = getActivity();

        //create references
        layout = inflater.inflate(R.layout.home_fragment, container, false);
        listView = (ListView) layout.findViewById(R.id.home_list_view);

        fetchingPolls();

/*        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "Entering onItemLongClick method in Polls ListView");
                Toast.makeText(getActivity(), "Poll Long Click Received", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thisActivity, PollItemActivity.class);
                intent.putExtra("mainTitle", list.get(position).getMainTitle());
                intent.putExtra("subTitle", list.get(position).getSubTitle());
                intent.putExtra("leftVotes", list.get(position).getLeftVotes());
                intent.putExtra("rightVotes", list.get(position).getRightVotes());
                startActivity(intent);
                thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                return true;
            }
        });*/

        return layout;
    }

    private void fetchingPolls()
    {
        FetchingPollTask fetchingPollTask = new FetchingPollTask(getActivity(), listView);
        fetchingPollTask.submitRequest();
    }

}