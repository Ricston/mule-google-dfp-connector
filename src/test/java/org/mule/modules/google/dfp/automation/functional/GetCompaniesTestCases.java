/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.Company;

public class GetCompaniesTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private Map<String, Object> queryParams;

    public GetCompaniesTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setup() {
        queryParams = TestDataBuilder.getGetCompanies();
    }

    @Test
    public void testGetCompanies() throws Exception {

        List<Company> companies = getConnector().getCompanies("type = :type AND id = :id", queryParams, "id ASC", 400, 0);

        Assert.assertNotNull(companies);
        if (CollectionUtils.isNotEmpty((List<Company>) companies)) {
            Assert.assertTrue(companies.get(0) instanceof Company);
        }
    }

}
