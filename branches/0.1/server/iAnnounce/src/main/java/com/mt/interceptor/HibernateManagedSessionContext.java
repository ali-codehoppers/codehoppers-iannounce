/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.log4j.Logger;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.ManagedSessionContext;

/**
 *
 * @author Administrator
 */
public class HibernateManagedSessionContext extends AbstractInterceptor {

    private static final Logger logger = Logger.getLogger(HibernateManagedSessionContext.class);
    private SessionFactory sessionFactory;

    public HibernateManagedSessionContext() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String intercept(ActionInvocation invocation)
            throws Exception {
        try {
            openAndBeginTx();
            String result = invocation.invoke();
            commitAndClose();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            rollbackAndClose();
            throw ex;
        }
    }

    private void openAndBeginTx() {
        Session session = sessionFactory.openSession();
        ManagedSessionContext.bind(session);
        session.beginTransaction();
    }

    private void commitAndClose() throws Exception {
        Session session;
        session = ManagedSessionContext.unbind(sessionFactory);
        if (session == null) {
            logger.error("Current hibernate session is already invalidated");
        } else {
            try {
                Transaction transaction = session.getTransaction();
                if (transaction != null && transaction.isActive()) {
                    try {
                        transaction.commit();
                    } catch (RuntimeException x) {
                        transaction.rollback();
                        throw x;
                    }
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
    }

    private void rollbackAndClose() throws Exception {
        Session session;
        session = ManagedSessionContext.unbind(sessionFactory);
        if (session == null) {
            logger.error("Current hibernate session is already invalidated");
        } else {
            try {
                Transaction transaction = session.getTransaction();
                if (transaction != null && transaction.isActive()) {
                    try {
                        transaction.rollback();
                    } catch (RuntimeException x) {
                        throw x;
                    }
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
    }
}
