/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Contact;

public class GetContactByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Long correctId;
    private Long incorrectId;

    public GetContactByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        correctId = TestDataBuilder.getGetContactByIdCorrectId();
        incorrectId = TestDataBuilder.getGetContactByIdWrongId();
    }

    @Test
    public void testGetContactById() throws Exception {

        Contact contact = getConnector().getContactById(correctId);
        Assert.assertNotNull(contact);
        Assert.assertEquals(correctId, contact.getId());
    }

    @Test
    public void testGetContactByIdNotFound() throws Exception {

        Contact contact = getConnector().getContactById(incorrectId);
        Assert.assertNull(contact);
    }

}
