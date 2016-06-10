/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.LineItem;

public class GetLineItemsByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetLineItemsByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    List<Long> correctsIds;
    List<Long> wrongIds;

    @Before
    public void setup() {
        correctsIds = TestDataBuilder.getGetLineItemsByIdCorrectIds();
        wrongIds = TestDataBuilder.getGetLineItemsByIdWrongIds();
    }

    @Test
    public void testGetLineItemsById() throws Exception {

        List<LineItem> lineItems = getConnector().getLineItemsById(correctsIds);
        // Ensure that the size of the list of line items is equal than size of ids
        Assert.assertEquals(lineItems.size(), correctsIds.size());
        Set<Long> ids = new HashSet<Long>(correctsIds);
        // Ensure that all the line items in the list of line items are these than we are requesting for
        for (LineItem p : lineItems) {
            Assert.assertTrue(ids.contains(p.getId()));
        }
    }

    @Test
    public void testGetLineItemsByIdNotFound() throws Exception {
        List<LineItem> lineItems = getConnector().getLineItemsById(wrongIds);
        // Ensure that the size of the list of line items is not equal than size of ids
        Assert.assertFalse(lineItems.size() == wrongIds.size());
    }

}
