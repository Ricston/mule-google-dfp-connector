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

import com.google.api.ads.dfp.axis.v201605.Proposal;

public class CreateProposalsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateProposalsTestCases() {
        super(GoogleDfpConnector.class);
    }

    private Proposal newProposal;
    private Proposal createdProposal;

    @Before
    public void setup() throws Exception {
        newProposal = TestDataBuilder.getCreateProposalsNewProposal();
    }

    @Test
    public void testCreateLineItems() throws Exception {
        createdProposal = getConnector().createProposals(Arrays.asList(newProposal))[0];
        Assert.assertNotNull(createdProposal);
        Assert.assertEquals(newProposal.getName(), createdProposal.getName());
    }

    @After
    public void tearDown() throws Exception {
        createdProposal.setName(createdProposal.getName() + createdProposal.getId());
        getConnector().updateProposals(Arrays.asList(createdProposal));
    }
}
