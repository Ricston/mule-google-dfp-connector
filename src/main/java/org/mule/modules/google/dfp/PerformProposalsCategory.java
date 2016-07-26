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
public class PerformProposalsCategory {

    @Inject
    private GoogleDfpConnector connector;

    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetaDataKeys() throws Exception {
        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        keys.add(new DefaultMetaDataKey("Archive Proposals", "Archive Proposals"));
        keys.add(new DefaultMetaDataKey("Bypass Proposal Workflow Rules", "Bypass Proposal Workflow Rules"));
        keys.add(new DefaultMetaDataKey("Cancel Retraction For Proposals", "Cancel Retraction For Proposals"));
        keys.add(new DefaultMetaDataKey("Retract Proposals", "Retract Proposals"));
        keys.add(new DefaultMetaDataKey("Submit Proposals For Approval", "Submit Proposals For Approval"));
        keys.add(new DefaultMetaDataKey("Submit Proposals For Approval Bypass Validation", "Submit Proposals For Approval Bypass Validation"));
        keys.add(new DefaultMetaDataKey("Submit Proposals For Archival", "Submit Proposals For Archival"));
        keys.add(new DefaultMetaDataKey("Unarchive Proposals", "Unarchive Proposals"));

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
