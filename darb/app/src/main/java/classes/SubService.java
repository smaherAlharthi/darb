package classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SubService {

    private int SubServiceId=0;
    private int ServiceId=0;
    private String SubServiceName="";

    public int getSubServiceId() {
        return SubServiceId;
    }

    public void setSubServiceId(int subServiceId) {
        SubServiceId = subServiceId;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public String getSubServiceName() {
        return SubServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        SubServiceName = subServiceName;
    }

    public List<SubService> SelectSubServiceByService(Integer ServiceId) {
        List<SubService> list = null;
        String OPERATION_NAME = "SelectSubServiceByService";

        String SOAP_ACTION = "http://tempuri.org/SelectSubServiceByService";

        SoapObject request = new SoapObject(Connection.NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Connection.ADDRESS);
        androidHttpTransport.debug = true;
        try {

            PropertyInfo PropertyInfo = new PropertyInfo();
            PropertyInfo.setName("ServiceId");
            PropertyInfo.setValue(ServiceId);
            PropertyInfo.setType(Integer.class);
            request.addProperty(PropertyInfo);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            if (response.toString().equals("anyType{}") || response == null) {
                // list = null;
            } else {
                list = new ArrayList<SubService>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    SubService objSubService = new SubService();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objSubService.setSubServiceId(Integer.parseInt(obj3.getProperty("SubServiceId").toString()));
                    objSubService.setSubServiceName(obj3.getProperty("SubServiceName").toString());
                    objSubService.setServiceId(Integer.parseInt(obj3.getProperty("ServiceId").toString()));

                    list.add(objSubService);

                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectSubServiceByService

    @Override
    public String toString() {
        return SubServiceName;
    }
}
