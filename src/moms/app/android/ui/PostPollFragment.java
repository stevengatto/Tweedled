package moms.app.android.ui;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import moms.app.android.R;
import moms.app.android.communication.CreatePollTask;
import moms.app.android.communication.WebGeneral;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by Steve on 4/8/14.
 */
public class PostPollFragment extends Fragment {

    private static Uri imageUri;
    EditText question;
    String mQuestion_str;
    EditText title1;
    String mTitle1_str;
    EditText title2;
    String mTitle2_str;
    ImageView photo1;
    ImageView photo2;
    Button submitBtn;
    String mAuth_token;
    private Button takePhoto1;
    private Button takePhoto2;
    private static int CHOOSE_PHOTO_1 = 1;
    private static int CHOOSE_PHOTO_2 = 2;
    private static int TAKE_PHOTO_1 = 3;
    private static int TAKE_PHOTO_2 = 4;
    private Activity mThisActivity;

    String mEncodedImage1;
    String mEncodedImage2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.post_poll_fragment, container, false);

        question = (EditText) layout.findViewById(R.id.et_post_question);
        title1 = (EditText) layout.findViewById(R.id.et_post_title1);
        title2 = (EditText) layout.findViewById(R.id.et_post_title2);
        photo1 = (ImageView) layout.findViewById(R.id.iv_poll_post_left);
        photo2 = (ImageView) layout.findViewById(R.id.iv_poll_post_right);
        takePhoto1 = (Button) layout.findViewById(R.id.btn_take_photo_1);
        takePhoto2 = (Button) layout.findViewById(R.id.btn_take_photo_2);
        submitBtn = (Button) layout.findViewById(R.id.btn_post_submit);
        mThisActivity = getActivity();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        question.getText()+"\n"
                                +title1.getText()+"\n"
                                +title2.getText().toString()
                        , Toast.LENGTH_SHORT).show();
                submit();
            }
        });

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO_1);
            }
        });

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO_2);
            }
        });

        takePhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PHOTO_1);
            }
        });

        takePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PHOTO_2);
            }
        });

        return layout;
    }

    //handle result from the gallery activity selecting image
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Uri selectedImage;
        Bitmap image = null;

        //will throw out of memory error if photo is too large
        switch(requestCode) {
            case 1:
                try {
                    selectedImage = imageReturnedIntent.getData(); //can be null
                    image = decodeUri(selectedImage);
                }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    image = cropBitmapCenter(image);
                    mEncodedImage1 = bitmapToBase64String(image);
                    photo1.setImageBitmap(image);
                break;
            case 2:
                try{
                    selectedImage = imageReturnedIntent.getData();  //can be null
                    image = decodeUri(selectedImage);
                }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    image = cropBitmapCenter(image);
                    mEncodedImage2 = bitmapToBase64String(image);
                    photo2.setImageBitmap(image);
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);

                    try{ image = decodeUri(selectedImage); }
                    catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                    if(image != null) {
                        mEncodedImage1 = bitmapToBase64String(image);
                        photo1.setImageBitmap(cropBitmapCenter(image));
                    }
                    break;
                }
            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);

                    try{ image = decodeUri(selectedImage); }
                    catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                    if(image != null){
                        mEncodedImage2 = bitmapToBase64String(image);
                        photo2.setImageBitmap(cropBitmapCenter(image));
                    }
                    break;
                }
        }
    }

    private String bitmapToBase64String(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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

    public void submit()
    {
        mQuestion_str = question.getText().toString();
        mTitle1_str = title1.getText().toString();
        mTitle2_str = title2.getText().toString();
        mAuth_token = WebGeneral.getsPreferences().getString("auth_token","");

        CreatePollTask pollTask = new CreatePollTask(mThisActivity);
        pollTask.submitRequest(mQuestion_str, mTitle1_str, mTitle2_str, mAuth_token, mEncodedImage1, mEncodedImage2);
    }
}