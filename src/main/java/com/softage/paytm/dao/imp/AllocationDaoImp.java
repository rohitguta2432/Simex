package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.PaytmMastEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
            transaction.commit();
            msg="done";
        } catch (Exception e) {
            msg="err";
            e.printStackTrace();
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
            String strQuery = "select al from AllocationMastEntity al where al.appointmentId=:appointmantId and al.agentCode=:agnetcode  order by importDate";
            query=entityManager.createQuery(strQuery);
            query.setParameter("agnetcode",agentCode);
            query.setParameter("appointmantId",appointmentid);
            list = query.getResultList();
            allocationMastEntity  =list.get(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  allocationMastEntity;
    }
}
