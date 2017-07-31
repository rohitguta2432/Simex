package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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

            result="done";
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
    @Transactional
    public String savePaytmMaster(PaytmMastEntity paytmMastEntity) {

        EntityManager entityManager=null;
        EntityTransaction transaction = null;
        String result=null;

        try{
            entityManager = entityManagerFactory.createEntityManager();
            transaction =  entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(paytmMastEntity);
            result="done";
        }catch (Exception e) {

            e.printStackTrace();
            result="error";
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                transaction.commit();
                entityManager.close();

            }
        }
        return  result;
    }

    @Override
    @Transactional
    public PaytmMastEntity getPaytmMasterData(int cust_uid) {
        EntityManager entityManager = null;
        PaytmMastEntity paytmMasterData=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select cust from PaytmMastEntity cust where cust.cust_uid=:cust_uid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("cust_uid",cust_uid);
            paytmMasterData = (PaytmMastEntity)query.getSingleResult();
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
        return  paytmMasterData;
    }

    @Override
    public PaytmMastEntity getPaytmMastEntityByDate(String mobile, String date) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        Query query=null;
        PaytmMastEntity paytmMastEntity=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            String hql = "select paytmMast from PaytmMastEntity paytmMast where paytmMast.customerPhone=:phone order by paytmMast.importDate desc ";
            query = entityManager.createQuery(hql);
            query.setParameter("phone", mobile);
            query.setMaxResults(1);
            //query.setParameter("req_date", date);
            paytmMastEntity = (PaytmMastEntity) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return paytmMastEntity;
    }


    @Override
    @Transactional
    public JSONObject getPaytmMastData(int  cust_uid) {
        EntityManager entityManager = null;
        List<PaytmMastEntity> list = null;
        PaytmMastEntity paytmMastEntity=null;
        JSONObject json=new JSONObject();
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select new map (pm.cust_uid as cust_uid,pm.coStatus as coStatus, pm.customerPhone as customerPhone, pm.username as username,pm.remarks as remarks,pm.address as address,pm.city as city,pm.pincode as pincode,pm.email as email,pm.state as state,pm.simType as simType,pm.alternatePhone1 as alternatePhone1,pm.alternatePhone2 as alternatePhone2,pm.circleMastByCirCode.cirCode as cir_code) from PaytmMastEntity pm  where pm.cust_uid=:cust_uid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("cust_uid",cust_uid);

            HashMap<String,Object> map=(HashMap<String,Object>)query.getSingleResult();
           /* json.put("address1",map.get("address1").toString().replace("#",""));*/
            Integer circleCode =(Integer) map.get("cir_code");



            json.put("customerPhone",map.get("customerPhone"));
            json.put("username",map.get("username"));
            json.put("remarks",map.get("remarks"));
            json.put("address",map.get("address").toString().replace("#",""));
            json.put("city",map.get("city"));
            // put here static pincode for testing purpose
            json.put("pincode",map.get("pincode"));
            //json.put("pincode","134109");
            json.put("email",map.get("email"));
            json.put("state",map.get("state"));
            json.put("simType",map.get("simType"));
            json.put("cust_uid",cust_uid);
            if(circleCode==13||circleCode==14){
              json.put("alternatePhone1",map.get("customerPhone"));
              json.put("alternatePhone2",map.get("alternatePhone1"));

            }else {
                json.put("alternatePhone1", map.get("alternatePhone1"));
                json.put("alternatePhone2", map.get("alternatePhone2"));

            }

            json.put("coStatus",map.get("coStatus"));

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
    @Transactional
    public JSONObject telecallingScreen(String username,int cirCode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject json=new JSONObject();
        EntityTransaction transaction = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            transaction=  entityManager.getTransaction();
    //        transaction.begin();
       //    StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_GetTeleData");
            Query query1= entityManager.createNativeQuery("{call sp_GetTeleDataByCustId(?)}");
            query1.setParameter(1,username);
         //   query1.setMaxResults(1);
            Object[] s = (Object[])query1.getSingleResult();
       //     transaction.commit();
            if (s.length>0) {
                json.put("cust_uid", s[0]);
                System.out.println(s[0]);
                json.put("username", s[1]);
                System.out.println(s[1]);

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
    @Transactional
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
    @Transactional
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

    @Override
    @Transactional
    public PaytmMastEntity getPaytmMaster(String mobileNo) {
        EntityManager entityManager = null;
        PaytmMastEntity paytmMastEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select pm from PaytmMastEntity pm where pm.customerPhone=:mobileno";
            query=entityManager.createQuery(strQuery);
            query.setParameter("mobileno",mobileNo);
            paytmMastEntity = (PaytmMastEntity)query.getSingleResult();
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
        return  paytmMastEntity;
    }

    @Override
    public <S extends PaytmMastEntity> S save(S s) {
        return null;
    }

    @Override
    public <S extends PaytmMastEntity> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    @Transactional
    public PaytmMastEntity findOne(String s) {
        EntityManager entityManager = null;
        PaytmMastEntity paytmMastEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select pm from PaytmMastEntity pm where pm.customerPhone=:mobileno";
            query=entityManager.createQuery(strQuery);
            query.setParameter("mobileno",s);
            paytmMastEntity = (PaytmMastEntity)query.getSingleResult();
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
        return  paytmMastEntity;
    }
    public PaytmMastEntity getpaytmmasterservice(int customerId){
        EntityManager entityManager = null;
        PaytmMastEntity paytmMastEntityServices=null;
        Query query=null;
        try
        {
            /*String likeParameter="%"+customerId+"%";*/
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "from PaytmMastEntity where cust_uid=:customerId";
            query=entityManager.createQuery(strQuery);
            query.setParameter("customerId",customerId);
            paytmMastEntityServices = (PaytmMastEntity)query.getSingleResult();

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
        return  paytmMastEntityServices;

    }

    @Override
    public PaytmcustomerDataEntity getPaytmCustomerData(int cust_uid) {
        EntityManager entityManager = null;
        PaytmcustomerDataEntity paytmcustomerDataEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select cust from PaytmcustomerDataEntity cust where cust.cust_uid=:cust_uid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("cust_uid",cust_uid);
            paytmcustomerDataEntity = (PaytmcustomerDataEntity)query.getSingleResult();
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
        return  paytmcustomerDataEntity;
    }

    @Override
    public SpokeMastEntity getSpokemast(String spokeCode) {
        EntityManager entityManager = null;
        SpokeMastEntity spokeMastEntity = null;
        Query query = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select cust from SpokeMastEntity cust where cust.spokeCode=:spokeCode";
            query = entityManager.createQuery(strQuery);
            query.setParameter("spokeCode", spokeCode);
            spokeMastEntity = (SpokeMastEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return spokeMastEntity;


    }

    @Override
    public AllocationMastEntity getAllocationentity(String custid, int jobid) {
        EntityManager entityManager = null;
        AllocationMastEntity allocationMastEntity=null;
        Query query=null;
        try
        {
            String likeParameter="%"+custid+"%";
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "from AllocationMastEntity where str(paytmcustomerDataByCustomerPhone.cust_uid) like :customer and id=:jobid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("customer",likeParameter);
            query.setParameter("jobid",jobid);
            allocationMastEntity = (AllocationMastEntity)query.getSingleResult();



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
        return  allocationMastEntity;


    }

    @Override
  //  @Transactional
    public String updatePaytmMast(PaytmMastEntity paytmMastEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(paytmMastEntity);

            msg="done";
        } catch (Exception e) {
            msg="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                transaction.commit();
                entityManager.close();
            }
        }
        return  msg;
    }

    @Override
    public String updateAddress(int cust_uid, String changeAddress, String changePincode, String userName) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call usp_updateAddress(?,?,?,?)}");
            query.setParameter(1, cust_uid);
            query.setParameter(2, changeAddress);
            query.setParameter(3, changePincode);
            query.setParameter(4, userName);
            msg = (String) query.getSingleResult();

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
    public boolean exists(String s) {
        return false;
    }

    @Override
    public Iterable<PaytmMastEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<PaytmMastEntity> findAll(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(PaytmMastEntity paytmMastEntity) {

    }

    @Override
    public void delete(Iterable<? extends PaytmMastEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
