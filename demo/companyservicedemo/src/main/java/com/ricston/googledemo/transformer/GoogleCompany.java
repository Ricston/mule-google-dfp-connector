/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.ricston.googledemo.transformer;

import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.google.api.ads.dfp.axis.v201602.Company;
import com.google.api.ads.dfp.axis.v201602.CompanyType;

public class GoogleCompany extends AbstractMessageTransformer {

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding)
            throws TransformerException {

        Map<String, Object> queryParams = message.getInboundProperty("http.query.params");
        String name = (String) queryParams.get("name");
        String type = (String) queryParams.get("type");

        Company newCompany = new Company();
        newCompany.setName(name);
        newCompany.setAddress("Mosta, Malta");
        if (type != null) {
            try {
                newCompany.setType(CompanyType.fromString(type.toUpperCase()));
            } catch (IllegalArgumentException e) {
                newCompany.setType(CompanyType.ADVERTISER);
            }
        } else {
            newCompany.setType(CompanyType.ADVERTISER);
        }
        newCompany.setEmail("googledfp@ricston.com");

        message.setPayload(newCompany);

        return message;
    }
}
