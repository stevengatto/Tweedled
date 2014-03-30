package moms.app.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Steve on 3/30/14.
 */
public class PollItemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_item_fragment, container, false);

        //set views with args from transaction
        Bundle bundle = this.getArguments();
        ((TextView)view.findViewById(R.id.tv_poll_main_title_fragment)).setText(bundle.get("mainTitle").toString());
        ((TextView)view.findViewById(R.id.tv_poll_left_title_fragment)).setText(bundle.get("leftTitle").toString());
        ((TextView)view.findViewById(R.id.tv_poll_right_title_fragment)).setText(bundle.get("rightTitle").toString());
        ((TextView)view.findViewById(R.id.tv_poll_right_votes_fragment)).setText(bundle.get("rightVotes").toString());
        ((TextView)view.findViewById(R.id.tv_poll_left_votes_fragment)).setText(bundle.get("leftVotes").toString());
        ((PollImageView)view.findViewById(R.id.iv_poll_left_fragment)).setImageResource(R.drawable.ic_placeholder);
        ((PollImageView)view.findViewById(R.id.iv_poll_right_fragment)).setImageResource(R.drawable.ic_placeholder);

        return view;
    }
}