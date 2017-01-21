package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AoAuditDao;
import com.softage.paytm.models.AoAuditEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by SS0090 on 1/12/2017.
 */
@Repository
public class AoAuditDaoImp implements AoAuditDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public JSONObject getAoAuditDetails(String spoke,String empcode) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        JSONObject jsonObject = new JSONObject();
        String customerNumber = null;
        Integer scanid = null;
        String simNo = null;
        String imgPath = null;
        String name = null;
        String address = null;
        Integer cust_uid = null;
        Integer imageCount = null;
        Integer actualImageCount = 0;
        String retStatus = "pending";
        String circleRemarks=null;
        while (retStatus.equalsIgnoreCase("pending")){
            try {
                entityManager = entityManagerFactory.createEntityManager();
                transaction = entityManager.getTransaction();
                Query query = entityManager.createNativeQuery("{call usp_getAoAuditDetails(?,?)}");
                query.setParameter(1, spoke);
                query.setParameter(2, empcode);
                Object[] auditedObj = (Object[]) query.getSingleResult();
                String returnedResult = (String) auditedObj[0];
                if (returnedResult.equalsIgnoreCase("unavailable")) {
                    retStatus="unavailable";
                    jsonObject.put("status", "Unavailable");

                }
                else if(returnedResult.equalsIgnoreCase("pending")){
                    jsonObject.put("status","Unavailable");
                    retStatus="pending";

                }
                else {

                    customerNumber = (String) auditedObj[1];
                    scanid = (Integer) auditedObj[2];
                    simNo = (String) auditedObj[3];
                    imgPath = (String) auditedObj[4];
                    name = (String) auditedObj[5];
                    address = (String) auditedObj[6];
                    cust_uid = (Integer) auditedObj[7];
                    imageCount = (Integer) auditedObj[8];
                    actualImageCount = ((BigInteger) auditedObj[9]).intValue();
                    //circleRemarks=(String)auditedObj[10];
                    jsonObject.put("mobile", customerNumber);
                    jsonObject.put("scanID", scanid);
                    jsonObject.put("simNo", simNo);
                    jsonObject.put("name", name);
                    jsonObject.put("address", address);
                    jsonObject.put("imagePath", imgPath);
                    jsonObject.put("custUID", cust_uid);
                    jsonObject.put("imgCount", imageCount);
                    jsonObject.put("status", "Available");
                    jsonObject.put("actualCount", actualImageCount);
                  //  jsonObject.put("circleRemarks",circleRemarks);
                    retStatus="available";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                }
            }
    }
        return jsonObject;
    }

    @Override
    public String saveAoAuditEntity(AoAuditEntity aoAuditEntity) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String result=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(aoAuditEntity);
            transaction.commit();
            result="saved";
        }catch (Exception e){
            transaction.rollback();
            result="error";
            e.printStackTrace();
        }finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }

    @Override
    public JSONObject getFormRecievingDetails(String mobileNumber,String spokecode) {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            Query query=entityManager.createNativeQuery("{call usp_getFormRecievingDetails(?,?)}");
            query.setParameter(1,mobileNumber);
            query.setParameter(2,spokecode);
            Object[] formRecievingDetails=(Object[])query.getSingleResult();
            String returnedVal=(String)formRecievingDetails[0];
            if (returnedVal.equalsIgnoreCase("available")) {
                String simnumber = (String) formRecievingDetails[1];
                String address = (String) formRecievingDetails[2];
                String name = (String) formRecievingDetails[3];
                Integer status = (Integer) formRecievingDetails[4];
                Integer scanID = ((BigInteger) formRecievingDetails[5]).intValue();
                String circleRemarks=(String)formRecievingDetails[6];
                Integer cirStatus=(Integer)formRecievingDetails[7];
                jsonObject.put("returned","available");
                jsonObject.put("simNo", simnumber);
                jsonObject.put("address", address);
                jsonObject.put("user_name", name);
                jsonObject.put("status", status);
                jsonObject.put("scanID", scanID);
                jsonObject.put("circleRemarks",circleRemarks);
                jsonObject.put("cirStatus",cirStatus);
            }else{
                jsonObject.put("returned","unavailable");
                jsonObject.put("simNo", "No Records Available For This Number");
                jsonObject.put("address", "No Records Available For This Number");
                jsonObject.put("user_name", "No Records Available For This Number");
                jsonObject.put("circleRemarks","No Records Available For This Number");
                jsonObject.put("status", 0);
                jsonObject.put("scanID", 0);
                jsonObject.put("cirStatus",0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }
}
