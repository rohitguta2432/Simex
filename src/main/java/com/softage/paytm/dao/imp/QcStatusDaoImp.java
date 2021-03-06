package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.QcStatusDao;
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
    public JSONObject qcGetMobileNumber() {
        JSONObject jsonObject=new JSONObject();
        EntityManager entityManager=null;
        String customerNumber="";
        String res="";
        try {
            entityManager=entityManagerFactory.createEntityManager();
            Query query=entityManager.createNativeQuery("{call usp_qcGetMobileNumber()}");
            customerNumber=(String)query.getSingleResult();
            jsonObject.put("mobile",customerNumber);
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


}
