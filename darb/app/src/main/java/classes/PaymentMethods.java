package classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethods {

    private int PaymentID = 0;
    private String PaymentName = "";

    public int getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(int paymentID) {
        PaymentID = paymentID;
    }

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    public List<PaymentMethods> SelectPaymentMethods() {
        List<PaymentMethods> list = null;
        String OPERATION_NAME = "SelectPaymentMethods";

        String SOAP_ACTION = "http://tempuri.org/SelectPaymentMethods";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<PaymentMethods>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    PaymentMethods objPaymentMethods = new PaymentMethods();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objPaymentMethods.setPaymentID(Integer.parseInt(obj3.getProperty("PaymentID").toString()));
                    objPaymentMethods.setPaymentName(obj3.getProperty("PaymentName").toString());

                    list.add(objPaymentMethods);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectPaymentMethods

    @Override
    public String toString() {
        return PaymentName;
    }
}
