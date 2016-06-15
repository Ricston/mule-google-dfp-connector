/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.annotations.param.Default;
import org.mule.modules.google.dfp.exceptions.AudienceSegmentException;
import org.mule.modules.google.dfp.exceptions.CreateFailedException;
import org.mule.modules.google.dfp.exceptions.CreateReportException;
import org.mule.modules.google.dfp.exceptions.CustomTargetingException;
import org.mule.modules.google.dfp.exceptions.GetAdvertiserByNameException;
import org.mule.modules.google.dfp.exceptions.GetAgencyByNameException;
import org.mule.modules.google.dfp.exceptions.GetAllCompaniesException;
import org.mule.modules.google.dfp.exceptions.GetAllContactsException;
import org.mule.modules.google.dfp.exceptions.GetCompanyByIdException;
import org.mule.modules.google.dfp.exceptions.GetContactByIdException;
import org.mule.modules.google.dfp.exceptions.GetContactByNameException;
import org.mule.modules.google.dfp.exceptions.GetCustomFieldsException;
import org.mule.modules.google.dfp.exceptions.GetLineItemsException;
import org.mule.modules.google.dfp.exceptions.GetOrdersException;
import org.mule.modules.google.dfp.exceptions.GetProductTemplatesException;
import org.mule.modules.google.dfp.exceptions.GetProductsByStatementException;
import org.mule.modules.google.dfp.exceptions.GetProposalLineItemsException;
import org.mule.modules.google.dfp.exceptions.GetProposalsException;
import org.mule.modules.google.dfp.exceptions.GetRateCardsException;
import org.mule.modules.google.dfp.exceptions.GetUsersException;
import org.mule.modules.google.dfp.exceptions.ReconciliationReportException;
import org.mule.modules.google.dfp.exceptions.ReconciliationReportRowException;
import org.mule.modules.google.dfp.exceptions.ReportDownloadException;
import org.mule.modules.google.dfp.exceptions.TooManyAdvertisersFoundException;
import org.mule.modules.google.dfp.exceptions.TooManyAgenciesFoundException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.modules.google.dfp.reconciliationreport.ReconciliationQueryParams;
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
import com.google.api.ads.dfp.axis.v201605.ReconciliationReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRow;
import com.google.api.ads.dfp.axis.v201605.ReportJob;
import com.google.api.ads.dfp.axis.v201605.User;

/**
 * Google DFP Connector
 * 
 * @author Ricston, Ltd.
 */
@MetaDataScope(DimensionCategory.class)
@RequiresEnterpriseLicense
@Connector(name = "google-dfp", schemaVersion = "1.0", friendlyName = "GoogleDfp", minMuleVersion = "3.5")
public class GoogleDfpConnector {

    @Config
    GoogleDfpConnectionStrategy connectionStrategy;

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @param ids
     *            the proposal line ids
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createContractedReport(Date startDate, Date endDate, List<Long> ids) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createContractedProposalLineItemsReport(connectionStrategy.getSession(), startDate, endDate, ids);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @param ids
     *            the proposal line ids
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createContractedReportWithAdUnits(Date startDate, Date endDate, List<Long> ids) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createContractedProposalLineItemsReportWithAdUnits(connectionStrategy.getSession(), startDate, endDate, ids);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createAudienceReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createAudienceReport(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createTargetingReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createTargetingReport(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createTotalContractedImpressionsReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .totalContractedImpressions(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createTotalDeliveredImpressionsReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .totalDeliveredImpressions(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createReachReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createReachReport(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createIfReportIsReady(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .checkIfReportIsReady(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createReachLifetimeReport() throws CreateReportException {
        return connectionStrategy.getReportService()
                .createReachLifetimeReport(connectionStrategy.getSession());
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createAllActiveLineItemsReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createAllActiveLineItemsReport(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @param ids
     *            the line items ids
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createActualsReports(Date startDate, Date endDate, List<Long> ids) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createActualsReport(connectionStrategy.getSession(), startDate, endDate, ids);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @param ids
     *            the line items ids
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob createActualsReportsWithoutAds(Date startDate, Date endDate, List<Long> ids) throws CreateReportException {
        return connectionStrategy.getReportService()
                .createActualsReportWithoutAds(connectionStrategy.getSession(), startDate, endDate, ids);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob activeLineItemsReport(Date startDate, Date endDate) throws CreateReportException {
        return connectionStrategy.getReportService()
                .activeLineItemsReport(connectionStrategy.getSession(), startDate, endDate);
    }

    /**
     * Creates a report given a date range
     * 
     * @param startDate
     *            the start of the report date
     * @param endDate
     *            the end of the report date
     * @param lineItems
     *            the line items
     * @return The report job
     * @throws CreateReportException
     *             Create Report Exception
     * 
     */
    @Processor
    public ReportJob ageAndGenderReport(Date startDate, Date endDate, List<Integer> lineItems) throws CreateReportException {
        return connectionStrategy.getReportService()
                .ageAndGenderReport(connectionStrategy.getSession(), startDate, endDate, lineItems);
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
     * Retrieve the reconciliation report IDs given the start date
     * 
     * @param startDate
     *            Start date used for searching reconciliation reports
     * @return List of reconciliation report
     * @throws ReconciliationReportException
     *             Reconciliation Report By ID Exception
     */
    @Processor
    public List<ReconciliationReport> getReconciliationReportIdsByStartDate(@Default("#[payload]") Date startDate) throws ReconciliationReportException {
        try {
            String dateFormat = "%04d-%02d-%02d";
            String stringDate = String.format(dateFormat, startDate.getYear(), startDate.getMonth(), startDate.getDay());
            return connectionStrategy.getReconciliationReportService()
                    .getReconciliationReportByStartDate(connectionStrategy.getSession(), stringDate);
        } catch (Exception e) {
            throw new ReconciliationReportException(e);
        }
    }

    /**
     * Retrieve Reconciliation report rows given the reconciliation report ID, order ID and line item ID
     * 
     * @param queryParams
     *            query parameters to get reconciliation report row
     * @return List of reconcialtion report rows
     * @throws ReconciliationReportRowException
     *             Reconciliation Report Row Exception
     */
    @Processor
    public List<ReconciliationReportRow> getReconciliationReportRows(@Default("#[payload]") ReconciliationQueryParams queryParams) throws ReconciliationReportRowException {
        try {
            return connectionStrategy.getReconciliationReportRowService()
                    .getReconciliationReportRows(connectionStrategy.getSession(), queryParams);
        } catch (Exception e) {
            throw new ReconciliationReportRowException(e);
        }
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
     * Retrieve a list of products by id
     * 
     * @param ids
     *            the ids of the products
     * @return List of products
     * @throws GetProductsByStatementException
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
     * Retrieve proposals by modified date
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of Proposals
     * @throws GetProposalsException
     *             Get proposals exception
     */
    @Processor
    public List<Proposal> getProposalsByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetProposalsException {
        return connectionStrategy.getProposalService()
                .getProposalsByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
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
     * Retrieve proposals by modified date
     * 
     * @param lastModifiedDate
     *            the last modified date of the companies
     * @param snapshotDateTime
     *            the snapshot date of the companies
     * @return List of Proposal Line Items
     * @throws GetProposalLineItemsException
     *             Get proposal line items exception
     */
    @Processor
    public List<ProposalLineItem> getProposalLineItemsByStatement(DateTime lastModifiedDate, DateTime snapshotDateTime) throws GetProposalLineItemsException {
        return connectionStrategy.getProposalLineItemService()
                .getProposalLineItemsByStatement(connectionStrategy.getSession(), lastModifiedDate, snapshotDateTime);
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
    public Contact createContact(Contact contact) throws CreateFailedException {
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
    public Contact updateContact(Contact contact) throws UpdateFailedException {
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