package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import moms.app.android.R;
import moms.app.android.utils.ImageLoadingListener;

/**
 * Created by Steve on 4/27/14.
 */
public class ImageSelectedFragment extends Fragment {

    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_selected_fragment, container, false);

        //get url of selected image thumbnail
        Intent intent = getActivity().getIntent();
        url = intent.getStringExtra("Url");

        ImageView image = (ImageView) view.findViewById(R.id.iv_image_selected);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_image_selected);
        Button backBtn = (Button) view.findViewById(R.id.image_selected_back);
        Button selectBtn = (Button) view.findViewById(R.id.image_selected_select);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("url", url);
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });

        ImageLoader.getInstance().displayImage(url ,image, new ImageLoadingListener(progressBar));

        return view;
    }
}