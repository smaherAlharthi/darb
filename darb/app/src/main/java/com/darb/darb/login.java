package com.darb.darb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import classes.Connection;
import classes.Current;
import classes.GetToast;
import classes.LocalLang;
import classes.ShowProgressDialog;
import classes.Users;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText edtUserName, edtPassword;
    Button btnLogin;

    Context context = this;

    ShowProgressDialog objShowProgressDialog;

    Users objUsers;
    List<Users> listusers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } // End if
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        new LocalLang().setLocale(context, "ar");
        inti();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }//onCreate

    private void inti() {
        try {
            edtUserName = (EditText) findViewById(R.id.edtUserName_Login);
            edtPassword = (EditText) findViewById(R.id.edtPassword_Login);

            btnLogin = (Button) findViewById(R.id.btnRegistration_Login);

            btnLogin.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//inti

    @Override
    public void onBackPressed() {
        System.exit(0);
    }//onBackPressed

    private class LoadLogin extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "أرجو الإنتظار...");
            objShowProgressDialog.ShowDialog();
            objUsers = new Users();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            listusers = objUsers.SelectLogin(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            objShowProgressDialog.EndDialog();
            if (listusers == null) {
                GetToast.Toast(context, "تم تعطيل اسم المستخدم الخاطئ أو كلمة المرور أو حسابك.");
                edtUserName.setText(null);
                edtPassword.setText(null);
                return;
            }
            for (Users e : listusers) {
                Current.Id = e.getUserId();
                Current.FullName = e.getFullName();
                Current.Password = e.getPassword();
                Current.UserName = e.getUserName();
                Current.UserTypeId = e.getUserTypeId();
                Current.ProviderId=e.getProviderId();
            }
            if (Current.Id > 0) {
                try {
                    if (Current.UserTypeId == 1) {
                        login.this.finish();
                        Intent ShowMain = new Intent(context, MainActivity.class);
                        startActivity(ShowMain);
                        edtUserName.setText("");
                        edtPassword.setText("");
                    } else if (Current.UserTypeId == 3) {
                        login.this.finish();
                        Intent ShowMain = new Intent(context, ShowMainProvider.class);
                        startActivity(ShowMain);
                        edtUserName.setText("");
                        edtPassword.setText("");
                    }
                } catch (Exception e) {
                    GetToast.Toast(context, e.getMessage());
                }

                // MainActivity.this.finish();
            } else {
                GetToast.Toast(context, "خطا فى الاسم او الرقم السرى المستخدم");
                edtUserName.setText("");
                edtPassword.setText("");
                return;

            }

        }
    } //LoadLogin

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistration_Login:
                if (Connection.isConnectingToInternet(context)) {
                    new LoadLogin().execute(edtUserName.getText().toString(), edtPassword.getText().toString());
                } else {
                    Connection.showAlertDialog(context);
                }
                break;
            default:
                break;
        }
    }
}
