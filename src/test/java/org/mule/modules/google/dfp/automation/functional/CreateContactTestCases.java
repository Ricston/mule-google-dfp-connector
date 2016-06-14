/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Contact;

public class CreateContactTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateContactTestCases() {
        super(GoogleDfpConnector.class);
    }

    private Contact newContact;
    private Contact createdContact;

    @Before
    public void setup() throws Exception {
        newContact = new Contact();
        newContact.setEmail("ricston@ricston.com");
        newContact.setName("ContactTest");
        newContact.setCompanyId(59503208L);
    }

    @Test
    public void testCreateContact() throws Exception {
        createdContact = getConnector().createContact(newContact);
        Assert.assertNotNull(createdContact);
        Assert.assertEquals(newContact.getName(), createdContact.getName());
    }

    @After
    public void tearDown() throws Exception {
        createdContact.setName(createdContact.getName() + createdContact.getId());
        getConnector().updateContact(createdContact);
    }
}
