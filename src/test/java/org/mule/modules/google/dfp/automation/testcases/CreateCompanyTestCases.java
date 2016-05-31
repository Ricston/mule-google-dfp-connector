/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.testcases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201602.Company;
import com.google.api.ads.dfp.axis.v201602.CompanyType;

public class CreateCompanyTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public CreateCompanyTestCases() {
        super(GoogleDfpConnector.class);
    }

    Company newCompany;

    @Before
    public void setup() throws Exception {
        newCompany = new Company();
        newCompany.setName("Ricston Ltd2");
        newCompany.setAddress("Mosta Malta");
        newCompany.setEmail("dfp@ricston.com");
        newCompany.setType(CompanyType.AGENCY);
    }

    @Test
    public void testCreateCompany() throws Throwable {
        Company comp = (Company) getDispatcher().runMethod("createCompany", new Object[] { newCompany
        });
        Assert.assertNotNull(comp);
        Assert.assertEquals(newCompany.getName(), comp.getName());
    }
}
