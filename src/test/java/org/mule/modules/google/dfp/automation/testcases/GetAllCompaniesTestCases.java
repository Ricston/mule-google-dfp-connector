/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.testcases;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201602.Company;
import com.google.api.ads.dfp.axis.v201602.Date;
import com.google.api.ads.dfp.axis.v201602.DateTime;

public class GetAllCompaniesTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetAllCompaniesTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetAllCompanies() throws Throwable {
        DateTime lastModifiedTime = new DateTime(new Date(2015, 1, 1), 0, 0, 0, "1300");
        DateTime snapshotDate = new DateTime(new Date(2017, 1, 31), 0, 0, 0, "1300");

        Object companies = getDispatcher().runMethod("getAllCompanies", new Object[] {
                lastModifiedTime,
                snapshotDate
        });
        Assert.assertNotNull(companies);
        Assert.assertTrue(companies instanceof List);
        if (CollectionUtils.isNotEmpty((List<Company>) companies)) {
            Assert.assertTrue(((List<Company>) companies).get(0) instanceof Company);
        }
    }

}
