package com.cloudrain.derecho.ds.zk.discover;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Created by lugan on 12/23/2016.
 */

@JsonRootName("details")
public class InstanceDetails {
    private String description;

    public InstanceDetails(String description) {
        this.description = description;
    }

    public InstanceDetails() {
        this("");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
