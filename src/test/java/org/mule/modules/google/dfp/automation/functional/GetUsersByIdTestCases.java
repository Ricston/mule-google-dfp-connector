/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.User;

public class GetUsersByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetUsersByIdTestCases.class);

    private List<Long> listIds;

    public GetUsersByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setUp() throws Exception {

        try {
            List<User> listUsers;
            listUsers = getConnector().getUsersByStatement();
            Long id = listUsers.get(0)
                    .getId();

            listIds = new ArrayList<Long>();
            listIds.add(id);
        } catch (Exception e) {
            logger.error("Failed to get a valid 'Id' to test 'getUsersById()' operation. ");
            throw e;
        }
    }

    @Test
    public void testGetUsersById() throws Exception {

        List<User> listUsers = getConnector().getUsersById(listIds);

        Assert.assertNotNull(listUsers);
        if (!listUsers.isEmpty()) {
            Assert.assertTrue(((List<User>) listUsers).get(0) instanceof User);
            Assert.assertEquals(listIds.get(0), listUsers.get(0)
                    .getId());
        } else {
            Assert.assertTrue(listIds.isEmpty());
        }
    }
}
