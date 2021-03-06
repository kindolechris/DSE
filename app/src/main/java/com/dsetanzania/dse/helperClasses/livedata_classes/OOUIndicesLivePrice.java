package com.dsetanzania.dse.helperClasses.livedata_classes;
//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 5.7.7.8
//
// Created by Quasar Development 
//
//---------------------------------------------------



import java.util.Hashtable;
import org.ksoap2.serialization.*;
import java.math.BigDecimal;

public class OOUIndicesLivePrice extends AttributeContainer implements KvmSerializable
{
    
    public Integer IdIndices;
    
    public String IndexCode;
    
    public String IndexDescription;
    
    public BigDecimal IndexPrice;
    
    public BigDecimal OpeningPrice;
    
    public BigDecimal TotalMarketCap;
    
    public java.util.Date TradeDate;
        
    private transient Object __source;
    

    
    
    public void loadFromSoap(Object paramObj, OOUExtendedSoapSerializationEnvelope __envelope)
    {
        if (paramObj == null)
            return;
        AttributeContainer inObj=(AttributeContainer)paramObj;
        __source=inObj; 
        
        if(inObj instanceof SoapObject)
        {
            SoapObject soapObject=(SoapObject)inObj;
            int size = soapObject.getPropertyCount();
            for (int i0=0;i0< size;i0++)
            {
                PropertyInfo info=soapObject.getPropertyInfo(i0);
                if(!loadProperty(info,soapObject,__envelope))
                {
                }
            } 
        }



    }

    
    protected boolean loadProperty(PropertyInfo info,SoapObject soapObject,OOUExtendedSoapSerializationEnvelope __envelope)
    {
        Object obj = info.getValue();
        if (info.name.equals("IdIndices"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.IdIndices = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.IdIndices = (Integer)obj;
                }
            }
            return true;
        }
        if (info.name.equals("IndexCode"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.IndexCode = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.IndexCode = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("IndexDescription"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.IndexDescription = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.IndexDescription = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("IndexPrice"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.IndexPrice = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.IndexPrice = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("OpeningPrice"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.OpeningPrice = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.OpeningPrice = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("TotalMarketCap"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.TotalMarketCap = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.TotalMarketCap = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("TradeDate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.TradeDate = OOUHelper.ConvertFromWebService(j.toString());
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.TradeDate = (java.util.Date)obj;
                }
            }
            return true;
        }
        return false;
    }
    
    public Object getOriginalXmlSource()
    {
        return __source;
    }    
    

    @Override
    public Object getProperty(int propertyIndex) {
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==0)
        {
            return this.IdIndices!=null?this.IdIndices:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.IndexCode!=null?this.IndexCode:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.IndexDescription!=null?this.IndexDescription:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==3)
        {
            return this.IndexPrice!=null?this.IndexPrice.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==4)
        {
            return this.OpeningPrice!=null?this.OpeningPrice.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==5)
        {
            return this.TotalMarketCap!=null?this.TotalMarketCap.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==6)
        {
            return this.TradeDate!=null?OOUHelper.getDateTimeFormat().format(this.TradeDate):SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "IdIndices";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "IndexCode";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "IndexDescription";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==3)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "IndexPrice";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==4)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "OpeningPrice";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==5)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "TotalMarketCap";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==6)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "TradeDate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

    
}
