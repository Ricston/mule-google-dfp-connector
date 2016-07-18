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

import com.google.api.ads.dfp.axis.v201605.ReconciliationLineItemReport;

public class UpdateReconciliationLineItemReportsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateReconciliationLineItemReportsTestCases() {
        super(GoogleDfpConnector.class);
    }

    ReconciliationLineItemReport reconciliationLineItemReportToUpdate;
    Long reconciliationLineItemReportLineItemId;

    @Before
    public void setup() throws Exception {
        // XXX Reconciliation Line Item Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationLineItemReportToUpdate = getConnector().getReconciliationLineItemReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationLineItemReportLineItemId = reconciliationLineItemReportToUpdate.getLineItemId();
        // reconciliationLineItemReportToUpdate.setLineItemId(113219888L);
    }

    @After
    public void tearDown() throws Exception {
        // XXX Reconciliation Line Item Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationLineItemReportToUpdate = getConnector().getReconciliationLineItemReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationLineItemReportToUpdate.setLineItemId(reconciliationLineItemReportLineItemId);
        // getConnector().updateReconciliationLineItemReports(Arrays.asList(reconciliationLineItemReportToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateReconciliationLineItemReports() throws Exception {
        // XXX Reconciliation Line Item Report cannot be created without a premium account, so we cannot retrieve them
        // List<ReconciliationLineItemReport> list = new ArrayList<ReconciliationLineItemReport>();
        // list.add(reconciliationLineItemReportToUpdate);
        // ReconciliationLineItemReport[] updatedreconciliationLineItemReports = getConnector().updateReconciliationLineItemReports(list);
        // Assert.assertNotNull(updatedreconciliationLineItemReports);
        // Assert.assertEquals((Long) updatedreconciliationLineItemReports[0].getLineItemId(), (Long) 113219888L);
    }

    @Test
    @Ignore
    public void testUpdateReconciliationLineItemReportsNotFound() throws Exception {
        // XXX Reconciliation Line Item Report cannot be created without a premium account, so we cannot retrieve them
        // try {
        // reconciliationLineItemReportToUpdate.setId(12345L);
        // getConnector().updateReconciliationLineItemReports(Arrays.asList(reconciliationLineItemReportToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
