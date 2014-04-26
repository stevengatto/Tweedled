package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import moms.app.android.R;
import moms.app.android.communication.ImageSearchTask;

/**
 * Created by Steve on 4/26/14.
 */
public class ImageSearchFragment extends Fragment {

    private Activity mActivity;
    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();

        final View view = inflater.inflate(R.layout.image_search_fragment, container, false);
        final Button searchBtn = (Button) view.findViewById(R.id.btn_image_search);
        final EditText editText = (EditText) view.findViewById(R.id.tv_image_search);
        gridView = (GridView) view.findViewById(R.id.image_search_grid_view);


        //create button listener to make web call with search query
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchImages(editText.getText().toString());
            }
        });

        return view;
    }

    public void fetchImages(String query){
        ImageSearchTask imageSearchTask = new ImageSearchTask(getActivity(), gridView);
        imageSearchTask.submitRequest(query);
    }
}