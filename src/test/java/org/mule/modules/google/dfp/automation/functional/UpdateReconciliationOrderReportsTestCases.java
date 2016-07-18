/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReport;

public class UpdateReconciliationOrderReportsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateReconciliationOrderReportsTestCases() {
        super(GoogleDfpConnector.class);
    }

    ReconciliationOrderReport reconciliationOrderReportToUpdate;
    Long reconciliationOrderReportOrderId;

    @Before
    public void setup() throws Exception {
        // XXX Reconciliation Order Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationOrderReportToUpdate = getConnector().getReconciliationOrderReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationOrderReportOrderId = reconciliationOrderReportToUpdate.getOrderId();
        // reconciliationOrderReportToUpdate.setOrderId(312984608L);
    }

    @After
    public void tearDown() throws Exception {
        // XXX Reconciliation Order Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationOrderReportToUpdate = getConnector().getReconciliationOrderReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationOrderReportToUpdate.setOrderId(reconciliationOrderReportOrderId);
        // getConnector().updateReconciliationOrderReports(Arrays.asList(reconciliationOrderReportToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateReconciliationOrderReports() throws Exception {
        // XXX Reconciliation Order Report cannot be created without a premium account, so we cannot retrieve them
        // List<ReconciliationOrderReport> list = new ArrayList<ReconciliationOrderReport>();
        // list.add(reconciliationOrderReportToUpdate);
        // ReconciliationOrderReport[] updatedReconciliationOrderReports = getConnector().updateReconciliationOrderReports(list);
        // Assert.assertNotNull(updatedReconciliationOrderReports);
        // Assert.assertEquals((Long) updatedReconciliationOrderReports[0].getOrderId(), (Long) 312984608L);
    }

    @Test
    @Ignore
    public void testUpdateReconciliationOrderReportsNotFound() throws Exception {
        // XXX Reconciliation Order Report cannot be created without a premium account, so we cannot retrieve them
        // try {
        // reconciliationOrderReportToUpdate.setId(12345L);
        // getConnector().updateReconciliationOrderReports(Arrays.asList(reconciliationOrderReportToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
