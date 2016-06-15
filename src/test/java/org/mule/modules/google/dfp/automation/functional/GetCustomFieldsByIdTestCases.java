/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.CustomField;

public class GetCustomFieldsByIdTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetCustomFieldsByIdTestCases.class);

    private List<Long> listIds;

    public GetCustomFieldsByIdTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Before
    public void setUp() throws Exception {

        try {
            List<CustomField> listCustomFields;
            listCustomFields = getConnector().getCustomFieldsByStatement();
            Long id = listCustomFields.get(0)
                    .getId();

            listIds = new ArrayList<Long>();
            listIds.add(id);
        } catch (Exception e) {
            logger.error("Failed to get a valid 'Id' to test 'getCustomFieldsById()' operation. ");
            throw e;
        }
    }

    @Test
    public void testGetCustomFieldsById() throws Exception {

        List<CustomField> listCustomFields = getConnector().getCustomFieldsById(listIds);

        Assert.assertNotNull(listCustomFields);
        if (!listCustomFields.isEmpty()) {
            Assert.assertEquals(listIds.get(0), listCustomFields.get(0)
                    .getId());
        } else {
            Assert.assertTrue(listIds.isEmpty());
        }

        // List<CustomField> listCustomFieldsTMP;
        // listCustomFieldsTMP = getConnector().getCustomFieldsByStatement();
        // CustomField customField_1 = listCustomFieldsTMP.get(1);
        //
        // CustomFieldOption customFieldOption = getConnector().getCustomFieldOption(36008L);
        //
        // Assert.assertNotNull(customFieldOption);
    }
}
