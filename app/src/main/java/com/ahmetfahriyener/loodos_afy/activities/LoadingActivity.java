package com.ahmetfahriyener.loodos_afy.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ahmetfahriyener.loodos_afy.R;
import com.github.ybq.android.spinkit.style.Circle;

public class LoadingActivity extends Activity {

    private static final int LOADING_TIME = 1500;
    static LoadingActivity loadingActivity;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingActivity = this;



    }
    public static LoadingActivity getInstance(){
        return  loadingActivity;
    }
}
