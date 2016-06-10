/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Company;

public class UpdateCompanyTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateCompanyTestCases() {
        super(GoogleDfpConnector.class);
    }

    Company companyToUpdate;
    String externalID;

    @Before
    public void setupBefore() throws Exception {
        companyToUpdate = getConnector().getCompanyById(59503568L);
        externalID = companyToUpdate.getExternalId();
        companyToUpdate.setExternalId("123456789");
    }

    @After
    public void setupAfter() throws Exception {
        companyToUpdate = getConnector().getCompanyById(59503568L);
        companyToUpdate.setExternalId(externalID);
        getConnector().updateCompany(companyToUpdate);
    }

    @Test
    public void testUpdateCompany() throws Exception {

        // Companys can not be deleted so the company with ID=59503568 always will exist.
        Company updatedCompany = getConnector().updateCompany(companyToUpdate);
        Assert.assertNotNull(updatedCompany);
        Assert.assertEquals(updatedCompany.getExternalId(), "123456789");
    }

    @Test
    public void testUpdateCompanyNotFound() throws Exception {
        try {
            companyToUpdate.setId(12345L);
            getConnector().updateCompany(companyToUpdate);
        } catch (UpdateFailedException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }

    }

}
