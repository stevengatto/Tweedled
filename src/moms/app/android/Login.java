package moms.app.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by klam on 3/29/14.
 */
public class Login extends Activity {
    public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login);
    }

    public void login(View loginButton) {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }

    public void register(View registerText)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}