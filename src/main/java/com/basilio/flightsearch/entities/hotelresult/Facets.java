package com.basilio.flightsearch.entities.hotelresult;

import java.util.List;

public class Facets {
    private String key;
    private String subType;
    private String type;
    private List<Values> values;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Values> getValues() {
        return this.values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}
