package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.models.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select telemast from TelecallMastEntity telemast where telemast.tmCustomerPhone=:phoneNumber";
            query=entityManager.createQuery(strQuery);
            query.setParameter("phoneNumber",phoneNumber);
            telecallMastEntity= (TelecallMastEntity)query.getSingleResult();
            entityTransaction.commit();
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
    public TelecallMastEntity getByReferenceId(int custId) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        TelecallMastEntity telecallMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select telemast from TelecallMastEntity telemast where telemast.paytmMastByTmCustomerPhone.cust_uid=:custId";
            query=entityManager.createQuery(strQuery);
            query.setParameter("custId",custId);
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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            String strQuery = "select al.appointmentId from AllocationMastEntity al where al.appointmentId=:appointmentId and (al.confirmation=:con1 or al.confirmation=:con2 or al.finalConfirmation=:con3)";
            query=entityManager.createQuery(strQuery);
            query.setParameter("appointmentId",appointmentid);
            query.setParameter("con1","W");
            query.setParameter("con2","Y");
            query.setParameter("con3","Y");
            appoinmentId = (long)query.getSingleResult();
            entityTransaction.commit();
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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();

            String strQuery = "select pc.pcdName as name,pc.pcdAddress as address,pc.pcdArea as area,pc.pcdCity as city,pc.pcdPincode as pinCode," +
                    "pc.pcdVisitDate as visitDate,pc.pcdVisitTIme  as visitTime" +
                    " from PaytmcustomerDataEntity pc join AppointmentMastEntity am on" +
                    " pc.pcdCustomerPhone=am.customerPhone where am.appointmentId=:appoinmentId" +
                    " and am.customerPhone=:customerno";
            query=entityManager.createQuery(strQuery);
            query.setParameter("appoinmentId",appointmentid);
            query.setParameter("customerno",customerNo);
            map=query.getHints();
            entityTransaction.commit();
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

    public String getAgentCode(String pinCode, Date date, String visitDateTime, int maxAllocation, String agentCode) {


        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        String agentCode1=null;
        //  String status = "Open";
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
         //   entityTransaction=entityManager.getTransaction();
     //       entityTransaction.begin();
            query = entityManager.createNativeQuery("{call sp_AllocateAgentNew(?,?,?)}");
            query.setParameter(1, pinCode);
            query.setParameter(2, date.toString());
            query.setParameter(3, visitDateTime);
            agentCode1 = (String)query.getSingleResult();
       //     entityTransaction.commit();
        } catch (Exception e) {
            logger.error("Agent Allocate error ",e);

        } finally {
            if (entityManager != null && entityManager.isOpen()) {
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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from AppointmentMastEntity am where am.customerPhone=:phoneNumber";
            query=entityManager.createQuery(strQuery);
            query.setParameter("phoneNumber",customerNumber);
            appointmentMastEntity= (AppointmentMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
    public AppointmentMastEntity getByCustId(int custId) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        AppointmentMastEntity appointmentMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from AppointmentMastEntity am where am.paytmcustomerDataByCustomerPhone.cust_uid=:custId";
            query=entityManager.createQuery(strQuery);
            query.setParameter("custId",custId);
            appointmentMastEntity= (AppointmentMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
    public AppointmentMastEntity getByAppointmentId(long appointmentId) {
        EntityManager entityManager=null;
        Query query=null;
        List<CircleMastEntity> list=new ArrayList<>();
        AppointmentMastEntity appointmentMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from AppointmentMastEntity am where am.appointmentId=:appointmentId";
            query=entityManager.createQuery(strQuery);
            query.setParameter("appointmentId",appointmentId);
            appointmentMastEntity= (AppointmentMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from RemarkMastEntity am where am.remarksCode=:remarkCode";
            query=entityManager.createQuery(strQuery);
            query.setParameter("remarkCode",remarkCode);
            RemarkMastEntity= (RemarkMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
    public List<RemarkMastEntity> remarkList() {
        EntityManager entityManager=null;
        Query query=null;
        List<RemarkMastEntity> remarkMastEntities=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from RemarkMastEntity am ";
            query=entityManager.createQuery(strQuery);
            remarkMastEntities= query.getResultList();
            entityTransaction.commit();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return remarkMastEntities;
    }

    @Override
    public ReceiverMastEntity getRecivedByCode(int code) {
        EntityManager entityManager=null;
        Query query=null;
        ReceiverMastEntity receiverMastEntity=null;
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from ReceiverMastEntity am where am.receiverCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            receiverMastEntity= (ReceiverMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
    public String save(ReOpenTaleCallMaster openTaleCallMaster) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(openTaleCallMaster);
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
    public JSONObject getLeadData() {
        EntityManager entityManager = null;
           JSONObject jsonObject=new JSONObject();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("{call getAutoLeadInformation()}");
            Object[] auditedObj = (Object[]) query.getSingleResult();
            String pincode = (String) auditedObj[0];
            String address = (String) auditedObj[1];
            String customerPhone = (String) auditedObj[2];
            String alternatePhone1 = (String) auditedObj[3];
            Integer cust_uid = (Integer) auditedObj[4];
            Date appointment_Date = (Date) auditedObj[5];
            Time appointment_Time = (Time) auditedObj[6];
            BigInteger appointment_id = (BigInteger) auditedObj[7];
            Integer cirCode = (Integer) auditedObj[8];
            String customerName = (String) auditedObj[9];
            jsonObject.put("Pincode",pincode);
            jsonObject.put("Address",address);
            jsonObject.put("customerPhone",customerPhone);
            jsonObject.put("alternatePhone1",alternatePhone1);
            jsonObject.put("cust_uid",cust_uid);
            jsonObject.put("appointment_Date",appointment_Date);
            jsonObject.put("appointment_Time",appointment_Time);
            jsonObject.put("appointment_id",appointment_id);
            jsonObject.put("cirCode",cirCode);
            jsonObject.put("customerName",customerName);

        }catch (Exception e){

            logger.error(" error to getting Auto lead data ",e);
        }
        return jsonObject;
    }

    @Override
    public String callJobAllocatedProcedure(Map<String,String> map) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject json=new JSONObject();
        String result="";
        Integer appointmentID=null;
        EntityTransaction entityTransaction=null;
        int custId=Integer.parseInt(map.get("custId"));
        String number=map.get("number");
        String name=map.get("name");
        String status=map.get("status");
        String address=map.get("address");
        String area=map.get("area");
        String city=map.get("city");
        String emailId=map.get("emailId");
        String importby=map.get("importby");
        String importType=map.get("importType");
        String simType=map.get("simType");
        String co_status=map.get("co_status");
        String pincode=map.get("pinCode");
        String state=map.get("state");
        String pcdvisitTime=map.get("visitDate");
        String visitTime1=map.get("visitTime");
        String visitTime=visitTime1+":00:00";
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
        //    entityTransaction.begin();
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_GetTeleData");
            Query query1= entityManager.createNativeQuery("{call usp_insertPaytmCustomerData(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            query1.setParameter(1,custId);
            query1.setParameter(2,status);
            query1.setParameter(3,address);
            query1.setParameter(4,area);
            query1.setParameter(5,city);
            query1.setParameter(6,number);
            query1.setParameter(7,emailId);
            query1.setParameter(8,importby);
            query1.setParameter(9,importType);
            query1.setParameter(10,name);
            query1.setParameter(11,pincode);
            query1.setParameter(12,state);
            query1.setParameter(13,pcdvisitTime);
            query1.setParameter(14,visitTime);
            query1.setParameter(15,simType);
            query1.setParameter(16,co_status);
            appointmentID=((BigInteger)query1.getSingleResult()).intValue();
            result=appointmentID.toString();

        }catch (Exception e){
            result="error";
            logger.error("error to appointment data  ",e);
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
    public String JobAllocatedProcedure(Map<String, String> map) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject json=new JSONObject();
        String result="";
        Integer allocationID=null;
        EntityTransaction entityTransaction=null;
       String appointment_ID=(String) map.get("appointmentID");
        String agent_code=(String)map.get("agentcode");
        String custUID=(String)map.get("custUID");
        String mobNo=(String)map.get("mobileNo");
        //map.get("allocationTime",);  use now()
        String visitDatetiem=(String)map.get("visitDatetime");
        String importBy=(String)map.get("importBy");
        //map.get("importDatetime") use now()
       String confirmationDatetime=(String) map.get("confirmationDatetime");
        // map.get("sendSMSDatetime",) use now()
        String final_confirmation=(String)map.get("finalConfirmation");
        String confirmation=(String)map.get("confirmation");
        String confirmationAllowed=(String)map.get("confirmationAllowed");
        String kycCollected=(String)map.get("kycCollected");
        String remCode=(String)map.get("remarkCode");
        String spoke=(String)map.get("spokeCode");
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            Query query1= entityManager.createNativeQuery("{call usp_insertAllocationData(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            query1.setParameter(1,agent_code);
            query1.setParameter(2,appointment_ID);
            query1.setParameter(3,confirmation);
            query1.setParameter(4,confirmationAllowed);
            query1.setParameter(5,confirmationDatetime);
            query1.setParameter(6,mobNo);
            query1.setParameter(7,final_confirmation);
            query1.setParameter(8,importBy);
            query1.setParameter(9,kycCollected);
            query1.setParameter(10,remCode);
            query1.setParameter(11,spoke);
            query1.setParameter(12,visitDatetiem);
            query1.setParameter(13,custUID);
            allocationID=(Integer)query1.getSingleResult();
            result=  allocationID.toString();
        }catch (Exception e){
            result="error";
            logger.error("allocate job error",e);
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
        EntityTransaction entityTransaction=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction=entityManager.getTransaction();
            entityTransaction.begin();
            String strQuery = "select am from ProcessMastEntity am where am.processCode=:code";
            query=entityManager.createQuery(strQuery);
            query.setParameter("code",code);
            processMastEntity= (ProcessMastEntity)query.getSingleResult();
            entityTransaction.commit();

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
