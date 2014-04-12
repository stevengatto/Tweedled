package moms.app.android.ui;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    private SharedPreferences mPreferences;

    final String URL = "http://kcl/polls/new";

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
        selectedImage = null;
        switch(requestCode) {
            case 1:
                try {
                    selectedImage = imageReturnedIntent.getData(); //can be null
                }catch (Exception e)
                {}
                try{ image = decodeUri(selectedImage); }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    photo1.setImageBitmap(cropBitmapCenter(image));
                break;
            case 2:
                try{
                selectedImage = imageReturnedIntent.getData();  //can be null   
                }catch (Exception e)
                {}
                try{ image = decodeUri(selectedImage); }
                catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                if(image != null)
                    photo2.setImageBitmap(cropBitmapCenter(image));
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);

                    try{ image = decodeUri(selectedImage); }
                    catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                    if(image != null)
                        photo1.setImageBitmap(cropBitmapCenter(image));
                    break;
                }
            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);

                    try{ image = decodeUri(selectedImage); }
                    catch (Exception e) { Log.e(null, "Incorrect Uri Exception on Image Select"); }

                    if(image != null)
                        photo2.setImageBitmap(cropBitmapCenter(image));
                    break;
                }
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

    public void submit()
    {
        mQuestion_str = question.getText().toString();
        mTitle1_str = title1.getText().toString();
        mTitle2_str = title2.getText().toString();
        //e29c93


        mPreferences = mThisActivity.getSharedPreferences("CurrentUser", mThisActivity.MODE_PRIVATE);
        mAuth_token = mPreferences.getString("AuthToken", "");

        CreatePollTask pollTask = new CreatePollTask(mThisActivity);
        pollTask.setMessageLoading("Creating poll...");
        pollTask.execute(URL);
    }

    private class CreatePollTask extends UrlJsonAsyncTask {
        public CreatePollTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject pollObj = new JSONObject();
            String response;
            JSONObject json = new JSONObject();
            try {
                try {
                    json.put("utf8", "\u2713");
                    json.put("authenticity_token", "");
                    pollObj.put("question", mQuestion_str);
                    pollObj.put("title_one", mTitle1_str);
                    pollObj.put("title_two", mTitle2_str);
                    pollObj.put("auth_token", mAuth_token);
                    pollObj.put("commit", "submit");
                    pollObj.put("controller", "polls");
                    pollObj.put("action", "create");
                    json.put("poll",pollObj);

                    StringEntity se = new StringEntity(json.toString());
                    post.setEntity(se);
                    post.setHeader("Content-Type", "application/json");
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Email and/or password are invalid. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {

                    Toast.makeText(context, json.getString("info"),
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }



}