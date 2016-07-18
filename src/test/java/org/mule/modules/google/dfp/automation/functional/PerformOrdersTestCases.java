/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.PerformOrdersException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformOrdersTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformOrdersTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetOrders();
        queryParamsNotFound = TestDataBuilder.getGetOrdersNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performOrders("id = :id", queryParams, "Unarchive Orders");
    }

    @Test
    public void testPerformOrders() throws Exception {

        Integer ordersPerformed = getConnector().performOrders("id = :id", queryParams, "Archive Orders");
        Assert.assertEquals(ordersPerformed, Integer.valueOf(2));
    }

    @Test
    public void testPerformOrdersNotFound() throws Exception {
        try {
            getConnector().performOrders("id = :id", queryParamsNotFound, "Archive Orders");
        } catch (PerformOrdersException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
