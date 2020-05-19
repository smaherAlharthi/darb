package classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darb.darb.R;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class Custom_TransAction extends BaseAdapter {

    private Context context;
    private ArrayList<Transactions> date;
    LayoutInflater infalInflater;

    GPSTracker GPS;

    public Custom_TransAction(Context context, ArrayList<Transactions> listdate) {
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
            convertView = infalInflater.inflate(R.layout.custom_transaction_list, null);

            viewHolder.tvTransactionId = (TextView) convertView.findViewById(R.id.tvNo_CustomTransActionList);
            viewHolder.tvProvicerName = (TextView) convertView.findViewById(R.id.tvProviderName_CustomTransActionList);
            viewHolder.tvChargeAmount = (TextView) convertView.findViewById(R.id.tvAmount_CustomTransActionList);
            viewHolder.tvRequestStatus = (TextView) convertView.findViewById(R.id.tvStatus_CustomTransActionList);
            viewHolder.tvPaymentName = (TextView) convertView.findViewById(R.id.tvPaymentName_CustomTransActionList);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate_CustomTransActionList);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final int temp = date.get(position).getTransactionId();

        viewHolder.tvTransactionId.setText("" + date.get(position).getTransactionId());
        viewHolder.tvProvicerName.setText("" + date.get(position).getProvicerName());
        viewHolder.tvChargeAmount.setText("" + date.get(position).getChargeAmount());
        viewHolder.tvRequestStatus.setText("" + date.get(position).getRequestStatus());
        viewHolder.tvPaymentName.setText("" + date.get(position).getPaymentName());
        viewHolder.tvDate.setText("" + date.get(position).getDateAdded());


        return convertView;
    }

    public class ViewHolder {
        TextView tvTransactionId, tvProvicerName, tvChargeAmount, tvRequestStatus, tvPaymentName, tvDate;
    }
}