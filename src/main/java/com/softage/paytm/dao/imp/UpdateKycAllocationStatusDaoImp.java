package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.UpdateKycAllocationStatusDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Repository
public class UpdateKycAllocationStatusDaoImp implements UpdateKycAllocationStatusDao {
    private static final Logger logger = LoggerFactory.getLogger(AcceptedEntryDaoImpl.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String updateKycStatus(String cust_id,String status,String jobId,String agent_code ){
        String result="";
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();

            Query query=entityManager.createNativeQuery("{call usp_updateKycStatus(?,?)}");
            query.setParameter(1,cust_id);
            query.setParameter(2,status);
            result=(String)query.getSingleResult();
            logger.info("save accept Entry>>>>>>  "+result);
        }catch (Exception e){
            result="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();

            }
        }

        return result;
    }

}
