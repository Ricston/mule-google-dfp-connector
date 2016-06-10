/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class GetAdvertiserByNameTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetAdvertiserByNameTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetAdvertiserByName() throws Exception {

        // Companys can not be deleted and type can not be updated,
        // so the company named "PROOF" always will exist as an Advertiser.
        String externalID = getConnector().getAdvertiserByName("PROOF");
        Assert.assertNotNull(externalID);
        Assert.assertEquals(externalID, "123456");
    }

    @Test
    public void testGetAdvertiserByNameNotFound() throws Exception {
        String externalID = getConnector().getAdvertiserByName("Invented");
        Assert.assertEquals(externalID, StringUtils.EMPTY);
    }

}
