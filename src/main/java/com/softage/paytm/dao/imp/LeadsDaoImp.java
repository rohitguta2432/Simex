package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.LeadsDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 02-02-2016.
 */
@Repository
public class LeadsDaoImp implements LeadsDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<JSONObject> getAgentLeads(String agentCode,int timedeff,String cuurentDate) {

        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();


        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_AgentLeads(?,?)}");
            query1.setParameter(1, agentCode);
            query1.setParameter(2, cuurentDate);
            List<Object[]> list1 = query1.getResultList();
            for (Object[] s : list1) {
                JSONObject json = new JSONObject();
                if (s.length > 0) {

                    json.put("JobNumber", s[0]);
                    json.put("PhoneNumber", s[1]);
                    json.put("CustomerName", s[2]);
                    json.put("CustomerAddress", s[3]);
                    json.put("City", s[4]);
                    json.put("PinCode", s[5]);
                    json.put("AppointmentDate", s[6]);
                    json.put("AppointmentTime", s[7]);
                    json.put("simType", s[8]);
                    json.put("spoke", s[9]);

                }
                arrList.add(json);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return arrList;
    }

    @Override
    public String jobConfirmtocustomer(long appointmentId, String customerPhone, String agentCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject json = new JSONObject();
        String result = "";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_jobconfirmToCustomer(?,?,?)}");
            query1.setParameter(1, appointmentId);
            query1.setParameter(2, customerPhone);
            query1.setParameter(3, agentCode);
            result = (String) query1.getSingleResult();
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
    public List<JSONObject> agentAcceptedLeads(String agentCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_AgentAcceptedLeads(?)}");
            query1.setParameter(1, agentCode);
            List<Object[]> list1 = query1.getResultList();
            for (Object[] s : list1) {
                JSONObject json = new JSONObject();
                if (s.length > 0) {
                    json.put("JobNumber", s[0]);
                    json.put("PhoneNumber", s[1]);
                    json.put("CustomerName", s[2]);
                    json.put("CustomerAddress", s[3]);
                    json.put("City", s[4]);
                    json.put("PinCode", s[5]);
                    json.put("AppointmentDate", s[6]);
                    json.put("AppointmentTime", s[7]);
                }
                arrList.add(json);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return arrList;
    }

    @Override
    public List<JSONObject> agentRejectedLeads(String agentCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_AgentRejectedLeads(?)}");
            query1.setParameter(1, agentCode);
            List<Object[]> list1 = query1.getResultList();
            for (Object[] s : list1) {
                JSONObject json = new JSONObject();
                if (s.length > 0) {
                    json.put("JobNumber", s[0]);
                    json.put("PhoneNumber", s[1]);
                    json.put("CustomerName", s[2]);
                    json.put("CustomerAddress", s[3]);
                    json.put("City", s[4]);
                    json.put("PinCode", s[5]);
                    json.put("AppointmentDate", s[6]);
                    json.put("AppointmentTime", s[7]);
                }
                arrList.add(json);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return arrList;
    }

    @Override
    public List<JSONObject> kycDone(String agentCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_AgentKYCDoneLeads(?)}");
            query1.setParameter(1, agentCode);
            List<Object[]> list1 = query1.getResultList();
            for (Object[] s : list1) {
                JSONObject json = new JSONObject();
                if (s.length > 0) {
                    json.put("JobNumber", s[0]);
                    json.put("PhoneNumber", s[1]);
                    json.put("CustomerName", s[2]);
                    json.put("CustomerAddress", s[3]);
                    json.put("City", s[4]);
                    json.put("PinCode", s[5]);
                    json.put("AppointmentDate", s[6]);
                    json.put("AppointmentTime", s[7]);
                }
                arrList.add(json);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return arrList;
    }

    @Override
    public List<JSONObject> kycNotDone(String agentCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call sp_AgentKYCNotDoneLeads(?)}");
            query1.setParameter(1, agentCode);
            List<Object[]> list1 = query1.getResultList();
            for (Object[] s : list1) {
                JSONObject json = new JSONObject();
                if (s.length > 0) {
                    json.put("JobNumber", s[0]);
                    json.put("PhoneNumber", s[1]);
                    json.put("CustomerName", s[2]);
                    json.put("CustomerAddress", s[3]);
                    json.put("City", s[4]);
                    json.put("PinCode", s[5]);
                    json.put("AppointmentDate", s[6]);
                    json.put("AppointmentTime", s[7]);
                    json.put("RemarkText", s[8]);
                }
                arrList.add(json);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return arrList;
    }

    @Override
    public ProofMastEntity findBykey(String key) {
        EntityManager entityManager=null;
        Query query=null;
        ProofMastEntity proofMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select proofEntity from ProofMastEntity proofEntity where proofEntity.idCode=:idCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("idCode",key);
            proofMastEntity= (ProofMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return proofMastEntity;

    }

    @Override
    public ReasonMastEntity findByprimaryKey(String key) {
        EntityManager entityManager=null;
        Query query=null;
        ReasonMastEntity reasonMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select reasonEntity from ReasonMastEntity reasonEntity where reasonEntity.reasonCode=:reasonCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("reasonCode",key);
            reasonMastEntity= (ReasonMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return reasonMastEntity;

    }

    @Override
    public List<ReasonMastEntity> reasonList() {
        EntityManager entityManager = null;
        List<ReasonMastEntity> reasonMastEntities = null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select reason from ReasonMastEntity reason";
            query=entityManager.createQuery(strQuery);
            reasonMastEntities = query.getResultList();
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
        return  reasonMastEntities;
    }

    @Override
    public String getCustomerName(String mobileNo) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONArray array = new JSONArray();
        List<JSONObject> arrList = new ArrayList<>();
        String  name=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query1 = entityManager.createNativeQuery("{call usp_getRejectValidation(?)}");
            query1.setParameter(1, mobileNo);
            name = (String)query1.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return name;
    }


}
