package com.basilio.flightsearch.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * NamedQuery parameter helper to create the querys
 * 
 * @author Basilio
 */

public class QueryParameters
{

    private Map<String, Object> parameters = null;

    public static QueryParameters with(String name, Object value)
    {
        return new QueryParameters(name, value);
    }

    private QueryParameters(String name, Object value)
    {
        this.parameters = new HashMap<String, Object>();
        this.parameters.put(name, value);
    }

    public QueryParameters and(String name, Object value)
    {
        this.parameters.put(name, value);
        return this;
    }

    public Map<String, Object> parameters()
    {
        return this.parameters;
    }
}
