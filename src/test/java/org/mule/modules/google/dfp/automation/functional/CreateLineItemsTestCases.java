/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.LineItem;

public class CreateLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    private LineItem newLineItem;
    private LineItem createdLineItem;

    @Before
    public void setup() throws Exception {
        newLineItem = TestDataBuilder.getCreateLineItemsNewLineItem();
    }

    @Test
    public void testCreateLineItems() throws Exception {
        createdLineItem = getConnector().createLineItems(Arrays.asList(newLineItem))[0];
        Assert.assertNotNull(createdLineItem);
        Assert.assertEquals(newLineItem.getName(), createdLineItem.getName());
    }

    @After
    public void tearDown() throws Exception {
        createdLineItem.setName(createdLineItem.getName() + createdLineItem.getId());
        getConnector().updateLineItems(Arrays.asList(createdLineItem));
    }
}
