package com.dsetanzania.dse;
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

public class OOUBondInformation extends AttributeContainer implements KvmSerializable
{
    
    public Long Amount;
    
    public BigDecimal AmountBln;
    
    public String BondNumber;
    
    public BigDecimal CouponRate;
    
    public Integer Deals;
    
    public String DisplayCode;
    
    public String EquityCode;
    
    public String InstrumentTypeCode;
    
    public java.util.Date IssueDate;
    
    public java.util.Date MaturityDate;
    
    public BigDecimal Price;
    
    public BigDecimal Rate;
    
    public java.util.Date ReportDate;
    
    public String ShortName;
    
    public Integer TermYears;
        
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
        if (info.name.equals("Amount"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.Amount = new Long(j.toString());
                    }
                }
                else if (obj instanceof Long){
                    this.Amount = (Long)obj;
                }
            }
            return true;
        }
        if (info.name.equals("AmountBln"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.AmountBln = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.AmountBln = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("BondNumber"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.BondNumber = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.BondNumber = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("CouponRate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.CouponRate = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.CouponRate = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("Deals"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.Deals = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.Deals = (Integer)obj;
                }
            }
            return true;
        }
        if (info.name.equals("DisplayCode"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.DisplayCode = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.DisplayCode = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("EquityCode"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.EquityCode = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.EquityCode = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("InstrumentTypeCode"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.InstrumentTypeCode = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.InstrumentTypeCode = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("IssueDate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.IssueDate = OOUHelper.ConvertFromWebService(j.toString());
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.IssueDate = (java.util.Date)obj;
                }
            }
            return true;
        }
        if (info.name.equals("MaturityDate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.MaturityDate = OOUHelper.ConvertFromWebService(j.toString());
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.MaturityDate = (java.util.Date)obj;
                }
            }
            return true;
        }
        if (info.name.equals("Price"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.Price = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.Price = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("Rate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.Rate = new BigDecimal(j.toString());
                    }
                }
                else if (obj instanceof BigDecimal){
                    this.Rate = (BigDecimal)obj;
                }
            }
            return true;
        }
        if (info.name.equals("ReportDate"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.ReportDate = OOUHelper.ConvertFromWebService(j.toString());
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.ReportDate = (java.util.Date)obj;
                }
            }
            return true;
        }
        if (info.name.equals("ShortName"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.ShortName = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.ShortName = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("TermYears"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.TermYears = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.TermYears = (Integer)obj;
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
            return this.Amount!=null?this.Amount:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.AmountBln!=null?this.AmountBln.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.BondNumber!=null?this.BondNumber:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==3)
        {
            return this.CouponRate!=null?this.CouponRate.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==4)
        {
            return this.Deals!=null?this.Deals:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==5)
        {
            return this.DisplayCode!=null?this.DisplayCode:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==6)
        {
            return this.EquityCode!=null?this.EquityCode:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==7)
        {
            return this.InstrumentTypeCode!=null?this.InstrumentTypeCode:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==8)
        {
            return this.IssueDate!=null?OOUHelper.getDateTimeFormat().format(this.IssueDate):SoapPrimitive.NullSkip;
        }
        if(propertyIndex==9)
        {
            return this.MaturityDate!=null?OOUHelper.getDateTimeFormat().format(this.MaturityDate):SoapPrimitive.NullSkip;
        }
        if(propertyIndex==10)
        {
            return this.Price!=null?this.Price.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==11)
        {
            return this.Rate!=null?this.Rate.toString():SoapPrimitive.NullSkip;
        }
        if(propertyIndex==12)
        {
            return this.ReportDate!=null?OOUHelper.getDateTimeFormat().format(this.ReportDate):SoapPrimitive.NullSkip;
        }
        if(propertyIndex==13)
        {
            return this.ShortName!=null?this.ShortName:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==14)
        {
            return this.TermYears!=null?this.TermYears:SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 15;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.LONG_CLASS;
            info.name = "Amount";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "AmountBln";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "BondNumber";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==3)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "CouponRate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==4)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "Deals";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==5)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "DisplayCode";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==6)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "EquityCode";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==7)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "InstrumentTypeCode";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==8)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "IssueDate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==9)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "MaturityDate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==10)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Price";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==11)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Rate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==12)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "ReportDate";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==13)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "ShortName";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
        if(propertyIndex==14)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "TermYears";
            info.namespace= "http://schemas.datacontract.org/2004/07/FeedWebService";
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

    
}
