package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by SS0085 on 30-12-2015.
 */

@Repository
public class AgentPaytmDaoImp implements AgentPaytmDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(paytmagententryEntity);
            transaction.commit();
            msg="done";
        } catch (Exception e) {
           msg="err";
            e.printStackTrace();
        }
     return  msg;
    }
}