/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Product;

public class GetProductsByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetProductsByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    List<Long> corectsIds;
    List<Long> wrongIds;

    @Before
    public void setup() {
        corectsIds = TestDataBuilder.getGetProductsByIdCorrectIds();
        wrongIds = TestDataBuilder.getGetProductsByIdWrongIds();
    }

    @Test
    @Ignore
    public void testGetProductsById() throws Exception {
        // XXX Products can not be created without a premium account of DFP, , so we can not retrieve them

        // List<Product> products = getConnector().getProductsById(corectsIds);
        // // Ensure that the size of product lists is equal than size of ids
        // Assert.assertEquals(products.size(), corectsIds.size());
        // Set<Long> ids = new HashSet<Long>(corectsIds);
        // // Ensure that all the products in the product lists are these than we are requesting for
        // for (Product p : products) {
        // Assert.assertTrue(ids.contains(p.getId()));
        // }
    }

    @Test
    @Ignore
    public void testGetProductsByIdNotFound() throws Exception {
        List<Product> products = getConnector().getProductsById(wrongIds);
        // Ensure that the size of product lists is not equal than size of ids
        Assert.assertFalse(products.size() == wrongIds.size());
    }

}
