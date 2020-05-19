package com.darb.darb;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import classes.Connection;
import classes.Current;
import classes.GPSTracker;
import classes.GetToast;
import classes.LocalLang;
import classes.ShowProgressDialog;
import classes.Transactions;


public class View_Order extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    Context context = this;
    EditText edtCustomerName, edtMoileNo, edtSubService, edtPrority, edtInCity, edtPaymentMethod, edtTotal;
    Button btnCancel, btnAccept;

    Transactions objTransactions;

    ShowProgressDialog objShowProgressDialog;
    private GoogleMap mMap;
    GPSTracker GPS;
    Location Getlocation;
    SupportMapFragment mapFragment;

    String Latitude = "", Longitude = "";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } // End if
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order);
        new LocalLang().setLocale(context, "ar");
        inti();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Current.IsFlag = 1;
        View_Order.this.finish();
    }

    private void inti() {
        try {
            edtCustomerName = (EditText) findViewById(R.id.edtCustomerName_ViewOrder);
            edtMoileNo = (EditText) findViewById(R.id.edtMobileNo_ViewOrder);
            edtSubService = (EditText) findViewById(R.id.edtSubService_ViewOrder);
            edtPrority = (EditText) findViewById(R.id.edtPriority_ViewOrder);
            edtPaymentMethod = (EditText) findViewById(R.id.edtPaymentMethod_ViewOrder);
            edtTotal = (EditText) findViewById(R.id.edtTotal_ViewOrder);

            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_ViewOrder);

            edtCustomerName.setText("" + ShowMainProvider.FullName);
            edtMoileNo.setText("" + ShowMainProvider.MobileNo);
            edtSubService.setText("" + ShowMainProvider.SubServiceName);
            edtPrority.setText("" + ShowMainProvider.PrioriyName);
            edtPaymentMethod.setText("" + ShowMainProvider.PaymentName);
            edtTotal.setText("" + ShowMainProvider.ChargeAmount);


            btnAccept = (Button) findViewById(R.id.btnAccept_ViewOrder);
            btnCancel = (Button) findViewById(R.id.btnCancel_ViewOrder);

            btnAccept.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            if(checkPermission()){
                GPS = new GPSTracker(context);
                if (GPS.canGetLocation()) {
                    GetMapLogcation();
                } else {
                    GetToast.Toast(context, "الرجاء تفعيل GPS");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GPS = new GPSTracker(context);
                    if (GPS.canGetLocation()) {
                        GetMapLogcation();
                    } else {
                        GetToast.Toast(context, "الرجاء تفعيل GPS");
                    }
                } else {
                    GetToast.Toast(context, "Deny");
                }
                break;
        }
    }

    private boolean UpdateTransactionsStatus(int StatusID) {
        boolean Check = true;
        try {
            objTransactions = new Transactions();
            if (objTransactions.UpdateTransactionsStatus(StatusID, Integer.parseInt(ShowMainProvider.TransactionId), Current.ProviderId) > 0) {
                Check = true;
            } else {
                Check = false;
            }

        } catch (Exception ex) {
            Check = false;
        }
        return Check;
    }

    private class UpdateTransactionsStatus extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {

            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الإنتظار...");
            objShowProgressDialog.ShowDialog();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return UpdateTransactionsStatus(Integer.parseInt(params[0]));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            if (result == true) {
                Current.IsFlag = 0;
                GetToast.Toast(context, "تم تسجيل الحالة المطلوبه بنجاح");
                View_Order.this.finish();
            }

        }
    } //UpdateTransactionsStatus

    private void GetMapLogcation() {

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAccept_ViewOrder:
                AlertDialog.Builder builder = new AlertDialog.Builder(View_Order.this);
                builder.setMessage("هل أنت متأكد؟");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {

                            new UpdateTransactionsStatus().execute("2");
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
            case R.id.btnCancel_ViewOrder:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(View_Order.this);
                builder1.setMessage("هل أنت متأكد؟");
                builder1.setCancelable(false);
                builder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {

                            new UpdateTransactionsStatus().execute("2");
                        } else {
                            Connection.showAlertDialog(context);
                        }
                    }
                });
                builder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(Current.Latitude), Double.parseDouble(Current.Longitude));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Customer"));

        GPS = new GPSTracker(context);
        if (GPS.canGetLocation()) {
            Getlocation = GPS.getLocation();
            if (Getlocation != null) {
                Longitude = String.valueOf(Getlocation.getLongitude());
                Latitude = String.valueOf(Getlocation.getLatitude());
            }
        } else {
            GetToast.Toast(context, "الرجاء تفعيل GPS");
        }
        LatLng To = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));

        mMap.addMarker(new MarkerOptions().position(To).title("Provider"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.55f));
    }
}
