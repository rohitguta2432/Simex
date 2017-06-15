package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.ReportMastEntity;
import com.softage.paytm.models.SpokeMastEntity;
import com.softage.paytm.models.SpokePinMast;
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
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return circleMastEntity;
    }

    @Override
    public CircleMastEntity findByPrimaryKey(String circleName) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        CircleMastEntity circleMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select c from CircleMastEntity c where c.circleName=:circleName";
            query=entityManager.createQuery(strQuery);
            query.setParameter("circleName",circleName);
            circleMastEntity=(CircleMastEntity)query.getSingleResult();
            //  circleMastEntity=entityManager.find(CircleMastEntity.class,1);
            logger.info("circle successfully geting by circle_code");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error to getting Circle List",e);
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return circleMastEntity;
    }


    @Override
    public List<CircleMastEntity> getCircleList() {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> Circles=null;
        CircleMastEntity circleMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select circlemast from CircleMastEntity circlemast";
            query=entityManager.createQuery(strQuery);
            Circles=query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return Circles;
    }

    @Override
    public List<String> getSpokeList(String circleName,String empType) {
        EntityManager entityManager=null;
        Query query=null;
        List<String> spokeCodeList=null;
        SpokeMastEntity spokeMastEntity=null;
        String strQuery="";
        try{
            entityManager = entityManagerFactory.createEntityManager();
            if("AOQC".equalsIgnoreCase(empType)){
                strQuery = "select distinct spokeList.ao_spoke_code from SpokeMastEntity spokeList where spokeList.circle=:cirle";
            }else{
                strQuery = "select spokeList.spokeCode from SpokeMastEntity spokeList where spokeList.circle=:cirle";
            }

            System.out.println("query>>>>>    "+strQuery);
            query=entityManager.createQuery(strQuery);
            query.setParameter("cirle",circleName);
            spokeCodeList=query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return spokeCodeList;
    }

    @Override
    public List<ReportMastEntity> getReporttypes() {
        EntityManager entityManager=null;
        Query query=null;
        List<ReportMastEntity> getReportTypes=null;
        SpokeMastEntity spokeMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select rm from ReportMastEntity rm";
            System.out.println("query>>>>>    "+strQuery);
            query=entityManager.createQuery(strQuery);
            getReportTypes=query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return getReportTypes;
    }

    @Override
    public List<String> getBySpokeCode(String spokecode) {
        EntityManager entityManager=null;
        Query query=null;
        List<String> spokePinList=null;
        SpokePinMast SpokePinMast=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select spokepin.pincode from SpokePinMast spokepin where spokepin.spokeCode=:spokecode";
            System.out.println("query>>>>>    "+strQuery);
            query=entityManager.createQuery(strQuery);
            query.setParameter("spokecode",spokecode);
            spokePinList=query.getResultList();

        }catch (Exception e){
          logger.error("",e);
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return spokePinList;
    }

    @Override
    public String getAospokeCode(String spokeCode) {
        EntityManager entityManager=null;
        Query query=null;
        List<String> spokePinList=null;
        SpokePinMast SpokePinMast=null;
        String ao_spoke_code=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select distinct spokepin.ao_spoke_code from SpokeMastEntity spokepin where spokepin.spokeCode=:spokecode";
            System.out.println("query>>>>>    "+strQuery);
            query=entityManager.createQuery(strQuery);
            query.setParameter("spokecode",spokeCode);
            ao_spoke_code=(String)query.getSingleResult();

        }catch (Exception e){
            logger.error("",e);
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return ao_spoke_code;
    }
}
