package com.softage.paytm.dao.imp;

import com.google.code.geocoder.Geocoder;
import com.softage.paytm.dao.ReportDao;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SS0085 on 08-01-2016.
 */
@Repository
public class ReportDaoImp implements ReportDao {
    @Autowired
    public EntityManagerFactory entityManagerFactory;


    @Override
    public JSONObject getReports(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getreportTelecalling(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                String status = "Open";
                if (objects.length > 0) {
                    JSONObject json = new JSONObject();

                    String latitude=(String)objects[20];
                    String longitude=(String)objects[21];
                    String location=(String)objects[19];
                    if(location.equalsIgnoreCase("")){
                        location="LocationNotFound";
                    location=getlocation(latitude,longitude);
                    }

                    json.put("CustomerId", objects[0]);
                    json.put("MobileNumber", objects[1]);
                    json.put("CallStatus", objects[2]);
                    String callStatus = (String) objects[2];
                    json.put("Attempts", objects[3]);
                    byte attempts = (Byte) objects[3];
                   String callStatus1=(String)objects[17];
                    System.out.println("call Status  "+callStatus1);


              /*    Docs Incomplete
                    Call Back Later
                    Not Interested
                    Not Reachable
                    Not Responding
                    Switched Off
                    Wrong Number
                    Already picked By Other Person
                    User Out Of Station
                    Ok For Docs Collection   */
                    if (callStatus.equalsIgnoreCase("Not Interested") || callStatus.equalsIgnoreCase("Wrong Number") || callStatus.equalsIgnoreCase("Already picked By Other Person")) {
                        status = "Close";
                    } else if (attempts == 9 && callStatus.equalsIgnoreCase("Ok For Docs Collection")) {
                        status = "Close";
                        json.put("Attempts", 1);
                    } else if (attempts == 9) {
                        status = "Close";
                    } else if (attempts == 3 && callStatus.equalsIgnoreCase("User Out Of Station")) {
                        status = "Close";
                    }
                    else if (callStatus.equalsIgnoreCase("Ok For Docs Collection") && callStatus1.equalsIgnoreCase("NA")) {
                        status = "Agent Not Found";
                    }else if (callStatus.equalsIgnoreCase("Ok For Docs Collection") ) {
                        status = "Done";
                    }


                    json.put("CallDateTime", objects[4].toString());
                    System.out.println(objects[4].toString());
                    json.put("TeleCallerName", objects[5]);
                    json.put("CustomerName", objects[6]);
                    json.put("AppointmentDate", objects[7]);
                    json.put("AppointmentTime", objects[8]);
                    json.put("Address", objects[9]);
                    json.put("Landmark", objects[10]);
                    json.put("City", objects[11]);
                    json.put("State", objects[12]);
                    json.put("Pincode", objects[13]);
                    json.put("Agent_Code", objects[14]);
                    json.put("ScanOn",objects[15]);
                    System.out.println("ScanOn = "+objects[15] +"ScanBy = "+objects[16]);
                    json.put("ScanBy",objects[16]);
                    json.put("importdate",objects[18].toString());
                    json.put("location",location);
                    json.put("Status", status);
                    jsonObject.put("record-" + i, json);

                    System.out.println("latitude  ="+latitude+" longitude  = "+longitude);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getReportTelecallingOutput(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getreportOutputTelecalling(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                if (objects.length > 0) {

                    JSONObject json = new JSONObject();
                    json.put("circleName",objects[0]);
                    json.put("mobileNumber",objects[1]);
                    json.put("CallStatus", objects[2]);
                    json.put("Attempts", objects[3]);
                    json.put("CallDateTime", objects[4].toString());
                    json.put("TeleCallerName", objects[5]);
                    jsonObject.put("record-" + i, json);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getReportCallStatus(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getCallReport(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                if (objects.length > 0) {

                    JSONObject json = new JSONObject();
                    json.put("customerNumber",objects[0]);
                    json.put("custname",objects[1]);
                    json.put("callby", objects[2]);
                    json.put("callStatus", objects[3]);
                    jsonObject.put("record-" + i, json);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getReportData(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getreportdata(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                String status = "Open";
                if (objects.length > 0) {

                    JSONObject json = new JSONObject();
                    json.put("CustomerId", objects[0]);
                    json.put("MobileNumber", objects[1]);
                    json.put("CustomerName", objects[2]);
                    json.put("DateOfBirth", objects[3]);
                    json.put("Gender", objects[4]);
                    json.put("status",objects[5]);
                    json.put("RejectionReason", objects[6]);
                    json.put("POIType", objects[7]);
                    json.put("POINumber", objects[8]);
                    json.put("POAType", objects[9]);
                    json.put("POANumber", objects[10]);
                    json.put("Address", objects[11]);
                    json.put("Agency", objects[12]);
                    json.put("DateOfCollection", objects[13]);
                    jsonObject.put("record-" + i, json);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getReportMis(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getreportMis(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                String status = "Open";
                if (objects.length > 0) {

                    JSONObject json = new JSONObject();
                    json.put("Date", objects[0]);
                    json.put("TotalDataRecevied", objects[1]);
                    json.put("CallPending", objects[2]);
                    json.put("Contacted", objects[3]);
                    json.put("NonContacted", objects[4]);
                    json.put("AppoinmentFixed", objects[5]);
                    json.put("NotAgreeforKyc", objects[6]);
                    json.put("KycDone", objects[7]);
                    json.put("PendingForKyc", objects[8]);
                    json.put("FinalDataSharedWithPaytm", objects[9]);
                    jsonObject.put("record-" + i, json);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getReOpenCalling(String from, String to, JSONObject jsonObject) {

        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query = null;
        //  String status = "Open";
        try {
            entityManager = entityManagerFactory.createEntityManager();
            query = entityManager.createNativeQuery("{call sp_getReopenreportTelecalling(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2, to);
            List<Object[]> resultList = query.getResultList();
            int i = 1;
            for (Object[] objects : resultList) {
                String status = "Open";
                if (objects.length > 0) {

                    JSONObject json = new JSONObject();
                    json.put("CustomerId", objects[0]);
                    json.put("MobileNumber", objects[1]);
                    json.put("CallStatus", objects[2]);
                    json.put("Attempts", objects[3]);
                    byte attempts = (Byte) objects[3];
                    if (attempts == 9) {
                        status = "Close";
                    }
                    json.put("CallDateTime", objects[4].toString());
                    System.out.println(objects[5].toString());
                    json.put("Tele-CallerName", objects[5]);
                    json.put("CustomerName", "");
                    json.put("AppointmentDate", "");
                    json.put("AppointmentTime", "");
                    json.put("Address", "");
                    json.put("Landmark", "");
                    json.put("City", "");
                    json.put("State", "");
                    json.put("Pincode", "");
                    json.put("Agent_Code", "");
                    json.put("Status", "close");
                    jsonObject.put("Rerecord-" + i, json);
                }
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    public static String getlocation(String lati,String longi){
      Context context=null;

        try{


        }catch (Exception e){

        }




        return "location";
    }
}
