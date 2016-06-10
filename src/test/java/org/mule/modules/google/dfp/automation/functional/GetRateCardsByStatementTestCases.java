/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.DateTime;
import com.google.api.ads.dfp.axis.v201605.RateCard;

public class GetRateCardsByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetRateCardsByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    DateTime lastModifiedDate;
    DateTime snapshotDateTime;

    @Before
    public void setup() {
        lastModifiedDate = TestDataBuilder.getGetRateCardsByStatementLastModifiedDate();
        snapshotDateTime = TestDataBuilder.getGetRateCardsByStatementSnapshotDateTime();
    }

    @Test
    public void testGetRateCardsByStatementNoResults() throws Exception {
        List<RateCard> rateCards = getConnector().getRateCardsByLastModifiedDate(lastModifiedDate, snapshotDateTime);
        // Since there is not the possibility of creating Rate Cards in DFP Small Business, this list will be always empty
        Assert.assertTrue(rateCards.isEmpty());
    }
}
