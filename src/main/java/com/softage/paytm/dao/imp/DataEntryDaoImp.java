package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.DataEntryDao;
import com.softage.paytm.models.DataentryEntity;
import com.softage.paytm.models.PaytmMastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Created by SS0085 on 03-02-2016.
 */
@Repository
public class DataEntryDaoImp implements DataEntryDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String saveDataEntry(DataentryEntity dataentryEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(dataentryEntity);
            transaction.commit();
            msg = "done";
        } catch (Exception e) {
            msg = "err";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return msg;
    }

    @Override
    public DataentryEntity getuserById(int cust_uid) {
        EntityManager entityManager = null;
        DataentryEntity dataentryEntity = null;
        Query query = null;
        try {

            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "from DataentryEntity where customerId=:cust_uid";
            query = entityManager.createQuery(strQuery);
            query.setParameter("cust_uid", cust_uid);
            dataentryEntity = (DataentryEntity) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return dataentryEntity;
    }

    @Override
    public String deleteExistEntry(int cust_uid) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String result="";
        try{
            entityManager=entityManagerFactory.createEntityManager();

            Query query=entityManager.createNativeQuery("{call usp_deleteAcceptedEntry(?)}");
            query.setParameter(1,cust_uid);
            result=(String)query.getSingleResult();

        }catch (Exception e){
            result="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();

            }
        }



        return result;
    }
}
