/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.api.ads.dfp.axis.v201605.CustomField;

public class GetCustomFieldsByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    private static final Logger logger = Logger.getLogger(GetUsersByIdTestCases.class);

    public GetCustomFieldsByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetCustomFieldsByStatement() throws Exception {

        List<CustomField> listCustomFields;
        listCustomFields = getConnector().getCustomFieldsByStatement();

        Assert.assertNotNull(listCustomFields);
        if (!listCustomFields.isEmpty()) {
            Assert.assertNotNull(listCustomFields.get(0));
            Assert.assertNotNull(listCustomFields.get(0)
                    .getId());
        } else {
            logger.warn("'getCustomFieldsByStatement()' operation returned an empty list, dataset for testing could be incorrect or outdated. ");
        }
    }
}
