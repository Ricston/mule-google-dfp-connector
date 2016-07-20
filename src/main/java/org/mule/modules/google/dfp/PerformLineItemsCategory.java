/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.components.MetaDataCategory;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;

@MetaDataCategory
public class PerformLineItemsCategory {

    @Inject
    private GoogleDfpConnector connector;

    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetaDataKeys() throws Exception {
        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        keys.add(new DefaultMetaDataKey("Activate Line Items", "Activate Line Items"));
        keys.add(new DefaultMetaDataKey("Archive Line Items", "Archive Line Items"));
        keys.add(new DefaultMetaDataKey("Delete Line Items", "Delete Line Items"));
        keys.add(new DefaultMetaDataKey("Pause Line Items", "Pause Line Items"));
        keys.add(new DefaultMetaDataKey("Release Line Items", "Release Line Items"));
        keys.add(new DefaultMetaDataKey("Reserve Line Items", "Reserve Line Items"));
        keys.add(new DefaultMetaDataKey("Resume Line Items", "Resume Line Items"));
        keys.add(new DefaultMetaDataKey("Unarchive Line Items", "Unarchive Line Items"));

        return keys;
    }

    @MetaDataRetriever
    public MetaData getMetaData(MetaDataKey key) throws Exception {
        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();
        MetaDataModel model = builder.build();
        return new DefaultMetaData(model);
    }

    public GoogleDfpConnector getConnector() {
        return connector;
    }

    public void setConnector(GoogleDfpConnector connector) {
        this.connector = connector;
    }
}
