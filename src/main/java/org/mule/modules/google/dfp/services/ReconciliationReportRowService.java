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
import org.mule.modules.google.dfp.exceptions.GetReconciliationReportRowsException;
import org.mule.modules.google.dfp.exceptions.ReconciliationReportRowException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.modules.google.dfp.reconciliationreport.ReconciliationQueryParams;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRow;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRowPage;
import com.google.api.ads.dfp.axis.v201605.ReconciliationReportRowServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class ReconciliationReportRowService {

    private static final Logger logger = Logger.getLogger(ReconciliationReportRowService.class);

    protected ReconciliationReportRowServiceInterface createReconciliationReportRowService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        ReconciliationReportRowServiceInterface reconciliationReportService = dfpServices
                .get(session, ReconciliationReportRowServiceInterface.class);

        return reconciliationReportService;
    }

    public List<ReconciliationReportRow> getReconciliationReportRows(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder,
            Integer queryLimit, Integer queryOffset)
            throws GetReconciliationReportRowsException {
        try {
            ReconciliationReportRowServiceInterface reconciliationReportRowService = createReconciliationReportRowService(session);

            // Create a statement to get reconciliation report rows by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<ReconciliationReportRow> reconciliationReportRowsFound = new ArrayList<ReconciliationReportRow>();

            do {
                // Get Reconciliation Report Rows by statement.
                ReconciliationReportRowPage page = reconciliationReportRowService
                        .getReconciliationReportRowsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    reconciliationReportRowsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Reconciliation Report Rows found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Reconciliation Report Rows found:" + totalResultSetSize);
            return reconciliationReportRowsFound;
        } catch (ApiException e) {
            throw new GetReconciliationReportRowsException(e);
        } catch (RemoteException e) {
            throw new GetReconciliationReportRowsException(e);
        } catch (Exception e) {
            throw new GetReconciliationReportRowsException(e);
        }
    }

    public ReconciliationReportRow[] updateReconciliationReportRows(DfpSession session, List<ReconciliationReportRow> reconciliationReportRowsToUpdate)
            throws UpdateFailedException {

        try {
            ReconciliationReportRowServiceInterface reconciliationReportRowService = createReconciliationReportRowService(session);

            ReconciliationReportRow[] reconciliationReportRowsArray = new ReconciliationReportRow[reconciliationReportRowsToUpdate.size()];
            reconciliationReportRowsArray = reconciliationReportRowsToUpdate.toArray(reconciliationReportRowsArray);

            // Update the reconciliation report rows on the server.
            ReconciliationReportRow[] reconciliationReportRows = reconciliationReportRowService.updateReconciliationReportRows(reconciliationReportRowsArray);

            for (ReconciliationReportRow item : reconciliationReportRows) {
                logger.info(String
                        .format("Reconciliation Report Row with ID \"%d\" and order id \"%d\" was updated.\"%n\"",
                                item.getId(), item.getOrderId()));
            }

            return reconciliationReportRows;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public List<ReconciliationReportRow> getReconciliationReportRows(
            DfpSession session, ReconciliationQueryParams queryParams) throws ReconciliationReportRowException {
        try {
            ReconciliationReportRowServiceInterface reconciliationReportRowService = createReconciliationReportRowService(session);

            // Create a statement to get all reconciliation reports.
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("orderId = :orderId AND lineItemid = :lineItemId AND reconciliationReportId = :reconciliationReportId")
                    .withBindVariableValue("orderId", queryParams.getOrderId())
                    .withBindVariableValue("lineItemId", queryParams.getLineItemId())
                    .withBindVariableValue("reconciliationReportId",
                            queryParams.getReconciliationReportId());

            // Default for total result set size.
            int totalResultSetSize = 0;

            // List to store the comments found for the lineItemId.
            List<ReconciliationReportRow> reconciliationRows = new ArrayList<ReconciliationReportRow>();

            do {
                ReconciliationReportRowPage page = reconciliationReportRowService
                        .getReconciliationReportRowsByStatement(statementBuilder
                                .toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    for (ReconciliationReportRow reconciliationReportRow : page
                            .getResults()) {
                        reconciliationRows.add(reconciliationReportRow);

                        logger.info("Order ID: "
                                + reconciliationReportRow.getOrderId()
                                + ", Line item ID: "
                                + reconciliationReportRow.getLineItemId()
                                + ", and Reconciliation Report ID"
                                + reconciliationReportRow
                                        .getReconciliationReportId()
                                + " was found. The following comment was found for this record: "
                                + reconciliationReportRow.getComments() + ".");
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            return reconciliationRows;
        } catch (ApiException e) {
            throw new ReconciliationReportRowException(e);
        } catch (RemoteException e) {
            throw new ReconciliationReportRowException(e);
        } catch (Exception e) {
            throw new ReconciliationReportRowException(e);
        }
    }

}
