package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;

/**
 * Created by SS0085 on 22-12-2015.
 */
@Repository
public class PaytmMasterDaoImp implements PaytmMasterDao {
    private static final Logger logger = LoggerFactory.getLogger(PaytmMasterDaoImp.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String savePaytmMaster(List<PaytmMastEntity> paytmMastEntity) {

        EntityManager entityManager=null;
        EntityTransaction transaction = null;
        String result=null;

        try{
            entityManager = entityManagerFactory.createEntityManager();
            transaction =  entityManager.getTransaction();
            transaction.begin();
            for (PaytmMastEntity mastEntity :paytmMastEntity) {
                entityManager.persist(mastEntity);
            }
            transaction.commit();
            result="success";
        }catch (Exception e) {

           e.printStackTrace();
            result="error";
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return  result;
    }

    @Override
    public JSONObject getPaytmMastData(String mobileNo) {
        EntityManager entityManager = null;
        List<PaytmMastEntity> list = null;
        PaytmMastEntity paytmMastEntity=null;
        JSONObject json=new JSONObject();
        Query query=null;

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select new map (pm.addressStreet1 as address1,pm.addressStreet2 as address2,pm.city as city,pm.pincode as pincode,pm.email as email) from PaytmMastEntity pm  where pm.customerPhone=:mobileNo";
            query=entityManager.createQuery(strQuery);
            query.setParameter("mobileNo",mobileNo);
            HashMap<String,Object> map=(HashMap<String,Object>)query.getSingleResult();
            json.put("address1",map.get("address1").toString().replace("#",""));
            json.put("address2",map.get("address2").toString().replace("#",""));
            json.put("city",map.get("city"));
            json.put("pincode",map.get("pincode"));
            json.put("email",map.get("email"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return  json;
    }

    @Override
    public JSONObject telecallingScreen(String username,int cirCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject json=new JSONObject();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_GetTeleData");
            Query query1= entityManager.createNativeQuery("{call sp_GetTeleDataFinal1(?)}");
            query1.setParameter(1,username);
            Object[] s = (Object[])query1.getSingleResult();
            if (s.length>0) {
                json.put("mobileNo", s[0]);
                json.put("customerName", s[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return json;
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
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
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
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return  listStatus;
    }
}
