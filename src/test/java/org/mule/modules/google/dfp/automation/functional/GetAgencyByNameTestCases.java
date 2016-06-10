/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Company;

public class GetAgencyByNameTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetAgencyByNameTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetAgencyByName() throws Exception {

        // Companys can not be deleted and type can not be updated,
        // so the company named "MuleDemo" always will exist as a Agency.
        Company company = getConnector().getAgencyByName("MuleDemo");
        Assert.assertNotNull(company);
        Assert.assertEquals(company.getName(), "MuleDemo");
    }

    @Test
    public void testGetAgencyByNameNotFound() throws Exception {
        Company company = getConnector().getAgencyByName("Invented");
        Assert.assertNull(company);
    }

}
