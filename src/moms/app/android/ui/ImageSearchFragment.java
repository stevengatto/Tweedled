package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import moms.app.android.R;
import moms.app.android.communication.ImageSearchTask;
import moms.app.android.model.testing.ImageResult;

/**
 * Created by Steve on 4/26/14.
 */
public class ImageSearchFragment extends Fragment {

    private Activity mActivity;
    private GridView gridView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();

        final View view = inflater.inflate(R.layout.image_search_fragment, container, false);
        final ImageButton searchBtn = (ImageButton) view.findViewById(R.id.btn_image_search);
        final EditText editText = (EditText) view.findViewById(R.id.tv_image_search);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_grid_view);
        gridView = (GridView) view.findViewById(R.id.image_search_grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, ImageSelectedActivity.class);
                ImageResult result = (ImageResult) gridView.getAdapter().getItem(i);
                intent.putExtra("Url", result.getUrl());
                startActivityForResult(intent, 1);
            }
        });


        //create button listener to make web call with search query
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hide soft keyboard
                Activity currentActivity = getActivity();
                InputMethodManager inputManager = (InputMethodManager) currentActivity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                //make web call for urls
                fetchImages(editText.getText().toString());
            }
        });

        return view;
    }

    public void fetchImages(String query){
        ImageSearchTask imageSearchTask = new ImageSearchTask(getActivity(), gridView, progressBar);
//        getActivity().setProgressBarIndeterminateVisibility(true);
        progressBar.setVisibility(View.VISIBLE);
        imageSearchTask.submitRequest(query);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        if(resultCode==Activity.RESULT_OK){
            Intent data = new Intent();
            data.putExtra("url", imageReturnedIntent.getStringExtra("url"));
            getActivity().setResult(Activity.RESULT_OK, data);
            getActivity().finish();
        }
    }
}