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
import org.mule.modules.google.dfp.exceptions.GetOrdersException;
import org.mule.modules.google.dfp.exceptions.PerformOrdersException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Order;
import com.google.api.ads.dfp.axis.v201605.OrderAction;
import com.google.api.ads.dfp.axis.v201605.OrderPage;
import com.google.api.ads.dfp.axis.v201605.OrderServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class);

    protected OrderServiceInterface createOrderService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the Order service.
        OrderServiceInterface ordersService = dfpServices.get(session,
                OrderServiceInterface.class);

        return ordersService;
    }

    public List<Order> getOrders(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder, Integer queryLimit, Integer queryOffset)
            throws GetOrdersException {
        try {
            OrderServiceInterface ordersInterface = createOrderService(session);

            // Create a statement to get orders by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<Order> ordersFound = new ArrayList<Order>();

            do {
                // Get Orders by statement.
                OrderPage page = ordersInterface
                        .getOrdersByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    ordersFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Orders found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Orders found:" + totalResultSetSize);
            return ordersFound;
        } catch (ApiException e) {
            throw new GetOrdersException(e);
        } catch (RemoteException e) {
            throw new GetOrdersException(e);
        } catch (Exception e) {
            throw new GetOrdersException(e);
        }
    }

    public Integer performOrders(DfpSession session, String orderActionString, String queryString, Map<String, Object> queryParams)
            throws PerformOrdersException {
        try {
            OrderServiceInterface ordersInterface = createOrderService(session);

            // Create a statement to get orders by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of orders performed
            Integer ordersPerformed = 0;

            String trimmedString = orderActionString.replace(" ", "")
                    .trim();
            OrderAction orderAction = (OrderAction) Class.forName("com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform OrderAction over Orders by statement.
            UpdateResult updateResult = ordersInterface
                    .performOrderAction(orderAction, statementBuilder.toStatement());

            if (updateResult != null) {
                ordersPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Orders found with given parameters");
            }

            logger.info("Number of Orders performed:" + ordersPerformed);
            return ordersPerformed;
        } catch (ApiException e) {
            throw new PerformOrdersException(e);
        } catch (RemoteException e) {
            throw new PerformOrdersException(e);
        } catch (Exception e) {
            throw new PerformOrdersException(e);
        }
    }

    public Order[] createOrders(DfpSession session, List<Order> ordersToCreate)
            throws CreateFailedException {

        try {
            // Get the OrderService
            OrderServiceInterface ordersInterface = createOrderService(session);

            Order[] ordersArray = new Order[ordersToCreate.size()];
            ordersArray = ordersToCreate.toArray(ordersArray);

            Order[] orders = ordersInterface.createOrders(ordersArray);

            for (Order item : orders) {
                logger.info(String
                        .format("Order with ID \"%d\" and name \"%s\" was created.\"%n\"",
                                item.getId(), item.getName()));
            }

            return orders;

        } catch (ApiException e) {
            throw new CreateFailedException(e);
        } catch (RemoteException e) {
            throw new CreateFailedException(e);
        } catch (Exception e) {
            throw new CreateFailedException(e);
        }
    }

    public Order[] updateOrders(DfpSession session, List<Order> ordersToUpdate)
            throws UpdateFailedException {

        try {
            // Get the OrderService
            OrderServiceInterface ordersInterface = createOrderService(session);

            Order[] ordersArray = new Order[ordersToUpdate.size()];
            ordersArray = ordersToUpdate.toArray(ordersArray);

            // Update the orders on the server.
            Order[] orders = ordersInterface.updateOrders(ordersArray);

            for (Order item : orders) {
                logger.info(String
                        .format("Order with ID \"%d\" and name \"%s\" was updated.\"%n\"",
                                item.getId(), item.getName()));
            }

            return orders;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public List<Order> getOrdersByStatement(DfpSession session,
            DateTime lastModifiedDateTime, DateTime snapshotDateTime)
            throws GetOrdersException {
        try {

            OrderServiceInterface ordersInterface = createOrderService(session);

            // Create a statement to select all orders modified since
            // lastModifiedDateTime.
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
                    .orderBy("id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                    .withBindVariableValue("lastModifiedDateTime",
                            lastModifiedDateTime)
                    .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<Order> results = new ArrayList<Order>();

            logger.info("Getting all modified orders.");

            OrderPage initialPage = ordersInterface
                    .getOrdersByStatement(statementBuilder.toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            do {
                // Get orders by statement.
                OrderPage page = ordersInterface
                        .getOrdersByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {

                    for (Order order : page.getResults()) {
                        results.add(order);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize + '\n'
                    + "Number of results retrieved: " + results.size());

            return results;
        } catch (ApiException e) {
            throw new GetOrdersException(e);
        } catch (RemoteException e) {
            throw new GetOrdersException(e);
        }

    }

    public List<Order> getOrdersById(DfpSession session, List<Long> ids)
            throws GetOrdersException {
        try {

            OrderServiceInterface orderService = createOrderService(session);

            List<List<Long>> idsBatches = Lists.partition(ids, 400);
            List<Order> results = new ArrayList<Order>();

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

                OrderPage initialPage = orderService.getOrdersByStatement(statementBuilder
                        .toStatement());
                totalResultSetSize = initialPage.getTotalResultSetSize();

                do {
                    OrderPage page = orderService.getOrdersByStatement(statementBuilder
                            .toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (Order order : page.getResults()) {
                            results.add(order);
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
            throw new GetOrdersException(e);
        } catch (RemoteException e) {
            throw new GetOrdersException(e);
        }

    }

}
