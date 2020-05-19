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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import classes.Connection;
import classes.Current;
import classes.Custom_ProviderServiceList;
import classes.ProviderServices;
import classes.Service;
import classes.ShowProgressDialog;
import classes.SubService;

public class ProviderService_List extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    Button btnAdd, btnSearch;
    Spinner spService, spSubService;
    ListView lvProviderService;

    int ServiceId = 0, SubServiceId = 0;

    ProviderServices objProviderServices;
    ShowProgressDialog objShowProgressDialog;
    Service objService;
    SubService objSubService;

    List<ProviderServices> ListProviderServices;
    List<Service> ListServices;
    List<SubService> ListSubServices;

    ArrayAdapter<Service> adService;
    ArrayAdapter<SubService> adSubService;

    Custom_ProviderServiceList cscustom_providerServiceList;

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

        setContentView(R.layout.service_list);

        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProviderService_List.this.finish();

    }

    private void init() {
        try {
            btnAdd = (Button) findViewById(R.id.btnAdd_ProviderServiceList);
            btnSearch = (Button) findViewById(R.id.btnSearch_ProviderServiceList);

            lvProviderService = (ListView) findViewById(R.id.lvProviderService_ProviderServiceList);

            spService = (Spinner) findViewById(R.id.spService_ProviderServiceList);
            spSubService = (Spinner) findViewById(R.id.spSubService_ProviderServiceList);

            if (Connection.isConnectingToInternet(context)) {
                new SelectService().execute();
            } else {
                Connection.showAlertDialog(context);
            }

            spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ServiceId = adService.getItem(i).getSeriveId();
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
                    SubServiceId = adSubService.getItem(i).getSubServiceId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnAdd.setOnClickListener(this);
            btnSearch.setOnClickListener(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        }
    } //SelectService

    private boolean SelectSubService() {
        boolean Check = true;
        try {
            objSubService = new SubService();

            ListSubServices = objSubService.SelectSubServiceByService(ServiceId);
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

    private class SelectProviderServices extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectProviderServices();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            if (result == true) {
                cscustom_providerServiceList = new Custom_ProviderServiceList(context, (ArrayList<ProviderServices>) ListProviderServices);
                lvProviderService.setAdapter(cscustom_providerServiceList);
            } else {
                lvProviderService.setAdapter(null);
            }
        }
    } //SelectProviderServices

    private boolean SelectProviderServices() {
        boolean Check = true;
        try {
            objProviderServices = new ProviderServices();
            ListProviderServices=objProviderServices.SelectProviderServices(Current.Id,SubServiceId);
            if (ListProviderServices == null) {
                return Check = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Check;
    }//SelectProviderServices

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd_ProviderServiceList:
                ProviderService_List.this.finish();
                startActivity(new Intent(context, ProviderService_Add.class));
                break;

            case R.id.btnSearch_ProviderServiceList:
                if (Connection.isConnectingToInternet(context)) {
                    new SelectProviderServices().execute();
                }
                break;
            default:
                break;
        }
    }
}
