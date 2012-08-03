package com.basilio.flightsearch.dal.persistence;

import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/14/12
 * Time: 10:50 AM
 * This initializes Hibernate as the main Object Relational Manager Service Data Access Object.
 * On services/AppModule.Java we tell Tapestry this is a SubModule, so it will get called at the start.
 */

public class HibernateModule {
    public static void bind(ServiceBinder binder) {
        binder.bind(ServiceDAO.class, HibernateServiceDAO.class);
    }

    public static void contributeBeanValidatorSource(
            OrderedConfiguration<BeanValidatorConfigurer> configuration) {
        configuration.add("AppConfigurer", new BeanValidatorConfigurer() {
            public void configure(javax.validation.Configuration<?> configuration) {
                configuration.ignoreXmlConfiguration();
            }
        });
    }

    //HibernateTransactionAdviser to add transaction advice
    @Match("*DAO")
    public static void adviseTransactions(HibernateTransactionAdvisor advisor,
                                          MethodAdviceReceiver receiver) {
        advisor.addTransactionCommitAdvice(receiver);
    }
}
