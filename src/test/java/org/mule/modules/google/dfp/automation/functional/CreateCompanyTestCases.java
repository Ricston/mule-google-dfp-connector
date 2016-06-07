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

public class CreateCompanyTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateCompanyTestCases() {
        super(GoogleDfpConnector.class);
    }

    private Company newCompany;

    @Before
    public void setup() throws Exception {
        newCompany = TestDataBuilder.getCreateCompanyNewCompany();
    }

    @Test
    public void testCreateCompany() throws Exception {
        Company comp = getConnector().createCompany(newCompany);
        Assert.assertNotNull(comp);
        Assert.assertEquals(newCompany.getName(), comp.getName());
    }
}
