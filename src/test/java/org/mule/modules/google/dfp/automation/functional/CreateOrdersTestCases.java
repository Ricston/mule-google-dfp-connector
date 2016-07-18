/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Order;

public class CreateOrdersTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateOrdersTestCases() {
        super(GoogleDfpConnector.class);
    }

    private Order newOrder;
    private Order createdOrder;

    @Before
    public void setup() throws Exception {
        newOrder = TestDataBuilder.getCreateOrdersNewOrder();
    }

    @Test
    public void testCreateOrders() throws Exception {
        createdOrder = getConnector().createOrders(Arrays.asList(newOrder))[0];
        Assert.assertNotNull(createdOrder);
        Assert.assertEquals(newOrder.getName(), createdOrder.getName());
    }

    @After
    public void tearDown() throws Exception {
        createdOrder.setName(createdOrder.getName() + createdOrder.getId());
        getConnector().updateOrders(Arrays.asList(createdOrder));
    }
}
