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

import com.google.api.ads.dfp.axis.v201605.LineItem;

public class GetFilteredLineItemsByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetFilteredLineItemsByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    List<Long> correctOrderIds;
    List<Long> wrongOrderIds;

    @Before
    public void setup() {
        correctOrderIds = TestDataBuilder.getGetFilteredLineItemsByStatementCorrectIds();
        wrongOrderIds = TestDataBuilder.getGetFilteredLineItemsByStatementWrongIds();
    }

    @Test
    public void testGetFilteredLineItemsByStatement() throws Exception {
        List<LineItem> lineItems = getConnector().getFilteredLineItemsByStatement(correctOrderIds);
        Assert.assertTrue(!lineItems.isEmpty());
    }

    @Test
    public void testGetFilteredLineItemsByStatementNotFound() throws Exception {
        List<LineItem> lineItems = getConnector().getFilteredLineItemsByStatement(wrongOrderIds);
        Assert.assertTrue(lineItems.isEmpty());
    }

}
