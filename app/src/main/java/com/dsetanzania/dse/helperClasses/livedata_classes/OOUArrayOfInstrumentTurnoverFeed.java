    package com.dsetanzania.dse.helperClasses.livedata_classes;

//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 5.7.7.8
//
// Created by Quasar Development 
//
//---------------------------------------------------




import org.ksoap2.serialization.*;
import java.util.Vector;
import java.util.Hashtable;


public class OOUArrayOfInstrumentTurnoverFeed extends Vector< OOUInstrumentTurnoverFeed> implements KvmSerializable
{
    private transient Object __source;

    public void loadFromSoap(Object inObj, OOUExtendedSoapSerializationEnvelope __envelope)
    {
        if (inObj == null)
            return;
        __source=inObj;
        SoapObject soapObject=(SoapObject)inObj;
        int size = soapObject.getPropertyCount();
        for (int i0=0;i0< size;i0++)
        {
            Object obj = soapObject.getProperty(i0);
            if (obj!=null && obj instanceof AttributeContainer)
            {
                AttributeContainer j =(AttributeContainer) soapObject.getProperty(i0);
                OOUInstrumentTurnoverFeed j1= (OOUInstrumentTurnoverFeed)__envelope.get(j,OOUInstrumentTurnoverFeed.class,false);
                add(j1);
            }
        }
    }

    public Object getSourceObject()
    {
        return __source;
    }
    
    @Override
    public Object getProperty(int arg0) {
        return this.get(arg0)!=null?this.get(arg0):SoapPrimitive.NullNilElement;
    }
    
    @Override
    public int getPropertyCount() {
        return this.size();
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        info.name = "InstrumentTurnoverFeed";
        info.type = OOUInstrumentTurnoverFeed.class;
    	info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    
}