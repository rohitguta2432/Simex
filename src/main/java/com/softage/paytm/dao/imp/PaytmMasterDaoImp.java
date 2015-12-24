package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.PaytmMastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by SS0085 on 22-12-2015.
 */
@Repository
public class PaytmMasterDaoImp implements PaytmMasterDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void savePaytmMaster(List<PaytmMastEntity> paytmMastEntity) {

        EntityManager entityManager=null;
        EntityTransaction transaction = null;

        try{
            entityManager = entityManagerFactory.createEntityManager();
            transaction =  entityManager.getTransaction();
            transaction.begin();
            for (PaytmMastEntity mastEntity :paytmMastEntity) {
                entityManager.persist(mastEntity);
                transaction.commit();
            }
        }catch (Exception e) {

           e.printStackTrace();
        }
    }

    @Override
    public PaytmMastEntity getPaytmMastData() {
        EntityManager entityManager = null;
        List<PaytmMastEntity> list = null;
        PaytmMastEntity paytmMastEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select paytmmast from PaytmMastEntity paytmmast";
            query=entityManager.createQuery(strQuery);
            list = query.getResultList();
            paytmMastEntity  =list.get(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  paytmMastEntity;
    }
}
