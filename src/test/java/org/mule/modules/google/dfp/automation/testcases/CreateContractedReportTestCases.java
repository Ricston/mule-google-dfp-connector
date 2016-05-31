/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.testcases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201602.Date;
import com.google.api.ads.dfp.axis.v201602.ReportJob;

public class CreateContractedReportTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateContractedReportTestCases() {
        super(GoogleDfpConnector.class);
    }

    Date startDate;
    Date endDate;

    @Before
    public void setup() {

        // IMPORTANT!!
        // Reach reports need to include date ranges that can be broken down into full weeks (or months).
        // For example, valid full weeks have a Sunday start date and a Saturday end date.

        startDate = new Date(2015, 1, 1);
        endDate = new Date(2015, 1, 31);
    }

    @Test
    public void testCreateContratedReport() throws Throwable {

        Object report = getDispatcher().runMethod("createContratedReport", new Object[] {
                startDate,
                endDate
        });
        Assert.assertNotNull(report);
        Assert.assertTrue(report instanceof ReportJob);
    }

}
