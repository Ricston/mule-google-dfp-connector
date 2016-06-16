/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Order;

public class GetOrdersByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetUsersByIdTestCases.class);

    private DateTime lastModifiedDate;
    private DateTime snapshotDateTime;

    public GetOrdersByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        lastModifiedDate = TestDataBuilder.GetOrdersByStatementLastModifiedDate();
        snapshotDateTime = TestDataBuilder.GetOrdersByStatementSnapshotDateTime();
    }

    @Test
    public void testGetOrdersByStatement() throws Exception {

        List<Order> listOrders;
        listOrders = getConnector().getOrdersByStatement(lastModifiedDate, snapshotDateTime);

        Assert.assertNotNull(listOrders);
        if (!listOrders.isEmpty()) {
            Assert.assertNotNull(listOrders.get(0));
            Assert.assertNotNull(listOrders.get(0)
                    .getId());
        } else {
            logger.warn("'getOrdersByStatement()' operation returned an empty list, dataset for testing could be incorrect or outdated. ");
        }
    }
}
