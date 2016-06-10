/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class GetProposalRetractionReasonPqlTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetProposalRetractionReasonPqlTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    @Ignore
    public void testGetProposalRetractionReasonPql() throws Exception {
        // XXX The table 'Proposal_Retraction_Reason' does not exists for non premium accounts so we can not consult this.
        // List<String[]> proposalsRetractionReasons = getConnector().getProposalRetractionReasonPql();
        //
        // Assert.assertTrue(proposalsRetractionReasons.size() == 0);
    }

}
