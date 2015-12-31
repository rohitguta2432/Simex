package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.SpokeMastEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CircleMastDaoImp.class);

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
            String strQuery = "select c from CircleMastEntity c where c.cirCode=:circleCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("circleCode",circleCode);
            circleMastEntity=(CircleMastEntity)query.getSingleResult();
          //  circleMastEntity=entityManager.find(CircleMastEntity.class,1);
           logger.info("circle successfully geting by circle_code");
        }catch (Exception e){
          e.printStackTrace();
          logger.error("error to getting Circle List",e);
        }
        return circleMastEntity;
    }

    @Override
    public List<String> getCircleList() {
        EntityManager entityManager=null;
        Query query=null;
        List<String> Circles=null;
        CircleMastEntity circleMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select circlemast.circleName from CircleMastEntity circlemast";
            query=entityManager.createQuery(strQuery);
            Circles=query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        return Circles;
    }

    @Override
    public List<String> getSpokeList(String circleName) {
        EntityManager entityManager=null;
        Query query=null;
        List<String> spokeCodeList=null;
        SpokeMastEntity spokeMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select spokeList.spokeCode from SpokeMastEntity spokeList where spokeList.circle=:cirle";
            System.out.println("query>>>>>    "+strQuery);
            query=entityManager.createQuery(strQuery);
            query.setParameter("cirle",circleName);
            spokeCodeList=query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        return spokeCodeList;
    }
}
