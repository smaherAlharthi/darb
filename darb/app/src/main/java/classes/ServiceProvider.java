package classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class ServiceProvider {

    private int ProviderId = 0;
    private String ProvicerName = "";
    private String Location = "";
    private String Email = "";
    private String MobileNo = "";
    private String Longitude = "";
    private String Latitude = "";
    private boolean Active = false;
    private boolean Male=false;

    public boolean isMale() {
        return Male;
    }

    public void setMale(boolean male) {
        Male = male;
    }

    public boolean isFemale() {
        return Female;
    }

    public void setFemale(boolean female) {
        Female = female;
    }

    private boolean Female=false;

    public int getProviderId() {
        return ProviderId;
    }

    public void setProviderId(int providerId) {
        ProviderId = providerId;
    }

    public String getProvicerName() {
        return ProvicerName;
    }

    public void setProvicerName(String provicerName) {
        ProvicerName = provicerName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
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

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public int InsertServiceProvider(int ProviderId, String ProvicerName, String Location, String Email,
                                     String MobileNo, boolean Male, boolean Female,String Longitude, String Latitude, int LinkUserID) {

        String OPERATION_NAME = "InsertServiceProvider";

        String SOAP_ACTION = "http://tempuri.org/InsertServiceProvider";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProviderId");
        PropertyInfo.setValue(ProviderId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("ProvicerName");
        PropertyInfo.setValue(ProvicerName);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Location");
        PropertyInfo.setValue(Location);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Email");
        PropertyInfo.setValue(Email);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("MobileNo");
        PropertyInfo.setValue(MobileNo);
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
        PropertyInfo.setName("Female");
        PropertyInfo.setValue(Female);
        PropertyInfo.setType(boolean.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Male");
        PropertyInfo.setValue(Male);
        PropertyInfo.setType(boolean.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("LinkUserID");
        PropertyInfo.setValue(LinkUserID);
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
    }//InsertServiceProvider


    public List<ServiceProvider> SelectServiceProviderID(int SubServiceId, boolean Male,boolean Female) {
        List<ServiceProvider> list = null;
        String OPERATION_NAME = "SelectServiceProviderID";

        String SOAP_ACTION = "http://tempuri.org/SelectServiceProviderID";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("SubServiceId");
            PropertyInfo.setValue(SubServiceId);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);

            PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("Male");
            PropertyInfo.setValue(Male);
            PropertyInfo.setType(boolean.class);
            request.addProperty(PropertyInfo);

            PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("Female");
            PropertyInfo.setValue(Female);
            PropertyInfo.setType(boolean.class);
            request.addProperty(PropertyInfo);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<ServiceProvider>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    ServiceProvider objSerivcePriority = new ServiceProvider();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objSerivcePriority.setProviderId(Integer.parseInt(obj3.getProperty("ProviderId").toString()));
                    objSerivcePriority.setProvicerName(obj3.getProperty("ProvicerName").toString());
                    objSerivcePriority.setLocation(obj3.getProperty("Location").toString());
                    objSerivcePriority.setEmail(obj3.getProperty("Email").toString());
                    objSerivcePriority.setMobileNo(obj3.getProperty("MobileNo").toString());
                    objSerivcePriority.setLongitude(obj3.getProperty("Longitude").toString());
                    objSerivcePriority.setLatitude(obj3.getProperty("Latitude").toString());

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

    public List<ServiceProvider> SelectServiceProvider(int LinkUserID) {
        List<ServiceProvider> list = null;
        String OPERATION_NAME = "SelectServiceProvider";

        String SOAP_ACTION = "http://tempuri.org/SelectServiceProvider";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("LinkUserID");
            PropertyInfo.setValue(LinkUserID);
            PropertyInfo.setType(int.class);
            request.addProperty(PropertyInfo);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<ServiceProvider>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    ServiceProvider objSerivcePriority = new ServiceProvider();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objSerivcePriority.setProviderId(Integer.parseInt(obj3.getProperty("ProviderId").toString()));
                    objSerivcePriority.setProvicerName(obj3.getProperty("ProvicerName").toString());
                    objSerivcePriority.setLocation(obj3.getProperty("Location").toString());
                    objSerivcePriority.setEmail(obj3.getProperty("Email").toString());
                    objSerivcePriority.setMobileNo(obj3.getProperty("MobileNo").toString());
                    objSerivcePriority.setLongitude(obj3.getProperty("Longitude").toString());
                    objSerivcePriority.setLatitude(obj3.getProperty("Latitude").toString());
                    objSerivcePriority.setMale(Boolean.parseBoolean(obj3.getProperty("Male").toString()));
                    objSerivcePriority.setFemale(Boolean.parseBoolean(obj3.getProperty("Female").toString()));
                    list.add(objSerivcePriority);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectServiceProvider

    @Override
    public String toString() {
        return ProvicerName;
    }
}
