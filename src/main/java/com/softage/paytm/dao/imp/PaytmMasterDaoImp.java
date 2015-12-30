package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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
            }
            transaction.commit();
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

    @Override
    public List telecallingScreen(String username) {
        EntityManager entityManager = null;
        List list = null;
        Query query=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_GetTeleData");
            Query query1= entityManager.createNativeQuery("{call sp_GetTeleData(?)}").setParameter(1, username);
            list= query1.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<StateMasterEntity> getStatemaster() {
        EntityManager entityManager = null;
        List<StateMasterEntity> listState = null;
        StateMasterEntity stateMasterEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select statemaster from StateMasterEntity statemaster";
            query=entityManager.createQuery(strQuery);
            listState = query.getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  listState;
    }

    @Override
    public List<CallStatusMasterEntity> getStatusList() {
        List<CallStatusMasterEntity> listStatus = null;
        EntityManager entityManager = null;
        CallStatusMasterEntity statusMasterEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select statusMaster from CallStatusMasterEntity statusMaster";
            query=entityManager.createQuery(strQuery);
            listStatus = query.getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  listStatus;
    }
}
