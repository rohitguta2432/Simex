package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public List getAgentDetails() {
        EntityManager entityManager = null;
        JSONObject jsonresponse = new JSONObject();
        ArrayList<JSONObject> listArray = new ArrayList<JSONObject>();
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
    @Transactional
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
    @Transactional
    public JSONObject GetAgentsCode(String allocatedTime,String agentPincode) {
        EntityManager entityManager = null;
        List agent = new ArrayList();
        List<PaytmagententryEntity> ListAgentcode = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject agentJson = new JSONObject();
        try {

            Date allocatedDate = dateFormat.parse(allocatedTime);
            String convertedAllocatedTime  = dateFormat1.format(allocatedDate);

            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call usp_getReasignAgents(?,?)}");
            query.setParameter(1, convertedAllocatedTime);
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
    @Transactional
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
    @Transactional
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

}
