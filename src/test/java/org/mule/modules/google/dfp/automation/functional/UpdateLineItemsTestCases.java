/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.LineItem;

public class UpdateLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    LineItem lineItemToUpdate;
    String lineItemName;

    @Before
    public void setup() throws Exception {
        lineItemToUpdate = getConnector().getLineItemsById(Arrays.asList(113219888L))
                .get(0);
        lineItemName = lineItemToUpdate.getName();
        lineItemToUpdate.setName("TestingFormLineItem");
    }

    @After
    public void tearDown() throws Exception {
        lineItemToUpdate = getConnector().getLineItemsById(Arrays.asList(113219888L))
                .get(0);
        lineItemToUpdate.setName(lineItemName);
        getConnector().updateLineItems(Arrays.asList(lineItemToUpdate));
    }

    @Test
    public void testUpdateLineItems() throws Exception {

        // Line Itmes can not be deleted so the line item with ID=113219888 always will exist.
        List<LineItem> list = new ArrayList<LineItem>();
        list.add(lineItemToUpdate);
        LineItem[] updatedLineItems = getConnector().updateLineItems(list);
        Assert.assertNotNull(updatedLineItems);
        Assert.assertEquals(updatedLineItems[0].getName(), "TestingFormLineItem");
    }

    @Test
    public void testUpdateLineItemsNotFound() throws Exception {
        try {
            lineItemToUpdate.setId(12345L);
            getConnector().updateLineItems(Arrays.asList(lineItemToUpdate));
        } catch (UpdateFailedException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }

    }

}
