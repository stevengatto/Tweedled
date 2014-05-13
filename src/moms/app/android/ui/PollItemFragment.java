package moms.app.android.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import moms.app.android.R;
import moms.app.android.communication.CreateCommentTask;
import moms.app.android.communication.FetchingCommentTask;
import moms.app.android.model.testing.Comment;
import moms.app.android.utils.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 3/30/14.
 */
public class PollItemFragment extends Fragment {

    private EditText mCommentBox;
    private ImageButton mPostComment;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_item_fragment, container, false);
        View header = inflater.inflate(R.layout.poll_item_header, container, false);

        listView = (ListView) view.findViewById(R.id.comment_list_view);

        TextView mainTitle = (TextView) header.findViewById(R.id.tv_poll_main_title);
        TextView leftTitle = (TextView) header.findViewById(R.id.tv_poll_sub_title_left);
        TextView rightTitle = (TextView) header.findViewById(R.id.tv_poll_sub_title_right);
        ImageView leftImage = (ImageView) header.findViewById(R.id.iv_poll_left);
        ImageView rightImage = (ImageView) header.findViewById(R.id.iv_poll_right);
        TextView leftVotes = (TextView) header.findViewById(R.id.tv_poll_left_votes);
        TextView rightVotes = (TextView)header.findViewById(R.id.tv_poll_right_votes);
        ProgressBar leftProgBar = (ProgressBar) header.findViewById(R.id.pb_poll_left);
        ProgressBar rightProgBar = (ProgressBar) header.findViewById(R.id.pb_poll_right);

        mCommentBox = (EditText) view.findViewById(R.id.et_comment);
        mPostComment = (ImageButton) view.findViewById(R.id.btn_post_comment);

        mPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mCommentBox.getText().toString(), Toast.LENGTH_LONG).show();
                submitComment();
            }
        });

        Bundle bundle = BaseActivity.intentToFragmentArguments(getActivity().getIntent());

        mainTitle.setText((String) bundle.get("mainTitle"));
        leftTitle.setText((String) bundle.get("leftTitle"));
        rightTitle.setText((String) bundle.get("rightTitle"));
        leftVotes.setText(bundle.get("leftVotes").toString());
        rightVotes.setText(bundle.get("rightVotes").toString());
        ImageLoader.getInstance().displayImage(bundle.get("leftImageUrl").toString(), leftImage,
                null, new ImageLoadingListener(leftProgBar));
        ImageLoader.getInstance().displayImage(bundle.get("rightImageUrl").toString(), rightImage,
                null, new ImageLoadingListener(rightProgBar));

        //make list of random comments and put them in listview

        //--------------------------------------------------------------
        //
        //     Make List of Comments web call here!!!!
        //     Then do create adapter and setAdapter()
        //     after web call like normal
        //
        //--------------------------------------------------------------
        int id = (Integer) bundle.get("id");
        listView.addHeaderView(header);
        List<Comment> list = new ArrayList<Comment>();
       FetchingCommentTask fetchingCommentTask = new FetchingCommentTask(getActivity(),listView);
       fetchingCommentTask.submitRequest(id);



        return view;
    }

    private void submitComment(){
        String comment = mCommentBox.getText().toString();
        Bundle bundle = BaseActivity.intentToFragmentArguments(getActivity().getIntent());
        //do something with comment
        CreateCommentTask createCommentTask = new CreateCommentTask(getActivity());
        createCommentTask.submitRequest(bundle.getInt("id"), comment );
        FetchingCommentTask fetchingCommentTask = new FetchingCommentTask(getActivity(),listView);
        fetchingCommentTask.submitRequest(bundle.getInt("id"));
        return;
    }
}