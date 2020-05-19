package com.darb.darb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import classes.Connection;
import classes.Current;
import classes.GetToast;
import classes.LocalLang;
import classes.Order;
import classes.ServiceProvider;
import classes.ShowProgressDialog;


public class ShowMainProvider extends AppCompatActivity implements View.OnClickListener {
    Context context = this;

    Button btnProviderInfo, btnAddService, btnOrder, btnExit;
    Timer timer;
    Order objOrder;
    List<Order> ListOrders;
    public static String TransactionId = "0", FullName, MobileNo, SubServiceName, ChargeAmount, Longitude, Latitude, PrioriyName, PaymentName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } // End if
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmain_provider);
        new LocalLang().setLocale(context, "ar");
        inti();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowMainProvider.this);
        builder.setMessage("هل أنت متأكد من تسجيل الخروج");
        builder.setCancelable(false);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ShowMainProvider.this.finish();
                startActivity(new Intent(context, login.class));
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

    private void inti() {
        try {
            btnProviderInfo = (Button) findViewById(R.id.btnProviderInfo_ShowMainProvider);
            btnAddService = (Button) findViewById(R.id.btnAddService_ShowMainProvider);
            btnOrder = (Button) findViewById(R.id.btnOrder_ShowMainProvider);
            btnExit = (Button) findViewById(R.id.btnExit_ShowMainProvider);

            btnOrder.setOnClickListener(this);
            btnAddService.setOnClickListener(this);
            btnProviderInfo.setOnClickListener(this);
            btnExit.setOnClickListener(this);
            OpenTimer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private synchronized boolean ViewOrder() {
        boolean Check = true;
        try {
            objOrder = new Order();
            ListOrders = objOrder.SelectViewOrderByProviderID(Current.ProviderId);
            if (ListOrders == null) {
                return Check = false;
            }

        } catch (Exception ex) {
            Check = false;
            Log.e("Tag", ex.getMessage());
        }
        return Check;
    }

    private class ViewOrder extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return ViewOrder();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                for (Order e : ListOrders) {
                    TransactionId = String.valueOf(e.getTransactionId());
                    FullName = e.getFullName();
                    MobileNo = e.getMobileNo();
                    SubServiceName = e.getSubServiceName();
                    ChargeAmount = e.getChargeAmount();
                    Current.Longitude = e.getLongitude();
                    Current.Latitude = e.getLatitude();
                    PrioriyName = e.getPrioriyName();
                    PaymentName = e.getPaymentName();
                    Current.IsFlag = 1;
                    PupNotification(SubServiceName);
                }
            } else {
                TransactionId = "0";
                FullName = "";
                MobileNo = "";
                SubServiceName = "";
                ChargeAmount = "";
                Longitude = "";
                Latitude = "";
                PrioriyName = "";
            }

        }
    } //View_Order

    private void OpenTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            if (Current.IsFlag == 0) {
                                if (Connection.isConnectingToInternet(context)) {
                                    new ViewOrder().execute();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }, 0, 5000);

    }// End OpenTimer

    public void PupNotification(String ServiceName) {
        try {
            int notificationId = 0;
            Intent notification_intent = new Intent(context, View_Order.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notification_intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.wrench)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("طلب خدمة جديدة في " + ServiceName)
                    .setContentText("الرجاء اختيار موافقة او رفض")
                    .setAutoCancel(true);

            NotificationManager notifyMgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notifyMgr.notify(notificationId, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProviderInfo_ShowMainProvider:
                startActivity(new Intent(context, ServiceProvider_Add.class));
                break;
            case R.id.btnAddService_ShowMainProvider:
                startActivity(new Intent(context, ProviderService_List.class));
                break;
            case R.id.btnOrder_ShowMainProvider:
                startActivity(new Intent(context, Order_List.class));
                break;
            case R.id.btnExit_ShowMainProvider:
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowMainProvider.this);
                builder.setMessage("هل أنت متأكد من تسجيل الخروج");
                builder.setCancelable(false);
                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowMainProvider.this.finish();
                        startActivity(new Intent(context, login.class));
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
