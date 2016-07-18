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

import com.google.api.ads.dfp.axis.v201605.ReconciliationReport;

public class UpdateReconciliationReportsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateReconciliationReportsTestCases() {
        super(GoogleDfpConnector.class);
    }

    ReconciliationReport reconciliationReportToUpdate;
    String reconciliationReportNotes;

    @Before
    public void setup() throws Exception {
        // XXX Reconciliation Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationReportToUpdate = getConnector().getReconciliationReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationReportNotes = reconciliationReportToUpdate.getNotes();
        // reconciliationReportToUpdate.setNotes("Proof Notes");
    }

    @After
    public void tearDown() throws Exception {
        // XXX Reconciliation Report cannot be created without a premium account, so we cannot retrieve them
        // reconciliationReportToUpdate = getConnector().getReconciliationReports(queryString, queryParams, queryOrder, queryLimit, queryOffset)
        // .get(0);
        // reconciliationReportToUpdate.setNotes(reconciliationReportNotes);
        // getConnector().updateReconciliationReports(Arrays.asList(reconciliationReportToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateReconciliationReports() throws Exception {
        // XXX Reconciliation Report cannot be created without a premium account, so we cannot retrieve them
        // List<ReconciliationReport> list = new ArrayList<ReconciliationReport>();
        // list.add(reconciliationReportToUpdate);
        // ReconciliationReport[] updatedReconciliationReports = getConnector().updateReconciliationReports(list);
        // Assert.assertNotNull(updatedReconciliationReports);
        // Assert.assertEquals(updatedReconciliationReports[0].getNotes(), "Proof Notes");
    }

    @Test
    @Ignore
    public void testUpdateReconciliationReportsNotFound() throws Exception {
        // XXX Reconciliation Report cannot be created without a premium account, so we cannot retrieve them
        // try {
        // reconciliationReportToUpdate.setId(12345L);
        // getConnector().updateReconciliationReports(Arrays.asList(reconciliationReportToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
