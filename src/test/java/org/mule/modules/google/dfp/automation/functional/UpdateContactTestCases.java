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
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Contact;

public class UpdateContactTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateContactTestCases() {
        super(GoogleDfpConnector.class);
    }

    Contact contactToUpdate;
    String trueEmail;

    @Before
    public void setup() throws Exception {
        contactToUpdate = getConnector().getContactByName("Contact Test");
        trueEmail = contactToUpdate.getEmail();
        contactToUpdate.setEmail("fake@ricston.com");
    }

    @After
    public void tearDown() throws Exception {
        contactToUpdate = getConnector().getContactByName("Contact Test");
        contactToUpdate.setEmail(trueEmail);
        getConnector().updateContact(contactToUpdate);
    }

    @Test
    public void testUpdateContact() throws Exception {

        Contact updatedContact = getConnector().updateContact(contactToUpdate);
        Assert.assertNotNull(updatedContact);
        Assert.assertEquals(updatedContact.getEmail(), "fake@ricston.com");
    }

    @Test
    public void testUpdateContactNotFound() throws Exception {
        try {
            contactToUpdate.setId(12345L);
            getConnector().updateContact(contactToUpdate);
        } catch (UpdateFailedException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }

    }

}
