package moms.app.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import moms.app.android.R;
import moms.app.android.communication.FetchingPollTask;
import moms.app.android.model.testing.Poll;


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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(null, "Entering onItemLongClick method in Polls ListView");
                Toast.makeText(getActivity(), "Poll Long Click Received", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thisActivity, PollItemActivity.class);
                Poll current = (Poll) adapterView.getAdapter().getItem(position);
                intent.putExtra("id", current.getId());
                intent.putExtra("mainTitle", current.getMainTitle());
                intent.putExtra("leftTitle", current.getSubTitleLeft());
                intent.putExtra("rightTitle", current.getSubTitleRight());
                intent.putExtra("leftVotes", current.getLeftVotes());
                intent.putExtra("rightVotes", current.getRightVotes());
                intent.putExtra("leftImageUrl", current.getLeftImageUrl());
                intent.putExtra("rightImageUrl", current.getRightImageUrl());
                startActivity(intent);
                thisActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                return true;
            }
        });

        return layout;
    }

    private void fetchingPolls()
    {
        FetchingPollTask fetchingPollTask = new FetchingPollTask(getActivity(), listView);
        fetchingPollTask.submitRequest();
    }

}