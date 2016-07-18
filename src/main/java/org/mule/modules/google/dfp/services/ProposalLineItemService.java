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
import org.mule.modules.google.dfp.exceptions.GetProposalLineItemsException;
import org.mule.modules.google.dfp.exceptions.PerformProposalLineItemsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItemAction;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItemPage;
import com.google.api.ads.dfp.axis.v201605.ProposalLineItemServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class ProposalLineItemService {

    private static final Logger logger = Logger
            .getLogger(ProposalLineItemService.class);

    protected ProposalLineItemServiceInterface createProposalLineItemService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the ProposalLineItems service.
        ProposalLineItemServiceInterface proposalLineItemService = dfpServices
                .get(session, ProposalLineItemServiceInterface.class);

        return proposalLineItemService;
    }

    public List<ProposalLineItem> getProposalLineItems(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder, Integer queryLimit,
            Integer queryOffset)
            throws GetProposalLineItemsException {
        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            // Create a statement to get proposal line items by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<ProposalLineItem> proposalLineItemsFound = new ArrayList<ProposalLineItem>();

            do {
                // Get Proposal Line Items by statement.
                ProposalLineItemPage page = proposalLineItemService
                        .getProposalLineItemsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    proposalLineItemsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Proposal Line Items found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Proposal Line Items found:" + totalResultSetSize);
            return proposalLineItemsFound;
        } catch (ApiException e) {
            throw new GetProposalLineItemsException(e);
        } catch (RemoteException e) {
            throw new GetProposalLineItemsException(e);
        } catch (Exception e) {
            throw new GetProposalLineItemsException(e);
        }
    }

    public ProposalLineItem[] createProposalLineItems(DfpSession session, List<ProposalLineItem> proposalLineItemsToCreate)
            throws CreateFailedException {

        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            ProposalLineItem[] proposalLineItemsArray = new ProposalLineItem[proposalLineItemsToCreate.size()];
            proposalLineItemsArray = proposalLineItemsToCreate.toArray(proposalLineItemsArray);

            ProposalLineItem[] proposalLineItems = proposalLineItemService
                    .createProposalLineItems(proposalLineItemsArray);

            for (ProposalLineItem item : proposalLineItems) {
                logger.info(String
                        .format("Proposal Line Item with ID \"%d\" and name \"%s\" was created.\"%n\"",
                                item.getId(), item.getName()));
            }

            return proposalLineItems;

        } catch (ApiException e) {
            throw new CreateFailedException(e);
        } catch (RemoteException e) {
            throw new CreateFailedException(e);
        } catch (Exception e) {
            throw new CreateFailedException(e);
        }
    }

    public ProposalLineItem[] updateProposalLineItems(DfpSession session, List<ProposalLineItem> proposalLineItemsToUpdate)
            throws UpdateFailedException {

        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            ProposalLineItem[] proposalLineItemsArray = new ProposalLineItem[proposalLineItemsToUpdate.size()];
            proposalLineItemsArray = proposalLineItemsToUpdate.toArray(proposalLineItemsArray);

            ProposalLineItem[] proposalLineItems = proposalLineItemService
                    .updateProposalLineItems(proposalLineItemsArray);

            for (ProposalLineItem item : proposalLineItems) {
                logger.info(String
                        .format("Proposal Line Item with ID \"%d\" and name \"%s\" was updated.\"%n\"",
                                item.getId(), item.getName()));
            }

            return proposalLineItems;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public Integer performProposalLineItems(DfpSession session, String proposalLineItemActionString, String queryString, Map<String, Object> queryParams)
            throws PerformProposalLineItemsException {
        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            // Create a statement to get proposal line items by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of proposal line items performed
            Integer proposalLineItemsPerformed = 0;

            String trimmedString = proposalLineItemActionString.replace(" ", "")
                    .trim();
            ProposalLineItemAction proposalLineItemAction = (ProposalLineItemAction) Class.forName("com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform ProposalLineItemAction over Proposal Line Items by statement.
            UpdateResult updateResult = proposalLineItemService
                    .performProposalLineItemAction(proposalLineItemAction, statementBuilder.toStatement());

            if (updateResult != null) {
                proposalLineItemsPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Proposal Line Items found with given parameters");
            }

            logger.info("Number of Proposal Line Items performed:" + proposalLineItemsPerformed);
            return proposalLineItemsPerformed;
        } catch (ApiException e) {
            throw new PerformProposalLineItemsException(e);
        } catch (RemoteException e) {
            throw new PerformProposalLineItemsException(e);
        } catch (Exception e) {
            throw new PerformProposalLineItemsException(e);
        }
    }

    public List<ProposalLineItem> getProposalLineItemsByStatement(
            DfpSession session, DateTime lastModifiedDateTime,
            DateTime snapshotDateTime) throws GetProposalLineItemsException {
        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            // Create a statement to only select proposals that were modified
            // recently.
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
                    .orderBy("lastModifiedDateTime ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                    .withBindVariableValue("lastModifiedDateTime",
                            lastModifiedDateTime)
                    .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            // Default for total result set size.
            int totalResultSetSize = 0;

            List<ProposalLineItem> results = new ArrayList<ProposalLineItem>();

            logger.info("Getting all modified proposal line items.");

            ProposalLineItemPage initialPage = proposalLineItemService
                    .getProposalLineItemsByStatement(statementBuilder
                            .toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();
            do {
                // Get proposal line items by statement.
                ProposalLineItemPage page = proposalLineItemService
                        .getProposalLineItemsByStatement(statementBuilder
                                .toStatement());

                if (page.getResults() != null) {
                    for (ProposalLineItem proposalLineItem : page.getResults()) {
                        results.add(proposalLineItem);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize);
            logger.info("Number of results retrieved: " + results.size());

            return results;
        } catch (ApiException e) {
            throw new GetProposalLineItemsException(e);
        } catch (RemoteException e) {
            throw new GetProposalLineItemsException(e);
        }
    }

    public List<ProposalLineItem> getProposalLineItemsByProposalId(
            DfpSession session, List<Long> proposalIds)
            throws GetProposalLineItemsException {
        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            List<List<Long>> proposalIdsBatches = Lists.partition(proposalIds,
                    400);
            List<ProposalLineItem> results = new ArrayList<ProposalLineItem>();

            for (List<Long> currentBatch : proposalIdsBatches) {

                String whereClauseFilter = Joiner.on(", ")
                        .join(currentBatch);
                String whereQueryStatement = "proposalId IN ("
                        + whereClauseFilter + ")";

                // Create a statement to only select proposal lines filtered by
                // proposalID
                StatementBuilder statementBuilder = new StatementBuilder()
                        .where(whereQueryStatement)
                        .orderBy("id ASC")
                        .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

                int totalResultSetSize = 0;

                logger.info("Getting the filtered proposal line items.");

                do {
                    // Get proposal line items by statement.
                    ProposalLineItemPage page = proposalLineItemService
                            .getProposalLineItemsByStatement(statementBuilder
                                    .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (ProposalLineItem proposalLineItem : page
                                .getResults()) {
                            results.add(proposalLineItem);
                        }
                    }

                    statementBuilder
                            .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
                } while (statementBuilder.getOffset() < totalResultSetSize);

                logger.info("Retrieved " + totalResultSetSize
                        + " proposal line items.");

            }

            return results;
        } catch (ApiException e) {
            throw new GetProposalLineItemsException(e);
        } catch (RemoteException e) {
            throw new GetProposalLineItemsException(e);
        }
    }

    public List<ProposalLineItem> getProposalLineItemsById(
            DfpSession session, List<Long> ids)
            throws GetProposalLineItemsException {
        try {
            ProposalLineItemServiceInterface proposalLineItemService = createProposalLineItemService(session);

            List<List<Long>> proposalIdsBatches = Lists.partition(ids,
                    400);
            List<ProposalLineItem> results = new ArrayList<ProposalLineItem>();

            for (List<Long> currentBatch : proposalIdsBatches) {

                String whereClauseFilter = Joiner.on(", ")
                        .join(currentBatch);
                String whereQueryStatement = "id IN ("
                        + whereClauseFilter + ")";

                // Create a statement to only select proposal lines filtered by
                // proposalID
                StatementBuilder statementBuilder = new StatementBuilder()
                        .where(whereQueryStatement)
                        .orderBy("id ASC")
                        .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

                int totalResultSetSize = 0;

                logger.info("Getting the filtered proposal line items by ID.");

                do {
                    // Get proposal line items by statement.
                    ProposalLineItemPage page = proposalLineItemService
                            .getProposalLineItemsByStatement(statementBuilder
                                    .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (ProposalLineItem proposalLineItem : page
                                .getResults()) {
                            results.add(proposalLineItem);
                        }
                    }

                    statementBuilder
                            .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
                } while (statementBuilder.getOffset() < totalResultSetSize);

                logger.info("Retrieved " + totalResultSetSize
                        + " proposal line items.");

            }

            return results;
        } catch (ApiException e) {
            throw new GetProposalLineItemsException(e);
        } catch (RemoteException e) {
            throw new GetProposalLineItemsException(e);
        }
    }

}
