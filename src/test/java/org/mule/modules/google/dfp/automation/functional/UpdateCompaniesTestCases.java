/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Company;

public class UpdateCompaniesTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public UpdateCompaniesTestCases() {
        super(GoogleDfpConnector.class);
    }

    Company companyToUpdate;
    String companyName;

    @Before
    public void setup() throws Exception {
        companyToUpdate = getConnector().getCompanyById(60288368L);
        companyName = companyToUpdate.getName();
        companyToUpdate.setName("TestingFormCompany");
    }

    @After
    public void tearDown() throws Exception {
        companyToUpdate = getConnector().getCompanyById(60288368L);
        companyToUpdate.setName(companyName);
        getConnector().updateCompany(companyToUpdate);
    }

    @Test
    public void testUpdateCompany() throws Exception {

        // Companys can not be deleted so the company with ID=59503568 always will exist.
        List<Company> list = new ArrayList<Company>();
        list.add(companyToUpdate);
        Company[] updatedCompanies = getConnector().updateCompanies(list);
        Assert.assertNotNull(updatedCompanies);
        Assert.assertEquals(updatedCompanies[0].getName(), "TestingFormCompany");
    }

    @Test
    public void testUpdateCompanyNotFound() throws Exception {
        try {
            companyToUpdate.setId(12345L);
            getConnector().updateCompanies(Arrays.asList(companyToUpdate));
        } catch (UpdateFailedException e) {
            Assert.assertTrue(e.getCause() instanceof ApiException);
        }

    }

}
