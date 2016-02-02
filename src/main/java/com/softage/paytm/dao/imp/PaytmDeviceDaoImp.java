package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmDeviceDao;
import com.softage.paytm.models.PaytmdeviceidinfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by SS0085 on 02-01-2016.
 */

@Repository
public class PaytmDeviceDaoImp implements PaytmDeviceDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public String saveDevice(PaytmdeviceidinfoEntity paytmdeviceidinfoEntity) {

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg = "0";

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(paytmdeviceidinfoEntity);
            transaction.commit();
            msg = "1";
        } catch (Exception e) {
            msg = "0";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return msg;
    }

    @Override
    public PaytmdeviceidinfoEntity getByloginId(String loginid) {
        return null;
    }
}
