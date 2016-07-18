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

import com.google.api.ads.dfp.axis.v201605.ProposalLineItem;

public class UpdateProposalLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateProposalLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    ProposalLineItem proposalLineItemToUpdate;
    String proposalLineItemName;

    @Before
    public void setup() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // proposalLineItemToUpdate = getConnector().getProposalLineItemsById()
        // .get(0);
        // proposalLineItemName = proposalLineItemToUpdate.getName();
        // proposalLineItemToUpdate.setName("TestingFormProposalLineItem");
    }

    @After
    public void tearDown() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // proposalLineItemToUpdate = getConnector().getProposalLineItemsById()
        // .get(0);
        // proposalLineItemToUpdate.setName(proposalLineItemName);
        // getConnector().updateProposalLineItems(Arrays.asList(proposalLineItemToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateProposalLineItems() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // List<ProposalLineItem> list = new ArrayList<ProposalLineItem>();
        // list.add(proposalLineItemToUpdate);
        // ProposalLineItem[] updatedProposalLineItems = getConnector().updateProposalLineItems(list);
        // Assert.assertNotNull(updatedProposalLineItems);
        // Assert.assertEquals(updatedProposalLineItems[0].getName(), "TestingFormProposalLineItem");
    }

    @Test
    @Ignore
    public void testUpdateProposalLineItemsNotFound() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // try {
        // proposalLineItemToUpdate.setId(12345L);
        // getConnector().updateProposalLineItems(Arrays.asList(proposalLineItemToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
