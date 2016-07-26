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
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.datatype.DataType;

@MetaDataCategory
public class PerformProposalLineItemsCategory {

    @Inject
    private GoogleDfpConnector connector;

    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetaDataKeys() throws Exception {
        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        keys.add(new DefaultMetaDataKey("Actualize Proposal Line Items", "Actualize Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Archive Proposal Line Items", "Archive Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Pause Proposal Line Items", "Pause Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Release Proposal Line Items", "Release Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Resume Proposal Line Items", "Resume Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Unarchive Proposal Line Items", "Unarchive Proposal Line Items"));
        keys.add(new DefaultMetaDataKey("Unlink Proposal Line Items", "Unlink Proposal Line Items"));

        return keys;
    }

    @MetaDataRetriever
    public MetaData getMetaData(MetaDataKey key) throws Exception {
        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();
        DynamicObjectBuilder<?> dynamicObject = builder.createDynamicObject("dynamicobject");
        dynamicObject.addSimpleField(key.getId(), DataType.STRING);
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
