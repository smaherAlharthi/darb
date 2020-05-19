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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import classes.Connection;
import classes.Current;
import classes.GetToast;
import classes.ProviderServices;
import classes.Service;
import classes.ShowProgressDialog;
import classes.SubService;


public class ProviderService_Add extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    Spinner spService, spSubService;

    EditText edtInCity, edtOutCity;

    Button btnSave, btnCancel;

    int ServiceID = 0, SubServiceID = 0;

    List<Service> ListServices;
    List<SubService> ListSubServices;

    ArrayAdapter<Service> adService;
    ArrayAdapter<SubService> adSubService;

    Service objService;
    SubService objSubService;
    ShowProgressDialog objShowProgressDialog;

    ProviderServices objProviderServices;
    String Id = "0", ProviderId, SubServiceId, ServiceId, InCityCharge, OutCityCharge, ViewID;
    int Flag = 0;

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

        setContentView(R.layout.service_add);

        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void init() {
        spService = (Spinner) findViewById(R.id.spService_ProviderServiceAdd);
        spSubService = (Spinner) findViewById(R.id.spSubService_ProviderServiceAdd);

        edtInCity = (EditText) findViewById(R.id.edtInCity_ProviderServiceAdd);
        edtOutCity = (EditText) findViewById(R.id.edtOutCity_ProviderServiceAdd);

        btnSave = (Button) findViewById(R.id.btnSave_ServiceProviderAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel_ServiceProviderAdd);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (Connection.isConnectingToInternet(context)) {
            new SelectService().execute();
        } else {
            Connection.showAlertDialog(context);
        }


    }

    private void GetInfo(Bundle extras) {
        Id = String.valueOf(extras.getSerializable("Id"));
        ProviderId = String.valueOf(extras.getSerializable("ProviderId"));
        SubServiceId = String.valueOf(extras.getSerializable("SubServiceId"));
        ServiceId = String.valueOf(extras.getSerializable("ServiceId"));
        InCityCharge = String.valueOf(extras.getSerializable("InCityCharge"));
        OutCityCharge = String.valueOf(extras.getSerializable("OutCityCharge"));
        ViewID = String.valueOf(extras.getSerializable("ViewID"));

        if (!ViewID.equals("null")) {
            if (ViewID.equals("22")) {
                btnSave.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
                edtInCity.setText(InCityCharge);
                edtInCity.setEnabled(false);
                edtOutCity.setText(OutCityCharge);
                edtOutCity.setEnabled(false);
                spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spService.setSelection(getIndexService(adService.getItem(i).getSeriveId()) - 1);
                        ServiceID = adService.getItem(i).getSeriveId();
                        objSubService = new SubService();

                        ListSubServices = objSubService.SelectSubServiceByService(ServiceID);
                        if (ListSubServices == null) {
                            return;
                        }
                        adSubService = new ArrayAdapter<SubService>(context, android.R.layout.simple_spinner_item, ListSubServices);
                        adSubService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSubService.setAdapter(adSubService);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spSubService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spSubService.setSelection(getIndexSubService(adSubService.getItem(i).getSubServiceId()) - 1);
                        SubServiceID = adSubService.getItem(i).getSubServiceId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            } else if (ViewID.equals("11")) {
                btnSave.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                edtInCity.setText(InCityCharge);
                edtOutCity.setText(OutCityCharge);
                spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spService.setSelection(getIndexService(adService.getItem(i).getSeriveId()) - 1);
                        ServiceID = adService.getItem(i).getSeriveId();
                        objSubService = new SubService();

                        ListSubServices = objSubService.SelectSubServiceByService(ServiceID);
                        if (ListSubServices == null) {
                            return;
                        }
                        adSubService = new ArrayAdapter<SubService>(context, android.R.layout.simple_spinner_item, ListSubServices);
                        adSubService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spSubService.setAdapter(adSubService);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spSubService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spSubService.setSelection(getIndexSubService(adSubService.getItem(i).getSubServiceId()) - 1);
                        SubServiceID = adSubService.getItem(i).getSubServiceId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
    }

    private int getIndexService(Integer PlantsID) {
        int i = 0;
        try {
            for (Service e : ListServices) {
                i += 1;
                if (e.getSeriveId() == Integer.parseInt(ServiceId)) {
                    break;
                }
            }// End For
        } catch (Exception e) {
            Log.d("Error In Get Index ", e.getMessage());
        }
        return i;
    }//getIndexClass

    private int getIndexSubService(Integer PlantsID) {
        int i = 0;
        try {
            for (SubService e : ListSubServices) {
                i += 1;
                if (e.getSubServiceId() == Integer.parseInt(SubServiceId)) {
                    break;
                }
            }// End For
        } catch (Exception e) {
            Log.d("Error In Get Index ", e.getMessage());
        }
        return i;
    }//getIndexClass

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProviderService_Add.this
                .finish();
        startActivity(new Intent(context, ProviderService_List.class));
    }

    private boolean SelectService() {
        boolean Check = true;
        try {
            objService = new Service();

            ListServices = objService.SelectService();
            if (ListServices == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectService extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                adService = new ArrayAdapter<Service>(context, android.R.layout.simple_spinner_item, ListServices);
                adService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spService.setAdapter(adService);

            }
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                GetInfo(extras);


            } else {
                spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ServiceID = adService.getItem(i).getSeriveId();

                        if (Connection.isConnectingToInternet(context)) {
                            new SelectSubService().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spSubService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SubServiceID = adSubService.getItem(i).getSubServiceId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
    } //SelectService

    private boolean SelectSubService() {
        boolean Check = true;
        try {
            objSubService = new SubService();

            ListSubServices = objSubService.SelectSubServiceByService(ServiceID);
            if (ListSubServices == null) {
                return Check = false;
            }
        } catch (Exception ex) {
            Check = false;
            ex.printStackTrace();
        }
        return Check;
    }

    private class SelectSubService extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectSubService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            if (result == true) {
                adSubService = new ArrayAdapter<SubService>(context, android.R.layout.simple_spinner_item, ListSubServices);
                adSubService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSubService.setAdapter(adSubService);

            }
        }
    } //SelectSubService

    private boolean IsValidToSave() {
        int i = 0;
        edtOutCity.setError(null);
        edtInCity.setError(null);
        if (edtOutCity.getText().toString().equals("")) {
            edtOutCity.setError("الرجاء ادخال تكلفة خارج المدينة");
            edtOutCity.requestFocus();
            i = i + 1;
        }

        if (edtInCity.getText().toString().equals("")) {
            edtInCity.setError("الرجاء ادخال تكلفة داخل المدينة");
            edtInCity.requestFocus();
            i = i + 1;
        }

        if (i == 0)
            return true;
        else
            return false;

    } //IsValidToSave

    private class InsertProviderServices extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return InsertProviderServices();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                GetToast.Toast(context, "تم حفظ المعلومه بنجاح");
                ProviderService_Add.this.finish();
                startActivity(new Intent(context, ProviderService_List.class));
            }
        }
    } //InsertProviderServices

    private boolean InsertProviderServices() {
        boolean Check=true;
        try {
            objProviderServices = new ProviderServices();
            if (objProviderServices.InsertProviderServices(Integer.parseInt(Id),Current.ProviderId, SubServiceID, edtInCity.getText().toString(),
                    edtOutCity.getText().toString(), 0) > 0) {
                Check = true;
            } else {
                Check = false;
            }
        }catch (Exception ex){
            Check = false;
            ex.printStackTrace();
        }


        return Check;
    }//InsertProviderServices

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave_ServiceProviderAdd:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProviderService_Add.this);
                builder.setMessage("هل أنت متأكد من أنك تريد حفظ المعلومات؟");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {
                            if (!IsValidToSave()) {
                                return;
                            }
                            new InsertProviderServices().execute();
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
            case R.id.btnCancel_ServiceProviderAdd:
                ProviderService_Add.this.finish();
                startActivity(new Intent(context, ProviderService_List.class));
                break;
        }

    }
}
