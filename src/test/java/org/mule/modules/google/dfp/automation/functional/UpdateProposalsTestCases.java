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

import com.google.api.ads.dfp.axis.v201605.Proposal;

public class UpdateProposalsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateProposalsTestCases() {
        super(GoogleDfpConnector.class);
    }

    Proposal proposalToUpdate;
    String proposalName;

    @Before
    public void setup() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // proposalToUpdate = getConnector().getProposalsById()
        // .get(0);
        // proposalName = proposalToUpdate.getName();
        // proposalToUpdate.setName("TestingFormProposal");
    }

    @After
    public void tearDown() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // proposalToUpdate = getConnector().getProposalsById()
        // .get(0);
        // proposalToUpdate.setName(proposalName);
        // getConnector().updateProposals(Arrays.asList(proposalToUpdate));
    }

    @Test
    @Ignore
    public void testUpdateProposals() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // List<Proposal> list = new ArrayList<Proposal>();
        // list.add(proposalToUpdate);
        // Proposal[] updatedProposals = getConnector().updateProposals(list);
        // Assert.assertNotNull(updatedProposals);
        // Assert.assertEquals(updatedProposals[0].getName(), "TestingFormProposal");
    }

    @Test
    @Ignore
    public void testUpdateProposalsNotFound() throws Exception {
        // XXX Proposals cannot be created without a premium account, so we cannot retrieve them
        // try {
        // proposalToUpdate.setId(12345L);
        // getConnector().updateProposals(Arrays.asList(proposalToUpdate));
        // } catch (UpdateFailedException e) {
        // Assert.assertTrue(e.getCause() instanceof ApiException);
        // }

    }

}
