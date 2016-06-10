package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Contact;

public class GetContactsByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetUsersByIdTestCases.class);

    public GetContactsByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetContactsByStatement() throws Exception {

        List<Contact> listContacts;
        listContacts = getConnector().getContactsByStatement();

        Assert.assertNotNull(listContacts);
        if (!listContacts.isEmpty()) {
            Assert.assertNotNull(listContacts.get(0));
            Assert.assertNotNull(listContacts.get(0)
                    .getId());
        } else {
            logger.warn("'getContactsByStatement()' operation returned an empty list, dataset for testing could be incorrect or outdated. ");
        }
    }
}
