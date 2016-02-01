package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.SmsSendLogDao;
import com.softage.paytm.models.ProcessMastEntity;
import com.softage.paytm.models.ReceiverMastEntity;
import com.softage.paytm.models.SmsSendlogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by SS0085 on 05-01-2016.
 */

@Repository
public class SmsSendLogDaoImp implements SmsSendLogDao {
    private static final Logger logger = LoggerFactory.getLogger(AgentPaytmDaoImp.class);

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public List<SmsSendlogEntity> getSendData() {
        EntityManager entityManager=null;
        Query query=null;
        List< SmsSendlogEntity> entityList=null;
        try{

            entityManager= entityManagerFactory.createEntityManager();
            String strQuery="select smsentity from SmsSendlogEntity smsentity where smsentity.smsDelivered=:status";
            query=entityManager.createQuery(strQuery);
            query.setParameter("status","N");
            query.setMaxResults(10);
            entityList=query.getResultList();
           /* smsSendlogEntity=(SmsSendlogEntity)query.getSingleResult();*/
        }catch (Exception e){
            logger.error("error to geting SmsSendlogEntity",e);
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }


        return entityList;
    }

    @Override
    public String updateSmsLogData(SmsSendlogEntity smsSendlogEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(smsSendlogEntity);
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
    public ProcessMastEntity getByPrimarykey(int processCode) {
        return null;
    }

    @Override
    public ReceiverMastEntity getbyPrimaryKey(int reciverId) {
        return null;
    }
}
