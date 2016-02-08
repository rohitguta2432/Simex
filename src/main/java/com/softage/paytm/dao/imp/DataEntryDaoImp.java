package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.DataEntryDao;
import com.softage.paytm.models.DataentryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(dataentryEntity);
            transaction.commit();
            msg="done";
        } catch (Exception e) {
            msg="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return  msg;
    }
}
