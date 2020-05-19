package classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SerivcePriority {

    private int PriorityId = 0;
    private String PrioriyName = "";
    private int Priority = 0;

    public int getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(int priorityId) {
        PriorityId = priorityId;
    }

    public String getPrioriyName() {
        return PrioriyName;
    }

    public void setPrioriyName(String prioriyName) {
        PrioriyName = prioriyName;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public List<SerivcePriority> SelectPriority() {
        List<SerivcePriority> list = null;
        String OPERATION_NAME = "SelectPriority";

        String SOAP_ACTION = "http://tempuri.org/SelectPriority";

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
                list = new ArrayList<SerivcePriority>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    SerivcePriority objSerivcePriority = new SerivcePriority();
                    obj3 = (SoapObject) obj2.getProperty(i);

                    objSerivcePriority.setPriorityId(Integer.parseInt(obj3.getProperty("PriorityId").toString()));
                    objSerivcePriority.setPrioriyName(obj3.getProperty("PrioriyName").toString());
                    objSerivcePriority.setPriority(Integer.parseInt(obj3.getProperty("Priority").toString()));

                    list.add(objSerivcePriority);
                } // End for
            } // End Else
        } // End try
        catch (Exception e) {
            list = null;
            return list;
        }
        return list;

    }//SelectPriority

    @Override
    public String toString() {
        return PrioriyName;
    }
}
