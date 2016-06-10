package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.User;

public class GetUsersByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetUsersByIdTestCases.class);

    public GetUsersByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetUsersByStatement() throws Exception {

        List<User> listUsers;
        listUsers = getConnector().getUsersByStatement();

        Assert.assertNotNull(listUsers);
        if (!listUsers.isEmpty()) {
            Assert.assertNotNull(listUsers.get(0));
            Assert.assertNotNull(listUsers.get(0)
                    .getId());
        } else {
            logger.warn("'getUsersByStatement()' operation returned an empty list, dataset for testing could be incorrect or outdated. ");
        }
    }
}
