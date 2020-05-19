package com.darb.darb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import classes.GetToast;
import classes.LocalLang;

public class choose_login extends AppCompatActivity implements View.OnClickListener {
    Button btnRegistration, btnRegistration_Logn;

    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_login);
        new LocalLang().setLocale(context, "ar");
        init();
    }

    private void init() {
        try {
            btnRegistration = (Button) findViewById(R.id.btnNewRegistration_ChooseLogin);
            btnRegistration_Logn = (Button) findViewById(R.id.btnLogin_ChooseLogin);

            btnRegistration_Logn.setOnClickListener(this);
            btnRegistration.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewRegistration_ChooseLogin:
                choose_login.this.finish();
                startActivity(new Intent(context, Registration.class));
                break;
            case R.id.btnLogin_ChooseLogin:
                choose_login.this.finish();
                startActivity(new Intent(context, login.class));
                break;
        }
    }
}
