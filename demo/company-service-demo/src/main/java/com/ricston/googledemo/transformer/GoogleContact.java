package com.ricston.googledemo.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.module.http.internal.ParameterMap;
import org.mule.transformer.AbstractMessageTransformer;

import com.google.api.ads.dfp.axis.v201605.Contact;

public class GoogleContact extends AbstractMessageTransformer {

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding)
            throws TransformerException {
         
    	String companyId = ((ParameterMap) message.getPayload()).get("companyId");
        String name = ((ParameterMap) message.getPayload()).get("contactName");        
        String email = ((ParameterMap) message.getPayload()).get("contactEmail");

        Contact newContact = new Contact();
        newContact.setCompanyId(Long.valueOf(companyId));
        
        newContact.setName(name);        
        newContact.setEmail(email);              
        
        message.setPayload(newContact);

        return message;
        
    }
}
