package classes;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.darb.darb.ProviderService_Add;
import com.darb.darb.ProviderService_List;
import com.darb.darb.R;
import com.darb.darb.ServiceProvider_Add;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class Custom_ProviderServiceList extends BaseAdapter {

    private Context context;
    private ArrayList<ProviderServices> date;
    LayoutInflater infalInflater;
    Dialog dialog;
    ProviderServices objProviderServices;
    ShowProgressDialog objShowProgressDialog;

    public Custom_ProviderServiceList(Context context, ArrayList<ProviderServices> listdate) {
        this.context = context;
        date = listdate;

        infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = infalInflater.inflate(R.layout.customlist, null);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder = new ViewHolder();

        viewHolder.Title = (TextView) convertView.findViewById(R.id.tvTitle_CustomList);
        viewHolder.btnView = (Button) convertView.findViewById(R.id.btnView_CustomList);
        viewHolder.btnEdit = (Button) convertView.findViewById(R.id.btnEdit_CustomList);
        viewHolder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete_CustomList);

        final int temp = date.get(position).getId();
        viewHolder.Title.setText(date.get(position).getSubServiceName());

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewProfile = new Intent(context, ProviderService_Add.class);

                ViewProfile.putExtra("Id", date.get(position).getId());
                ViewProfile.putExtra("ProviderId", date.get(position).getProviderId());
                ViewProfile.putExtra("ServiceId", date.get(position).getServiceId());
                ViewProfile.putExtra("SubServiceId", date.get(position).getSubServiceId());
                ViewProfile.putExtra("InCityCharge", date.get(position).getInCityCharge());
                ViewProfile.putExtra("OutCityCharge", date.get(position).getOutCityCharge());

                ViewProfile.putExtra("ViewID", 11);

                context.startActivity(ViewProfile);
                ((Activity) context).finish();

            }
        });


        viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewProfile = new Intent(context, ProviderService_Add.class);

                ViewProfile.putExtra("Id", date.get(position).getId());
                ViewProfile.putExtra("ServiceId", date.get(position).getServiceId());
                ViewProfile.putExtra("SubServiceId", date.get(position).getSubServiceId());
                ViewProfile.putExtra("InCityCharge", date.get(position).getInCityCharge());
                ViewProfile.putExtra("OutCityCharge", date.get(position).getOutCityCharge());

                context.startActivity(ViewProfile);
                ((Activity) context).finish();

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to Provider Service?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Connection.isConnectingToInternet(context)) {
                            new InsertProviderServices().execute(position, date.get(position).getId());
                            date.remove(position); //or some other task
                            notifyDataSetChanged();
                        } else {
                            Connection.showAlertDialog(context);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return convertView;
    }

    private class InsertProviderServices extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            objShowProgressDialog = new ShowProgressDialog(context, "Please wait...");
            objShowProgressDialog.ShowDialog();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            return InsertProviderServices(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            objShowProgressDialog.EndDialog();

            if (result == true) {
                //  MyProfile.this.finish();
            }

        }
    }//InsertDiseases

    private boolean InsertProviderServices(int position, int ID) {
        boolean Check;
        objProviderServices = new ProviderServices();

        if (objProviderServices.InsertProviderServices(ID, 0, 0, "", "", 1) > 0) {
            Check = true;
        } else {
            Check = false;
        }
        return Check;
    }//InsertDiseases

    public class ViewHolder {
        TextView Title;
        Button btnView, btnEdit, btnDelete;
    }
}