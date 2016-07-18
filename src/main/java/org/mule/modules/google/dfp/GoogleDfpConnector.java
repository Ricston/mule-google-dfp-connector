/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.google.dfp.exceptions.AudienceSegmentException;
import org.mule.modules.google.dfp.exceptions.CreateFailedException;
import org.mule.modules.google.dfp.exceptions.CreateReportException;
import org.mule.modules.google.dfp.exceptions.CustomTargetingException;
import org.mule.modules.google.dfp.exceptions.GetAdvertiserByNameException;
import org.mule.modules.google.dfp.exceptions.GetAgencyByNameException;
import org.mule.modules.google.dfp.exceptions.GetAllCompaniesException;
import org.mule.modules.google.dfp.exceptions.GetAllContactsException;
import org.mule.modules.google.dfp.exceptions.GetCompaniesException;
import org.mule.modules.google.dfp.exceptions.GetCompanyByIdException;
import org.mule.modules.google.dfp.exceptions.GetContactByIdException;
import org.mule.modules.google.dfp.exceptions.GetContactByNameException;
import org.mule.modules.google.dfp.exceptions.GetCustomFieldsException;
import org.mule.modules.google.dfp.exceptions.GetLineItemsException;
import org.mule.modules.google.dfp.exceptions.GetOrdersException;
import org.mule.modules.google.dfp.exceptions.GetProductTemplatesException;
import org.mule.modules.google.dfp.exceptions.GetProductsByStatementException;
import org.mule.modules.google.dfp.exceptions.GetProductsException;
import org.mule.modules.google.dfp.exceptions.GetProposalLineItemsException;
import org.mule.modules.google.dfp.exceptions.GetProposalsException;
import org.mule.modules.google.dfp.exceptions.GetRateCardsException;
import org.mule.modules.google.dfp.exceptions.GetReconciliationLineItemReportsException;
import org.mule.modules.google.dfp.exceptions.GetReconciliationOrderReportsException;
import org.mule.modules.google.dfp.exceptions.GetReconciliationReportRowsException;
import org.mule.modules.google.dfp.exceptions.GetReconciliationReportsException;
import org.mule.modules.google.dfp.exceptions.GetUsersException;
import org.mule.modules.google.dfp.exceptions.PerformLineItemsException;
import org.mule.modules.google.dfp.exceptions.PerformOrdersException;
import org.mule.modules.google.dfp.exceptions.PerformProductsException;
import org.mule.modules.google.dfp.exceptions.PerformProposalLineItemsException;
import org.mule.modules.google.dfp.exceptions.PerformProposalsException;
import org.mule.modules.google.dfp.exceptions.PerformReconciliationOrderReportsException;
import org.mule.modules.google.dfp.exceptions.ReportDownloadException;
import org.mule.modules.google.dfp.exceptions.TooManyAdvertisersFoundException;
import org.mule.modules.google.dfp.exceptions.TooManyAgenciesFoundException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.modules.google.dfp.strategy.GoogleDfpConnectionStrategy;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.AudienceSegment;
import com.google.api.ads.dfp.axis.v201605.Company;
import com.google.api.ads.dfp.axis.v201605.Contact;
import com.google.api.ads.dfp.axis.v201605.CustomField;
import com.google.api.ads.dfp.axis.v201605.CustomFieldOption;
import com.google.api.ads.dfp.axis.v201605.CustomTargetingKey;
import com.google.api.ads.dfp.axis.v201605.CustomTargetingValue;
import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.LineItem;
import com.google.api.ads.dfp.axis.v201605.Order;
import com.google.api.ads.dfp.axis.v201605.Product;
import com.google.api.ads.dfp.axis.v201605.ProductTemplate;
import com.google.api.ads.dfp.axis.v201605.Proposal;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;
import com.google.api.ads.dfp.axis.v201605.RateCard;
import com.google.api.ads.dfp.axis.v201605.ReconciliationLineItemReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRow;
import com.google.api.ads.dfp.axis.v201605.ReportJob;
import com.google.api.ads.dfp.axis.v201605.User;

/**
 * Google DFP Connector
 * 
 * @author Ricston, Ltd.
 */
@RequiresEnterpriseLicense
@Connector(name = "google-dfp", schemaVersion = "1.0", friendlyName = "GoogleDfp", minMuleVersion = "3.6")
public class GoogleDfpConnector {

    @Config
    GoogleDfpConnectionStrategy connectionStrategy;

    /**
     * Creates a report with the given parameters dynamically
     * 
     * @param dimensions
     *            comma-delimited dimensions list
     * @param columns
     *            comma-delimited columns list
     * @param dimensionAttributes
     *            comma-delimited dimension attributes list
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     */
    @Processor
    public ReportJob createReport(String dimensions, String columns, @Optional String dimensionAttributes, Date startDate,
            Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createReport(connectionStrategy.getSession(), startDate, endDate, dimensions, columns, dimensionAttributes);
    }

    /**
     * Download a report from the Google DFP services
     * 
     * @param reportJob
     *            Content to be processed
     * @return Input Stream containing a gz version of the CSV dump of the report
     * @throws ReportDownloadException
     *             Report Download Exception
     */
    @Processor
    public InputStream downloadReport(@Default("#[payload]") ReportJob reportJob) throws ReportDownloadException {
        return connectionStrategy.getReportService()
                .downloadReport(connectionStrategy.getSession(), reportJob);
    }

    /**
     * Retrieve the companies that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<Company> List of companies that match with the parameters
     * @throws GetCompaniesException
     *             Get Companies Exception
     */
    @Processor
    public List<Company> getCompanies(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit, @Default("0") Integer queryOffset)
            throws GetCompaniesException {
        return connectionStrategy.getCompanyService()
                .getCompanies(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Retrieve all companies
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of all companies
     * @throws GetAllCompaniesException
     *             Get All Companies Exception
     */
    @Processor
    public List<Company> getAllCompanies(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetAllCompaniesException {
        return connectionStrategy.getCompanyService()
                .getAllCompanies(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Retrieve the reconciliation report IDs given the start date
     *
     * @param startDate
     *            Start date used for searching reconciliation reports
     * @return List of reconciliation report
     * @throws GetReconciliationReportsException
     *             Reconciliation Report By ID Exception
     */
    @Processor
    public List<ReconciliationReport> getReconciliationReportIdsByStartDate(@Default("#[payload]") Date startDate) throws GetReconciliationReportsException {
        try {
            String dateFormat = "%04d-%02d-%02d";
            String stringDate = String.format(dateFormat, startDate.getYear(), startDate.getMonth(), startDate.getDay());
            return connectionStrategy.getReconciliationReportService()
                    .getReconciliationReportByStartDate(connectionStrategy.getSession(), stringDate);
        } catch (Exception e) {
            throw new GetReconciliationReportsException(e);
        }
    }

    /**
     * Retrieve the Reconciliation Report Rows that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<ReconciliationReportRow> List of reconciliation report rows that match with the parameters
     * @throws GetReconciliationReportRowsException
     *             Get Reconciliation Report Rows Exception
     */
    @Processor
    public List<ReconciliationReportRow> getReconciliationReportRows(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit,
            @Default("0") Integer queryOffset) throws GetReconciliationReportRowsException {
        return connectionStrategy.getReconciliationReportRowService()
                .getReconciliationReportRows(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);

    }

    /**
     * Update a list of Reconciliation Report Rows
     * 
     * @param reconciliationReportRows
     *            The Reconciliation Report Rows for update
     * @return Array of Reconciliation Report Rows that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public ReconciliationReportRow[] updateReconciliationReportRows(@Default("#[payload]") List<ReconciliationReportRow> reconciliationReportRows) throws UpdateFailedException {
        return connectionStrategy.getReconciliationReportRowService()
                .updateReconciliationReportRows(connectionStrategy.getSession(), reconciliationReportRows);
    }

    /**
     * Retrieve the Reconciliation Reports that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<ReconciliationReport> List of reconciliation reports that match with the parameters
     * @throws GetReconciliationReporsException
     *             Get Reconciliation Reports Exception
     */
    @Processor
    public List<ReconciliationReport> getReconciliationReports(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit,
            @Default("0") Integer queryOffset) throws GetReconciliationReportsException {
        return connectionStrategy.getReconciliationReportService()
                .getReconciliationReports(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);

    }

    /**
     * Update a list of Reconciliation Reports
     * 
     * @param reconciliationReports
     *            The Reconciliation Reports for update
     * @return Array of ReconciliationReport that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public ReconciliationReport[] updateReconciliationReports(@Default("#[payload]") List<ReconciliationReport> reconciliationReports) throws UpdateFailedException {
        return connectionStrategy.getReconciliationReportService()
                .updateReconciliationReports(connectionStrategy.getSession(), reconciliationReports);
    }

    /**
     * Retrieve the Reconciliation Line Item Reports that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<ReconciliationLineItemReport> List of reconciliation line item reports that match with the parameters
     * @throws GetReconciliationLineItemReportsException
     *             Get Reconciliation Line Item Reports Exception
     */
    @Processor
    public List<ReconciliationLineItemReport> getReconciliationLineItemReports(String queryString, Map<String, Object> queryParams, String queryOrder,
            @Default("500") Integer queryLimit, @Default("0") Integer queryOffset) throws GetReconciliationLineItemReportsException {
        return connectionStrategy.getReconciliationLineItemReportService()
                .getReconciliationLineItemReports(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);

    }

    /**
     * Update a list of Reconciliation Line Item Reports
     * 
     * @param reconciliationLineItemReports
     *            The Reconciliation Line Item Reports for update
     * @return Array of ReconciliationLineItemReport that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public ReconciliationLineItemReport[] updateReconciliationLineItemReports(@Default("#[payload]") List<ReconciliationLineItemReport> reconciliationLineItemReports)
            throws UpdateFailedException {
        return connectionStrategy.getReconciliationLineItemReportService()
                .updateReconciliationLineItemReports(connectionStrategy.getSession(), reconciliationLineItemReports);
    }

    /**
     * Retrieve the Reconciliation Order Reports that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<ReconciliationOrderReport> List of reconciliation order reports that match with the parameters
     * @throws GetReconciliationOrderReportsException
     *             Get Reconciliation Order Reports Exception
     */
    @Processor
    public List<ReconciliationOrderReport> getReconciliationOrderReports(String queryString, Map<String, Object> queryParams, String queryOrder,
            @Default("500") Integer queryLimit, @Default("0") Integer queryOffset) throws GetReconciliationOrderReportsException {
        return connectionStrategy.getReconciliationOrderReportService()
                .getReconciliationOrderReports(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);

    }

    /**
     * Update a list of Reconciliation Order Reports
     * 
     * @param reconciliationOrderReports
     *            The Reconciliation Order Reports for update
     * @return Array of ReconciliationOrderReport that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public ReconciliationOrderReport[] updateReconciliationOrderReports(@Default("#[payload]") List<ReconciliationOrderReport> reconciliationOrderReports)
            throws UpdateFailedException {
        return connectionStrategy.getReconciliationOrderReportService()
                .updateReconciliationOrderReports(connectionStrategy.getSession(), reconciliationOrderReports);
    }

    /**
     * Perform the choosen action over the Reconciliation Order Reports that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param reconciliationOrderReportAction
     *            Action for perform over the Reconciliation Order Reports that match with the query string
     * @return Number of Reconciliation Order Reports updated
     * @throws PerformReconciliationOrderReportsException
     *             Perform Reconciliation Order Reports Exception
     */
    @MetaDataScope(PerformReconciliationOrderReportsCategory.class)
    @Processor
    public Integer performReconciliationOrderReports(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String reconciliationOrderReportAction)
            throws PerformReconciliationOrderReportsException {
        return connectionStrategy.getReconciliationOrderReportService()
                .performReconciliationOrderReports(connectionStrategy.getSession(), reconciliationOrderReportAction, queryString, queryParams);
    }

    /**
     * Retrieve the company by ID. Null is returned if the company is not found
     *
     * @param companyId
     *            The company ID
     * @return The company
     * @throws GetCompanyByIdException
     *             Get Company By ID Exception
     */
    @Processor
    public Company getCompanyById(Long companyId) throws GetCompanyByIdException {
        return connectionStrategy.getCompanyService()
                .getCompanyById(connectionStrategy.getSession(), companyId);
    }

    /**
     * Retrieve the agency ID by name
     * 
     * @param agencyName
     *            The agency name to retrieve
     * @return The company found
     * @throws GetAgencyByNameException
     *             Get Agency exception
     * @throws TooManyAgenciesFoundException
     *             Too many agencies found exception
     */
    @Processor
    public Company getAgencyByName(String agencyName) throws GetAgencyByNameException, TooManyAgenciesFoundException {
        return connectionStrategy.getCompanyService()
                .getAgencyByName(connectionStrategy.getSession(), agencyName);
    }

    /**
     * Retrieve the advertiser ID by name
     * 
     * @param advertiserName
     *            The advertiser name to retrieve
     * @return The advertiser
     * @throws GetAdvertiserByNameException
     *             Get advertiser by name exception
     * @throws TooManyAdvertisersFoundException
     *             Too many advertisers found exception
     */
    @Processor
    public String getAdvertiserByName(String advertiserName) throws GetAdvertiserByNameException, TooManyAdvertisersFoundException {
        return connectionStrategy.getCompanyService()
                .getAdvertiserByName(connectionStrategy.getSession(), advertiserName);
    }

    /**
     * Retrieve the company by ID. Null is returned if the company is not found
     *
     * @param ids
     *            list of ids
     * @return List of companies
     * @throws GetAllCompaniesException
     *             Get All Companies Exception
     */
    @Processor
    public List<Company> getCompaniesById(List<Long> ids) throws GetAllCompaniesException {
        return connectionStrategy.getCompanyService()
                .getCompaniesById(connectionStrategy.getSession(), ids);
    }

    /**
     * Create a company by supplying a company object
     * 
     * @param company
     *            The company to create
     * @return The created company
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public Company createCompany(@Default("#[payload]") Company company) throws CreateFailedException {
        return connectionStrategy.getCompanyService()
                .createCompany(connectionStrategy.getSession(), company);
    }

    /**
     * Update company by supplying a company object
     * 
     * @param company
     *            The company to update
     * @return The updated company
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Company updateCompany(@Default("#[payload]") Company company) throws UpdateFailedException {
        return connectionStrategy.getCompanyService()
                .updateCompany(connectionStrategy.getSession(), company);
    }

    /**
     * Create a list of Companies
     * 
     * @param companies
     *            The Companies for create
     * @return Array of Companies that has been created
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public Company[] createCompanies(@Default("#[payload]") List<Company> companies) throws CreateFailedException {
        return connectionStrategy.getCompanyService()
                .createCompanies(connectionStrategy.getSession(), companies);
    }

    /**
     * Update a list of Companies
     * 
     * @param companies
     *            The Companies for update
     * @return Array of Companies that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Company[] updateCompanies(@Default("#[payload]") List<Company> companies) throws UpdateFailedException {
        return connectionStrategy.getCompanyService()
                .updateCompanies(connectionStrategy.getSession(), companies);
    }

    /**
     * Retrieve the Products that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<Product> List of products that match with the parameters
     * @throws GetProductsException
     *             Get Products Exception
     */
    @Processor
    public List<Product> getProducts(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit,
            @Default("0") Integer queryOffset) throws GetProductsException {
        return connectionStrategy.getProductService()
                .getProducts(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Retrieve all products
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of all products
     * @throws GetProductsByStatementException
     *             Get Products Exception
     */
    @Processor
    public List<Product> getProductsByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetProductsByStatementException {
        return connectionStrategy.getProductService()
                .getProductsByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Update a list of Products
     * 
     * @param Product
     *            The Products for update
     * @return Array of Products that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Product[] updateProducts(@Default("#[payload]") List<Product> products) throws UpdateFailedException {
        return connectionStrategy.getProductService()
                .updateProducts(connectionStrategy.getSession(), products);
    }

    /**
     * Perform the choosen action over the Products that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param productAction
     *            Action for perform over the Products that match with the query string
     * @return Number of Products updated
     * @throws PerformProductsException
     *             Perform Products Exception
     */
    @MetaDataScope(PerformProductsCategory.class)
    @Processor
    public Integer performProducts(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String productAction) throws PerformProductsException {
        return connectionStrategy.getProductService()
                .performProducts(connectionStrategy.getSession(), productAction, queryString, queryParams);
    }

    /**
     * Retrieve a list of products by id
     *
     * @param ids
     *            the ids of the products
     * @return List of products
     * @throws GetProductsException
     *             Get Products By Statement Exception
     */
    @Processor
    public List<Product> getProductsById(List<Long> ids) throws GetProductsByStatementException {
        return connectionStrategy.getProductService()
                .getProductsById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve all product templates
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of all product templates
     * @throws GetProductTemplatesException
     *             Get Product Templates Exception
     */
    @Processor
    public List<ProductTemplate> getProductTemplatesByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetProductTemplatesException {
        return connectionStrategy.getProductTemplateService()
                .getProductTemplatesByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Retrieve a list of product templates by id
     * 
     * @param ids
     *            the ids of the product templates
     * @return List of product templates by id
     * @throws GetProductTemplatesException
     *             Get Product Templates Exception
     */
    @Processor
    public List<ProductTemplate> getProductTemplatesById(List<Long> ids) throws GetProductTemplatesException {
        return connectionStrategy.getProductTemplateService()
                .getProductTemplatesById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve the Line Items that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<LineItem> List of line items that match with the parameters
     * @throws GetLineItemsException
     *             Get Line Items Exception
     */
    @Processor
    public List<LineItem> getLineItems(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit, @Default("0") Integer queryOffset)
            throws GetLineItemsException {
        return connectionStrategy.getLineItemService()
                .getLineItems(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Retrieve line items by modified date
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of line items
     * @throws GetLineItemsException
     *             Get Line Items Exception
     */
    @Processor
    public List<LineItem> getLineItemsByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetLineItemsException {
        return connectionStrategy.getLineItemService()
                .getLineItemsByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Create a list of Line Items
     * 
     * @param lineItems
     *            The Line Items for create
     * @return Array of Line Items that has been created
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public LineItem[] createLineItems(@Default("#[payload]") List<LineItem> lineItems) throws CreateFailedException {
        return connectionStrategy.getLineItemService()
                .createLineItems(connectionStrategy.getSession(), lineItems);
    }

    /**
     * Update a list of Line Items
     * 
     * @param lineItems
     *            The Line Items for update
     * @return Array of Line Items that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public LineItem[] updateLineItems(@Default("#[payload]") List<LineItem> lineItems) throws UpdateFailedException {
        return connectionStrategy.getLineItemService()
                .updateLineItems(connectionStrategy.getSession(), lineItems);
    }

    /**
     * Perform the choosen action over the Line Items that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param lineItemAction
     *            Action for perform over the Line Items that match with the query string
     * @return Number of Line Items updated
     * @throws PerformLineItemsException
     *             Perform Line Items Exception
     */
    @Processor
    @MetaDataScope(PerformLineItemsCategory.class)
    public Integer performLineItems(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String lineItemAction)
            throws PerformLineItemsException {
        return connectionStrategy.getLineItemService()
                .performLineItems(connectionStrategy.getSession(), lineItemAction, queryString, queryParams);
    }

    /**
     * Retrieve audience segments
     * 
     * @return List of Audience Segments
     * @throws AudienceSegmentException
     *             Audience Segment Exception
     */
    @Processor
    public List<AudienceSegment> getAudienceSegmentsByStatement() throws AudienceSegmentException {
        return connectionStrategy.getAudienceSegmentService()
                .getAudienceSegmentsByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrieve custom targeting keys
     * 
     * 
     * @return List of Custom Targeting Keys
     * @throws CustomTargetingException
     *             Custom Targeting Exception
     * 
     */
    @Processor
    public List<CustomTargetingKey> getCustomTargetingKeysByStatement() throws CustomTargetingException {
        return connectionStrategy.getCustomTargetingService()
                .getCustomTargetingKeysByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrieve custom targeting values
     * 
     * 
     * @return List of Custom Targeting Values
     * @throws CustomTargetingException
     *             Custom Targeting Exception
     * 
     */
    @Processor
    public List<CustomTargetingValue> getCustomTargetingValuesByStatement() throws CustomTargetingException {
        return connectionStrategy.getCustomTargetingService()
                .getCustomTargetingValuesByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrieve line items filtered by order IDs
     *
     * @param orderIds
     *            the order ids of the line items
     * @return List of line items
     * @throws GetLineItemsException
     *             Get Line Items Exception
     */
    @Processor
    public List<LineItem> getFilteredLineItemsByStatement(@Default("#[payload]") List<Long> orderIds) throws GetLineItemsException {
        return connectionStrategy.getLineItemService()
                .getFilteredLineItemsByStatement(connectionStrategy.getSession(), orderIds);
    }

    /**
     * Retrieve line items filtered by order IDs
     *
     * @param ids
     *            the line items ids
     * @return List of line items
     * @throws GetLineItemsException
     *             Get Line Items Exception
     */
    @Processor
    public List<LineItem> getLineItemsById(@Default("#[payload]") List<Long> ids) throws GetLineItemsException {
        return connectionStrategy.getLineItemService()
                .getLineItemsById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve custom fields by modified date
     * 
     * @return List of custom fields
     * @throws GetCustomFieldsException
     *             Get custom fields exception
     */
    @Processor
    public List<CustomField> getCustomFieldsByStatement() throws GetCustomFieldsException {
        return connectionStrategy.getCustomFieldService()
                .getCustomFieldsByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrive a list of CustomFields by ids
     * 
     * @param ids
     *            the ids of the custom fields
     * @return List of custom fields
     * @throws GetCustomFieldsException
     *             Get Custom Fields Exception
     */
    @Processor
    public List<CustomField> getCustomFieldsById(List<Long> ids) throws GetCustomFieldsException {
        return connectionStrategy.getCustomFieldService()
                .getCustomFieldsById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve custom field options
     * 
     * @param id
     *            the id of the custom field option
     * @return The custom field option
     * @throws GetCustomFieldsException
     *             Get custom fields exception
     */
    @Processor
    public CustomFieldOption getCustomFieldOption(Long id) throws GetCustomFieldsException {
        return connectionStrategy.getCustomFieldService()
                .getCustomFieldOption(connectionStrategy.getSession(), id);
    }

    /**
     * Retrieve custom fields by modified date
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of orders
     * @throws GetOrdersException
     *             Get orders exception
     */
    @Processor
    public List<Order> getOrdersByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetOrdersException {
        return connectionStrategy.getOrderService()
                .getOrdersByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Retrieve a list of orders by ids
     * 
     * @param ids
     *            the ids of the orders
     * @return a list of Orders
     * @throws GetOrdersException
     *             Get Orders Exception
     */
    @Processor
    public List<Order> getOrdersById(List<Long> ids) throws GetOrdersException {
        return connectionStrategy.getOrderService()
                .getOrdersById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve the Orders that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<Order> List of orders that match with the parameters
     * @throws GetOrdersException
     *             Get Orders Exception
     */
    @Processor
    public List<Order> getOrders(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit, @Default("0") Integer queryOffset)
            throws GetOrdersException {
        return connectionStrategy.getOrderService()
                .getOrders(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Create a list of Orders
     * 
     * @param orders
     *            The Orders for create
     * @return Array of Orders that has been created
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public Order[] createOrders(@Default("#[payload]") List<Order> orders) throws CreateFailedException {
        return connectionStrategy.getOrderService()
                .createOrders(connectionStrategy.getSession(), orders);
    }

    /**
     * Update a list of Orders
     * 
     * @param orders
     *            The Orders for update
     * @return Array of Orders that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Order[] updateOrders(@Default("#[payload]") List<Order> orders) throws UpdateFailedException {
        return connectionStrategy.getOrderService()
                .updateOrders(connectionStrategy.getSession(), orders);
    }

    /**
     * Perform the choosen action over the Orders that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param orderAction
     *            Action for perform over the Orders that match with the query string
     * @return Number of Orders updated
     * @throws PerformOrdersException
     *             Perform Orders Exception
     */
    @MetaDataScope(PerformOrdersCategory.class)
    @Processor
    public Integer performOrders(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String orderAction) throws PerformOrdersException {
        return connectionStrategy.getOrderService()
                .performOrders(connectionStrategy.getSession(), orderAction, queryString, queryParams);
    }

    /**
     * Retrieve the Proposals that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string *
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<Proposal> List of proposals that match with the parameters
     * @throws GetProposalsException
     *             Get Proposals Exception
     */
    @Processor
    public List<Proposal> getProposals(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit, @Default("0") Integer queryOffset)
            throws GetProposalsException {
        return connectionStrategy.getProposalService()
                .getProposals(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Create a list of Proposals
     * 
     * @param proposals
     *            The Proposals for create
     * @return Array of Proposals that has been created
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public Proposal[] createProposals(@Default("#[payload]") List<Proposal> proposals) throws CreateFailedException {
        return connectionStrategy.getProposalService()
                .createProposals(connectionStrategy.getSession(), proposals);
    }

    /**
     * Update a list of Proposals
     * 
     * @param proposals
     *            TheProposals for update
     * @return Array of Proposals that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Proposal[] updateProposals(@Default("#[payload]") List<Proposal> proposals) throws UpdateFailedException {
        return connectionStrategy.getProposalService()
                .updateProposals(connectionStrategy.getSession(), proposals);
    }

    /**
     * Perform the choosen action over the Proposals that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param proposalAction
     *            Action for perform over the Proposals that match with the query string
     * @return Number of Proposals updated
     * @throws PerformProposalsException
     *             Perform Proposals Exception
     */
    @MetaDataScope(PerformProposalsCategory.class)
    @Processor
    public Integer performProposals(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String proposalAction)
            throws PerformProposalsException {
        return connectionStrategy.getProposalService()
                .performProposals(connectionStrategy.getSession(), proposalAction, queryString, queryParams);
    }

    /**
     * Retrieve proposals by ids
     *
     * @param ids
     *            the ids of the proposals
     * @return a list of proposals
     * @throws GetProposalsException
     *             Get Proposals Exception
     */
    @Processor
    public List<Proposal> getProposalsById(List<Long> ids) throws GetProposalsException {
        return connectionStrategy.getProposalService()
                .getProposalsById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve the Proposal Line Items that match with the queryString
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param queryOrder
     *            the query order for the statement
     * @param queryLimit
     *            the query limit for the statement
     * @param queryOffset
     *            the query offset for the statement
     * @return List<ProposalLineItem> List of proposal line items that match with the parameters
     * @throws GetProposalLineItemsException
     *             Get Proposal Line Items Exception
     */
    @Processor
    public List<ProposalLineItem> getProposalLineItems(String queryString, Map<String, Object> queryParams, String queryOrder, @Default("500") Integer queryLimit,
            @Default("0") Integer queryOffset) throws GetProposalLineItemsException {
        return connectionStrategy.getProposalLineItemService()
                .getProposalLineItems(connectionStrategy.getSession(), queryString, queryParams, queryOrder, queryLimit, queryOffset);
    }

    /**
     * Create a list of Proposal Line Items
     * 
     * @param proposalLineItems
     *            The Proposal Line Items for create
     * @return Array of Proposal Line Items that has been created
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public ProposalLineItem[] createProposalLineItems(@Default("#[payload]") List<ProposalLineItem> proposalLineItems) throws CreateFailedException {
        return connectionStrategy.getProposalLineItemService()
                .createProposalLineItems(connectionStrategy.getSession(), proposalLineItems);
    }

    /**
     * Update a list of Proposal Line Items
     * 
     * @param proposalLineItems
     *            The Proposal Line Items for update
     * @return Array of Proposal Line Items that has been updated
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public ProposalLineItem[] updateProposalLineItems(@Default("#[payload]") List<ProposalLineItem> proposalLineItems) throws UpdateFailedException {
        return connectionStrategy.getProposalLineItemService()
                .updateProposalLineItems(connectionStrategy.getSession(), proposalLineItems);
    }

    /**
     * Perform the choosen action over the Proposal Line Items that match with the query string given by parameter
     * 
     * @param queryString
     *            the query string for the statement
     * @param queryParams
     *            a map with the query params included in the query string
     * @param proposalLineItemAction
     *            Action for perform over the Proposal Line Items that match with the query string
     * @return Number of Proposal Line Items updated
     * @throws PerformProposalLineItemsException
     *             Perform Proposal Line Items Exception
     */
    @MetaDataScope(PerformProposalLineItemsCategory.class)
    @Processor
    public Integer performProposalLineItems(String queryString, Map<String, Object> queryParams, @MetaDataKeyParam String proposalLineItemAction)
            throws PerformProposalLineItemsException {
        return connectionStrategy.getProposalLineItemService()
                .performProposalLineItems(connectionStrategy.getSession(), proposalLineItemAction, queryString, queryParams);
    }

    /**
     * Retrieve proposals by IDs
     *
     * @param ids
     *            the ids of the proposal line items
     * @return List of Proposal Line Items
     * @throws GetProposalLineItemsException
     *             Get proposal line items exception
     */
    @Processor
    public List<ProposalLineItem> getProposalLineItemsById(@Default("#[payload]") List<Long> ids) throws GetProposalLineItemsException {
        return connectionStrategy.getProposalLineItemService()
                .getProposalLineItemsById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve proposals by proposal IDs
     *
     * @param proposalIds
     *            the proposal ids for the line items
     * @return List of Proposal LineI tems
     * @throws GetProposalLineItemsException
     *             Get proposal line items exception
     */
    @Processor
    public List<ProposalLineItem> getProposalLineItemsByStatementFilter(@Default("#[payload]") List<Long> proposalIds) throws GetProposalLineItemsException {
        return connectionStrategy.getProposalLineItemService()
                .getProposalLineItemsByProposalId(connectionStrategy.getSession(), proposalIds);
    }

    /**
     * Retrieve contacts
     * 
     * @return List of Contacts
     * @throws GetAllContactsException
     *             Get all contacts exception
     */
    @Processor
    public List<Contact> getContactsByStatement() throws GetAllContactsException {
        return connectionStrategy.getContactService()
                .getContactsByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrieve a contact by a given ID
     * 
     * @return The searched contact
     * @param contactId
     *            the id of the contact
     * @throws GetContactByIdException
     *             Get Contact By Id Exception
     */
    @Processor
    public Contact getContactById(Long contactId) throws GetContactByIdException {
        return connectionStrategy.getContactService()
                .getContactById(connectionStrategy.getSession(), contactId);
    }

    /**
     * Retrieve a contact by a given name
     * 
     * @return The searched contact
     * @param contactName
     *            the name of the contact
     * @throws GetContactByNameException
     *             Get Contact By Name Exception
     */
    @Processor
    public Contact getContactByName(String contactName) throws GetContactByNameException {
        return connectionStrategy.getContactService()
                .getContactByName(connectionStrategy.getSession(), contactName);
    }

    /**
     * Create a contact
     * 
     * @param contact
     *            The Contact to create
     * @return The created contact
     * @throws CreateFailedException
     *             Create Failed Exception
     */
    @Processor
    public Contact createContact(@Default("#[payload]") Contact contact) throws CreateFailedException {
        return connectionStrategy.getContactService()
                .createContact(connectionStrategy.getSession(), contact);
    }

    /**
     * Update a contact
     * 
     * @param contact
     *            The Contact to update
     * @return The updated contact
     * @throws UpdateFailedException
     *             Update Failed Exception
     */
    @Processor
    public Contact updateContact(@Default("#[payload]") Contact contact) throws UpdateFailedException {
        return connectionStrategy.getContactService()
                .updateContact(connectionStrategy.getSession(), contact);
    }

    /**
     * Retrieve users
     * 
     * @return List of Users
     * @throws GetUsersException
     *             Get users exception
     */
    @Processor
    public List<User> getUsersByStatement() throws GetUsersException {
        return connectionStrategy.getUserService()
                .getUsersByStatement(connectionStrategy.getSession());
    }

    /**
     * Retrieve a List of Users by ids
     * 
     * @param ids
     *            the ids of the users
     * @return List of users
     * @throws GetUsersException
     *             Get Users Exception
     */
    @Processor
    public List<User> getUsersById(List<Long> ids) throws GetUsersException {
        return connectionStrategy.getUserService()
                .getUsersById(connectionStrategy.getSession(), ids);
    }

    /**
     * Retrieve modified rate cards
     * 
     * @return List of RateCard
     * @throws GetRateCardsException
     *             Get Rate Cards Exception
     */

    /**
     * Retrieve modified rate cards
     * 
     * @param lastModifiedDate
     *            last modified date
     * @param snapshotDateTime
     *            snapshot date
     * @return List of modified rate cards
     * @throws GetRateCardsException
     *             Get Rate Cards Exception
     */
    @Processor
    public List<RateCard> getRateCardsByLastModifiedDate(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetRateCardsException {
        return connectionStrategy.getRateCardService()
                .getRateCardsByLastModifiedDate(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
    }

    /**
     * Retrieve retraction reasons
     * 
     * @return List of Retraction Reasons
     * @throws ReportDownloadException
     *             Report Download Exception
     * @throws IllegalAccessException
     *             Illegal Access Exception
     * @throws RemoteException
     *             Remote Exception
     * @throws ApiException
     *             Api Exception
     */
    @Processor
    public List<String[]> getProposalRetractionReasonPql() throws ApiException, RemoteException, IllegalAccessException, ReportDownloadException {
        return connectionStrategy.getPqlService()
                .getProposalRetractionReasonPql(connectionStrategy.getSession());
    }

    /**
     * Retrieve all line items
     * 
     * 
     * @return List of an Array of Strings
     * @throws ReportDownloadException
     *             Report Download Exception
     * @throws ApiException
     *             Api Exception
     * @throws RemoteException
     *             Remote Exception
     * @throws IllegalAccessException
     *             Illegal Access Exception
     */
    @Processor
    public List<String[]> getAllLineItemsPql() throws ReportDownloadException, ApiException, RemoteException, IllegalAccessException {
        return connectionStrategy.getPqlService()
                .getAllLineItemsPql(connectionStrategy.getSession());
    }

    /**
     * @return connection strategy
     */
    public GoogleDfpConnectionStrategy getConnectionStrategy() {
        return connectionStrategy;
    }

    /**
     * @param connectionStrategy
     *            Google DFP connection strategy
     */
    public void setConnectionStrategy(GoogleDfpConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

}