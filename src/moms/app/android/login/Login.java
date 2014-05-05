package moms.app.android.login;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import moms.app.android.R;
import moms.app.android.communication.LoginTask;
import moms.app.android.communication.WebGeneral;
import moms.app.android.ui.BaseActivity;



/**
 * Created by klam on 4/8/14.
 */
public class Login extends Activity {
    private String mUserEmail;
    private String mUserPassword;
    private Activity mActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
        actionBar.setLogo(R.drawable.action_bar_logo);
        actionBar.setTitle("");
        actionBar.setHomeButtonEnabled(true);

        setContentView(R.layout.login);
        mActivity = this;
    }
    public void register(View loginButton){

        Intent intent = new Intent(mActivity.getApplicationContext(),
                moms.app.android.login.Register.class);
        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mActivity.startActivity(intent);
    }
    public void login(View loginButton) {
        //hide soft keyboard
        Activity currentActivity = Login.this;
        InputMethodManager inputManager = (InputMethodManager) currentActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

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