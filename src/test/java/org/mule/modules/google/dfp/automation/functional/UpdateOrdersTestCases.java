/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Order;

public class UpdateOrdersTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateOrdersTestCases() {
        super(GoogleDfpConnector.class);
    }

    Order orderToUpdate;
    String orderName;

    @Before
    public void setup() throws Exception {
        orderToUpdate = getConnector().getOrdersById(Arrays.asList(312984608L))
                .get(0);
        orderName = orderToUpdate.getName();
        orderToUpdate.setName("TestingFormOrder");
    }

    @After
    public void tearDown() throws Exception {
        orderToUpdate = getConnector().getOrdersById(Arrays.asList(312984608L))
                .get(0);
        orderToUpdate.setName(orderName);
        getConnector().updateOrders(Arrays.asList(orderToUpdate));
    }

    @Test
    public void testUpdateOrders() throws Exception {

        // Order can not be deleted so the order with ID=312984608 always will exist.
        List<Order> list = new ArrayList<Order>();
        list.add(orderToUpdate);
        Order[] updatedOrders = getConnector().updateOrders(list);
        Assert.assertNotNull(updatedOrders);
        Assert.assertEquals(updatedOrders[0].getName(), "TestingFormOrder");
    }

    @Test
    public void testUpdateOrdersNotFound() throws Exception {
        try {
            orderToUpdate.setId(12345L);
            getConnector().updateOrders(Arrays.asList(orderToUpdate));
        } catch (UpdateFailedException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }

    }

}
