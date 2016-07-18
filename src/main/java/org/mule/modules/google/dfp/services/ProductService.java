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
import org.mule.modules.google.dfp.exceptions.GetProductsByStatementException;
import org.mule.modules.google.dfp.exceptions.GetProductsException;
import org.mule.modules.google.dfp.exceptions.PerformProductsException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Product;
import com.google.api.ads.dfp.axis.v201605.ProductAction;
import com.google.api.ads.dfp.axis.v201605.ProductPage;
import com.google.api.ads.dfp.axis.v201605.ProductServiceInterface;
import com.google.api.ads.dfp.axis.v201605.UpdateResult;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class);

    protected ProductServiceInterface createProductService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the Product Service.
        ProductServiceInterface productService = dfpServices.get(session,
                ProductServiceInterface.class);

        return productService;
    }

    public List<Product> getProducts(DfpSession session, String queryString, Map<String, Object> queryParams, String queryOrder, Integer queryLimit, Integer queryOffset)
            throws GetProductsException {
        try {
            // Get the ProductService.
            ProductServiceInterface productService = createProductService(session);

            // Create a statement to get products by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString)
                    .orderBy(queryOrder)
                    .limit(queryLimit)
                    .offset(queryOffset);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<Product> productsFound = new ArrayList<Product>();

            do {
                // Get Products by statement.
                ProductPage page = productService
                        .getProductsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {
                    totalResultSetSize = page.getTotalResultSetSize();
                    Arrays.asList(page.getResults());
                    productsFound.addAll(Arrays.asList(page.getResults()));
                } else {
                    logger.info("No Products found with given parameters");
                }

                statementBuilder
                        .increaseOffsetBy(queryLimit);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of Products found:" + totalResultSetSize);
            return productsFound;
        } catch (ApiException e) {
            throw new GetProductsException(e);
        } catch (RemoteException e) {
            throw new GetProductsException(e);
        } catch (Exception e) {
            throw new GetProductsException(e);
        }
    }

    public Integer performProducts(DfpSession session, String productActionString, String queryString, Map<String, Object> queryParams)
            throws PerformProductsException {
        try {
            // Get the ProductService.
            ProductServiceInterface productService = createProductService(session);

            // Create a statement to get products by the given parameters
            StatementBuilder statementBuilder = new StatementBuilder()
                    .where(queryString);

            ServicesUtils.bindVariables(queryParams, statementBuilder);

            // Number of products performed
            Integer productsPerformed = 0;

            String trimmedString = productActionString.replace(" ", "")
                    .trim();
            ProductAction productAction = (ProductAction) Class.forName("com.google.api.ads.dfp.axis.v201605." + trimmedString)
                    .newInstance();

            // Perform ProductAction over Products by statement.
            UpdateResult updateResult = productService
                    .performProductAction(productAction, statementBuilder.toStatement());

            if (updateResult != null) {
                productsPerformed = updateResult.getNumChanges();
            } else {
                logger.info("No Products found with given parameters");
            }

            logger.info("Number of Products performed:" + productsPerformed);
            return productsPerformed;
        } catch (ApiException e) {
            throw new PerformProductsException(e);
        } catch (RemoteException e) {
            throw new PerformProductsException(e);
        } catch (Exception e) {
            throw new PerformProductsException(e);
        }
    }

    public Product[] updateProducts(DfpSession session, List<Product> productsToUpdate)
            throws UpdateFailedException {

        try {
            // Get the ProductService.
            ProductServiceInterface productService = createProductService(session);

            Product[] productsArray = new Product[productsToUpdate.size()];
            productsArray = productsToUpdate.toArray(productsArray);

            // Update the products on the server.
            Product[] products = productService.updateProducts(productsArray);

            for (Product item : products) {
                logger.info(String
                        .format("Product with ID \"%d\" and name \"%s\" was updated.\"%n\"",
                                item.getId(), item.getName()));
            }

            return products;
        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }
    }

    public List<Product> getProductsByStatement(DfpSession session,
            DateTime lastModifiedDateTime, DateTime snapshotDateTime)
            throws GetProductsByStatementException {
        try {

            ProductServiceInterface productService = createProductService(session);

            StatementBuilder statementBuilder = new StatementBuilder()
                    .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
                    .orderBy("id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                    .withBindVariableValue("lastModifiedDateTime",
                            lastModifiedDateTime)
                    .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            int totalResultSetSize = 0;
            List<Product> results = new ArrayList<Product>();
            logger.info("Retrieving modified products.");

            ProductPage initialPage = productService
                    .getProductsByStatement(statementBuilder.toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            do {
                // Get products by statement.
                ProductPage page = productService
                        .getProductsByStatement(statementBuilder.toStatement());

                if (page.getResults() != null) {

                    for (Product product : page.getResults()) {
                        results.add(product);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize + '\n'
                    + "Number of results retrieved: " + results.size());

            return results;

        } catch (ApiException e) {
            throw new GetProductsByStatementException(e);
        } catch (RemoteException e) {
            throw new GetProductsByStatementException(e);
        } catch (Exception e) {
            throw new GetProductsByStatementException(e);
        }
    }

    public List<Product> getProductsById(DfpSession session, List<Long> ids)
            throws GetProductsByStatementException {
        try {

            ProductServiceInterface productService = createProductService(session);

            List<List<Long>> idsBatches = Lists.partition(ids, 400);
            List<Product> results = new ArrayList<Product>();

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

                ProductPage initialPage = productService
                        .getProductsByStatement(statementBuilder.toStatement());
                totalResultSetSize = initialPage.getTotalResultSetSize();

                do {
                    ProductPage page = productService
                            .getProductsByStatement(statementBuilder.toStatement());

                    if (page.getResults() != null) {
                        totalResultSetSize = page.getTotalResultSetSize();
                        for (Product product : page.getResults()) {
                            results.add(product);
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
            throw new GetProductsByStatementException(e);
        } catch (RemoteException e) {
            throw new GetProductsByStatementException(e);
        } catch (Exception e) {
            throw new GetProductsByStatementException(e);
        }
    }
}
