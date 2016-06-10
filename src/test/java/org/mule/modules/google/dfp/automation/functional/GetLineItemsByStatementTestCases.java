/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.LineItem;

public class GetLineItemsByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetLineItemsByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    DateTime lastModifiedDate;
    DateTime snapshotDateTime;
    DateTime lastModifiedDateNoResults;
    DateTime snapshotDateTimeNoResults;

    @Before
    public void setup() {
        lastModifiedDate = TestDataBuilder.getGetLineItemsByStatementLastModifiedDate();
        snapshotDateTime = TestDataBuilder.getGetLineItemsByStatementSnapshotDateTime();
        lastModifiedDateNoResults = TestDataBuilder.getGetLineItemsByStatementLastModifiedDateNoResults();
        snapshotDateTimeNoResults = TestDataBuilder.getGetLineItemsByStatementSnapshotDateTimeNoResults();
    }

    @Test
    public void testGetLineItemsByStatement() throws Exception {
        List<LineItem> lineItems = getConnector().getLineItemsByStatement(lastModifiedDate, snapshotDateTime);
        Assert.assertTrue(!lineItems.isEmpty());
    }

    @Test
    public void testGetLineItemsByStatementNotFound() throws Exception {
        List<LineItem> lineItems = getConnector().getLineItemsByStatement(lastModifiedDateNoResults, snapshotDateTimeNoResults);
        Assert.assertTrue(lineItems.isEmpty());
    }

}
