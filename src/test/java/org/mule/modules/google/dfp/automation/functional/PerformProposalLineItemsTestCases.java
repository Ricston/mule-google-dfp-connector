/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.PerformProposalLineItemsException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformProposalLineItemsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformProposalLineItemsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetProposalLineItems();
        queryParamsNotFound = TestDataBuilder.getGetProposalLineItemsNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performProposalLineItems("", queryParams, "Unarchive Proposal Line Items");
    }

    @Test
    @Ignore
    public void testPerformProposalLineItems() throws Exception {

        Integer proposalLineItemsPerformed = getConnector().performProposalLineItems("", queryParams, "Archive Proposal Line Items");
        Assert.assertTrue(proposalLineItemsPerformed > 0);
    }

    @Test
    @Ignore
    public void testPerformProposalLineItemsNotFound() throws Exception {
        try {
            getConnector().performProposalLineItems("id = :id", queryParamsNotFound, "Archive Proposal Line Items");
        } catch (PerformProposalLineItemsException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
