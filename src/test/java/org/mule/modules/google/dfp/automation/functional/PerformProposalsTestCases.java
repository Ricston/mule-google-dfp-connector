/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.PerformProposalsException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;

public class PerformProposalsTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;
    private Map<String, Object> queryParamsNotFound;

    public PerformProposalsTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() throws Exception {
        queryParams = TestDataBuilder.getGetProposals();
        queryParamsNotFound = TestDataBuilder.getGetProposalsNotFound();
    }

    @After
    public void tearDown() throws Exception {
        getConnector().performProposals("", queryParams, "Unarchive Proposals");
    }

    @Test
    public void testPerformProposals() throws Exception {

        Integer proposalsPerformed = getConnector().performProposals("", queryParams, "Archive Proposals");
        Assert.assertTrue(proposalsPerformed > 0);
    }

    @Test
    public void testPerformProposalsNotFound() throws Exception {
        try {
            getConnector().performProposals("id = :id", queryParamsNotFound, "Archive Proposals");
        } catch (PerformProposalsException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }
    }

}
