package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */
@Repository
public class PostCallingDaoImp implements PostCallingDao {
    private static final Logger logger = LoggerFactory.getLogger(PostCallingDaoImp.class);
   @Autowired
   public EntityManagerFactory entityManagerFactory;


    @Override
    public String saveTeleCallLog(TelecallLogEntity telecallLogEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(telecallLogEntity);
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
    public String savePaytmCustomer(PaytmcustomerDataEntity paytmcustomerDataEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(paytmcustomerDataEntity);
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
    public String saveAppointment(AppointmentMastEntity appointmentMastEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(appointmentMastEntity);
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
    public String updateTeleCall(TelecallMastEntity telecallMastEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
           // entityManager.persist(telecallMastEntity);
            entityManager.merge(telecallMastEntity);
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
    public TelecallMastEntity getByPrimaryKey(String phoneNumber) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        TelecallMastEntity telecallMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select telemast from TelecallMastEntity telemast where telemast.tmCustomerPhone=:phoneNumber";
            query=entityManager.createQuery(strQuery);
            query.setParameter("phoneNumber",phoneNumber);
            telecallMastEntity= (TelecallMastEntity)query.getSingleResult();
          //  telecallMastEntity=entityManager.find(CircleMastEntity.class,1);

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return telecallMastEntity;
    }

    @Override
    public long checkAppointmentId(long appointmentid) {

        EntityManager entityManager=null;
        Query query=null;
        AllocationMastEntity allocationMastEntity=null;
        long appoinmentId=0;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select al.appointmentId from AllocationMastEntity al where al.appointmentId=:appointmentId and (al.confirmation=:con1 or al.confirmation=:con2 or al.finalConfirmation=:con3)";
            query=entityManager.createQuery(strQuery);
            query.setParameter("appointmentId",appointmentid);
            query.setParameter("con1","W");
            query.setParameter("con2","Y");
            query.setParameter("con3","Y");
            appoinmentId = (long)query.getSingleResult();
            //  telecallMastEntity=entityManager.find(CircleMastEntity.class,1);

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }

        return appoinmentId;
    }

    @Override
    public Map<String, Object> getData(long appointmentid, String customerNo) {
        EntityManager entityManager=null;
        Query query=null;
        AllocationMastEntity allocationMastEntity=null;
       Map<String, Object> map=new HashMap<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();

            String strQuery = "select pc.pcdName as name,pc.pcdAddress as address,pc.pcdArea as area,pc.pcdCity as city,pc.pcdPincode as pinCode," +
                              "pc.pcdVisitDate as visitDate,pc.pcdVisitTIme  as visitTime" +
                             " from PaytmcustomerDataEntity pc join AppointmentMastEntity am on" +
                    " pc.pcdCustomerPhone=am.customerPhone where am.appointmentId=:appoinmentId" +
                    " and am.customerPhone=:customerno";
            query=entityManager.createQuery(strQuery);
            query.setParameter("appoinmentId",appointmentid);
            query.setParameter("customerno",customerNo);
            map=query.getHints();
            //  telecallMastEntity=entityManager.find(CircleMastEntity.class,1);

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }

        return map;
    }

    @Override
    public String getAgentCode(String pinCode, Date date, Date date1, int maxAllocation, String agentCode) {

        EntityManager entityManager=null;
        Query query=null;
        AllocationMastEntity allocationMastEntity=null;
        String agentCode1=null;
        String strQuery=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();

            if("0".equals(agentCode)) {

                strQuery="select A.APM_Acode from agentpinmaster A join paytmagententry P on " +
                        " A.APM_Acode=P.acode where  A.APM_APincode=? and  (select count(am.Agent_Code) from allocation_mast am where am.Agent_Code=A.APM_Acode and am.Allocation_datetime>=? and am.Allocation_datetime<?)<=?";

               /* strQuery = "select A.apmAcode from AgentpinmasterEntity A join PaytmagententryEntity P " +
                        "on  A.apmAcode=P.acode where  A.apmAPincode=:agentPinCode "+
                        " and (select count(am.agentCode) from AllocationMastEntity am" +
                        " where am.agentCode=A.apmAcode and am.allocationDatetime>=:date and am.allocationDatetime<:date1)<=:maxAllocation";*/
            }else {


/*                strQuery="select A.APM_Acode from agentpinmaster A join paytmagententry P on " +
                        " A.APM_Acode=P.acode where A.APM_Acode <>? A.APM_APincode=? and (select COUNT (am.agent_code)  from allocation_mast am where am.agent_code=A.APM_Acode and am.Allocation_datetime>=? and am.Allocation_datetime<? )<=?";*/

             /*  strQuery = "select A.apmAcode from AgentpinmasterEntity A join PaytmagententryEntity P " +
                        "on  A.apmAcode=P.acode where A.apmAcode <>:agentcode and A.apmAPincode=:agentPinCode" +
                        " and (select count(am.agentCode) from AllocationMastEntity am" +
                        " where am.agentCode=A.apmAcode and am.allocationDatetime>=:date and am.allocationDatetime<:date1)<=:maxAllocation";*/

            }
          query=entityManager.createNativeQuery(strQuery);
             if(!"0".equals(agentCode)){
                query.setParameter(1,agentCode);
                query.setParameter(2,pinCode);
                query.setParameter(3,date);
                query.setParameter(4,date1);
            }
            query.setParameter(1,pinCode);
            query.setParameter(2,date);
            query.setParameter(3,date1);
            query.setParameter(4,maxAllocation);
            agentCode1=(String)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }

        return agentCode1;
    }

    @Override
    public String saveSmsSendEntity(SmsSendlogEntity smsSendlogEntity) {

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(smsSendlogEntity);
            entityManager.flush();
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
    public String saveTabNotification(TblNotificationLogEntity tblNotificationLogEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(tblNotificationLogEntity);
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
    public AppointmentMastEntity getByCustomerNuber(String customerNumber) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        AppointmentMastEntity appointmentMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select am from AppointmentMastEntity am where am.customerPhone=:phoneNumber";
            query=entityManager.createQuery(strQuery);
            query.setParameter("phoneNumber",customerNumber);
            appointmentMastEntity= (AppointmentMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }

        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return appointmentMastEntity;
    }

    @Override
    public RemarkMastEntity getByPrimaryCode(String remarkCode) {
        EntityManager entityManager=null;
        Query query=null;
        RemarkMastEntity RemarkMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select am from RemarkMastEntity am where am.remarksCode=:remarkCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("remarkCode",remarkCode);
            RemarkMastEntity= (RemarkMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return RemarkMastEntity;
    }

    @Override
    public ReceiverMastEntity getRecivedByCode(int code) {
        EntityManager entityManager=null;
        Query query=null;
        ReceiverMastEntity receiverMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select am from ReceiverMastEntity am where am.receiverCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            receiverMastEntity= (ReceiverMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return receiverMastEntity;
    }

    @Override
    public String callJobAllocatedProcedure(long allocationId, String moblieno, String agentcode) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject json=new JSONObject();
        String result="";
        try{
            entityManager = entityManagerFactory.createEntityManager();
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_GetTeleData");
            Query query1= entityManager.createNativeQuery("{call sp_allocate1(?,?,?)}");
            query1.setParameter(1,allocationId);
            query1.setParameter(2,moblieno);
            query1.setParameter(3,agentcode);
            String s = (String)query1.getSingleResult();
          /*  if (s.length>0) {
               *//* json.put("mobileNo", s[0]);
                json.put("customerName", s[1]);*//*
                result=(String)s[0];
            }*/
        }catch (Exception e){
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

    @Override
    public ProcessMastEntity getProcessByCode(int code) {
        EntityManager entityManager=null;
        Query query=null;
        ProcessMastEntity processMastEntity=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select am from ProcessMastEntity am where am.processCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            processMastEntity= (ProcessMastEntity)query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return processMastEntity;
    }
}
