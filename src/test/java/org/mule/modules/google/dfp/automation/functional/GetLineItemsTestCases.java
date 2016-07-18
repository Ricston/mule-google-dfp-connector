/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.LineItem;

public class GetLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;

    public GetLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        queryParams = TestDataBuilder.getGetLineItems();
    }

    @Test
    public void testGetLineItems() throws Exception {

        List<LineItem> lineItems = getConnector().getLineItems("id IN (:id)", queryParams, "id ASC", 400, 0);

        Assert.assertNotNull(lineItems);
        if (CollectionUtils.isNotEmpty((List<LineItem>) lineItems)) {
            Assert.assertTrue(lineItems.get(0) instanceof LineItem);
        }
    }

}
