package com.basilio.flightsearch.dal;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/15/12
 * Time: 7:21 PM
 *
 * Hibernate Create, Read, Update, and Delete service data access interface
 * Many of the methods have @SuppressWarnings("unchecked") because Eclipse will complain about illegal behavior, however we know it will be legal.
 *
 *  <T>,  type entity
 *  <PK>, primary key
 */
public class HibernateServiceDAO implements ServiceDAO {

    private static final Logger logger = LoggerFactory.getLogger(HibernateServiceDAO.class);

    @Inject
    private Session session;

    public <T> T create(T t) {
        String createString = "Creating " + t.toString();
        logger.info(createString);
        System.out.println(createString);
        session.persist(t);
        session.flush();
        session.refresh(t);
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T, PK extends Serializable> T find(Class<T> type, PK id) {
        return (T) session.get(type, id);
    }

    public <T> T update(T type) {
        logger.info("Updating " + type.toString());
        session.merge(type);
        return type;
    }

    public <T, PK extends Serializable> void delete(Class<T> type, PK id) {
        logger.info("Deleting " + type.toString() + " with id " + id.toString());
        @SuppressWarnings("unchecked")
        T ref = (T) session.get(type, id);
        session.delete(ref);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName) {
        return session.getNamedQuery(queryName).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params) {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = session.getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName) {
        System.out.println(queryName);
        T t = (T) session.getNamedQuery(queryName).uniqueResult();
        if (t == null) {
            logger.error("There was no Object using "+queryName);
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params) {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = session.getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return (T) query.uniqueResult();
    }
}
