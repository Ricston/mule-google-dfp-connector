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
import org.mule.modules.google.dfp.automation.functional.CreateCompaniesTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateCompanyTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateContactTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateOrdersTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateProposalLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateProposalsTestCases;
import org.mule.modules.google.dfp.automation.functional.CreateReportTestCases;
import org.mule.modules.google.dfp.automation.functional.DownloadReportTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAdvertiserByNameTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAgencyByNameTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAllCompaniesTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAllLineItemsPqlTestCases;
import org.mule.modules.google.dfp.automation.functional.GetAudienceSegmentsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCompaniesByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetCompaniesTestCases;
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
import org.mule.modules.google.dfp.automation.functional.GetLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetOrdersByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetOrdersByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetOrdersTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProductsByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProductsByStatmentTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProductsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProposalLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProposalRetractionReasonPqlTestCases;
import org.mule.modules.google.dfp.automation.functional.GetProposalsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetRateCardsByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.GetReconciliationLineItemReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetReconciliationOrderReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetReconciliationReportRowsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetReconciliationReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.GetUsersByIdTestCases;
import org.mule.modules.google.dfp.automation.functional.GetUsersByStatementTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformOrdersTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformProductsTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformProposalLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformProposalsTestCases;
import org.mule.modules.google.dfp.automation.functional.PerformReconciliationOrderReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.TestDataBuilder;
import org.mule.modules.google.dfp.automation.functional.UpdateCompaniesTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateCompanyTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateContactTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateOrdersTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateProductsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateProposalLineItemsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateProposalsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateReconciliationLineItemReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateReconciliationOrderReportsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateReconciliationReportRowsTestCases;
import org.mule.modules.google.dfp.automation.functional.UpdateReconciliationReportsTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateCompaniesTestCases.class,
        CreateCompanyTestCases.class,
        CreateContactTestCases.class,
        CreateLineItemsTestCases.class,
        CreateOrdersTestCases.class,
        CreateProposalLineItemsTestCases.class,
        CreateProposalsTestCases.class,
        CreateReportTestCases.class,
        DownloadReportTestCases.class,
        GetAdvertiserByNameTestCases.class,
        GetAgencyByNameTestCases.class,
        GetAllCompaniesTestCases.class,
        GetAllLineItemsPqlTestCases.class,
        GetAudienceSegmentsByStatementTestCases.class,
        GetCompaniesByIdTestCases.class,
        GetCompaniesTestCases.class,
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
        GetLineItemsTestCases.class,
        GetOrdersByIdTestCases.class,
        GetOrdersByStatementTestCases.class,
        GetOrdersTestCases.class,
        GetProductsByIdTestCases.class,
        GetProductsByStatmentTestCases.class,
        GetProductsTestCases.class,
        GetProposalLineItemsTestCases.class,
        GetProposalRetractionReasonPqlTestCases.class,
        GetProposalsTestCases.class,
        GetRateCardsByStatementTestCases.class,
        GetReconciliationLineItemReportsTestCases.class,
        GetReconciliationOrderReportsTestCases.class,
        GetReconciliationReportRowsTestCases.class,
        GetReconciliationReportsTestCases.class,
        GetUsersByIdTestCases.class,
        GetUsersByStatementTestCases.class,
        PerformLineItemsTestCases.class,
        PerformOrdersTestCases.class,
        PerformProductsTestCases.class,
        PerformProposalLineItemsTestCases.class,
        PerformProposalsTestCases.class,
        PerformReconciliationOrderReportsTestCases.class,
        TestDataBuilder.class,
        UpdateCompaniesTestCases.class,
        UpdateCompanyTestCases.class,
        UpdateContactTestCases.class,
        UpdateLineItemsTestCases.class,
        UpdateOrdersTestCases.class,
        UpdateProductsTestCases.class,
        UpdateProposalLineItemsTestCases.class,
        UpdateProposalsTestCases.class,
        UpdateReconciliationLineItemReportsTestCases.class,
        UpdateReconciliationOrderReportsTestCases.class,
        UpdateReconciliationReportRowsTestCases.class,
        UpdateReconciliationReportsTestCases.class
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
