package com.darb.darb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import classes.Connection;
import classes.Current;
import classes.Custom_TransAction;
import classes.GetToast;
import classes.LocalLang;
import classes.ShowProgressDialog;
import classes.Transactions;


public class Trasnaction_List extends Fragment {
    View myView;

    Context context;

    ListView lvTransaction;


    List<Transactions> ListTransactions;

    Custom_TransAction csCustom_transAction;
    ShowProgressDialog objShowProgressDialog;
    Transactions objTransactions;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.transactions_list, container, false);
        inti();
        return myView;
    }

    private void inti() {
        lvTransaction = (ListView) myView.findViewById(R.id.lvTransactions_TransactionsList);



        new LocalLang().setLocale(myView.getContext(), "ar");
        if (Connection.isConnectingToInternet(myView.getContext())) {
            new SelectTransactionsByUserId().execute();
        } else {
            Connection.showAlertDialog(myView.getContext());
        }
    }


    private boolean SelectTransactionsByUserId() {
        boolean Check = true;
        try {
            objTransactions = new Transactions();
            ListTransactions = objTransactions.SelectTransactionsByUserId(Current.Id,0);
            if (ListTransactions == null) {
                return Check = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Check;
    }// SelectItem

    private class SelectTransactionsByUserId extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(myView.getContext(), "الرجاء الانتظار...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return SelectTransactionsByUserId();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            try {
                if (result == true) {
                    csCustom_transAction = new Custom_TransAction(myView.getContext(), (ArrayList<Transactions>) ListTransactions);
                    lvTransaction.setAdapter(csCustom_transAction);
                } else {
                    lvTransaction.setAdapter(null);
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    } //SelectTransactionsByUserId
}
