package moms.app.android.ui;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import moms.app.android.R;

import java.io.FileNotFoundException;

/**
 * Created by Steve on 4/8/14.
 */
public class PostPollFragment extends Fragment {

    EditText question;
    EditText title1;
    EditText title2;
    ImageView photo1;
    ImageView photo2;
    Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.post_poll_fragment, container, false);

        question = (EditText) layout.findViewById(R.id.et_post_question);
        title1 = (EditText) layout.findViewById(R.id.et_post_title1);
        title2 = (EditText) layout.findViewById(R.id.et_post_title2);
        photo1 = (ImageView) layout.findViewById(R.id.iv_poll_post_left);
        photo2 = (ImageView) layout.findViewById(R.id.iv_poll_post_right);
        submitBtn = (Button) layout.findViewById(R.id.btn_post_submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        question.getText()+"\n"
                        +title1.getText()+"\n"
                        +title2.getText().toString()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        return layout;
    }

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Uri selectedImage;
        Bitmap image = null;

        //will throw out of memory error if photo is too large
        switch(requestCode) {
            case 1:
                selectedImage = imageReturnedIntent.getData();

                try{ image = decodeUri(selectedImage); }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    photo1.setImageBitmap(cropBitmapCenter(image));
                break;
            case 2:
                selectedImage = imageReturnedIntent.getData();

                try{ image = decodeUri(selectedImage); }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    photo2.setImageBitmap(cropBitmapCenter(image));
                break;
        }
    }

    //method to decode Uri to image and scale down to 500 pixels
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 500;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }


    //method used to take a bitmap, crop its center, and return that
    public Bitmap cropBitmapCenter(Bitmap srcBmp){

        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }

        else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return dstBmp;
    }
}