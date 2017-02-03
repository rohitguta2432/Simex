package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.PaytmMastEntity;
import org.apache.commons.net.ntp.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by SS0085 on 02-01-2016.
 */
@Repository
public class AllocationDaoImp implements AllocationDao {
    private static final Logger logger = LoggerFactory.getLogger(AllocationDaoImp.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public String saveAllocation(AllocationMastEntity allocationMastEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(allocationMastEntity);

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
    public AllocationMastEntity findByAgentCode(long appointmentid, String agentCode) {

        EntityManager entityManager = null;
        List<AllocationMastEntity> list = null;
        AllocationMastEntity allocationMastEntity=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select al from AllocationMastEntity al where al.appointmentId=:appointmantId and al.agentCode=:agentCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("agentCode",agentCode);
            query.setParameter("appointmantId",appointmentid);
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
    public AllocationMastEntity findById(String agentCode, String jobid) {
        EntityManager entityManager = null;
        AllocationMastEntity allocationMastEntity=null;
        Query query=null;
        try
        {
            int id=  Integer.parseInt(jobid);
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select al from AllocationMastEntity al where al.id=:id and al.agentCode=:agentCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("id",id);
            query.setParameter("agentCode",agentCode);
            allocationMastEntity = (AllocationMastEntity)query.getSingleResult();
        }
        catch (Exception e)
        {
            logger.error("find by id allocationMast",e);
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
    public AllocationMastEntity findByAllocationTime(String agentCode, Timestamp dateTime) {
        EntityManager entityManager = null;
        AllocationMastEntity allocationMastEntity=null;
        EntityTransaction entityTransaction=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = " select al from AllocationMastEntity al where al.allocationDatetime=:dateTime and al.agentCode=:agentCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("dateTime",dateTime);
            query.setParameter("agentCode",agentCode);
            query.setMaxResults(1);
            allocationMastEntity = (AllocationMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
    public String findByAllocationTime(String agentCode, String dateTime) {
        EntityManager entityManager=null;
        String message=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
       //     entityTransaction=entityManager.getTransaction();
        //    entityTransaction.begin();
            Query query=entityManager.createNativeQuery("{call usp_available_agent(?,?)}");
            query.setParameter(1,agentCode);
            query.setParameter(2,dateTime);
            message=(String)query.getSingleResult();
   //         entityTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return message;
    }

    @Override
    public String updateAllocationMastEntity(AllocationMastEntity allocationMastEntity) {
        EntityManager entityManager=null;
        String message=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_updateKycAllocationMastEntity(?,?,?,?)}");
            query.setParameter(1,allocationMastEntity.getId());
            query.setParameter(2,allocationMastEntity.getAgentCode());
            query.setParameter(3,allocationMastEntity.getKycCollected());
            query.setParameter(4,allocationMastEntity.getRemarkMastByRemarksCode().getRemarksCode());
            message=(String)query.getSingleResult();
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return message;
    }

    @Override
    public AllocationMastEntity findByPrimaryKey(int id) {
        EntityManager entityManager = null;
        AllocationMastEntity allocationMastEntity=null;
        Query query=null;
        try
        {

            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select al from AllocationMastEntity al where al.id=:id";
            query=entityManager.createQuery(strQuery);
            query.setParameter("id",id);
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
    public AllocationMastEntity findByCustUid(int cust_uid) {
        EntityManager entityManager = null;
        AllocationMastEntity allocationMastEntity=null;
        Query query=null;
        try
        {

            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select al from AllocationMastEntity al where al.paytmcustomerDataByCustomerPhone.cust_uid=:cust_uid";
            query=entityManager.createQuery(strQuery);
            query.setParameter("cust_uid",cust_uid);
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
    public String allocationMastEntityUpdate(String jobId, String agentCode, String response) {
        EntityManager entityManager=null;
        String message=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_updateAllocationMastEntity(?,?,?)}");
            query.setParameter(1,jobId);
            query.setParameter(2,agentCode);
            query.setParameter(3,response);
            message=(String)query.getSingleResult();
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return message;
    }

    @Override
    public String updateKycAllocation(String AgentCode, String JobId, String remarksCode, String kycStatus) {
        EntityManager entityManager=null;
        String result=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_updateKycAllocation(?,?,?,?)}");
            query.setParameter(1,AgentCode);
            query.setParameter(2,JobId);
            query.setParameter(3,remarksCode);
            query.setParameter(4,kycStatus);
            result=(String)query.getSingleResult();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager!=null && entityManager.isOpen()){
                entityManager.close();
            }
        }
        return result;
    }



    @Override
    public <S extends AllocationMastEntity> S save(S s) {
        return null;
    }

    @Override
    public <S extends AllocationMastEntity> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public AllocationMastEntity findOne(Integer integer) {
        return null;
    }

    @Override
    public boolean exists(Integer integer) {
        return false;
    }

    @Override
    public Iterable<AllocationMastEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<AllocationMastEntity> findAll(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void delete(AllocationMastEntity allocationMastEntity) {

    }

    @Override
    public void delete(Iterable<? extends AllocationMastEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
