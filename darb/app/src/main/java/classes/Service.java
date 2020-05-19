package classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private int SeriveId=0;
    private String ServiceName="";
    private String IconURL="";
    private String Description="";

    public int getSeriveId() {
        return SeriveId;
    }

    public void setSeriveId(int seriveId) {
        SeriveId = seriveId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getIconURL() {
        return IconURL;
    }

    public void setIconURL(String iconURL) {
        IconURL = iconURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<Service> SelectService() {
        List<Service> list = null;
        String OPERATION_NAME = "SelectService";

        String SOAP_ACTION = "http://tempuri.org/SelectService";

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
                list = new ArrayList<Service>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Service objService = new Service();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objService.setSeriveId(Integer.parseInt(obj3.getProperty("SeriveId").toString()));
                    objService.setServiceName(obj3.getProperty("ServiceName").toString());
                    objService.setIconURL(obj3.getProperty("IconURL").toString());

                    list.add(objService);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectService

    @Override
    public String toString() {
        return ServiceName;
    }
}
