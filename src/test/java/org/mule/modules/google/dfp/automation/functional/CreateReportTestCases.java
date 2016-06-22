/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.CreateReportException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.ReportJob;

public class CreateReportTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateReportTestCases() {
        super(GoogleDfpConnector.class);
    }

    private Date startDate;
    private Date endDate;

    @Before
    public void setup() {
        // IMPORTANT!!
        // Reach reports need to include date ranges that can be broken down into full weeks (or months).
        // For example, valid full weeks have a Sunday start date and a Saturday end date.

        startDate = TestDataBuilder.getCreateReachReportStartDate();
        endDate = TestDataBuilder.getCreateReachReportEndDate();
    }

    @Test
    public void testCreateReport() throws Exception {
        ReportJob report = getConnector().createReport("LINE_ITEM_ID,MONTH_AND_YEAR", "REACH", null, startDate, endDate);
        Assert.assertNotNull(report);
    }

    @Test
    public void testCreateReportBadDimensions() throws Exception {
        try {
            getConnector().createReport("LINE_ITEM_ID,MONTH_AND_Y", "REACH", null, startDate, endDate);
        } catch (CreateReportException e) {
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testCreateReportBadColumns() throws Exception {
        try {
            getConnector().createReport("LINE_ITEM_ID,MONTH_AND_YEAR", "REAC", null, startDate, endDate);
        } catch (CreateReportException e) {
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testCreateNullParams() throws Exception {
        try {
            getConnector().createReport(null, null, null, startDate, endDate);
        } catch (CreateReportException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

    @Test
    public void testCreateNullDimensions() throws Exception {
        try {
            getConnector().createReport(null, "REACH", null, startDate, endDate);
        } catch (CreateReportException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
