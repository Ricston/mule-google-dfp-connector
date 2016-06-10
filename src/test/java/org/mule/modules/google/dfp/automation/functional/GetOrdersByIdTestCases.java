package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Order;

public class GetOrdersByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetOrdersByIdTestCases.class);

    private List<Long> listIds;

    public GetOrdersByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setUp() throws Exception {

        DateTime lastModifiedDate = TestDataBuilder.GetOrdersByStatementLastModifiedDate();
        DateTime snapshotDateTime = TestDataBuilder.GetOrdersByStatementSnapshotDateTime();
        try {
            List<Order> listOrders;
            listOrders = getConnector().getOrdersByStatement(lastModifiedDate, snapshotDateTime);
            Long id = listOrders.get(0)
                    .getId();

            listIds = new ArrayList<Long>();
            listIds.add(id);
        } catch (Exception e) {
            logger.error("Failed to get a valid 'Id' to test 'GetOrdersById()' operation. ");
            throw e;
        }
    }

    @Test
    public void testGetOrdersById() throws Exception {

        List<Order> listOrders = getConnector().getOrdersById(listIds);

        Assert.assertNotNull(listOrders);
        if (!listOrders.isEmpty()) {
            Assert.assertTrue(((List<Order>) listOrders).get(0) instanceof Order);
            Assert.assertEquals(listIds.get(0), listOrders.get(0)
                    .getId());
        } else {
            Assert.assertTrue(listIds.isEmpty());
        }
    }
}
