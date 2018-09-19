package com.ahmetfahriyener.loodos_afy.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetfahriyener.loodos_afy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;

    FirebaseRemoteConfig mFirebaseRemoteConfig;

    TextView logo_tv;
    ImageView internet_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo_tv = (TextView) findViewById(R.id.logo_tv);
        internet_connect = (ImageView) findViewById(R.id.internet_connect_image);

        //Internet Connection Control
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())
        {
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            long cacheExpiration = 0;
            mFirebaseRemoteConfig.fetch(cacheExpiration)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("Remote Config Connect","Fetch SUCCEEDED");
                                mFirebaseRemoteConfig.activateFetched();
                            } else {
                                Log.i("Remote Config Connect","Fetch FAİLED");
                            }
                            displaySplashMessage();
                        }
                    });
        }
        else
        {
            //connection alert
            internet_connect.setVisibility(View.VISIBLE);
            Toast.makeText(SplashActivity.this, "No Internet connection!",Toast.LENGTH_LONG).show();
        }

    }

    private void displaySplashMessage()
    {
        logo_tv.setText(mFirebaseRemoteConfig.getString("Logo"));
        splash_func();
    }

    private void splash_func()
    {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME);
    }


}
