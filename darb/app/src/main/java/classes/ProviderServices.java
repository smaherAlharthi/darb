package classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class ProviderServices {

    private int Id = 0;
    private int ProviderId = 0;
    private int SubServiceId = 0;
    private int ServiceId = 0;
    private String SubServiceName="";
    private String InCityCharge = "";
    private String OutCityCharge = "";

    public String getSubServiceName() {
        return SubServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        SubServiceName = subServiceName;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int ServiceID) {
        ServiceId = ServiceID;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProviderId() {
        return ProviderId;
    }

    public void setProviderId(int providerId) {
        ProviderId = providerId;
    }

    public int getSubServiceId() {
        return SubServiceId;
    }

    public void setSubServiceId(int subServiceId) {
        SubServiceId = subServiceId;
    }

    public String getInCityCharge() {
        return InCityCharge;
    }

    public void setInCityCharge(String inCityCharge) {
        InCityCharge = inCityCharge;
    }

    public String getOutCityCharge() {
        return OutCityCharge;
    }

    public void setOutCityCharge(String outCityCharge) {
        OutCityCharge = outCityCharge;
    }

    public int InsertProviderServices(int Id,int ProviderId,int SubServiceId,String InCityCharge,String OutCityCharge, int Flag) {

        String OPERATION_NAME = "InsertProviderServices";

        String SOAP_ACTION = "http://tempuri.org/InsertProviderServices";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Id");
        PropertyInfo.setValue(Id);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProviderId");
        PropertyInfo.setValue(ProviderId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("SubServiceId");
        PropertyInfo.setValue(SubServiceId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("InCityCharge");
        PropertyInfo.setValue(InCityCharge);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("OutCityCharge");
        PropertyInfo.setValue(OutCityCharge);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Flag");
        PropertyInfo.setValue(Flag);
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
    }//InsertProviderServices

    public List<ProviderServices> SelectProviderServices(int ProviderId, int SubServiceId) {
    List<ProviderServices> list = null;
    String OPERATION_NAME = "SelectProviderServices";

    String SOAP_ACTION = "http://tempuri.org/SelectProviderServices";

    SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
    envelope.dotNet = true;
    envelope.implicitTypes = true;
    HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
    androidHttpTransport.debug = true;
        try {

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProviderId");
        PropertyInfo.setValue(ProviderId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("SubServiceId");
        PropertyInfo.setValue(SubServiceId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject response = (SoapObject) envelope.getResponse();
        if (response.toString().equals("anyType{}") || response == null) {
            // list = null;
        } else {
            list = new ArrayList<ProviderServices>();
            SoapObject obj, obj1, obj2, obj3;
            obj = (SoapObject) envelope.getResponse();
            obj1 = (SoapObject) obj.getProperty("diffgram");
            obj2 = (SoapObject) obj1.getProperty("NewDataSet");

            for (int i = 0; i < obj2.getPropertyCount(); i++) {
                ProviderServices objSerivcePriority = new ProviderServices();
                obj3 = (SoapObject) obj2.getProperty(i);

                objSerivcePriority.setId(Integer.parseInt(obj3.getProperty("Id").toString()));
                objSerivcePriority.setProviderId(Integer.parseInt(obj3.getProperty("ProviderId").toString()));
                objSerivcePriority.setSubServiceId(Integer.parseInt(obj3.getProperty("SubServiceId").toString()));
                objSerivcePriority.setSubServiceName(obj3.getProperty("SubServiceName").toString());
                objSerivcePriority.setServiceId(Integer.parseInt(obj3.getProperty("ServiceId").toString()));
                objSerivcePriority.setInCityCharge(obj3.getProperty("InCityCharge").toString());
                objSerivcePriority.setOutCityCharge(obj3.getProperty("OutCityCharge").toString());

                list.add(objSerivcePriority);
            } // End for
        } // End Else
    } // End try
        catch (Exception e) {
        list = null;
        return list;
    }
        return list;

}//SelectSerivcePriority

    public List<ProviderServices> SelectProviderServicesByProvicerId(int ProviderId) {
        List<ProviderServices> list = null;
        String OPERATION_NAME = "SelectProviderServices";

        String SOAP_ACTION = "http://tempuri.org/SelectProviderServices";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("ProviderId");
            PropertyInfo.setValue(ProviderId);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<ProviderServices>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    ProviderServices objSerivcePriority = new ProviderServices();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objSerivcePriority.setId(Integer.parseInt(obj3.getProperty("Id").toString()));
                    objSerivcePriority.setProviderId(Integer.parseInt(obj3.getProperty("ProviderId").toString()));
                    objSerivcePriority.setSubServiceId(Integer.parseInt(obj3.getProperty("SubServiceId").toString()));
                    objSerivcePriority.setInCityCharge(obj3.getProperty("InCityCharge").toString());
                    objSerivcePriority.setOutCityCharge(obj3.getProperty("OutCityCharge").toString());

                    list.add(objSerivcePriority);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectProviderServicesByProvicerId
}
