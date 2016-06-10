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

import com.google.api.ads.dfp.axis.v201605.CustomTargetingKey;

public class GetCustomTargetingKeysByStatementTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public GetCustomTargetingKeysByStatementTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    public void testGetCustomTargetingKeysByStatement() throws Exception {
        List<CustomTargetingKey> customTargetingKeys = getConnector().getCustomTargetingKeysByStatement();
        Assert.assertTrue(!customTargetingKeys.isEmpty());
    }

}
