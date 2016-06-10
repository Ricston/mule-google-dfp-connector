/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.ReportJob;

public class DownloadReportTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public DownloadReportTestCases() {
        super(GoogleDfpConnector.class);
    }

    private ReportJob createdReport;
    private GoogleDfpConnector dfp;
    private Date startDate;
    private Date endDate;

    @Before
    public void setup() throws Exception {

        // IMPORTANT!!
        // Reach reports need to include date ranges that can be broken down into full weeks (or months).
        // For example, valid full weeks have a Sunday start date and a Saturday end date.
        //
        startDate = TestDataBuilder.getDownloadReportStartDate();
        endDate = TestDataBuilder.getDownloadReportEndDate();
        dfp = getConnector();
        createdReport = dfp.createReachReport(startDate, endDate);
    }

    protected InputStream downloadReport(ReportJob reportJob) throws Exception {
        InputStream downloadedReport = dfp.downloadReport(createdReport);
        Assert.assertNotNull(downloadedReport);
        return downloadedReport;
    }

    @Test
    public void testDownloadReport() throws Exception {
        InputStream report = downloadReport(createdReport);
        report.close();
    }

}
