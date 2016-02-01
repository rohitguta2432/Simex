package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PaytmPinMasterDao;
import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.PaytmPinMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by SS0085 on 21-01-2016.
 */
@Repository
public class PaytmPinMasterDaoImp implements PaytmPinMasterDao {
    private static final Logger logger = LoggerFactory.getLogger(AllocationDaoImp.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public PaytmPinMaster getByPincode(int pinCode) {

        EntityManager entityManager = null;
        PaytmPinMaster paytmPinMaster=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select pm from PaytmPinMaster pm where pm.pinCode=:pincode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("pincode",pinCode);
            paytmPinMaster = (PaytmPinMaster)query.getSingleResult();
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
        return  paytmPinMaster;
    }


    @Override
    public PaytmPinMaster getByPincodeState(int pinCode, String circleName) {
        EntityManager entityManager = null;
        PaytmPinMaster paytmPinMaster=null;
        Query query=null;
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = " select pm from PaytmPinMaster pm where pm.pinCode=:pincode and pm.circleName=:circlename";
            query=entityManager.createQuery(strQuery);
            query.setParameter("pincode",pinCode);
            query.setParameter("circlename",circleName);
            paytmPinMaster = (PaytmPinMaster)query.getSingleResult();
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
        return  paytmPinMaster;
    }
}
