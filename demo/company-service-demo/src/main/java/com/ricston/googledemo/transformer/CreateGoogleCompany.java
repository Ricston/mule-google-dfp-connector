/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.ricston.googledemo.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.module.http.internal.ParameterMap;
import org.mule.transformer.AbstractMessageTransformer;

import com.google.api.ads.dfp.axis.v201605.Company;
import com.google.api.ads.dfp.axis.v201605.CompanyType;

public class CreateGoogleCompany extends AbstractMessageTransformer {

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding)
            throws TransformerException {
         
        String name = ((ParameterMap) message.getPayload()).get("companyName");
        String address = ((ParameterMap) message.getPayload()).get("companyAddress");
        String email = ((ParameterMap) message.getPayload()).get("companyEmail");

        Company newCompany = new Company();
        newCompany.setName(name);
        
        newCompany.setAddress(address);
                
        newCompany.setEmail(email);

        newCompany.setType(CompanyType.AGENCY);
        
        message.setPayload(newCompany);

        return message;
    }
}