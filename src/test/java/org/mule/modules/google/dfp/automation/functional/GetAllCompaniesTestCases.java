/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Company;
import com.google.api.ads.dfp.axis.v201605.DateTime;

public class GetAllCompaniesTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private DateTime lastModifiedTime;
    private DateTime snapshotDate;

    public GetAllCompaniesTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {

        lastModifiedTime = TestDataBuilder.getGetAllCompaniesLastModifiedDate();
        snapshotDate = TestDataBuilder.getGetAllCompaniesSnapshotDateTime();
    }

    @Test
    public void testGetAllCompanies() throws Exception {

        List<Company> companies = getConnector().getAllCompanies(lastModifiedTime, snapshotDate);

        Assert.assertNotNull(companies);
        if (CollectionUtils.isNotEmpty((List<Company>) companies)) {
            Assert.assertTrue(companies.get(0) instanceof Company);
        }
    }

}
