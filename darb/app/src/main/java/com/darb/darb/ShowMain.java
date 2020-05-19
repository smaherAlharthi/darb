package com.darb.darb;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import classes.Connection;
import classes.Custom_Service;
import classes.LocalLang;
import classes.Service;
import classes.ShowProgressDialog;


public class ShowMain extends Fragment implements View.OnClickListener {
    View myView;
    GridView gvService;

    List<Service> ListService;

    ShowProgressDialog objShowProgressDialog;
    Service objService;

    Custom_Service custom_service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.showman, container, false);
        init();
        return myView;
    }

    private void init() {
        try {

            gvService = (GridView) myView.findViewById(R.id.gvImage_ShowMain);

            if (Connection.isConnectingToInternet(myView.getContext())) {
                new SelectService().execute();
            } else {
                Connection.showAlertDialog(myView.getContext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean SelectService() {
        boolean Check = true;
        try {
            objService = new Service();
            ListService = objService.SelectService();

            if (ListService == null) {
                return Check = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Check;
    }// SelectItem

    private class SelectService extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(myView.getContext(), "الرجاء الانتظار...");
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
            try {
                if (result == true) {
                    custom_service = new Custom_Service(myView.getContext(), (ArrayList<Service>) ListService);
                    gvService.setAdapter(custom_service);
                } else {
                    gvService.setAdapter(null);
                }
            }catch (Exception ex){
                ex.getMessage();
            }

        }
    } //SelectService

    @Override
    public void onClick(View view) {

    }
}
