/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.mule.transport.NullPayload;

import com.google.api.ads.dfp.axis.v201605.Company;

public class GetCompanyByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetCompanyByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetCompanyById() throws Throwable {

        Object company = getDispatcher().runMethod("getCompanyById", new Object[] {
                59518088L
        });
        Assert.assertNotNull(company);
        Assert.assertTrue(company instanceof Company);
    }

    @Test
    public void testGetCompanyByIdNotFound() throws Throwable {
        Object company = getDispatcher().runMethod("getCompanyById", new Object[] {
                22L
        });
        Assert.assertTrue(company instanceof NullPayload);
    }

}
