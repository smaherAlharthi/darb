package com.darb.darb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class Order_List extends AppCompatActivity {
    Context context=this;
    Button btnFinished,btnCanceled,btnClose;
    ListView lvTransaction;

    Transactions objTransactions;

    List<Transactions> ListTransactions;

    ShowProgressDialog objShowProgressDialog;

    Custom_TransAction csCustom_transAction;
    Dialog dialog;

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

        setContentView(R.layout.transactions_list);

        inti();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void inti() {
        lvTransaction = (ListView) findViewById(R.id.lvTransactions_TransactionsList);

        lvTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                try {
                    dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                    dialog.setContentView(R.layout.purchase_status);

                    btnFinished = (Button) dialog.findViewById(R.id.btnFinished_PurchaseStatus);
                    btnCanceled = (Button) dialog.findViewById(R.id.btnCanceled_PurchaseStatus);
                    btnClose = (Button) dialog.findViewById(R.id.btnClose_PurchaseStatus);

                    btnFinished.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("هل أنت متأكد؟");
                            builder.setCancelable(false);
                            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (Connection.isConnectingToInternet(context)) {

                                        new UpdateTransactionsStatus().execute("4", ""+ListTransactions.get(position).getTransactionId());
                                        ListTransactions.remove(position); //or some other task

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
                        }
                    });

                    btnCanceled.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("هل أنت متأكد؟");
                            builder.setCancelable(false);
                            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (Connection.isConnectingToInternet(context)) {

                                        new UpdateTransactionsStatus().execute("3", ""+ListTransactions.get(position).getTransactionId());
                                        ListTransactions.remove(position); //or some other task
                                        dialog.cancel();
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
                        }
                    });


                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                } catch (Exception e) {
                    Log.d("purchase_status", e.getMessage());
                }
            }
        });
        new LocalLang().setLocale(context, "ar");
        if (Connection.isConnectingToInternet(context)) {
            new SelectTransactionsByUserId().execute();
        } else {
            Connection.showAlertDialog(context);
        }
    }

    private boolean UpdateTransactionsStatus(int StatusID,int TransactionId) {
        boolean Check = true;
        try {
            objTransactions = new Transactions();
            if (objTransactions.UpdateTransactionsStatus(StatusID, TransactionId, Current.ProviderId) > 0) {
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
            return UpdateTransactionsStatus(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();
            if (result == true) {
                dialog.cancel();
                GetToast.Toast(context, "تم تحديث الحالة المطلوبه بنجاح");
                csCustom_transAction.notifyDataSetChanged();

                if (Connection.isConnectingToInternet(context)) {
                    new SelectTransactionsByUserId().execute();
                } else {
                    Connection.showAlertDialog(context);
                }
            }

        }
    } //UpdateTransactionsStatus

    private boolean SelectTransactionsByUserId() {
        boolean Check = true;
        try {
            objTransactions = new Transactions();
            ListTransactions = objTransactions.SelectTransactionsByUserId(0,Current.Id);
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
            objShowProgressDialog = new ShowProgressDialog(context, "الرجاء الانتظار...");
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
                    csCustom_transAction = new Custom_TransAction(context, (ArrayList<Transactions>) ListTransactions);
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
