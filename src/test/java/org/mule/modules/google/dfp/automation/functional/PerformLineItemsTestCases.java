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
import org.mule.modules.google.dfp.exceptions.PerformLineItemsException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetLineItems();
        queryParamsNotFound = TestDataBuilder.getGetLineItemsNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performLineItems("id = :id", queryParams, "Unarchive Line Items");
    }

    @Test
    public void testPerformLineItems() throws Exception {

        Integer lineItemsPerformed = getConnector().performLineItems("id = :id", queryParams, "Archive Line Items");
        Assert.assertEquals(lineItemsPerformed, Integer.valueOf(2));
    }

    @Test
    public void testPerformLineItemsNotFound() throws Exception {
        try {
            getConnector().performLineItems("id = :id", queryParamsNotFound, "Archive Line Items");
        } catch (PerformLineItemsException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
