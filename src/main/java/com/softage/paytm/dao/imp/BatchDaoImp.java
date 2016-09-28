package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.BatchDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 29-08-2016.
 */
@Repository
public class BatchDaoImp implements BatchDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public JSONObject saveBatch(int inwordfrom, int inwordto, int totaldoc, int circle, String createdby) {
        JSONObject jsonObject = new JSONObject();
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String result = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_insertbatch(?,?,?,?,?)}");
            query.setParameter(1, inwordfrom);
            query.setParameter(2, inwordto);
            query.setParameter(3, totaldoc);
            query.setParameter(4, circle);
            query.setParameter(5, createdby);
      //      query.executeUpdate();
             result = (String) query.getSingleResult();
            transaction.commit();
       //     jsonObject.put("batch", result);
        } catch (Exception e) {
             result = "error";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        jsonObject.put("status", result);
        return jsonObject;
    }

    @Override
    public String getinwardfrom(int circle_code) {
        EntityManager entityManager = null;
        String inwardfrom = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query1 = entityManager.createNativeQuery("{call usp_getinwardfrom(?)}");
            query1.setParameter(1, circle_code);
            Integer inwardfrom1 = (Integer) query1.getSingleResult();
            inwardfrom1 = inwardfrom1 + 1;
            inwardfrom = inwardfrom1.toString();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return inwardfrom;
    }

    @Override
    public JSONObject getBatchDetails(int circlecode) {
        EntityManager entityManager = null;
        String inwardfrom = null;
        EntityTransaction transaction = null;
        JSONObject jsonObject = new JSONObject();
        int batchno = 0;
        int uidno = 0;
        int boxn0=0;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query1 = entityManager.createNativeQuery("{call usp_getuiddetails(?)}");
            query1.setParameter(1, circlecode);
            Object[] details = (Object[]) query1.getSingleResult();
            transaction.commit();
            batchno = (Integer) details[0];
            uidno = (Integer) details[1];
            boxn0 = (Integer) details[2];
            jsonObject.put("batchNumber", batchno);
            jsonObject.put("uidnumber", uidno);
            jsonObject.put("boxno",boxn0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public String createindexing(int batchno, int uid, int circlecode, String createdby) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String result = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_insertuid(?,?,?,?)}");
            query.setParameter(1, batchno);
            query.setParameter(2, uid);
            query.setParameter(3, circlecode);
            query.setParameter(4, createdby);
            //        query.executeUpdate();
            result = (String) query.getSingleResult();
            transaction.commit();
            //     jsonObject.put("batch", result);
        } catch (Exception e) {
            // result = "error";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
      return result;
    }

    @Override
    public JSONObject getuserDetails(String mobileno) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String inwardfrom = null;
        JSONObject jsonObject = new JSONObject();
        String customerid = null;
        String name = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query1 = entityManager.createNativeQuery("{call usp_getuserid(?)}");
            query1.setParameter(1, mobileno);
            Object[] userdetails = (Object[]) query1.getSingleResult();
            transaction.commit();
            customerid = (String) userdetails[0];
            name = (String) userdetails[1];
            jsonObject.put("customerid", customerid);
            jsonObject.put("name", name);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateBatch(String mobileno, String status, int batchNo, int uid, String name, String customerId, String remark, String createdby) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String inwardfrom = null;
        JSONObject jsonObject = new JSONObject();
        String customerid = null;
        String result = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query1 = entityManager.createNativeQuery("{call usp_updateuiddetail(?,?,?,?,?,?,?,?)}");
            query1.setParameter(1, mobileno);
            query1.setParameter(2, status);
            query1.setParameter(3, batchNo);
            query1.setParameter(4, uid);
            query1.setParameter(5, name);
            query1.setParameter(6, customerId);
            query1.setParameter(7, remark);
            query1.setParameter(8, createdby);
            result= (String) query1.getSingleResult();
            transaction.commit();



        } catch (Exception e) {
            e.printStackTrace();
            result="error";
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        jsonObject.put("status", result);
        return jsonObject;
    }

    @Override
    public JSONObject searchindexng(String mobileno, int batchNo, int uid,int circlecode) {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;
        EntityTransaction transaction = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_searchbatch(?,?,?,?)}");
            query.setParameter(1, mobileno);
            query.setParameter(2, batchNo);
            query.setParameter(3, uid);
            query.setParameter(4,circlecode);
            List<Object[]> resultList = query.getResultList();
            int i=0;
            for (Object[] object : resultList) {
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("mobileno1",object[0]);
                jsonObject1.put("name", object[1]);
                jsonObject1.put("batchno1",object[2]);
                jsonObject1.put("uidno", object[3]);
                jsonObject1.put("boxno",object[4]);
                jsonObject.put("indexList"+i,jsonObject1);
                i++;
            }

            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            {
                if(entityManager!=null && entityManager.isOpen()){
                    entityManager.close();
                }
            }
        }
        return jsonObject;
    }

}
