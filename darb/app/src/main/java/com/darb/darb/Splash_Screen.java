package com.darb.darb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Screen extends Activity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(context, choose_login.class));
                Splash_Screen.this.finish();
            }
        }, 3000);
    }
}
