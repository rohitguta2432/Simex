package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.SaveAadharDetailsDao;
import org.hibernate.loader.plan.spi.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Repository
public class SaveAadharDetailsDaoImp implements SaveAadharDetailsDao {
    private static final Logger logger = LoggerFactory.getLogger(AcceptedEntryDaoImpl.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public String saveAadharDetailData( int cust_id, int aadharNo, String residentName, String dob, String gender,
                                        String mobNo, String emailId, String careOf, String landmark, String locality,
                                        String vtc, String district, String hNo, String street, String postOffice, String subDistrict, String state, int pin
                                       , String uploadedBy,String uploadedOn
    ) {

        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String result="";
        try{
            entityManager=entityManagerFactory.createEntityManager();

            Query query=entityManager.createNativeQuery("{call usp_insertAadharDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            query.setParameter(1,cust_id);
            query.setParameter(2,aadharNo);
            query.setParameter(3,residentName);
            query.setParameter(4,dob);
            query.setParameter(5,gender);
            query.setParameter(6,mobNo);
            query.setParameter(7,emailId);
            query.setParameter(8,careOf);
            query.setParameter(9,landmark);
            query.setParameter(10,locality);
            query.setParameter(11,vtc);
            query.setParameter(12,district);
            query.setParameter(13,hNo );
            query.setParameter(14,street);
            query.setParameter(15,postOffice);
            query.setParameter(16,subDistrict);
            query.setParameter(17,state);
            query.setParameter(18,pin);
            query.setParameter(19,uploadedBy);
            query.setParameter(20,uploadedOn);
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