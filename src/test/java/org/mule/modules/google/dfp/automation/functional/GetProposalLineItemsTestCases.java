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

import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;

public class GetProposalLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;

    public GetProposalLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        queryParams = TestDataBuilder.getGetProposalLineItems();
    }

    @Test
    public void testGetProposalLineItems() throws Exception {

        List<ProposalLineItem> proposalLineItems = getConnector().getProposalLineItems("", queryParams, "id ASC", 400, 0);

        Assert.assertNotNull(proposalLineItems);
        if (CollectionUtils.isNotEmpty((List<ProposalLineItem>) proposalLineItems)) {
            Assert.assertTrue(proposalLineItems.get(0) instanceof ProposalLineItem);
        }
    }

}
