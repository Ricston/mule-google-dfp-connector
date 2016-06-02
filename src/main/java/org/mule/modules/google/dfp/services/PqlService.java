/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;
import org.mule.modules.google.dfp.exceptions.ReportDownloadException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.Pql;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.PublisherQueryLanguageServiceInterface;
import com.google.api.ads.dfp.axis.v201605.ResultSet;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class PqlService {

    private static final Logger logger = Logger.getLogger(PqlService.class);

    protected PublisherQueryLanguageServiceInterface createPqlService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        PublisherQueryLanguageServiceInterface pqlService = dfpServices.get(
                session, PublisherQueryLanguageServiceInterface.class);

        return pqlService;
    }

    public List<String[]> getAllLineItemsPql(DfpSession session)
            throws ReportDownloadException, ApiException, RemoteException,
            IllegalAccessException {

        PublisherQueryLanguageServiceInterface pqlService = createPqlService(session);

        // Create statement to select all line items.
        StatementBuilder statementBuilder = new StatementBuilder()
                .select("Id, StartDateTime, EndDateTime, LastModifiedDateTime")
                .from("Line_Item")
                .orderBy("Id ASC")
                .offset(0)
                .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

        // Default for result sets.
        ResultSet combinedResultSet = null;
        ResultSet resultSet;

        do {
            // Get all line items.
            resultSet = pqlService.select(statementBuilder.toStatement());

            // Combine result sets with previous ones.
            combinedResultSet = combinedResultSet == null ? resultSet : Pql
                    .combineResultSets(combinedResultSet, resultSet);

            logger.info("Offset: " + statementBuilder.getOffset());

            statementBuilder
                    .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
        } while (resultSet.getRows() != null && resultSet.getRows().length > 0);

        List<String[]> result = Pql
                .resultSetToStringArrayList(combinedResultSet);

        // size-1: not counting the header
        logger.info("Number of results retrieved: " + (result.size() - 1));

        return result;
    }

    public List<String[]> getProposalRetractionReasonPql(DfpSession session)
            throws ReportDownloadException, ApiException, RemoteException,
            IllegalAccessException {

        PublisherQueryLanguageServiceInterface pqlService = createPqlService(session);

        // Create statement to select all line items.
        StatementBuilder statementBuilder = new StatementBuilder()
                .select("Id, IsActive, Name")
                .from("Proposal_Retraction_Reason")
                .orderBy("Id ASC")
                .offset(0)
                .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

        // Default for result sets.
        ResultSet combinedResultSet = null;
        ResultSet resultSet;

        do {
            // Get all line items.
            resultSet = pqlService.select(statementBuilder.toStatement());

            // Combine result sets with previous ones.
            combinedResultSet = combinedResultSet == null ? resultSet : Pql
                    .combineResultSets(combinedResultSet, resultSet);

            logger.info("Offset: " + statementBuilder.getOffset());

            statementBuilder
                    .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
        } while (resultSet.getRows() != null && resultSet.getRows().length > 0);

        List<String[]> result = Pql
                .resultSetToStringArrayList(combinedResultSet);

        // size-1: not counting the header
        logger.info("Number of results retrieved: " + (result.size() - 1));

        return result;
    }
}
