package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.AcceptedEntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by SS0097 on 1/24/2017.
 */

@Repository
public class AcceptedEntryDaoImpl implements AcceptedEntryDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    @Transactional
    public String saveAcceptedEntryData(String setEntryBy, String setCusPOACode, String setCusPoaNumber, String setCusPoiCode, String setCusPoiNumber, int setCustomerId, String setDocStatus, String setFolder_name, int setPage_count, String setSim_no, String setImagePath, String setSRF, String setcPOA, String setcPOI, String OrigPoa, String OrigPoi, int origPoaPc, int origPoiPc, String photo, int PhotoPc, int poaPc, int poiPc, int SrfPc) {
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        String result="";
        try{
            entityManager=entityManagerFactory.createEntityManager();
          //  entityManager.getTransaction().begin();
            Query query=entityManager.createNativeQuery("{call usp_insertAcceptEntryData(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            query.setParameter(1,setEntryBy);
            query.setParameter(2,setCusPOACode);
            query.setParameter(3,setCusPoaNumber);
            query.setParameter(4,setCusPoiCode);
            query.setParameter(5,setCusPoiNumber);
            query.setParameter(6,setCustomerId);
            query.setParameter(7,setDocStatus);
            query.setParameter(8,setFolder_name);
            query.setParameter(9,setPage_count);
            query.setParameter(10,setSim_no);
            query.setParameter(11,setImagePath);
            query.setParameter(12,setSRF);
            query.setParameter(13,setcPOA );
            query.setParameter(14,setcPOI);
            query.setParameter(15,OrigPoa);
            query.setParameter(16,OrigPoi);
            query.setParameter(17,origPoaPc);
            query.setParameter(18,origPoiPc);
            query.setParameter(19,photo);
            query.setParameter(20,PhotoPc);
            query.setParameter(21,poaPc);
            query.setParameter(22,poiPc);
            query.setParameter(23,SrfPc);
            result=(String)query.getSingleResult();
          //  entityManager.getTransaction().commit();



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
