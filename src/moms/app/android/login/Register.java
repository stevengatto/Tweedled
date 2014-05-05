package moms.app.android.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import moms.app.android.R;
import moms.app.android.communication.RegisterTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by klam on 4/23/14.
 */
public class Register extends Activity {
    String mUserEmail;
    String mUserPassword;
    String mUserPasswordConfirmation;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.register);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void registerNewAccount(View button) {
        //hide soft keyboard
        Activity currentActivity = Register.this;
        InputMethodManager inputManager = (InputMethodManager) currentActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        EditText userEmailField = (EditText) findViewById(R.id.et_register_email);
        mUserEmail = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.et_register_password);
        mUserPassword = userPasswordField.getText().toString();
        EditText userPasswordConfirmationField = (EditText) findViewById(R.id.et_register_passwordConfirmation);
        mUserPasswordConfirmation = userPasswordConfirmationField.getText()
                .toString();

        if (mUserEmail.length() == 0
                || mUserPassword.length() == 0
                || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else if(mUserPassword.length() < 8) {
            Toast.makeText(this, "Password must contain at least 8 characters",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                Toast.makeText(
                        this,
                        "Your password doesn't match confirmation, check again",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                // everything is ok!
                RegisterTask registerTask = new RegisterTask(this);
                registerTask.submitRequest(mUserEmail, mUserPassword);
            }
        }
    }

}