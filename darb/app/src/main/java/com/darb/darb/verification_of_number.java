package com.darb.darb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import classes.Connection;
import classes.Current;
import classes.GetToast;
import classes.LocalLang;
import classes.Users;



public class verification_of_number extends AppCompatActivity implements View.OnClickListener {
    Context context = this;

    EditText edtOneCode, edtTwoCode, edtThreeCode, edtFourCode;

    Button btnCheckCode;

    Users objUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_of_number);
        new LocalLang().setLocale(context, "ar");
        init();
    }

    private void init() {
        try {
            edtOneCode = (EditText) findViewById(R.id.edtOneCode_VerificationOfNumber);
            edtTwoCode = (EditText) findViewById(R.id.edtTwoCode_VerificationOfNumber);
            edtThreeCode = (EditText) findViewById(R.id.edtThreeCode_VerificationOfNumber);
            edtFourCode = (EditText) findViewById(R.id.edtFourCode_VerificationOfNumber);

            btnCheckCode = (Button) findViewById(R.id.btnCheckCode_VerificationOfNumber);

            btnCheckCode.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        verification_of_number.this.finish();
        startActivity(new Intent(context, choose_login.class));
    }

    private boolean UpdateUserByActive() {
        boolean Check = true;
        try {
            objUsers = new Users();
            if (objUsers.UpdateUserByActive(Current.Id, true) > 0) {
                Check = true;
            } else {
                Check = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Check;
    }

    private class UpdateUserByActive extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return UpdateUserByActive();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                GetToast.Toast(context, "تم حفظ المعلومات بنجاح");
                verification_of_number.this.finish();
                startActivity(new Intent(context, login.class));
            } else {
                GetToast.Toast(context, "الرجاء المحاولة مره اخره");
            }

        }
    } //UpdateUserByActive

    private boolean IsValidToSave() {
        int i = 0;
        edtOneCode.setError(null);
        edtTwoCode.setError(null);
        edtThreeCode.setError(null);
        edtFourCode.setError(null);

        if (!edtOneCode.getText().toString().equals(Current.OneCode)) {
            i = i + 1;
        }
        if (edtOneCode.getText().toString().equals("")) {
            i = i + 1;
        }
        if (!edtTwoCode.getText().toString().equals(Current.TwoCode)) {
            i = i + 1;
        }
        if (edtTwoCode.getText().toString().equals("")) {
            i = i + 1;
        }
        if (!edtThreeCode.getText().toString().equals(Current.ThreeCode)) {
            i = i + 1;
        }
        if (edtThreeCode.getText().toString().equals("")) {
            i = i + 1;
        }
        if (!edtFourCode.getText().toString().equals(Current.FourCode)) {
            i = i + 1;
        }
        if (edtFourCode.getText().toString().equals("")) {
            i = i + 1;
        }

        if (i == 0)
            return true;
        else
            return false;

    } //IsValidToSave

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckCode_VerificationOfNumber:
                AlertDialog.Builder builder = new AlertDialog.Builder(verification_of_number.this);
                builder.setMessage("هل أنت متأكد من مفتاح التحقق");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!IsValidToSave()) {
                            GetToast.Toast(context,"الكود غير صحيح..");
                            return;
                        }else{
                            if (Connection.isConnectingToInternet(context)){
                                new UpdateUserByActive().execute();
                            }
                        }
                    }
                });
                builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            default:
                break;
        }
    }
}
