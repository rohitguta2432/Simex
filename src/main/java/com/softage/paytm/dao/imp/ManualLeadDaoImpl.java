package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
@Repository
public class ManualLeadDaoImpl implements ManualLeadDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List getAgentDetails() {
        EntityManager entityManager = null;
        List<Object[]> result = null;
        try {

            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call sp_ReAssignLeads()}");
            result = query.getResultList();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return result;
    }

    @Override
    public String getAgentPincode(String agentCode) {

        EntityManager entityManager = null;
        String message = "";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call sp_getAgentPincode(?)}");
            query.setParameter(1, agentCode);
            message = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return message;
    }

    @Override
    public List getAllocateDetails(int custId) {

        EntityManager entityManager = null;
        List<Object[]> result = null;
        try {

            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call sp_getAllocateDetails(?)}");
            query.setParameter(1, custId);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return result;
    }

    @Override
    public JSONObject GetAgentsCode(String allocatedTime,String agentPincode) {
        EntityManager entityManager = null;
        List agent = new ArrayList();
        List<PaytmagententryEntity> ListAgentcode = null;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject agentJson = new JSONObject();
        try {

            //Date allocatedDate = dateFormat.parse(allocatedTime);
            //String convertedAllocatedTime  = dateFormat1.format(allocatedDate);

            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call usp_getReasignAgents(?,?)}");
            query.setParameter(1, allocatedTime);
            query.setParameter(2, agentPincode);
            List<String> agentCodes = query.getResultList();

            agentJson.put("agentCodes",agentCodes);
            //System.out.println(agentJson);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return agentJson;
    }

    @Override
    public String updateAgentsByCustUid(int CustomerId, String agentCode, String lastAgent,String userId, String newAllocationDateTime) {
        EntityManager entityManager = null;
        String message = "";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call sp_ManualLeadAssign(?,?,?,?,?)}");
            query.setParameter(1, CustomerId);
            query.setParameter(2, agentCode);
            query.setParameter(3, lastAgent);
            query.setParameter(4, userId);
            query.setParameter(5, newAllocationDateTime);
            message = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return message;
    }

    @Override
    public String deAllocateLead(int custId){

        EntityManager entityManager = null;
        String result = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            Query query =  entityManager.createNativeQuery("{ call usp_deAllocateLead(?)}");
            query.setParameter(1,custId);
            result = (String) query.getSingleResult();
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }


    @Override
    @Transactional
    public PaytmagententryEntity findByPrimaryKey(String agentCode) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        PaytmagententryEntity paytmagententryEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select paytmAgent from PaytmagententryEntity paytmAgent where paytmAgent.acode=:agentCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("agentCode",agentCode);
            paytmagententryEntity= (PaytmagententryEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return paytmagententryEntity;

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

    @Override
    public ReceiverMastEntity getRecivedByCode(int code) {
        EntityManager entityManager=null;
        Query query=null;
        ReceiverMastEntity receiverMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from ReceiverMastEntity am where am.receiverCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            receiverMastEntity= (ReceiverMastEntity)query.getSingleResult();
            entityTransaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return receiverMastEntity;
    }

    @Override
    public ProcessMastEntity getProcessByCode(int code) {
        EntityManager entityManager=null;
        Query query=null;
        ProcessMastEntity processMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from ProcessMastEntity am where am.processCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            processMastEntity= (ProcessMastEntity)query.getSingleResult();
            entityTransaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return processMastEntity;
    }

    @Override
    public String saveSmsSendEntity(SmsSendlogEntity smsSendlogEntity) {

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(smsSendlogEntity);
            entityManager.flush();
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

    @Override
    public String saveTabNotification(TblNotificationLogEntity tblNotificationLogEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(tblNotificationLogEntity);
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
