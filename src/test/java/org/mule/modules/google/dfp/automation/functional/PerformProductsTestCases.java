/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.PerformProductsException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformProductsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformProductsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetProducts();
        queryParamsNotFound = TestDataBuilder.getGetProductsNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performProducts("", queryParams, "Activate Products");

    }

    @Test
    @Ignore
    public void testPerformProducts() throws Exception {

        Integer productsPerformed = getConnector().performProducts("", queryParams, "Deactivate Products");
        Assert.assertTrue(productsPerformed > 0);
    }

    @Test
    @Ignore
    public void testPerformProductsNotFound() throws Exception {
        try {
            getConnector().performProducts("id = :id", queryParamsNotFound, "Deactivate Products");
        } catch (PerformProductsException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
