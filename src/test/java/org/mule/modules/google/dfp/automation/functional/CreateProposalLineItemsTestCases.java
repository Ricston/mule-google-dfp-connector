/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;

public class CreateProposalLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateProposalLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    private ProposalLineItem newProposalLineItem;
    private ProposalLineItem createdProposalLineItem;

    @Before
    public void setup() throws Exception {
        newProposalLineItem = TestDataBuilder.getCreateProposalLineItemsNewProposalLineItem();
    }

    @Test
    @Ignore
    public void testCreateLineItems() throws Exception {
        createdProposalLineItem = getConnector().createProposalLineItems(Arrays.asList(newProposalLineItem))[0];
        Assert.assertNotNull(createdProposalLineItem);
        Assert.assertEquals(newProposalLineItem.getName(), createdProposalLineItem.getName());
    }

    @After
    public void tearDown() throws Exception {
        createdProposalLineItem.setName(createdProposalLineItem.getName() + createdProposalLineItem.getId());
        getConnector().updateProposalLineItems(Arrays.asList(createdProposalLineItem));
    }
}
