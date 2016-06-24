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
import org.mule.modules.google.dfp.automation.functional.CreateContactTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateReportTestCases;
import org.mule.modules.google.dfp.automation.functional.DownloadReportTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAdvertiserByNameTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAgencyByNameTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAllCompaniesTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAllLineItemsPqlTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAudienceSegmentsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCompaniesByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCompanyByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetContactByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetContactByNameTestCases;
import org.mule.modules.google.dfp.automation.functional.GetContactsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCustomFieldsByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCustomFieldsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCustomTargetingKeysByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCustomTargetingValuesByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetFilteredLineItemsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetLineItemsByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetLineItemsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetOrdersByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetOrdersByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProposalRetractionReasonPqlTestCases;
import org.mule.modules.google.dfp.automation.functional.GetRateCardsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetUsersByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetUsersByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateCompanyTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateContactTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateCompanyTestCases.class,
        CreateContactTestCases.class,
        CreateReportTestCases.class,
        DownloadReportTestCases.class,
        GetAdvertiserByNameTestCases.class,
        GetAgencyByNameTestCases.class,
        GetAllCompaniesTestCases.class,
        GetAllLineItemsPqlTestCases.class,
        GetAudienceSegmentsByStatementTestCases.class,
        GetCompaniesByIdTestCases.class,
        GetCompanyByIdTestCases.class,
        GetContactByIdTestCases.class,
        GetContactByNameTestCases.class,
        GetContactsByStatementTestCases.class,
        GetCustomFieldsByIdTestCases.class,
        GetCustomFieldsByStatementTestCases.class,
        GetCustomTargetingKeysByStatementTestCases.class,
        GetCustomTargetingValuesByStatementTestCases.class,
        GetFilteredLineItemsByStatementTestCases.class,
        GetLineItemsByIdTestCases.class,
        GetLineItemsByStatementTestCases.class,
        GetOrdersByIdTestCases.class,
        GetOrdersByStatementTestCases.class,
        // GetProductsByIdTestCases.class,
        // GetProductsByStatmentTestCases.class,
        GetProposalRetractionReasonPqlTestCases.class,
        GetRateCardsByStatementTestCases.class,
        GetUsersByIdTestCases.class,
        GetUsersByStatementTestCases.class,
        UpdateCompanyTestCases.class,
        UpdateContactTestCases.class
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
