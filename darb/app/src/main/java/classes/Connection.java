package classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.darb.darb.R;

public class Connection {

    public final static String NAMESPACE = "http://tempuri.org/";
    private static String Ip = "192.168.8.108";
    public static String ADDRESS = "http://" + Ip + "/WebServiceDarb/";
    public static String PathImage_MenuCategory = "http://" + Ip + "/WebServiceDarb/UploadFile/Service/";
    public static boolean IsConnectedToInternet = false;

    public synchronized static boolean isConnectingToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (connectivity.getActiveNetworkInfo() != null
                        && connectivity.getActiveNetworkInfo().isAvailable()
                        && connectivity.getActiveNetworkInfo().isConnected()
                    //&& CheckConnectedToInternet("http://www.google.com/",1000)
                        ) {
                    if (info != null)
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                                // NetworkInfo.DetailedState.
                                IsConnectedToInternet = true;
                                Connection.ADDRESS = "http://" + Ip + "/WebServiceDarb/";
                            }
                        }
                } else {
                    IsConnectedToInternet = false;
                }
            }
        } catch (Exception e) {
            IsConnectedToInternet = false;
            e.printStackTrace();
            e.getMessage();
        }
        return IsConnectedToInternet;
    } //isConnectingToInternet

    @SuppressWarnings("deprecation")
    public static void showAlertDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("No Internet Connection");
        alertDialog.setIcon(R.mipmap.fail);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    } //showAlertDialog
}// End Connection
