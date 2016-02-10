package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmDeviceDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.models.PaytmdeviceidinfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        PaytmdeviceidinfoEntity paytmdeviceidinfoEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select paytmDevice from PaytmdeviceidinfoEntity paytmDevice where paytmDevice.loginId=:loginid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("loginid",loginid);
            paytmdeviceidinfoEntity= (PaytmdeviceidinfoEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return paytmdeviceidinfoEntity;
    }
}
