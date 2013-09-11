package ru.eraskinalexei.headhunterschooltest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


public class SplashScreenActivity extends Activity implements Runnable {
    private int sleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.hh_logo);
    }

    public void run() {
        try {
            Thread.sleep(sleep);
            startActivity(new Intent(SplashScreenActivity.this, EditResumeActivity.class));
            onDestroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onResume () {
        super.onResume();
        sleep = 4000;
        new Thread(this).start();
    }
}
