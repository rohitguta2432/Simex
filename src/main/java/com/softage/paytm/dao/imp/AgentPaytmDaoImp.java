package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 30-12-2015.
 */

@Repository
public class AgentPaytmDaoImp implements AgentPaytmDao {
    private static final Logger logger = LoggerFactory.getLogger(AgentPaytmDaoImp.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(paytmagententryEntity);
            transaction.commit();
            msg="done";
        } catch (Exception e) {
           msg="err";
            e.printStackTrace();
        }    finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }

     return  msg;
    }

    @Override
    public String saveAgentPinMaster(AgentpinmasterEntity agentpinmasterEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(agentpinmasterEntity);
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
    public String saveEmployee(EmplogintableEntity emplogintableEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(emplogintableEntity);
            entityManager.flush();
            entityManager.clear();
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
    public PaytmagententryEntity findByPincode(String pincode) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        PaytmagententryEntity paytmagententryEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select paytmAgent from PaytmagententryEntity paytmAgent where paytmAgent.apincode=:pincode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("pincode",pincode);
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
    public String saveAgentLocation(String agentCode, String CustomerNumber, String location) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        List message=new ArrayList<>();
        String res="";
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_insertAgentLocation(?,?,?)}");
            query.setParameter(1,agentCode);
            query.setParameter(2,CustomerNumber);
            query.setParameter(3,location);
            res=(String)query.getSingleResult();
            entityManager.getTransaction().commit();
            /*if(message.equals("")||message.equals(null)){
                res="Unsuccessful";
            }else{
                res=message.get(0);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();

            }
        }

        return res;
    }

}
