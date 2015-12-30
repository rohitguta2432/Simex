package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.models.CircleMastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 29-12-2015.
 */
@Repository
public class CircleMastDaoImp implements CircleMastDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public CircleMastEntity findByPrimaryKey(int circleCode) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        CircleMastEntity circleMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select paytmmast from PaytmMastEntity paytmmast";
            query=entityManager.createQuery(strQuery);
            circleMastEntity=entityManager.find(CircleMastEntity.class,1);

        }catch (Exception e){
          e.printStackTrace();;
        }
        return circleMastEntity;
    }
}
