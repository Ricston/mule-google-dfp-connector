/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mule.common.Result;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.google.dfp.GoogleDfpConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.mule.tools.devkit.ctf.junit.MetaDataTest;

public class PerformReconciliationOrderReportsCategoryMetaDataTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public PerformReconciliationOrderReportsCategoryMetaDataTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    @MetaDataTest
    public void testMetaDataKeys() throws Exception {

        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        Assert.assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey submit = null;
        MetaDataKey revert = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getId()
                    .equals("Submit Reconciliation Order Reports"))
                submit = key;
            if (key.getId()
                    .equals("Revert Reconciliation Order Reports"))
                revert = key;
        }

        // Assert on MetaDataKeys
        Assert.assertNotNull(submit);
        Assert.assertNotNull(revert);
    }

    @Test
    @MetaDataTest
    public void testMetaData() throws Exception {

        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        Assert.assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey submit = null;
        MetaDataKey revert = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getId()
                    .equals("Submit Reconciliation Order Reports"))
                submit = key;
            if (key.getId()
                    .equals("Revert Reconciliation Order Reports"))
                revert = key;
        }

        // Asserts on MetaData
        Result<MetaData> recipeKeyResult = getDispatcher().fetchMetaData(submit);
        Assert.assertTrue(Result.Status.SUCCESS.equals(recipeKeyResult.getStatus()));

        Result<MetaData> ingredientKeyResult = getDispatcher().fetchMetaData(revert);
        Assert.assertTrue(Result.Status.SUCCESS.equals(ingredientKeyResult.getStatus()));
    }
}
