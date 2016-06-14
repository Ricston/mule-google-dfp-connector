/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.ReportJob;

public class CreateAudienceReportTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateAudienceReportTestCases() {
        super(GoogleDfpConnector.class);
    }

    Date startDate;
    Date endDate;

    @Before
    public void setup() {
        startDate = TestDataBuilder.getCreateAudienceReportStartDate();
        endDate = TestDataBuilder.getCreateAudienceReportEndDate();
    }

    @Test
    public void testCreateAudienceReport() throws Exception {
        ReportJob report = getConnector().createAudienceReport(startDate, endDate);
        Assert.assertNotNull(report);
    }

}