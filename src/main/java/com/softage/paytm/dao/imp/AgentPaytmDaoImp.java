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
}