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
import org.mule.modules.google.dfp.exceptions.GetReconciliationReportsException;
import org.mule.modules.google.dfp.exceptions.ReconciliationReportException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportPage;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class ReconciliationReportService {

    private static final Logger logger = Logger
            .getLogger(ReconciliationReportService.class);

    protected ReconciliationReportServiceInterface createReconciliationReportService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        ReconciliationReportServiceInterface reconciliationReportService = dfpServices
                .get(session, ReconciliationReportServiceInterface.class);

        return reconciliationReportService;
    }

    public List<ReconciliationReport> getReconciliationReports(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder,
            Integer queryLimit, Integer queryOffset)
            throws GetReconciliationReportsException {
        try {
            ReconciliationReportServiceInterface reconciliationReportService = createReconciliationReportService(session);

            // Create a statement to get reconciliation reports by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<ReconciliationReport> reconciliationReportsFound = new ArrayList<ReconciliationReport>();

            do {
                // Get Reconciliation Reports by statement.
                ReconciliationReportPage page = reconciliationReportService
                        .getReconciliationReportsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    reconciliationReportsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Reconciliation Reports found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Reconciliation Reports found:" + totalResultSetSize);
            return reconciliationReportsFound;
        } catch (ApiException e) {
            throw new GetReconciliationReportsException(e);
        } catch (RemoteException e) {
            throw new GetReconciliationReportsException(e);
        } catch (Exception e) {
            throw new GetReconciliationReportsException(e);
        }
    }

    public ReconciliationReport[] updateReconciliationReports(DfpSession session, List<ReconciliationReport> reconciliationReportsToUpdate)
            throws UpdateFailedException {

        try {
            ReconciliationReportServiceInterface reconciliationReportService = createReconciliationReportService(session);

            ReconciliationReport[] reconciliationReportsArray = new ReconciliationReport[reconciliationReportsToUpdate.size()];
            reconciliationReportsArray = reconciliationReportsToUpdate.toArray(reconciliationReportsArray);

            // Update the reconciliation reports on the server.
            ReconciliationReport[] reconciliationReports = reconciliationReportService.updateReconciliationReports(reconciliationReportsArray);

            for (ReconciliationReport item : reconciliationReports) {
                logger.info(String
                        .format("Reconciliation Report  with ID \"%d\"  was updated.\"%n\"",
                                item.getId()));
            }

            return reconciliationReports;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public List<ReconciliationReport> getReconciliationReportByStartDate(
            DfpSession session, String startDate)
            throws ReconciliationReportException {

        try {
            ReconciliationReportServiceInterface reconciliationReportService = createReconciliationReportService(session);

            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("startDate = :startDate")
                    .withBindVariableValue("startDate", startDate)
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

            int totalResultSetSize = 0;

            // List to store the report returned by the query.
            List<ReconciliationReport> reconciliationReportList = new ArrayList<ReconciliationReport>();

            do {
                ReconciliationReportPage page = reconciliationReportService
                        .getReconciliationReportsByStatement(statementBuilder
                                .toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    for (ReconciliationReport reconciliationReport : page
                            .getResults()) {
                        reconciliationReportList.add(reconciliationReport);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Reconciliation Reports found: " + totalResultSetSize + ".");
            return reconciliationReportList;

        } catch (NullPointerException e) {
            throw new ReconciliationReportException(e);
        } catch (ApiException e) {
            throw new ReconciliationReportException(e);
        } catch (RemoteException e) {
            throw new ReconciliationReportException(e);
        }
    }

}
