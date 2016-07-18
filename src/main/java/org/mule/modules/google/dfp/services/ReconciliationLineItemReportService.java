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
import org.mule.modules.google.dfp.exceptions.GetReconciliationLineItemReportsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.ReconciliationLineItemReport;
import com.google.api.ads.dfp.axis.v201605.ReconciliationLineItemReportPage;
import com.google.api.ads.dfp.axis.v201605.ReconciliationLineItemReportServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class ReconciliationLineItemReportService {

    private static final Logger logger = Logger
            .getLogger(ReconciliationLineItemReportService.class);

    protected ReconciliationLineItemReportServiceInterface createReconciliationLineItemReportService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        ReconciliationLineItemReportServiceInterface reconciliationLineItemReportService = dfpServices
                .get(session, ReconciliationLineItemReportServiceInterface.class);

        return reconciliationLineItemReportService;
    }

    public List<ReconciliationLineItemReport> getReconciliationLineItemReports(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder,
            Integer queryLimit, Integer queryOffset)
            throws GetReconciliationLineItemReportsException {

        try {
            ReconciliationLineItemReportServiceInterface reconciliationLineItemReportService = createReconciliationLineItemReportService(session);

            // Create a statement to get reconciliation order reports by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<ReconciliationLineItemReport> reconciliationLineItemReportsFound = new ArrayList<ReconciliationLineItemReport>();

            do {
                // Get Reconciliation Line Item Reports by statement.
                ReconciliationLineItemReportPage page = reconciliationLineItemReportService
                        .getReconciliationLineItemReportsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    reconciliationLineItemReportsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Reconciliation Line Item Reports found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Reconciliation Line Item Reports found:" + totalResultSetSize);
            return reconciliationLineItemReportsFound;
        } catch (ApiException e) {
            throw new GetReconciliationLineItemReportsException(e);
        } catch (RemoteException e) {
            throw new GetReconciliationLineItemReportsException(e);
        } catch (Exception e) {
            throw new GetReconciliationLineItemReportsException(e);
        }
    }

    public ReconciliationLineItemReport[] updateReconciliationLineItemReports(DfpSession session, List<ReconciliationLineItemReport> reconciliationLineItemReportsToUpdate)
            throws UpdateFailedException {

        try {
            ReconciliationLineItemReportServiceInterface reconciliationLineItemReportService = createReconciliationLineItemReportService(session);

            ReconciliationLineItemReport[] reconciliationLineItemReportsArray = new ReconciliationLineItemReport[reconciliationLineItemReportsToUpdate.size()];
            reconciliationLineItemReportsArray = reconciliationLineItemReportsToUpdate.toArray(reconciliationLineItemReportsArray);

            // Update the reconciliation order reports on the server.
            ReconciliationLineItemReport[] reconciliationLineItemReports = reconciliationLineItemReportService.updateReconciliationLineItemReports(reconciliationLineItemReportsArray);

            for (ReconciliationLineItemReport item : reconciliationLineItemReports) {
                logger.info(String
                        .format("Reconciliation Line Item Report  with ID \"%d\"  was updated.\"%n\"",
                                item.getId()));
            }

            return reconciliationLineItemReports;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }
}
