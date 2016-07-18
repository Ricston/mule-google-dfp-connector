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

import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRow;

public class UpdateReconciliationReportRowsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateReconciliationReportRowsTestCases() {
        super(GoogleDfpConnector.class);
    }

    ReconciliationReportRow reconciliationReportRowToUpdate;
    Long reconciliationReportRowOrderId;

    @Before
    public void setup() throws Exception {
        // XXX Reconciliation Report Row cannot be created without a premium account, so we cannot retrieve them
        // reconciliationReportRowToUpdate = getConnector().getReconciliationReportRows(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationReportRowOrderId = reconciliationReportRowToUpdate.getOrderId();
        // reconciliationReportRowToUpdate.setOrderId(312984608L);
    }

    @After
    public void tearDown() throws Exception {
        // XXX Reconciliation Report Row cannot be created without a premium account, so we cannot retrieve them
        // reconciliationReportRowToUpdate = getConnector().getReconciliationReportRows(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationReportRowToUpdate.setOrderId(reconciliationReportRowOrderId);
        // getConnector().updateReconciliationReportRows(Arrays.asList(reconciliationReportRowToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateReconciliationReportRows() throws Exception {
        // XXX Reconciliation Report Row cannot be created without a premium account, so we cannot retrieve them
        // List<ReconciliationReportRow> list = new ArrayList<ReconciliationReportRow>();
        // list.add(reconciliationReportRowToUpdate);
        // ReconciliationReportRow[] updatedReconciliationReportRows = getConnector().updateReconciliationReportRows(list);
        // Assert.assertNotNull(updatedReconciliationReportRows);
        // Assert.assertEquals((Long) updatedReconciliationReportRows[0].getOrderId(), (Long) 312984608L);
    }

    @Test
    @Ignore
    public void testUpdateReconciliationReportRowsNotFound() throws Exception {
        // XXX Reconciliation Report Row cannot be created without a premium account, so we cannot retrieve them
        // try {
        // reconciliationReportRowToUpdate.setId(12345L);
        // getConnector().updateReconciliationReportRows(Arrays.asList(reconciliationReportRowToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
