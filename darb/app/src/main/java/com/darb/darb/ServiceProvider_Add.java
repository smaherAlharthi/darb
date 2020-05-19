package com.darb.darb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;

import java.util.List;

import classes.Current;
import classes.Connection;
import classes.GetToast;
import classes.ServiceProvider;
import classes.ShowProgressDialog;

public class ServiceProvider_Add extends AppCompatActivity implements View.OnClickListener {
    EditText edtProvicerName, edtLocation, edtEmail, edtMobileNo, edtLongitude, edtLatitude;

    RadioButton rbMale, rbFemale;

    Button btnSave, btnCancel;

    Context context = this;

    ShowProgressDialog objShowProgressDialog;

    ServiceProvider objServiceProvider;

    List<ServiceProvider> ListServiceProviders;
    int ProviderId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } // End if

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.main_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.provider_add);
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void init() {
        try {
            edtProvicerName = (EditText) findViewById(R.id.edtProviderName_ServiceProvider);
            edtLocation = (EditText) findViewById(R.id.edtLocation_ServiceProvider);
            edtEmail = (EditText) findViewById(R.id.edtEmail_ServiceProvider);
            edtMobileNo = (EditText) findViewById(R.id.edtMobileNo_ServiceProvider);
            edtLongitude = (EditText) findViewById(R.id.edtLongitude_ServiceProvider);
            edtLatitude = (EditText) findViewById(R.id.edtLatitude_ServiceProvider);

            rbFemale = (RadioButton) findViewById(R.id.rbFemale_ServiceProvider);
            rbMale = (RadioButton) findViewById(R.id.rbMale_ServiceProvider);

            btnCancel = (Button) findViewById(R.id.btnSave_ServiceProvider);
            btnSave = (Button) findViewById(R.id.btnCancel_ServiceProvider);
            rbMale.setChecked(true);

            if (Connection.isConnectingToInternet(context)) {
                new SelectServiceProvicder().execute();
            } else {
                Connection.showAlertDialog(context);
            }

            btnSave.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean SelectServiceProvicder() {
        boolean Check = true;
        try {
            objServiceProvider = new ServiceProvider();
            ListServiceProviders = objServiceProvider.SelectServiceProvider(Current.Id);
            if (ListServiceProviders == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectServiceProvicder extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectServiceProvicder();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                for (ServiceProvider e : ListServiceProviders) {
                    ProviderId = e.getProviderId();
                    edtProvicerName.setText("" + e.getProvicerName());
                    edtEmail.setText("" + e.getEmail());
                    edtLatitude.setText("" + e.getLatitude());
                    edtLongitude.setText("" + e.getLongitude());
                    edtLocation.setText("" + e.getLocation());
                    edtMobileNo.setText("" + e.getMobileNo());
                    rbFemale.setChecked(e.isFemale());
                    rbMale.setChecked(e.isMale());
                }
            }

        }
    } //SelectServiceProvicder

    @Override
    public void onBackPressed() {

        ServiceProvider_Add.this.finish();
    }//onBackPressed

    private class InsertServiceProvider extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return InsertServiceProvider();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                GetToast.Toast(context, "تم حفظ المعلومات بنجاح");
                ServiceProvider_Add.this.finish();
            }

        }
    } //InsertDiseases

    private boolean InsertServiceProvider() {
        boolean Check = false;
        try {
            objServiceProvider = new ServiceProvider();
            if (objServiceProvider.InsertServiceProvider(ProviderId, edtProvicerName.getText().toString(),
                    edtLocation.getText().toString(), edtEmail.getText().toString(), edtMobileNo.getText().toString(),
                     rbMale.isChecked(), rbFemale.isChecked(),edtLongitude.getText().toString(), edtLatitude.getText().toString(), Current.Id) > 0) {
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
        edtProvicerName.setError(null);
        edtEmail.setError(null);
        edtLatitude.setError(null);
        edtLocation.setError(null);
        edtLongitude.setError(null);
        edtMobileNo.setError(null);


        if (edtProvicerName.getText().toString().equals("")) {
            edtProvicerName.setError("الرجاء ادخال اسم مقدم الخدمه");
            edtProvicerName.requestFocus();
            i = i + 1;
        }
        if (edtEmail.getText().toString().equals("")) {
            edtEmail.setError("الرجاء ادخال الايميل");
            edtEmail.requestFocus();
            i = i + 1;
        }
        if (edtLatitude.getText().toString().equals("")) {
            edtLatitude.setError("الرجاء ادخال خط العرض");
            edtLatitude.requestFocus();
            i = i + 1;
        }
        if (edtLongitude.getText().toString().equals("")) {
            edtLongitude.setError("الرجاء ادخال خط الطول");
            edtLongitude.requestFocus();
            i = i + 1;
        }
        if (edtLocation.getText().toString().equals("")) {
            edtLocation.setError("الرجاء ادخال موقع");
            edtLocation.requestFocus();
            i = i + 1;
        }
        if (edtMobileNo.getText().toString().equals("")) {
            edtMobileNo.setError("الرجاء ادخال رقم المحول");
            edtMobileNo.requestFocus();
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
            case R.id.btnCancel_ServiceProvider:
                ServiceProvider_Add.this.finish();
                break;
            case R.id.btnSave_ServiceProvider:
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceProvider_Add.this);
                builder.setMessage("هل أنت متأكد من أنك تريد حفظ المعلومات؟");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {
                            if (!IsValidToSave()) {
                                return;
                            }
                            new InsertServiceProvider().execute();
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
    }
}
