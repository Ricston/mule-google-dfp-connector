/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Product;

public class UpdateProductsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateProductsTestCases() {
        super(GoogleDfpConnector.class);
    }

    Product productToUpdate;
    String productName;

    @Before
    public void setup() throws Exception {
        // XXX Products can not be created without a premium account of DFP, so we can not retrieve them
        // productToUpdate = getConnector().getProductsById(Arrays.asList())
        // .get(0);
        // productName = productToUpdate.getName();
        // productToUpdate.setName("TestingFormProduct");
    }

    @After
    public void tearDown() throws Exception {
        // XXX Products can not be created without a premium account of DFP, so we can not retrieve them
        // productToUpdate = getConnector().getProductsById(Arrays.asList())
        // .get(0);
        // productToUpdate.setName(productName);
        // getConnector().updateProducts(Arrays.asList(productToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateOrders() throws Exception {
        // XXX Products can not be created without a premium account of DFP, so we can not retrieve them
        // List<Product> list = new ArrayList<Product>();
        // list.add(productToUpdate);
        // Product[] updatedProducts = getConnector().updateProducts(list);
        // Assert.assertNotNull(updatedProducts);
        // Assert.assertEquals(updatedProducts[0].getName(), "TestingFormProduct");
    }

    @Test
    @Ignore
    public void testUpdateOrdersNotFound() throws Exception {
        // XXX Products can not be created without a premium account of DFP, so we can not retrieve them
        // try {
        // productToUpdate.setId(12345L);
        // getConnector().updateProducts(Arrays.asList(productToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
