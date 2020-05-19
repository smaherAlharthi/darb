package classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import java.util.List;

public class UserType {
    private int UserTypeId = 0;
    private String UserType = "";
    
    public int getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public List<UserType> SelectUserType() {
        List<UserType> list = null;
        String OPERATION_NAME = "SelectUserType";

        String SOAP_ACTION = "http://tempuri.org/SelectUserType";

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
                list = new ArrayList<UserType>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    UserType objUserType = new UserType();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objUserType.setUserTypeId(Integer.parseInt(obj3.getProperty("UserTypeId").toString()));
                    objUserType.setUserType(obj3.getProperty("UserType").toString());
                    list.add(objUserType);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectUserType

    @Override
    public String toString() {
        return UserType;
    }
}
