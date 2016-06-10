/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Company;

public class GetCompaniesByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private List<Long> correctIds;
    private List<Long> wrongIds;

    public GetCompaniesByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {

        correctIds = TestDataBuilder.getGetCompaniesByIdCorrectIds();
        wrongIds = TestDataBuilder.getGetCompaniesByIdWrongIds();
    }

    @Test
    public void testGetCompaniesById() throws Exception {

        List<Company> companies = getConnector().getCompaniesById(correctIds);
        // Ensure that the size of the list of companies is equal than size of ids
        Assert.assertEquals(companies.size(), correctIds.size());
        Set<Long> ids = new HashSet<Long>(correctIds);
        // Ensure that all the companies in the list of companies are these than we are requesting for
        for (Company c : companies) {
            Assert.assertTrue(ids.contains(c.getId()));
        }
    }

    @Test
    public void testGetCompaniesByIdNotFound() throws Exception {

        List<Company> companies = getConnector().getCompaniesById(wrongIds);
        // Ensure that the size of the list of companies is not equal than size of ids
        Assert.assertFalse(companies.size() == wrongIds.size());
    }

}
