package com.basilio.flightsearch.dal;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/15/12
 * Time: 7:02 PM
 *
 * @param <T>,  type entity
 * @param <PK>, primary key
 * @CommitAfter annotation causes the HibernateSessionManager's commit() method to be executed before the method returns.
 */
public interface ServiceDAO {
    /**
     * Creates a new object for the given type. After a call to this method the entity will be
     * persisted into database and then refreshed. Also current persistent Session will be flushed.
     *
     * @param <T>
     * @param t
     * @return persisted Object
     */
    @CommitAfter
    <T> T create(T t);

    /**
     * Creates a list with the specified list of entities. will usually make use of the individual create method.
     * @param entities the list of entities
    public void create(List<?> entities); */

    /**
     * Updates the given object
     *
     * @param <T>
     * @param t
     * @return persisted object
     */
    @CommitAfter
    <T> T update(T t);

    /**
     * Deletes the given object by id
     *
     * @param <T>
     * @param <PK>
     * @param type , entity class type
     * @param id
     */
    @CommitAfter
    <T, PK extends Serializable> void delete(Class<T> type, PK id);

    /**
     * Finds an object by id
     *
     * @param <T>
     * @param <PK>
     * @param type
     * @param id
     * @return the object
     */
    <T, PK extends Serializable> T find(Class<T> type, PK id);

    /**
     * Finds a list of objects for the given query name
     *
     * @param <T>
     * @param queryName
     * @return returns a list of objects
     */
    <T> List<T> findWithNamedQuery(String queryName);

    /**
     * Find a query with parameters
     *
     * @param <T>
     * @param queryName
     * @param params
     * @return resulting list
     */
    <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params);

    /**
     * Returns one result, query without parameters
     *
     * @param <T>
     * @param queryName
     * @return T object
     */
    <T> T findUniqueWithNamedQuery(String queryName);

    /**
     * Returns just one result with a named query and parameters
     *
     * @param <T>
     * @param queryName
     * @param params
     * @return T object
     */
    <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params);
}
