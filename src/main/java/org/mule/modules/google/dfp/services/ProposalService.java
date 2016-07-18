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
import org.mule.modules.google.dfp.exceptions.GetProposalsException;
import org.mule.modules.google.dfp.exceptions.PerformProposalsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Proposal;
import com.google.api.ads.dfp.axis.v201605.ProposalAction;
import com.google.api.ads.dfp.axis.v201605.ProposalPage;
import com.google.api.ads.dfp.axis.v201605.ProposalServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class ProposalService {

    private static final Logger logger = Logger
            .getLogger(ProposalService.class);

    protected ProposalServiceInterface createProposalService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the Proposal service.
        ProposalServiceInterface proposalsService = dfpServices.get(session,
                ProposalServiceInterface.class);

        return proposalsService;
    }

    public List<Proposal> getProposals(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder, Integer queryLimit,
            Integer queryOffset)
            throws GetProposalsException {
        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            // Create a statement to get proposals by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<Proposal> proposalsFound = new ArrayList<Proposal>();

            do {
                // Get Proposals by statement.
                ProposalPage page = proposalService
                        .getProposalsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    proposalsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Proposals found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Proposals found:" + totalResultSetSize);
            return proposalsFound;
        } catch (ApiException e) {
            throw new GetProposalsException(e);
        } catch (RemoteException e) {
            throw new GetProposalsException(e);
        } catch (Exception e) {
            throw new GetProposalsException(e);
        }
    }

    public Proposal[] createProposals(DfpSession session, List<Proposal> proposalsToCreate)
            throws CreateFailedException {

        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            Proposal[] proposalsArray = new Proposal[proposalsToCreate.size()];
            proposalsArray = proposalsToCreate.toArray(proposalsArray);

            Proposal[] proposals = proposalService
                    .createProposals(proposalsArray);

            for (Proposal item : proposals) {
                logger.info(String
                        .format("Proposal with ID \"%d\" and name \"%s\" was created.\"%n\"",
                                item.getId(), item.getName()));
            }

            return proposals;

        } catch (ApiException e) {
            throw new CreateFailedException(e);
        } catch (RemoteException e) {
            throw new CreateFailedException(e);
        } catch (Exception e) {
            throw new CreateFailedException(e);
        }
    }

    public Proposal[] updateProposals(DfpSession session, List<Proposal> proposalsToUpdate)
            throws UpdateFailedException {

        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            Proposal[] proposalsArray = new Proposal[proposalsToUpdate.size()];
            proposalsArray = proposalsToUpdate.toArray(proposalsArray);

            Proposal[] proposals = proposalService.updateProposals(proposalsArray);

            for (Proposal item : proposals) {
                logger.info(String
                        .format("Proposal with ID \"%d\" and name \"%s\" was updated.\"%n\"",
                                item.getId(), item.getName()));
            }

            return proposals;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public Integer performProposals(DfpSession session, String proposalActionString, String queryString, Map<String, Object> queryParams)
            throws PerformProposalsException {
        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            // Create a statement to get proposals by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of proposals performed
            Integer proposalsPerformed = 0;

            String trimmedString = proposalActionString.replace(" ", "")
                    .trim();
            ProposalAction proposalAction = (ProposalAction) Class.forName("com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform ProposalAction over Proposals by statement.
            UpdateResult updateResult = proposalService
                    .performProposalAction(proposalAction, statementBuilder.toStatement());

            if (updateResult != null) {
                proposalsPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Proposals found with given parameters");
            }

            logger.info("Number of Proposals performed:" + proposalsPerformed);
            return proposalsPerformed;
        } catch (ApiException e) {
            throw new PerformProposalsException(e);
        } catch (RemoteException e) {
            throw new PerformProposalsException(e);
        } catch (Exception e) {
            throw new PerformProposalsException(e);
        }
    }

    public List<Proposal> getProposalsByStatement(DfpSession session,
            DateTime lastModifiedDateTime, DateTime snapshotDateTime)
            throws GetProposalsException {
        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            // Create a statement to only select proposals that were modified
            // recently.
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
                    .orderBy("id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                    .withBindVariableValue("lastModifiedDateTime",
                            lastModifiedDateTime)
                    .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            // Default for total result set size.
            int totalResultSetSize = 0;

            List<Proposal> results = new ArrayList<Proposal>();

            ProposalPage initialPage = proposalService
                    .getProposalsByStatement(statementBuilder.toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            logger.info("Getting all modified proposals.");

            do {
                // Get proposals by statement.
                ProposalPage page = proposalService
                        .getProposalsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    for (Proposal proposal : page.getResults()) {
                        results.add(proposal);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize
                    + '\n' + "Number of results retrieved: "
                    + results.size());

            return results;
        } catch (ApiException e) {
            throw new GetProposalsException(e);
        } catch (RemoteException e) {
            throw new GetProposalsException(e);
        }
    }

    public List<Proposal> getProposalsById(DfpSession session,
            List<Long> ids)
            throws GetProposalsException {
        try {
            ProposalServiceInterface proposalService = createProposalService(session);

            List<List<Long>> idsBatches = Lists.partition(ids, 400);
            List<Proposal> results = new ArrayList<Proposal>();

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

                ProposalPage initialPage = proposalService.getProposalsByStatement(statementBuilder
                        .toStatement());
                totalResultSetSize = initialPage.getTotalResultSetSize();

                do {
                    ProposalPage page = proposalService.getProposalsByStatement(statementBuilder
                            .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (Proposal proposal : page
                                .getResults()) {
                            results.add(proposal);
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
        } catch (ApiException e) {
            throw new GetProposalsException(e);
        } catch (RemoteException e) {
            throw new GetProposalsException(e);
        }
    }

}
