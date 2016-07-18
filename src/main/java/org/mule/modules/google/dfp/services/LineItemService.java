/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mule.modules.google.dfp.exceptions.CreateFailedException;
import org.mule.modules.google.dfp.exceptions.GetLineItemsException;
import org.mule.modules.google.dfp.exceptions.PerformLineItemsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.LineItem;
import com.google.api.ads.dfp.axis.v201605.LineItemAction;
import com.google.api.ads.dfp.axis.v201605.LineItemPage;
import com.google.api.ads.dfp.axis.v201605.LineItemServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class LineItemService {

    private static final Logger logger = Logger
            .getLogger(LineItemService.class);

    protected LineItemServiceInterface createLineItemService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the LineItem Service.
        LineItemServiceInterface lineItemService = dfpServices.get(session,
                LineItemServiceInterface.class);

        return lineItemService;
    }

    public List<LineItem> getLineItems(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder, Integer queryLimit, Integer queryOffset)
            throws GetLineItemsException {
        try {
            LineItemServiceInterface lineItemService = createLineItemService(session);

            // Create a statement to get line items by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<LineItem> lineItemsFound = new ArrayList<LineItem>();

            do {
                // Get Line Items by statement.
                LineItemPage page = lineItemService
                        .getLineItemsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    lineItemsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Line Items found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Line Items found:" + totalResultSetSize);
            return lineItemsFound;
        } catch (ApiException e) {
            throw new GetLineItemsException(e);
        } catch (RemoteException e) {
            throw new GetLineItemsException(e);
        } catch (Exception e) {
            throw new GetLineItemsException(e);
        }
    }

    public Integer performLineItems(DfpSession session, String lineItemActionString, String queryString, Map<String, Object> queryParams)
            throws PerformLineItemsException {
        try {
            LineItemServiceInterface lineItemService = createLineItemService(session);

            // Create a statement to get line items by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of line items performed
            Integer lineItemsPerformed = 0;

            String trimmedString = lineItemActionString.replace(" ", "")
                    .trim();
            LineItemAction lineItemAction = (LineItemAction) Class.forName("com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform LineItemAction over Line Items by statement.
            UpdateResult updateResult = lineItemService
                    .performLineItemAction(lineItemAction, statementBuilder.toStatement());

            if (updateResult != null) {
                lineItemsPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Line Items found with given parameters");
            }

            logger.info("Number of Line Items performed:" + lineItemsPerformed);
            return lineItemsPerformed;
        } catch (ApiException e) {
            throw new PerformLineItemsException(e);
        } catch (RemoteException e) {
            throw new PerformLineItemsException(e);
        } catch (Exception e) {
            throw new PerformLineItemsException(e);
        }
    }

    public LineItem[] createLineItems(DfpSession session, List<LineItem> lineItemsToCreate)
            throws CreateFailedException {

        try {
            // Get the LineItemService.
            LineItemServiceInterface lineItemService = createLineItemService(session);

            LineItem[] lineItemsArray = new LineItem[lineItemsToCreate.size()];
            lineItemsArray = lineItemsToCreate.toArray(lineItemsArray);

            LineItem[] lineItems = lineItemService.createLineItems(lineItemsArray);

            for (LineItem item : lineItems) {
                logger.info(String
                        .format("Line Item with ID \"%d\", name \"%s\", and order id \"%d\" was created.\"%n\"",
                                item.getId(), item.getName(), item.getOrderId()));
            }

            return lineItems;

        } catch (ApiException e) {
            throw new CreateFailedException(e);
        } catch (RemoteException e) {
            throw new CreateFailedException(e);
        } catch (Exception e) {
            throw new CreateFailedException(e);
        }
    }

    public LineItem[] updateLineItems(DfpSession session, List<LineItem> lineItemsToUpdate)
            throws UpdateFailedException {

        try {
            // Get the LineItemService.
            LineItemServiceInterface lineItemService = createLineItemService(session);

            LineItem[] lineItemsArray = new LineItem[lineItemsToUpdate.size()];
            lineItemsArray = lineItemsToUpdate.toArray(lineItemsArray);

            // Update the Line Items on the server.
            LineItem[] lineItems = lineItemService.updateLineItems(lineItemsArray);

            for (LineItem item : lineItems) {
                logger.info(String
                        .format("Line Item with ID \"%d\", name \"%s\", and order id \"%d\" was updated.\"%n\"",
                                item.getId(), item.getName(), item.getOrderId()));
            }

            return lineItems;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public List<LineItem> getLineItemsByStatement(DfpSession session,
            DateTime lastModifiedDateTime, DateTime snapshotDateTime)
            throws GetLineItemsException {
        try {

            LineItemServiceInterface lineItemService = createLineItemService(session);

            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
                    .orderBy("id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                    .withBindVariableValue("lastModifiedDateTime",
                            lastModifiedDateTime)
                    .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            int totalResultSetSize = 0;

            List<LineItem> results = new ArrayList<LineItem>();

            LineItemPage initialPage = lineItemService
                    .getLineItemsByStatement(statementBuilder.toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            do {
                LineItemPage page = lineItemService
                        .getLineItemsByStatement(statementBuilder.toStatement());
                logger.debug("Current Offset is" + statementBuilder.getOffset());

                if (page.getResults() != null) {
                    logger.debug("Result Set Size:" + totalResultSetSize);
                    for (LineItem lineItem : page.getResults()) {
                        results.add(lineItem);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);

                logger.debug("Offset increased to: "
                        + statementBuilder.getOffset());
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize
                    + '\n' + "Number of results retrieved: "
                    + results.size());

            return results;

        } catch (RemoteException e) {
            throw new GetLineItemsException(e);
        }

    }

    public List<LineItem> getFilteredLineItemsByStatement(DfpSession session,
            List<Long> orderIds) throws GetLineItemsException {
        try {

            LineItemServiceInterface lineItemService = createLineItemService(session);

            List<List<Long>> orderIdsBatches = Lists.partition(orderIds, 400);
            List<LineItem> results = new ArrayList<LineItem>();

            for (List<Long> currentBatch : orderIdsBatches) {

                String whereClauseFilter = Joiner.on(", ")
                        .join(currentBatch);
                String whereQueryStatement = "orderId IN (" + whereClauseFilter
                        + ")";

                StatementBuilder statementBuilder = new StatementBuilder()
                        .where(whereQueryStatement)
                        .limit(
                                StatementBuilder.SUGGESTED_PAGE_LIMIT);

                // Default for total result set size.
                int totalResultSetSize = 0;
                logger.info("Getting the filtered line items");

                LineItemPage initialPage = lineItemService
                        .getLineItemsByStatement(statementBuilder.toStatement());
                totalResultSetSize = initialPage.getTotalResultSetSize();

                do {
                    // Get line items by statement.
                    LineItemPage page = lineItemService
                            .getLineItemsByStatement(statementBuilder
                                    .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (LineItem lineItem : page.getResults()) {
                            results.add(lineItem);
                        }
                    }

                    statementBuilder
                            .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
                } while (statementBuilder.getOffset() < totalResultSetSize);

                logger.info("Number of results found: " + totalResultSetSize
                        + '\n' + "Number of results retrieved: "
                        + results.size());
            }
            return results;

        } catch (RemoteException e) {
            throw new GetLineItemsException(e);
        }

    }

    public List<LineItem> getLineItemsById(DfpSession session, List<Long> ids)
            throws GetLineItemsException {
        try {

            LineItemServiceInterface lineItemService = createLineItemService(session);

            List<List<Long>> idsBatches = Lists.partition(ids, 400);
            List<LineItem> results = new ArrayList<LineItem>();

            for (List<Long> currentBatch : idsBatches) {

                String whereClauseFilter = Joiner.on(", ")
                        .join(currentBatch);
                String whereQueryStatement = "id IN (" + whereClauseFilter
                        + ")";

                StatementBuilder statementBuilder = new StatementBuilder()
                        .where(whereQueryStatement)
                        .limit(
                                StatementBuilder.SUGGESTED_PAGE_LIMIT);

                // Default for total result set size.
                int totalResultSetSize = 0;
                logger.info("Getting the filtered line items by ID.");

                LineItemPage initialPage = lineItemService
                        .getLineItemsByStatement(statementBuilder.toStatement());
                totalResultSetSize = initialPage.getTotalResultSetSize();

                do {
                    // Get line items by statement.
                    LineItemPage page = lineItemService
                            .getLineItemsByStatement(statementBuilder
                                    .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (LineItem lineItem : page.getResults()) {
                            results.add(lineItem);
                        }
                    }

                    statementBuilder
                            .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
                } while (statementBuilder.getOffset() < totalResultSetSize);

                logger.info("Number of results found: " + totalResultSetSize
                        + '\n' + "Number of results retrieved: "
                        + results.size());
            }
            return results;

        } catch (RemoteException e) {
            throw new GetLineItemsException(e);
        }

    }

}
