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
public class PerformOrdersCategory {

    @Inject
    private GoogleDfpConnector connector;

    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetaDataKeys() throws Exception {
        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        keys.add(new DefaultMetaDataKey("Approve Orders", "Approve Orders"));
        keys.add(new DefaultMetaDataKey("Approve Orders Without Reservation Changes", "Approve Orders Without Reservation Changes"));
        keys.add(new DefaultMetaDataKey("Archive Orders", "Archive Orders"));
        keys.add(new DefaultMetaDataKey("Delete Orders", "Delete Orders"));
        keys.add(new DefaultMetaDataKey("Disapprove Orders", "Disapprove Orders"));
        keys.add(new DefaultMetaDataKey("Disapprove Orders Without Reservation Changes", "Disapprove Orders Without Reservation Changes"));
        keys.add(new DefaultMetaDataKey("Pause Orders", "Pause Orders"));
        keys.add(new DefaultMetaDataKey("Resume Orders", "Resume Orders"));
        keys.add(new DefaultMetaDataKey("Retract Orders", "Retract Orders"));
        keys.add(new DefaultMetaDataKey("Retract Orders Without Reservation Changes", "Retract Orders Without Reservation Changes"));
        keys.add(new DefaultMetaDataKey("Submit Orders For Approval", "Submit Orders For Approval"));
        keys.add(new DefaultMetaDataKey("Submit Orders For Approval Without Reservation Changes", "Submit Orders For Approval Without Reservation Changes"));
        keys.add(new DefaultMetaDataKey("Unarchive Orders", "Unarchive Orders"));

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
