/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class GetAllLineItemsPqlTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetAllLineItemsPqlTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetAllLineItemsPql() throws Exception {
        List<String[]> lineItems = getConnector().getAllLineItemsPql();
        // The size of the list must be greater than 1 for retrieving results, because at first position, we obtain the names of the columns (ie Id, lastModifiedDate...)
        Assert.assertTrue(lineItems.size() > 1);
    }

}
