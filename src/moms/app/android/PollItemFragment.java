package moms.app.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Steve on 3/30/14.
 */
public class PollItemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_item_fragment, container, false);

        //TODO: make webcall to get data on specific poll and modify view
        ImageView leftImage = (ImageView) view.findViewById(R.id.iv_poll_left);
        ImageView rightImage = (ImageView) view.findViewById(R.id.iv_poll_right);

        leftImage.setImageResource(R.drawable.ic_placeholder);
        rightImage.setImageResource(R.drawable.ic_placeholder);

        return view;
    }
}