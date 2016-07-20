/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReport;

public class GetReconciliationOrderReportsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;

    public GetReconciliationOrderReportsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        queryParams = TestDataBuilder.getGetReconciliationOrderReports();
    }

    @Test
    @Ignore
    public void testGetReconciliationOrderReports() throws Exception {

        List<ReconciliationOrderReport> reconciliationOrderReports = getConnector().getReconciliationOrderReports("", queryParams, "id ASC", 400, 0);

        Assert.assertNotNull(reconciliationOrderReports);
        if (CollectionUtils.isNotEmpty((List<ReconciliationOrderReport>) reconciliationOrderReports)) {
            Assert.assertTrue(reconciliationOrderReports.get(0) instanceof ReconciliationOrderReport);
        }
    }

}
