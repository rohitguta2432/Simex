package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.CallTimeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by SS0090 on 7/29/2016.
 */
@Repository
public class CallTimeDaoImp implements CallTimeDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public String insertCallTimeDetails(String customer_number, String callDateTime,int circleCode,String lastcallby,int cust_uid) {
        EntityManager entityManager=null;
        String result=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_insertCallTimeDetails(?,?,?,?,?)}");
            query.setParameter(1,customer_number);
            query.setParameter(2,callDateTime);
            query.setParameter(3,circleCode);
            query.setParameter(4,lastcallby);
            query.setParameter(5,cust_uid);
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
}
