package com.basilio.flightsearch.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/16/12
 * Time: 3:10 AM
 * simple namedQuery parameter helper to create the querys
 */

public class QueryParameters {

    //First Parameter will be column name and second parameter will be the value.
    private Map<String, Object> parameters = null;

    public static QueryParameters with(String name, Object value) {
        return new QueryParameters(name, value);
    }

    private QueryParameters(String name, Object value) {
        this.parameters = new HashMap<String, Object>();
        this.parameters.put(name, value);
    }

    public QueryParameters and(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public Map<String, Object> parameters() {
        return this.parameters;
    }
}
