package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.FtpDetailsDao;
import com.softage.paytm.models.UploadedImagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0090 on 7/18/2016.
 */
@Repository
public class FtpDetailsDaoImp implements FtpDetailsDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    public String insertFtpDetails(String custNumber, String imgPath, int pageNo, String createdBy, int qcStatus) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        List message=new ArrayList<>();
        String res="";
        try{
            entityManager=entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            Query query=entityManager.createNativeQuery("{call sp_insertScanDetails(?,?,?,?,?)}");
            query.setParameter(1,custNumber);
            query.setParameter(2,imgPath);
            query.setParameter(3,pageNo);
            query.setParameter(4,createdBy);
            query.setParameter(5,qcStatus);
            res=(String)query.getSingleResult();
            transaction.commit();
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
    @Override
    public String insertImageUploadDetails(UploadedImagesEntity imagesEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String result=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(imagesEntity);
            transaction.commit();
            result="done";
        } catch (Exception e) {
            result="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return result;
    }
}
