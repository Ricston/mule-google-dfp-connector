/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.ads.dfp.axis.utils.v201605.DateTimes;
import com.google.api.ads.dfp.axis.v201605.AdUnitTargeting;
import com.google.api.ads.dfp.axis.v201605.Company;
import com.google.api.ads.dfp.axis.v201605.CompanyType;
import com.google.api.ads.dfp.axis.v201605.CostType;
import com.google.api.ads.dfp.axis.v201605.CreativePlaceholder;
import com.google.api.ads.dfp.axis.v201605.CreativeSizeType;
import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Goal;
import com.google.api.ads.dfp.axis.v201605.GoalType;
import com.google.api.ads.dfp.axis.v201605.InventoryTargeting;
import com.google.api.ads.dfp.axis.v201605.LineItem;
import com.google.api.ads.dfp.axis.v201605.LineItemType;
import com.google.api.ads.dfp.axis.v201605.Money;
import com.google.api.ads.dfp.axis.v201605.Order;
import com.google.api.ads.dfp.axis.v201605.Proposal;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;
import com.google.api.ads.dfp.axis.v201605.Size;
import com.google.api.ads.dfp.axis.v201605.Targeting;
import com.google.api.ads.dfp.axis.v201605.UnitType;

public class TestDataBuilder {

    public static Date getCreateContractedReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateContractedReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static DateTime getGetAllCompaniesLastModifiedDate() {
        return DateTimes.toDateTime("2015-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetAllCompaniesSnapshotDateTime() {
        return DateTimes.toDateTime("2017-01-31T00:00:00", "Europe/Madrid");
    }

    public static Long getGetCompanyByIdCorrectId() {
        return 59518088L;
    }

    public static Long getGetCompanyByIdIncorrectId() {
        return 22L;
    }

    public static Company getCreateCompanyNewCompany() {
        Company newCompany = new Company();
        newCompany.setName("TestCompany");
        newCompany.setAddress("Mosta Malta");
        newCompany.setEmail("dfp@ricston.com");
        newCompany.setType(CompanyType.AGENCY);

        return newCompany;
    }

    public static Date getCreateAudienceReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateAudienceReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static Date getCreateTargetingReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateTargetingReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static Date getCreateTotalContractedImpressionsReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateTotalContractedImpressionsReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static Date getCreateReachReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateReachReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static Date getDownloadReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getDownloadReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static DateTime GetOrdersByStatementLastModifiedDate() {
        return DateTimes.toDateTime("2015-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime GetOrdersByStatementSnapshotDateTime() {
        return DateTimes.toDateTime("2017-01-31T00:00:00", "Europe/Madrid");
    }

    public static List<Long> getGetProductsByIdCorrectIds() {
        List<Long> correctIds = new ArrayList<Long>();
        // XXX Products can not be created without a premium account of DFP, so we can not retrieve them
        return correctIds;
    }

    public static List<Long> getGetProductsByIdWrongIds() {
        List<Long> wrongIds = new ArrayList<Long>();
        wrongIds.add(5000L);
        return wrongIds;
    }

    public static DateTime getGetProductsByStatementLastModifiedDate() {
        return DateTimes.toDateTime("2015-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetProductsByStatementSnapshotDateTime() {
        return DateTimes.toDateTime("2017-01-31T00:00:00", "Europe/Madrid");
    }

    public static List<Long> getGetLineItemsByIdCorrectIds() {
        List<Long> correctIds = new ArrayList<Long>();
        correctIds.add(113219888L);
        return correctIds;
    }

    public static List<Long> getGetLineItemsByIdWrongIds() {
        List<Long> wrongIds = new ArrayList<Long>();
        wrongIds.add(5000L);
        return wrongIds;
    }

    public static DateTime getGetLineItemsByStatementLastModifiedDate() {
        return DateTimes.toDateTime("2015-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetLineItemsByStatementSnapshotDateTime() {
        return DateTimes.toDateTime("2099-01-31T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetLineItemsByStatementLastModifiedDateNoResults() {
        return DateTimes.toDateTime("2099-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetLineItemsByStatementSnapshotDateTimeNoResults() {
        return DateTimes.toDateTime("2099-01-01T00:00:00", "Europe/Madrid");
    }

    public static List<Long> getGetFilteredLineItemsByStatementCorrectIds() {
        List<Long> correctIds = new ArrayList<Long>();
        correctIds.add(312984608L);
        return correctIds;
    }

    public static List<Long> getGetFilteredLineItemsByStatementWrongIds() {
        List<Long> wrongIds = new ArrayList<Long>();
        wrongIds.add(5000L);
        return wrongIds;
    }

    public static DateTime getGetRateCardsByStatementLastModifiedDate() {
        return DateTimes.toDateTime("2005-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetRateCardsByStatementSnapshotDateTime() {
        return DateTimes.toDateTime("2099-01-31T00:00:00", "Europe/Madrid");
    }

    public static List<Long> getGetCompaniesByIdCorrectIds() {
        List<Long> correctIds = new ArrayList<Long>();
        correctIds.add(54797168L);
        correctIds.add(55993208L);
        correctIds.add(59518208L);
        return correctIds;
    }

    public static List<Long> getGetCompaniesByIdWrongIds() {
        List<Long> wrongIds = new ArrayList<Long>();
        wrongIds.add(5000L);
        return wrongIds;
    }

    public static Long getGetContactByIdCorrectId() {
        return 1061648L;
    }

    public static Long getGetContactByIdWrongId() {
        return 22L;
    }

    public static Map<String, Object> getGetCompanies() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("type", CompanyType.ADVERTISER.toString());
        queryParams.put("id", 59503568);
        return queryParams;
    }

    public static Map<String, Object> getGetLineItems() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(113219888, 142097408));
        return queryParams;
    }

    public static Map<String, Object> getGetLineItemsNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetOrders() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(312984608, 424294568));
        return queryParams;
    }

    public static Map<String, Object> getGetOrdersNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetProducts() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetProductsNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetProposalLineItemsNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetProposalLineItems() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetProposals() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetProposalsNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetReconciliationLineItemReports() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetReconciliationOrderReports() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetReconciliationOrderReportsNotFound() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", Arrays.asList(12));
        return queryParams;
    }

    public static Map<String, Object> getGetReconciliationReportRows() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static Map<String, Object> getGetReconciliationReports() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        return queryParams;
    }

    public static LineItem getCreateLineItemsNewLineItem() {
        LineItem newLineItem = new LineItem();
        newLineItem.setName("TestLineItem");
        newLineItem.setOrderId(312984608L);
        Money money = new Money();
        money.setCurrencyCode("EUR");
        money.setMicroAmount(1000000L);
        newLineItem.setCostPerUnit(money);
        Goal goal = new Goal(GoalType.UNKNOWN, UnitType.IMPRESSIONS, 100L);
        newLineItem.setPrimaryGoal(goal);
        newLineItem.setStartDateTime(DateTimes.toDateTime("2017-01-01T00:00:00", "Europe/Madrid"));
        newLineItem.setEndDateTime(DateTimes.toDateTime("2017-01-31T00:00:00", "Europe/Madrid"));
        newLineItem.setLineItemType(LineItemType.NETWORK);
        Targeting target = new Targeting();
        InventoryTargeting invTargeting = new InventoryTargeting();
        invTargeting.setExcludedAdUnits(new AdUnitTargeting[] { new AdUnitTargeting("424854728", true)
        });
        invTargeting.setTargetedAdUnits(new AdUnitTargeting[] { new AdUnitTargeting("531235328", true)
        });
        invTargeting.setTargetedPlacementIds(new long[] {});
        target.setInventoryTargeting(invTargeting);
        newLineItem.setTargeting(target);
        newLineItem.setCostType(CostType.CPC);
        CreativePlaceholder creative = new CreativePlaceholder();
        Size size = new Size();
        size.setHeight(50);
        size.setWidth(360);
        size.setIsAspectRatio(false);
        creative.setSize(size);
        creative.setCreativeSizeType(CreativeSizeType.ASPECT_RATIO);
        newLineItem.setCreativePlaceholders(new CreativePlaceholder[] { creative
        });
        return newLineItem;
    }

    public static Order getCreateOrdersNewOrder() {
        Order newOrder = new Order();
        newOrder.setName("TestOrder");
        newOrder.setTraffickerId(136103528L);
        newOrder.setAdvertiserId(59503208L);
        return newOrder;
    }

    public static ProposalLineItem getCreateProposalLineItemsNewProposalLineItem() {
        ProposalLineItem newProposalLineItem = new ProposalLineItem();
        newProposalLineItem.setName("TestProposalLineItem");
        return newProposalLineItem;
    }

    public static Proposal getCreateProposalsNewProposal() {
        Proposal newProposal = new Proposal();
        newProposal.setName("TestProposal");
        newProposal.setProbabilityOfClose(100L);
        return newProposal;
    }

}
