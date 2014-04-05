package moms.app.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import moms.app.android.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 3/30/14.
 */
public class PollItemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_item_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.comment_list_view);

        //TODO: make webcall to get data on specific poll and modify view
        ImageView leftImage = (ImageView) view.findViewById(R.id.iv_poll_left);
        ImageView rightImage = (ImageView) view.findViewById(R.id.iv_poll_right);

        leftImage.setImageResource(R.drawable.ic_placeholder);
        rightImage.setImageResource(R.drawable.ic_placeholder);

        //make list of random comments and put them in listview
        List<Comment> list = new ArrayList<Comment>();
        for(int i=1; i<30; i++){
            Comment comment = new Comment(null,null);
            comment.setUsername("Username "+i);
            comment.setBody("Comment "+i+" goes here, you can say whatever you'd like. Design in-progess");
            list.add(comment);
        }

        PollItemAdapter adapter = new PollItemAdapter(getActivity(), R.layout.comment_list_item, list);
        listView.setAdapter(adapter);

        return view;
    }
}