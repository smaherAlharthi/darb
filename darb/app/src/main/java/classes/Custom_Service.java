package classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.darb.darb.R;
import com.darb.darb.RequestService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class Custom_Service extends BaseAdapter {

    private Context context;
    private ArrayList<Service> date;
    LayoutInflater infalInflater;

    GPSTracker GPS;

    public Custom_Service(Context context, ArrayList<Service> listdate) {
        this.context = context;
        date = listdate;
        infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                viewHolder = new ViewHolder();
                convertView = infalInflater.inflate(R.layout.custom_service, null);

                viewHolder.ImgService = (ImageView) convertView.findViewById(R.id.ImgService_CustomService);
                viewHolder.tvServiceTitle = (TextView) convertView.findViewById(R.id.tvServiceTitle_CustomService);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final int temp = date.get(position).getSeriveId();

            viewHolder.tvServiceTitle.setText("" + date.get(position).getServiceName());

            Picasso.with(context).load(Connection.PathImage_MenuCategory + date.get(position).getIconURL()).fit().into(viewHolder.ImgService);

        viewHolder.ImgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPS = new GPSTracker(context);
                if (GPS.canGetLocation()) {
                    Intent ViewProfile = new Intent(context, RequestService.class);
                    ViewProfile.putExtra("SeriveId",temp);
                    context.startActivity(ViewProfile);
                }else
                    {
                    GetToast.Toast(context,"الرجاء تفعيل GPS");
                }

            }
        });

        return convertView;
    }

    public class ViewHolder {
        ImageView ImgService;
        TextView tvServiceTitle;
    }
}