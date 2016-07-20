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
import org.mule.modules.google.dfp.exceptions.PerformReconciliationOrderReportsException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformReconciliationOrderReportsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformReconciliationOrderReportsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetReconciliationOrderReports();
        queryParamsNotFound = TestDataBuilder.getGetReconciliationOrderReportsNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performReconciliationOrderReports("", queryParams, "Revert Reconciliation Order Reports");
    }

    @Test
    @Ignore
    public void testPerformReconciliationOrderReports() throws Exception {

        Integer reconciliationOrderReportsPerformed = getConnector().performReconciliationOrderReports("", queryParams, "Submit Reconciliation Order Reports");
        Assert.assertTrue(reconciliationOrderReportsPerformed > 0);
    }

    @Test
    @Ignore
    public void testPerformReconciliationOrderReportsNotFound() throws Exception {
        try {
            getConnector().performReconciliationOrderReports("id = :id", queryParamsNotFound, "Submit Reconciliation Order Reports");
        } catch (PerformReconciliationOrderReportsException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
