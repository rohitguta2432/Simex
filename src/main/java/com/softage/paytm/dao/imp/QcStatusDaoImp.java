package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.QcStatusDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */
@Repository
public class QcStatusDaoImp implements QcStatusDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    public String qcStatusSave(String mobile_no, String status, String rejected_page, String remarks) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        List<String> message=new ArrayList<>();
        String res="";
        try{
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query=entityManager.createNativeQuery("{call usp_saveQcStatus(?,?,?,?)}");
        query.setParameter(1,mobile_no);
        query.setParameter(2,status);
        query.setParameter(3,rejected_page);
        query.setParameter(4,remarks);
        res= (String)query.getSingleResult();
        entityManager.getTransaction().commit();

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
        return res;
        }

    @Override
    public String qcStatusUpdate(String mobile_no, String status, String rejected_page, String remarks) {
        EntityManager entityManager=null;
        EntityTransaction entityTransaction=null;
        List<String> message= new ArrayList<>();
        String res="";
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_updateQcStatus(?,?,?,?)}");
            query.setParameter(1,mobile_no);
            query.setParameter(2,status);
            query.setParameter(3,rejected_page);
            query.setParameter(4,remarks);
            res= (String)query.getSingleResult();
            entityManager.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return res;
    }

    @Override
    public JSONObject qcGetMobileNumber(Integer circode,String empcode) {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;
        String customerNumber="";
        String res="";
        int scanid=0;
        String simNo="";
        String name="";
        String address="";
        String imgPath="";
        Integer cust_uid=0;
        Integer imageCount=0;
        try {
            entityManager=entityManagerFactory.createEntityManager();
            Query query=entityManager.createNativeQuery("{call usp_qcGetMobileNumber(?,?)}");
            query.setParameter(1,circode);
            query.setParameter(2,empcode);
            Object[] scanObj=(Object[])query.getSingleResult();
            //customerNumber=(String)query.getSingleResult();
            if(scanObj==null){
                jsonObject.put("status","Unavailable");
            }
            else {
                customerNumber = (String) scanObj[0];
                scanid = ((BigInteger) scanObj[1]).intValue();
                simNo = (String) scanObj[2];
                imgPath = (String) scanObj[3];
                name = (String) scanObj[4];
                address = (String) scanObj[5];
                cust_uid = (Integer) scanObj[6];
                imageCount = (Integer) scanObj[7];
                jsonObject.put("mobile", customerNumber);
                jsonObject.put("scanID", scanid);
                jsonObject.put("simNo", simNo);
                jsonObject.put("name", name);
                jsonObject.put("address", address);
                jsonObject.put("imagePath", imgPath);
                jsonObject.put("custUID", cust_uid);
                jsonObject.put("imgCount", imageCount);
                jsonObject.put("status","Available");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject qcGetCustomerDetails(String mobileNum) {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;
        EntityTransaction entityTransaction=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_getQcCustomerDetails(?)}");
            query.setParameter(1, mobileNum);
            Object[] object     =(Object[])query.getSingleResult();
            entityTransaction.commit();

            jsonObject.put("imagePath",object[0]);
            jsonObject.put("agentcode", object[1]);

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

    @Override
    public JSONObject downloadList(String mobileNumber, String todate, String fromdate) {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("{call usp_getDownloadList(?,?,?)}");
            query.setParameter(1, mobileNumber);
            query.setParameter(2, fromdate);
            query.setParameter(3, todate);
            List<Object[]> resultList = query.getResultList();
            int i=0;
            for (Object[] object : resultList) {
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("customerNo",object[0]);
                jsonObject1.put("agentcode", object[1]);
                jsonObject.put("downloadList"+i,jsonObject1);
                i++;
            }


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

    @Override
    public AuditStatusEntity getAuditStatusEntity(int status) {
        EntityManager entityManager = null;
        AuditStatusEntity auditStatusEntity=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            auditStatusEntity=entityManager.find(AuditStatusEntity.class,status);
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
                if(entityManager!=null && entityManager.isOpen()){
                    entityManager.close();
                }
        }
        return auditStatusEntity;
    }

    @Override
    public TblScan getScanTableEntity(int scanid) {
        EntityManager entityManager=null;
        TblScan tblScan=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            tblScan=entityManager.find(TblScan.class,scanid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return tblScan;
    }

    @Override
    public String saveCircleAuditEntity(CircleAuditEntity circleAuditEntity) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String message=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(circleAuditEntity);
            transaction.commit();
            message="inserted";
        }catch(Exception e){
            transaction.rollback();
            message="error";
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return message;
    }



    @Override
    public String updateTblScanEntity(TblScan tblScan) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String message=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(tblScan);
            transaction.commit();
            message="success";
        }catch (Exception e){
            transaction.rollback();
            message="error";
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }

        return message;
    }

    @Override
    public String savetblscan(TblScan savesimages) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(savesimages);
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
        return msg;
    }

    @Override
    public String saveTblDocdetails(TblcustDocDetails tblcustDocDetails) {

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(tblcustDocDetails);
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
        return msg;
    }

    @Override
    public TblScan getScanDetails(int cust_uid) {

        EntityManager entityManager = null;
        TblScan tblScan=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select cust from TblScan cust where cust.paytmcustomerDataEntity.cust_uid=:cust_uid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("cust_uid",cust_uid);
            tblScan = (TblScan)query.getSingleResult();
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
        return tblScan;


    }


}
