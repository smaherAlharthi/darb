package classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private int UserId = 0;
    private String UserName = "";
    private String FullName = "";
    private String Password = "";
    private String MobileNo = "";
    private String Email = "";
    private int UserTypeId = 0;
    private String Balance = "";
    private boolean Active = false;

    public int getProviderId() {
        return ProviderId;
    }

    public void setProviderId(int providerId) {
        ProviderId = providerId;
    }

    private int ProviderId=0;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public int InsertUsers(String UserName,String FullName,String Password,String MobileNo,String Email,int UserTypeId,String Balance, boolean Active,int UserId) {

        String OPERATION_NAME = "InsertUsers";

        String SOAP_ACTION = "http://tempuri.org/InsertUsers";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("UserName");
        PropertyInfo.setValue(UserName);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Password");
        PropertyInfo.setValue(Password);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("FullName");
        PropertyInfo.setValue(FullName);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("MobileNo");
        PropertyInfo.setValue(MobileNo);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Email");
        PropertyInfo.setValue(Email);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("UserTypeId");
        PropertyInfo.setValue(UserTypeId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Balance");
        PropertyInfo.setValue(Balance);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Active");
        PropertyInfo.setValue(Active);
        PropertyInfo.setType(boolean.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("UserId");
        PropertyInfo.setValue(UserId);
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
    }//InsertUser

    public List<Users> SelectLogin(String UserName, String Password) {
        List<Users> list = null;
        String OPERATION_NAME = "SelectLogin";

        String SOAP_ACTION = "http://tempuri.org/SelectLogin";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("UserName");
            PropertyInfo.setValue(UserName);
            PropertyInfo.setType(String.class);
            request.addProperty(PropertyInfo);

            PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("Password");
            PropertyInfo.setValue(Password);
            PropertyInfo.setType(String.class);
            request.addProperty(PropertyInfo);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<Users>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Users objUsers = new Users();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objUsers.setUserId(Integer.parseInt(obj3.getProperty("UserId").toString()));
                    objUsers.setFullName(obj3.getProperty("FullName").toString());
                    objUsers.setPassword(obj3.getProperty("Password").toString());
                    objUsers.setMobileNo(obj3.getProperty("MobileNo").toString());
                    objUsers.setUserName(obj3.getProperty("UserName").toString());
                    objUsers.setEmail(obj3.getProperty("Email").toString());
                    objUsers.setUserTypeId(Integer.parseInt(obj3.getProperty("UserTypeId").toString()));
                    objUsers.setProviderId(Integer.parseInt(obj3.getProperty("ProviderId").toString()));
                    list.add(objUsers);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//LoginUser

    public int UpdateUserByActive(int UserId, boolean Active) {
        String OPERATION_NAME = "UpdateUserByActive";

        String SOAP_ACTION = "http://tempuri.org/UpdateUserByActive";
        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);

        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("UserId");
        PropertyInfo.setValue(UserId);
        PropertyInfo.setType(int.class);
        request.addProperty(PropertyInfo);

        PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("Active");
        PropertyInfo.setValue(Active);
        PropertyInfo.setType(boolean.class);
        request.addProperty(PropertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

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
    }

}
