package com.darb.darb;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import classes.Connection;
import classes.ConvertToFormat;
import classes.Current;
import classes.GPSTracker;
import classes.GetToast;
import classes.LocalLang;
import classes.PaymentMethods;
import classes.ProviderServices;
import classes.SerivcePriority;
import classes.ServiceProvider;
import classes.ShowProgressDialog;
import classes.SubService;
import classes.Transactions;


public class RequestService extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    Spinner spSubService, spPriorityService_RequestService,
            spServiceProvider_RequestService, spPaymentMethod_RequestService;
    EditText etdChargeAmount;


    Button btnSendOrder;

    RadioButton rbOutCityCharge_RequestService, rbInCityCharge_RequestService, rbMale, rbFemale;

    GPSTracker GPS;
    Location Getlocation;

    SubService objSubService;
    SerivcePriority objSerivcePriority;
    ServiceProvider objServiceProvider;
    PaymentMethods objPaymentMethods;
    ProviderServices objProviderServices;
    Transactions objTransactions;

    List<SubService> ListSubServices;
    List<SerivcePriority> ListSerivcePriorities;
    List<ServiceProvider> ListServiceProviders;
    List<PaymentMethods> ListPaymentMethods;
    List<ProviderServices> ListProviderServices;

    ArrayAdapter<SubService> adSubService;
    ArrayAdapter<SerivcePriority> adSerivcePriority;
    ArrayAdapter<ServiceProvider> adServiceProvider;
    ArrayAdapter<PaymentMethods> adPaymentMethods;

    ShowProgressDialog objShowProgressDialog;
    Context context = this;

    String SeriveId = "0", Latitude = "", Longitude = "", ChargeAmount = "0.0";
    int SubServiceId = 0, SerivcePriorityID = 0, ServiceProviderID = 0, PaymentMethodID = 0;

    int CityCharge = 1, TransactionsNo = 0, Flag = 0;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } // End if
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_service);
        new LocalLang().setLocale(context, "ar");
        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void init() {
        try {

            spSubService = (Spinner) findViewById(R.id.spSubService_RequestService);
            spPriorityService_RequestService = (Spinner) findViewById(R.id.spPriorityService_RequestService);
            spServiceProvider_RequestService = (Spinner) findViewById(R.id.spServiceProvider_RequestService);
            spPaymentMethod_RequestService = (Spinner) findViewById(R.id.spPaymentMethod_RequestService);

            rbOutCityCharge_RequestService = (RadioButton) findViewById(R.id.rbOutCityCharge_RequestService);
            rbInCityCharge_RequestService = (RadioButton) findViewById(R.id.rbInCityCharge_RequestService);
            rbFemale = (RadioButton) findViewById(R.id.rbFemale_RequestService);
            rbMale = (RadioButton) findViewById(R.id.rbMale_RequestService);

            etdChargeAmount = (EditText) findViewById(R.id.edtChargeAmount_RequestService);

            btnSendOrder = (Button) findViewById(R.id.btnSendOrder_RequestService);
            rbMale.setChecked(true);
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                GetInfo(extras);
            }

            if (Connection.isConnectingToInternet(context)) {
                new SelectSubServiceByService().execute();
            } else {
                Connection.showAlertDialog(context);
            }

            rbInCityCharge_RequestService.setChecked(true);

            spSubService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    SubServiceId = adSubService.getItem(i).getSubServiceId();
                    if (Connection.isConnectingToInternet(context)) {
                        new SelectProviderServices().execute();
                    } else {
                        Connection.showAlertDialog(context);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spPriorityService_RequestService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    SerivcePriorityID = adSerivcePriority.getItem(i).getPriorityId();
                    //   ((TextView) adapterView.getChildAt(0)).setTextColor(Color.MAGENTA);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spServiceProvider_RequestService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ServiceProviderID = adServiceProvider.getItem(i).getProviderId();
                    if (Connection.isConnectingToInternet(context)) {
                        new SelectProviderServices().execute();
                    } else {
                        Connection.showAlertDialog(context);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spPaymentMethod_RequestService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PaymentMethodID = adPaymentMethods.getItem(i).getPaymentID();
                    // ((TextView) adapterView.getChildAt(0)).setTextColor(Color.MAGENTA);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            rbOutCityCharge_RequestService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ChargeAmount = "0.0";
                    if (b == true) {
                        CityCharge = 2;
                        for (ProviderServices e : ListProviderServices) {
                            if (CityCharge == 1) {
                                ChargeAmount = e.getInCityCharge();
                            } else if (CityCharge == 2) {
                                ChargeAmount = e.getOutCityCharge();
                            }
                        }
                    } else {
                        CityCharge = 1;
                        for (ProviderServices e : ListProviderServices) {
                            if (CityCharge == 1) {
                                ChargeAmount = e.getInCityCharge();
                            } else if (CityCharge == 2) {
                                ChargeAmount = e.getOutCityCharge();
                            }
                        }
                    }
                    etdChargeAmount.setText("" + new ConvertToFormat().convertToFormat(Double.parseDouble(ChargeAmount)));
                }
            });
            rbInCityCharge_RequestService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ChargeAmount = "0.0";
                    if (b == true) {
                        CityCharge = 1;
                        for (ProviderServices e : ListProviderServices) {
                            if (CityCharge == 1) {
                                ChargeAmount = e.getInCityCharge();
                            } else if (CityCharge == 2) {
                                ChargeAmount = e.getOutCityCharge();
                            }
                        }
                    } else {
                        CityCharge = 2;
                        for (ProviderServices e : ListProviderServices) {
                            if (CityCharge == 1) {
                                ChargeAmount = e.getInCityCharge();
                            } else if (CityCharge == 2) {
                                ChargeAmount = e.getOutCityCharge();
                            }
                        }
                    }
                    etdChargeAmount.setText("" + new ConvertToFormat().convertToFormat(Double.parseDouble(ChargeAmount)));
                }
            });
            rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Flag = 1;
                    if (Connection.isConnectingToInternet(context)) {
                        new SelectServiceProviderID().execute();
                    }
                }
            });
            rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Flag = 1;
                    if (Connection.isConnectingToInternet(context)) {
                        new SelectServiceProviderID().execute();
                    }
                }
            });
            btnSendOrder.setOnClickListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetMapLogcation() {

        mapFragment.getMapAsync(this);
    }

    private void GetInfo(Bundle extras) {
        SeriveId = String.valueOf(extras.getSerializable("SeriveId"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RequestService.this.finish();
    }

    private boolean SelectProviderServices() {
        boolean Check = true;
        try {
            objProviderServices = new ProviderServices();

            ListProviderServices = objProviderServices.SelectProviderServices(ServiceProviderID, SubServiceId);
            if (ListProviderServices == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectProviderServices extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectProviderServices();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                ChargeAmount = "0.0";
                for (ProviderServices e : ListProviderServices) {
                    if (CityCharge == 1) {
                        ChargeAmount = e.getInCityCharge();
                    } else if (CityCharge == 2) {
                        ChargeAmount = e.getOutCityCharge();
                    }
                }
                etdChargeAmount.setText("" + new ConvertToFormat().convertToFormat(Double.parseDouble(ChargeAmount)));
            }
        }
    } //SelectProviderServices

    private boolean SelectSubServiceByService() {
        boolean Check = true;
        try {
            objSubService = new SubService();

            ListSubServices = objSubService.SelectSubServiceByService(Integer.parseInt(SeriveId));
            if (ListSubServices == null) {
                return Check = false;
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
            case R.id.btnSendOrder_RequestService:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("هل أنت متأكد من الطلب؟");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {
                            new InsertTransaction().execute();
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

    private class SelectSubServiceByService extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectSubServiceByService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                adSubService = new ArrayAdapter<SubService>(context, android.R.layout.simple_spinner_item, ListSubServices);
                adSubService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSubService.setAdapter(adSubService);
            } else {
                spSubService.setAdapter(null);
            }
            if (Connection.isConnectingToInternet(context)) {
                new SelectPriority().execute();
            }
        }
    } //SelectSubServiceByService

    private boolean SelectPriority() {
        boolean Check = true;
        try {
            objSerivcePriority = new SerivcePriority();

            ListSerivcePriorities = objSerivcePriority.SelectPriority();
            if (ListSerivcePriorities == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectPriority extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectPriority();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                adSerivcePriority = new ArrayAdapter<SerivcePriority>(context, android.R.layout.simple_spinner_item, ListSerivcePriorities);
                adSerivcePriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPriorityService_RequestService.setAdapter(adSerivcePriority);
            } else {
                spPriorityService_RequestService.setAdapter(null);
            }
            if (Connection.isConnectingToInternet(context)) {
                new SelectPaymentMethods().execute();
            }

        }
    } //SelectPriority

    private boolean SelectServiceProviderID() {
        boolean Check = true;
        try {
            objServiceProvider = new ServiceProvider();

            ListServiceProviders = objServiceProvider.SelectServiceProviderID(SubServiceId, rbMale.isChecked(), rbFemale.isChecked());
            if (ListServiceProviders == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectServiceProviderID extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectServiceProviderID();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                adServiceProvider = new ArrayAdapter<ServiceProvider>(context, android.R.layout.simple_spinner_item, ListServiceProviders);
                adServiceProvider.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spServiceProvider_RequestService.setAdapter(adServiceProvider);

                for (ServiceProvider e : ListServiceProviders) {
                    Latitude = e.getLatitude();
                    Longitude = e.getLongitude();
                    GetMapLogcation();
                }
            } else {
                Latitude = "00.0";
                Longitude = "00.0";
                GetMapLogcation();

                spServiceProvider_RequestService.setAdapter(null);
            }

        }

    } //SelectServiceProviderID


    private boolean SelectPaymentMethods() {
        boolean Check = true;
        try {
            objPaymentMethods = new PaymentMethods();

            ListPaymentMethods = objPaymentMethods.SelectPaymentMethods();
            if (ListPaymentMethods == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private boolean InsertTransaction() {
        boolean Check;
        try {
            objTransactions = new Transactions();
            TransactionsNo = objTransactions.InsertTransactions(Current.Id, SubServiceId, ServiceProviderID,
                    etdChargeAmount.getText().toString(), Longitude, Latitude,
                    SerivcePriorityID,
                    1, PaymentMethodID);
            if (TransactionsNo > 0) {
                Check = true;
            } else {
                Check = false;
            }
        } catch (Exception ex) {
            Log.e("InsertTransactions", ex.getMessage());
            Check = false;
        }
        return Check;
    }

    private class InsertTransaction extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return InsertTransaction();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                GetToast.Toast(context, "تم حفظ الحركة بنجاح" + " ورقم الحركة: " + String.valueOf(TransactionsNo));
                RequestService.this.finish();
            }
        }
    } //InsertTransaction

    private class SelectPaymentMethods extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectPaymentMethods();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                adPaymentMethods = new ArrayAdapter<PaymentMethods>(context, android.R.layout.simple_spinner_item, ListPaymentMethods);
                adPaymentMethods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPaymentMethod_RequestService.setAdapter(adPaymentMethods);
            } else {
                spPaymentMethod_RequestService.setAdapter(null);
            }
            if (Connection.isConnectingToInternet(context)) {
                new SelectServiceProviderID().execute();
            }
        }

    } //SelectPaymentMethods

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(Longitude), Double.parseDouble(Latitude));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Provider"));

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

        mMap.addMarker(new MarkerOptions().position(To).title("Customer"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.55f));
    }
}
