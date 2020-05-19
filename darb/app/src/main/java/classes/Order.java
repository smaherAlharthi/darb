package classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int    TransactionId=0;
    private String FullName="";
    private String MobileNo="";
    private String SubServiceName="";
    private String ChargeAmount="";
    private String Longitude="";
    private String Latitude="";
    private String PrioriyName="";
    private int RequestStatusId=0;

    public int getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getSubServiceName() {
        return SubServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        SubServiceName = subServiceName;
    }

    public String getChargeAmount() {
        return ChargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        ChargeAmount = chargeAmount;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getPrioriyName() {
        return PrioriyName;
    }

    public void setPrioriyName(String prioriyName) {
        PrioriyName = prioriyName;
    }

    public int getRequestStatusId() {
        return RequestStatusId;
    }

    public void setRequestStatusId(int requestStatusId) {
        RequestStatusId = requestStatusId;
    }

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    private String PaymentName="";

    public List<Order> SelectViewOrderByProviderID( int ProvicerId) {
        List<Order> list = null;
        String OPERATION_NAME = "SelectViewOrderByProviderID";

        String SOAP_ACTION = "http://tempuri.org/SelectViewOrderByProviderID";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("ProvicerId");
            PropertyInfo.setValue(ProvicerId);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);


            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<Order>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Order objOrder = new Order();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objOrder.setTransactionId(Integer.parseInt(obj3.getProperty("TransactionId").toString()));
                    objOrder.setChargeAmount(obj3.getProperty("ChargeAmount").toString());
                    objOrder.setPaymentName(obj3.getProperty("PaymentName").toString());
                    objOrder.setFullName(obj3.getProperty("FullName").toString());
                    objOrder.setSubServiceName(obj3.getProperty("SubServiceName").toString());
                    objOrder.setLatitude(obj3.getProperty("Latitude").toString());
                    objOrder.setLongitude(obj3.getProperty("Longitude").toString());
                    objOrder.setPrioriyName(obj3.getProperty("PrioriyName").toString());
                    objOrder.setMobileNo(obj3.getProperty("MobileNo").toString());
                    objOrder.setRequestStatusId(Integer.parseInt(obj3.getProperty("RequestStatusId").toString()));
                    list.add(objOrder);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            Log.e("",e.getMessage());
            list = null;
            return list;
        }
        return list;

    }//SelectOrderByUserId

}
