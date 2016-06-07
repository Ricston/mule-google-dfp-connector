/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Company;

public class GetCompanyByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Long correctId;
    private Long incorrectId;

    public GetCompanyByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {

        correctId = TestDataBuilder.getGetCompanyByIdCorrectId();
        incorrectId = TestDataBuilder.getGetCompanyByIdIncorrectId();
    }

    @Test
    public void testGetCompanyById() throws Throwable {

        Object company = getConnector().getCompanyById(correctId);
        Assert.assertNotNull(company);
        Assert.assertTrue(company instanceof Company);
        Assert.assertEquals(correctId, ((Company) company).getId());
    }

    @Test
    public void testGetCompanyByIdNotFound() throws Throwable {

        Object company = getConnector().getCompanyById(incorrectId);
        Assert.assertNull(company);
    }

}
