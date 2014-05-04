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
import com.nostra13.universalimageloader.core.ImageLoader;
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
    private Button googlePhoto1;
    private Button googlePhoto2;
    private static int CHOOSE_PHOTO_1 = 1;
    private static int CHOOSE_PHOTO_2 = 2;
    private static int TAKE_PHOTO_1 = 3;
    private static int TAKE_PHOTO_2 = 4;
    private static int GOOGLE_PHOTO_1 = 5;
    private static int GOOGLE_PHOTO_2 = 6;
    private Activity mActivity;

    String mEncodedImage1;
    String mEncodedImage2;
    String mImageUrl1;
    String mImageUrl2;
    Boolean isPictureOneUrl;
    Boolean isPictureTwoUrl;

    EditText description;
    String mDescription_str;

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
        googlePhoto1 = (Button) layout.findViewById(R.id.btn_google_photo_1);
        googlePhoto2 = (Button) layout.findViewById(R.id.btn_google_photo_2);
        submitBtn = (Button) layout.findViewById(R.id.btn_post_submit);
        description = (EditText) layout.findViewById(R.id.et_poll_description);
        mActivity = getActivity();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        googlePhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ImageSearchActivity.class);
                startActivityForResult(intent, GOOGLE_PHOTO_1);
            }
        });

        googlePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ImageSearchActivity.class);
                startActivityForResult(intent, GOOGLE_PHOTO_2);
            }
        });

        return layout;
    }

    //handle result from the gallery, camera, or google search activity
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

                if(image != null){
                    image = cropBitmapCenter(image);
                    mEncodedImage1 = bitmapToBase64String(image);
                    photo1.setImageBitmap(image);
                    isPictureOneUrl = false;
                    mImageUrl1 = null;
                }
                break;

            case 2:
                try{
                    selectedImage = imageReturnedIntent.getData();  //can be null
                    image = decodeUri(selectedImage);
                }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null){
                    image = cropBitmapCenter(image);
                    mEncodedImage2 = bitmapToBase64String(image);
                    photo2.setImageBitmap(image);
                    isPictureTwoUrl = false;
                    mImageUrl2 = null;
                }
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
                        isPictureOneUrl = false;
                        mImageUrl1 = null;
                    }
                }
                break;

            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);

                    try{ image = decodeUri(selectedImage); }
                    catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                    if(image != null){
                        mEncodedImage2 = bitmapToBase64String(image);
                        photo2.setImageBitmap(cropBitmapCenter(image));
                        isPictureTwoUrl = false;
                        mImageUrl2 = null;
                    }
                }
                break;

            case 5:
                if(resultCode == Activity.RESULT_OK){
                    mImageUrl1 = imageReturnedIntent.getStringExtra("url");
                    ImageLoader.getInstance().displayImage(mImageUrl1, photo1);
                    isPictureOneUrl = true;
                    mEncodedImage1 = null;
                }
                break;

            case 6:
                if(resultCode == Activity.RESULT_OK){
                    mImageUrl2 = imageReturnedIntent.getStringExtra("url");
                    ImageLoader.getInstance().displayImage(mImageUrl2, photo2);
                    isPictureTwoUrl = true;
                    mEncodedImage2 = null;
                }
                break;
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

        //first check if user has logged in by catching null pointer on getPreferences
        try{
        mAuth_token = WebGeneral.getsPreferences().getString("auth_token","");
        } catch (NullPointerException e) {
            Toast.makeText(mActivity, "You must be logged in to create a poll", Toast.LENGTH_LONG).show();
            return;
        }

        boolean questionEmpty = mQuestion_str.isEmpty();
        boolean title1Empty = mTitle1_str.isEmpty();
        boolean title2Empty = mTitle2_str.isEmpty();
        mDescription_str = description.getText().toString();
        //then check that all fields have content
        if(!questionEmpty && !title1Empty && !title2Empty) {

            //if a URL or encoded image has been supplied for image one
            if((isPictureOneUrl!=null) && ((isPictureOneUrl && mImageUrl1!=null)
                    || (!isPictureOneUrl && mEncodedImage1!= null))){

                //if a URL or encoded image has been supplied for image two allow submit
                if((isPictureTwoUrl!=null) && ((isPictureTwoUrl && mImageUrl2!=null)
                        || (!isPictureTwoUrl && mEncodedImage2!= null))){
                    CreatePollTask pollTask = new CreatePollTask(mActivity);
                    pollTask.submitRequest(mQuestion_str, mTitle1_str, mTitle2_str, mAuth_token, mEncodedImage1,
                            mEncodedImage2, isPictureOneUrl, isPictureTwoUrl, mImageUrl1, mImageUrl2, mDescription_str);
                    return; //return so following lines don't execute
                }
            }
        }

        //otherwise tell user to fill in all fields
        if(questionEmpty)
            question.setHintTextColor(getResources().getColor(R.color.bg_peach));
        else
            question.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        if(title1Empty)
            title1.setHintTextColor(getResources().getColor(R.color.bg_peach));
        else
            title1.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        if(title2Empty)
            title2.setHintTextColor(getResources().getColor(R.color.bg_peach));
        else
            title2.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        Toast.makeText(mActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
    }
}