/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.automation.functional.CreateCompanyTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateReachReportTestCases;
import org.mule.modules.google.dfp.automation.functional.DownloadReportTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAllCompaniesTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCompanyByIdTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateCompanyTestCases.class,
        CreateReachReportTestCases.class,
        DownloadReportTestCases.class,
        GetAllCompaniesTestCases.class,
        GetCompanyByIdTestCases.class
})
public class FunctionalTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(GoogleDfpConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }

}
