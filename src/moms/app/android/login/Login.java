package moms.app.android.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import moms.app.android.R;
import moms.app.android.communication.LoginTask;
import moms.app.android.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by klam on 4/8/14.
 */
public class Login extends BaseActivity {
    private String mUserEmail;
    private String mUserPassword;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    public void login(View loginButton) {
        EditText userEmailField = (EditText) findViewById(R.id.et_userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.et_userPassword);
        mUserPassword = userPasswordField.getText().toString();
        if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
        } else {
            LoginTask loginTask = new LoginTask(this);
            loginTask.submitRequest(mUserEmail, mUserPassword);
        }
    }

}