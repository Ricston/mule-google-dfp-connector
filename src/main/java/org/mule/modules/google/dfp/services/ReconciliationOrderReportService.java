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
import org.mule.modules.google.dfp.exceptions.GetReconciliationOrderReportsException;
import org.mule.modules.google.dfp.exceptions.PerformReconciliationOrderReportsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReportAction;
import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReportPage;
import com.google.api.ads.dfp.axis.v201605.ReconciliationOrderReportServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class ReconciliationOrderReportService {

    private static final Logger logger = Logger
            .getLogger(ReconciliationOrderReportService.class);

    protected ReconciliationOrderReportServiceInterface createReconciliationOrderReportService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        ReconciliationOrderReportServiceInterface reconciliationOrderReportService = dfpServices
                .get(session, ReconciliationOrderReportServiceInterface.class);

        return reconciliationOrderReportService;
    }

    public List<ReconciliationOrderReport> getReconciliationOrderReports(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder,
            Integer queryLimit, Integer queryOffset)
            throws GetReconciliationOrderReportsException {

        try {
            ReconciliationOrderReportServiceInterface reconciliationOrderReportService = createReconciliationOrderReportService(session);

            // Create a statement to get reconciliation order reports by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<ReconciliationOrderReport> reconciliationOrderReportsFound = new ArrayList<ReconciliationOrderReport>();

            do {
                // Get Reconciliation Order Reports by statement.
                ReconciliationOrderReportPage page = reconciliationOrderReportService
                        .getReconciliationOrderReportsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    reconciliationOrderReportsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Reconciliation Order Reports found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Reconciliation Order Reports found:" + totalResultSetSize);
            return reconciliationOrderReportsFound;
        } catch (ApiException e) {
            throw new GetReconciliationOrderReportsException(e);
        } catch (RemoteException e) {
            throw new GetReconciliationOrderReportsException(e);
        } catch (Exception e) {
            throw new GetReconciliationOrderReportsException(e);
        }
    }

    public ReconciliationOrderReport[] updateReconciliationOrderReports(DfpSession session, List<ReconciliationOrderReport> reconciliationOrderReportsToUpdate)
            throws UpdateFailedException {

        try {
            ReconciliationOrderReportServiceInterface reconciliationOrderReportService = createReconciliationOrderReportService(session);

            ReconciliationOrderReport[] reconciliationOrderReportsArray = new ReconciliationOrderReport[reconciliationOrderReportsToUpdate.size()];
            reconciliationOrderReportsArray = reconciliationOrderReportsToUpdate.toArray(reconciliationOrderReportsArray);

            // Update the reconciliation order reports on the server.
            ReconciliationOrderReport[] reconciliationOrderReports = reconciliationOrderReportService.updateReconciliationOrderReports(reconciliationOrderReportsArray);

            for (ReconciliationOrderReport item : reconciliationOrderReports) {
                logger.info(String
                        .format("Reconciliation Order Report  with ID \"%d\"  was updated.\"%n\"",
                                item.getId()));
            }

            return reconciliationOrderReports;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public Integer performReconciliationOrderReports(DfpSession session, String reconciliationOrderReportActionString, String queryString, Map<String, Object> queryParams)
            throws PerformReconciliationOrderReportsException {
        try {
            ReconciliationOrderReportServiceInterface reconciliationOrderReportService = createReconciliationOrderReportService(session);

            // Create a statement to get reconciliation order reports by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of Reconciliation Order Reports performed
            Integer reconciliationOrderReportsPerformed = 0;

            String trimmedString = reconciliationOrderReportActionString.replace(" ", "")
                    .trim();
            ReconciliationOrderReportAction reconciliationOrderReportAction = (ReconciliationOrderReportAction) Class.forName(
                    "com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform ReconciliationOrderReportAction over Reconciliation Order Reports by statement.
            UpdateResult updateResult = reconciliationOrderReportService
                    .performReconciliationOrderReportAction(reconciliationOrderReportAction, statementBuilder.toStatement());

            if (updateResult != null) {
                reconciliationOrderReportsPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Reconciliation Order Reports found with given parameters");
            }

            logger.info("Number of Reconciliation Order Reports performed:" + reconciliationOrderReportsPerformed);
            return reconciliationOrderReportsPerformed;
        } catch (ApiException e) {
            throw new PerformReconciliationOrderReportsException(e);
        } catch (RemoteException e) {
            throw new PerformReconciliationOrderReportsException(e);
        } catch (Exception e) {
            throw new PerformReconciliationOrderReportsException(e);
        }
    }

}
