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

import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.Product;

public class GetProductsByStatmentTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetProductsByStatmentTestCases() {
        super(GoogleDfpConnector.class);
    }

    DateTime lastModifiedDate;
    DateTime snapshotDateTime;

    @Before
    public void setup() {
        lastModifiedDate = TestDataBuilder.getGetProductsByStatementLastModifiedDate();
        snapshotDateTime = TestDataBuilder.getGetProductsByStatementSnapshotDateTime();
    }

    @Test
    @Ignore
    public void testGetProductsByStatementNoResults() throws Exception {
        List<Product> products = getConnector().getProductsByStatement(lastModifiedDate, snapshotDateTime);
        // Ensure that the size of product lists is empty
        Assert.assertTrue(products.isEmpty());
    }
}
