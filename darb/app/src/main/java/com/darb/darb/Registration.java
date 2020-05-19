package com.darb.darb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.Connection;
import classes.Current;
import classes.GetToast;
import classes.LocalLang;
import classes.Mail;
import classes.ShowProgressDialog;
import classes.UserType;
import classes.Users;


public class Registration extends AppCompatActivity implements View.OnClickListener {
    Context context = this;

    EditText edtUserName, edtFullName, edtPassword, edtMobileNo, edtEmail;
    Spinner spUserType;

    Button btnSave_Registration;

    UserType objUserType;
    Users objUsers;
    ShowProgressDialog objShowProgressDialog;

    List<UserType> ListUserType;
    ArrayAdapter<UserType> adUserType;

    int UserTypeID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        new LocalLang().setLocale(context, "ar");
        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void init() {
        try {
            edtUserName = (EditText) findViewById(R.id.edtUserName_Registration);
            edtFullName = (EditText) findViewById(R.id.edtFullName_Registration);
            edtPassword = (EditText) findViewById(R.id.edtPassword_Registration);
            edtMobileNo = (EditText) findViewById(R.id.edtMobile_Registration);
            edtEmail = (EditText) findViewById(R.id.edtEmail_Registration);

            spUserType = (Spinner) findViewById(R.id.spUserType_Registration);

            btnSave_Registration = (Button) findViewById(R.id.btnSave_Registration);

            if (Connection.isConnectingToInternet(context)) {
                new SelectUserType().execute();
            } else {
                Connection.showAlertDialog(context);
            }

            spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    UserTypeID = adUserType.getItem(i).getUserTypeId();
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnSave_Registration.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Registration.this.finish();
        startActivity(new Intent(context, choose_login.class));
    }

    private boolean SelectUserType() {
        boolean Check = true;
        try {
            objUserType = new UserType();
            ListUserType = objUserType.SelectUserType();
            if (ListUserType == null) {
                return Check = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Check;
    }

    private class SelectUserType extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "Please Wait...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            return SelectUserType();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            if (result == true) {
                adUserType = new ArrayAdapter<UserType>(context, android.R.layout.simple_spinner_item, ListUserType);
                adUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spUserType.setAdapter(adUserType);
            }

        }
    }//SelectUserType

    private class InsertUsers extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return InsertUsers();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                GetToast.Toast(context, "تم حفظ المعلومات بنجاح");

                if (Connection.isConnectingToInternet(context)){
                    new SendEmail().execute();
                }
            }
        }
    } //InsertUsers

    private boolean InsertUsers() {
        boolean Check = false;
        try {
            objUsers = new Users();
            Current.Id = objUsers.InsertUsers(edtUserName.getText().toString(), edtFullName.getText().toString(), edtPassword.getText().toString(),
                    edtMobileNo.getText().toString(), edtEmail.getText().toString(), UserTypeID, "0.0", false, 0);

            if (Current.Id > 0) {
                Check = true;
            } else {
                Check = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Check;
    }

    private boolean IsValidToSave() {
        int i = 0;
        edtUserName.setError(null);
        edtFullName.setError(null);
        edtMobileNo.setError(null);
        edtEmail.setError(null);
        edtPassword.setError(null);


        if (edtUserName.getText().toString().equals("")) {
            edtUserName.setError("Please Enter UserName");
            edtUserName.requestFocus();
            i = i + 1;
        }

        if (edtFullName.getText().toString().equals("")) {
            edtFullName.setError("Please Enter FullName");
            edtFullName.requestFocus();
            i = i + 1;
        }
        if (edtMobileNo.getText().toString().length() < 10) {
            edtMobileNo.setError("Please Enter Mobile No greater than 10");
            edtMobileNo.requestFocus();
            i = i + 1;
        }

        if (edtEmail.getText().toString().length() < 0
                || !isValidEmail(edtEmail.getText().toString())) {
            edtEmail.setError("example@gmail.com");
            edtEmail.requestFocus();
            i = i + 1;

        }

        if (i == 0)
            return true;
        else
            return false;

    } //IsValidToSave

    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    } //isValidEmail

    private class SendEmail extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...", "ارسال الايميل");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return SendEmail(edtEmail.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                GetToast.Toast(context, "تم ارسال الايميل بنجاح");

            } else {
                GetToast.Toast(context,"لم يتم ارسال الايميل");
            }
            Registration.this.finish();
            startActivity(new Intent(context,verification_of_number.class));
        }
    } //SendEmail

    private boolean SendEmail(String ToEmail) {
        boolean Check;
        try {
            char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
            StringBuilder sb1 = new StringBuilder();
            Random random1 = new Random();

            for (int i = 0; i < 4; i++) {
                char c1 = chars1[random1.nextInt(chars1.length)];
                sb1.append(c1);
            }

            String random_string = sb1.toString();
            for (int i = 0; i < random_string.length(); i++) {
                char c = random_string.charAt(i);
                if (i == 0) {
                    Current.OneCode = String.valueOf(c);
                } else if (i == 1) {
                    Current.TwoCode = String.valueOf(c);
                } else if (i == 2) {
                    Current.ThreeCode = String.valueOf(c);
                } else if (i == 3) {
                    Current.FourCode = String.valueOf(c);
                }
            }
            Mail m = new Mail(Current.GmailEmail, Current.GmailPassword);

            String[] toArr = {ToEmail, Current.GmailEmail};
            m.setTo(toArr);
            m.setFrom(Current.GmailEmail);
            m.setSubject("Verification Password Key");
            m.setBody("This Number Key: " + random_string);

            if (m.send() == true) {
                Check = true;
            } else {
                Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();

        }
        return Check;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave_Registration:
                AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                builder.setMessage("هل أنت متأكد من أنك تريد حفظ المعلومات؟");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {
                            if (!IsValidToSave()) {
                                return;
                            }
                            new InsertUsers().execute();
                        } else {
                            Connection.showAlertDialog(context);
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
    }//onClick

}
