package tw.uglyindian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static tw.uglyindian.AppHelper.*;

public class SplashScreenActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!isNetworkAvailable(connectivityManager)) handleNetWorkFailure(this);

        setContentView(R.layout.splash_screen);

        String the_ugly_indian_prefs = "THE_UGLY_INDIAN_PREFS";
        final SharedPreferences sharedPreferences = getSharedPreferences(the_ugly_indian_prefs, 0);
        String email = sharedPreferences.getString("email", "");

        if(email != null && !email.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        } else {
            final EditText emailAddress = (EditText) findViewById(R.id.email_address);
            emailAddress.setVisibility(View.VISIBLE);
            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setVisibility(View.VISIBLE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                    preferenceEditor.putString("email", emailAddress.getText().toString());
                    preferenceEditor.commit();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
