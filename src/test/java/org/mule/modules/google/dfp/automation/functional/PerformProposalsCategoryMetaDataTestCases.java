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

public class PerformProposalsCategoryMetaDataTestCases extends AbstractTestCase<GoogleDfpConnector> {

    public PerformProposalsCategoryMetaDataTestCases() {
        super(GoogleDfpConnector.class);
    }

    @Test
    @MetaDataTest
    public void testMetaDataKeys() throws Exception {

        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        Assert.assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey archive = null;
        MetaDataKey unarchive = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getId()
                    .equals("Archive Proposals"))
                archive = key;
            if (key.getId()
                    .equals("Unarchive Proposals"))
                unarchive = key;
        }

        // Assert on MetaDataKeys
        Assert.assertNotNull(archive);
        Assert.assertNotNull(unarchive);
    }

    @Test
    @MetaDataTest
    public void testMetaData() throws Exception {

        Result<List<MetaDataKey>> metaDataKeysResult = getDispatcher().fetchMetaDataKeys();
        Assert.assertTrue(Result.Status.SUCCESS.equals(metaDataKeysResult.getStatus()));

        List<MetaDataKey> metaDataKeys = metaDataKeysResult.get();

        MetaDataKey archive = null;
        MetaDataKey unarchive = null;

        for (MetaDataKey key : metaDataKeys) {
            if (key.getId()
                    .equals("Archive Proposals"))
                archive = key;
            if (key.getId()
                    .equals("Unarchive Proposals"))
                unarchive = key;
        }

        // Asserts on MetaData
        Result<MetaData> recipeKeyResult = getDispatcher().fetchMetaData(archive);
        Assert.assertTrue(Result.Status.SUCCESS.equals(recipeKeyResult.getStatus()));

        Result<MetaData> ingredientKeyResult = getDispatcher().fetchMetaData(unarchive);
        Assert.assertTrue(Result.Status.SUCCESS.equals(ingredientKeyResult.getStatus()));
    }
}
