package classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class Transactions {
    private int TransactionId=0;
    private int UserId=0;
    private int SubServiceId=0;
    private int ProvicerId=0;
    private String ChargeAmount="";
    private String Longitude="";
    private String Latitude="";
    private int PriorityId=0;
    private int RequestStatusId=0;
    private int PaymentMethodsID=0;
    private String ProvicerName="";
    private String RequestStatus="";
    private String PaymentName="";
    private String DateAdded="";

    public String getProvicerName() {
        return ProvicerName;
    }

    public void setProvicerName(String provicerName) {
        ProvicerName = provicerName;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public int getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getSubServiceId() {
        return SubServiceId;
    }

    public void setSubServiceId(int subServiceId) {
        SubServiceId = subServiceId;
    }

    public int getProvicerId() {
        return ProvicerId;
    }

    public void setProvicerId(int provicerId) {
        ProvicerId = provicerId;
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

    public int getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(int priorityId) {
        PriorityId = priorityId;
    }

    public int getRequestStatusId() {
        return RequestStatusId;
    }

    public void setRequestStatusId(int requestStatusId) {
        RequestStatusId = requestStatusId;
    }

    public int getPaymentMethodsID() {
        return PaymentMethodsID;
    }

    public void setPaymentMethodsID(int paymentMethodsID) {
        PaymentMethodsID = paymentMethodsID;
    }

    public int InsertTransactions(int UserId,int SubServiceId,int ProvicerId,String ChargeAmount,String Longitude,String Latitude,
                                  int PriorityId,int RequestStatusId,int PaymentMethodsID) {

        String OPERATION_NAME = "InsertTransactions";

        String SOAP_ACTION = "http://tempuri.org/InsertTransactions";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("UserId");
        PropertyInfo.setValue(UserId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("SubServiceId");
        PropertyInfo.setValue(SubServiceId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProvicerId");
        PropertyInfo.setValue(ProvicerId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ChargeAmount");
        PropertyInfo.setValue(ChargeAmount);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Longitude");
        PropertyInfo.setValue(Longitude);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Latitude");
        PropertyInfo.setValue(Latitude);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("PriorityId");
        PropertyInfo.setValue(PriorityId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("RequestStatusId");
        PropertyInfo.setValue(RequestStatusId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("PaymentMethodsID");
        PropertyInfo.setValue(PaymentMethodsID);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(2);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);

        Object response = null;
        int res = 0;

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
            Log.i("Error", exception.getMessage());
            res = 0;
        } // End try Catch
        if (Integer.valueOf(response.toString()) > 0) {

            res = Integer.valueOf(response.toString());
        } else {
            res = 0;
        }
        return res;
    }//InsertTransactions

    public int UpdateTransactionsStatus(int RequestStatusId,int TransactionId,int ProviderId) {

        String OPERATION_NAME = "UpdateTransactionsStatus";

        String SOAP_ACTION = "http://tempuri.org/UpdateTransactionsStatus";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("RequestStatusId");
        PropertyInfo.setValue(RequestStatusId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("TransactionId");
        PropertyInfo.setValue(TransactionId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProviderId");
        PropertyInfo.setValue(ProviderId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(2);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);

        Object response = null;
        int res = 0;

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
            Log.i("Error", exception.getMessage());
            res = 0;
        } // End try Catch
        if (Integer.valueOf(response.toString()) > 0) {

            res = Integer.valueOf(response.toString());
        } else {
            res = 0;
        }
        return res;
    }//UpdateTransactionsStatus

    public List<Transactions> SelectTransactionsByUserId(int UserId,int ProviderId) {
        List<Transactions> list = null;
        String OPERATION_NAME = "SelectTransactionsByUserId";

        String SOAP_ACTION = "http://tempuri.org/SelectTransactionsByUserId";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("UserId");
            PropertyInfo.setValue(UserId);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);

            PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("ProviderId");
            PropertyInfo.setValue(ProviderId);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);


            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<Transactions>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Transactions objTransactions = new Transactions();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objTransactions.setTransactionId(Integer.parseInt(obj3.getProperty("TransactionId").toString()));
                    objTransactions.setChargeAmount(obj3.getProperty("ChargeAmount").toString());
                    objTransactions.setPaymentName(obj3.getProperty("PaymentName").toString());
                    objTransactions.setProvicerName(obj3.getProperty("ProvicerName").toString());
                    objTransactions.setRequestStatus(obj3.getProperty("RequestStatus").toString());
                    objTransactions.setDateAdded(obj3.getProperty("DateAdded").toString());

                    list.add(objTransactions);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectTransactionsByUserId
}
