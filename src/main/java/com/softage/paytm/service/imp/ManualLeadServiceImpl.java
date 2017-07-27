package com.softage.paytm.service.imp;

import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.*;
import com.softage.paytm.service.ManualLeadService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
@Service
public class ManualLeadServiceImpl implements ManualLeadService {
    @Inject
    private ManualLeadDao manualLeadDao;

    private static final Logger logger = LoggerFactory.getLogger(ManualLeadServiceImpl.class);

    @Autowired
    private AgentPaytmDao agentPaytmDao;


    @Override
    public List getAgentLeadDetails() {

        ArrayList<JSONObject> listArray = new ArrayList<JSONObject>();
        try {
            List<Object[]> result = manualLeadDao.getAgentDetails();
            for (Object[] s : result) {
                JSONObject listjson = new JSONObject();
                listjson.put("jobid", s[0]);
                listjson.put("PhoneNumber", s[1]);
                listjson.put("customerid", s[2]);
                listjson.put("agentCode", s[3]);
                listjson.put("agentname", s[4]);
                listjson.put("customeraddress", s[5]);
                listjson.put("customername", s[6]);

                String agentPincode = manualLeadDao.getAgentPincode(s[3].toString());
                listjson.put("agentPincode", agentPincode);

                //added by prakash
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy ");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
                String visitTime = dateFormat.format(s[7]);
                String visitDate = dateFormat1.format(s[7]);
                String agentVisitTime = dateFormat2.format(s[7]);

                List<String> dateList = new ArrayList<String>();
                dateList.add(visitDate);
                for (int i = 1; i < 3; i++) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateFormat1.parse(visitDate));
                    c.add(Calendar.DATE, i);
                    String nextDay = dateFormat1.format(c.getTime());
                    dateList.add(nextDay);
                }
                listjson.put("allocatedTime", visitTime);
                listjson.put("allocationDate", dateList);
                listjson.put("allocationTime", agentVisitTime);

                listArray.add(listjson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listArray;
    }

    @Override
    public JSONObject getAgentCode(String allocatedTime, String agentPincode) {
        return manualLeadDao.GetAgentsCode(allocatedTime, agentPincode);
    }

    @Override
    public String updateAgentsBycustUid(int cust_uid, String agentCode, String lastAgent, String userId, String newAllocationDateTime) {

        String message = null;
        try {
            message = manualLeadDao.updateAgentsByCustUid(cust_uid, agentCode, lastAgent, userId, newAllocationDateTime);
            List<Object[]> allocateList = manualLeadDao.getAllocateDetails(cust_uid);

            for (Object[] s : allocateList) {
                int pincode = (int) s[0];
                String address = (String) s[1];
                String customerPhone = (String) s[2];
                String alternatePhone1 = (String) s[3];
                String appointment_Date = (String) s[5];
                String appointment_Time = (String) s[6];
                //String appointmentId = (String) s[7];
                int cirCode = (int) s[8];
                String customerName = (String) s[9];
                String jobNumber = (String) s[10];
                String custext = null;
                PaytmagententryEntity paytmagententryEntity = manualLeadDao.findByPrimaryKey(agentCode);
                String agentMobileNumber = paytmagententryEntity.getAphone();
                String loginId = null;
                String custId = String.valueOf(cust_uid);
                if (!"error".equalsIgnoreCase(jobNumber)) {
                    String text = "Dear Agent Job No- " + jobNumber + "" +
                            " , Your visit is fixed at " + appointment_Date
                            + " " + appointment_Time + " with " + customerName + " Address- " +
                            "" + address + " " + pincode + " Contact nos- " +
                            "" + alternatePhone1 + " , " + alternatePhone1 + " Please See Leads in App ";
                    PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = manualLeadDao.getByloginId(agentCode);
                    if (paytmdeviceidinfoEntity != null) {
                        loginId = paytmdeviceidinfoEntity.getLoginId();
                    }


                    if (loginId != null) {
                        String res2 = saveTblNotificationLogEntity(text, agentCode, paytmdeviceidinfoEntity);
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");


                        if (cirCode == 13 || cirCode == 14) {
                            custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                    + customerPhone + " has been confirmed. Your Reference ID id " + cust_uid + " Our Agent will be visiting on " +
                                    appointment_Date + " " + appointment_Time + ":00 hrs, kindly Available";
                            String res3 = saveSmsSendLog(customerPhone, custId, custext, "1", "4");

                        } else {
                            custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                    + customerPhone + " has been confirmed. Your Reference ID id " + cust_uid + " Our Agent will be visiting on " +
                                    appointment_Date + " " + appointment_Time + ":00 hrs, kindly be ready with your photo ID & address proof ";

                            if (alternatePhone1 == null || alternatePhone1 == "") {
                                String res3 = saveSmsSendLog(customerPhone, custId, custext, "1", "4");
                            } else {
                                String res4 = saveSmsSendLog(alternatePhone1, custId, custext, "1", "4");
                            }
                        }

                        //  String res3 = saveSmsSendLog("8588998890", map.get("custId"), custext, "1", "4");
                    } else {
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");
                        if (cirCode == 13 || cirCode == 14) {
                            custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                    + customerPhone + " has been confirmed. Your Reference ID id " + cust_uid + " Our Agent will be visiting on " +
                                    appointment_Date + " " + appointment_Time + ":00 hrs, kindly Available";

                            String res3 = saveSmsSendLog(customerPhone, custId, custext, "1", "4");
                        } else {
                            custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                    + customerPhone + " has been confirmed. Your Reference ID id " + cust_uid + " Our Agent will be visiting on " +
                                    appointment_Date + " " + appointment_Time + ":00 hrs, kindly be ready with your photo ID & address proof ";
                            if (alternatePhone1 == null || alternatePhone1 == "") {
                                String res3 = saveSmsSendLog(customerPhone, custId, custext, "1", "4");
                            } else {
                                String res4 = saveSmsSendLog(alternatePhone1, custId, custext, "1", "4");
                            }
                        }


                        //  String res3 = saveSmsSendLog("8588998890", map.get("custId"), custext, "1", "4");
                    }


                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return message;
    }

    @Override
    public JSONObject deAllocateLead(int custId) {

        String result = manualLeadDao.deAllocateLead(custId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultMessage", result);
        return resultJson;
    }

    public String saveSmsSendLog(String agentMobileNumber, String agentCode, String text, String reciveCode1, String processCode1) {

        String result = null;
        try {
            int reciveCode = Integer.parseInt(reciveCode1);
            int processCode = Integer.parseInt(processCode1);
            ReceiverMastEntity receiverMastEntity = manualLeadDao.getRecivedByCode(reciveCode);
            ProcessMastEntity processMastEntity = manualLeadDao.getProcessByCode(processCode);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(agentMobileNumber);
            smsSendlogEntity.setReceiverId(agentCode);
            smsSendlogEntity.setSmsText(text);
            smsSendlogEntity.setSmsDelivered("N");
            smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setImportDate(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            result = manualLeadDao.saveSmsSendEntity(smsSendlogEntity);
        } catch (Exception e) {
            e.printStackTrace();
            result = "err";
        }
        return result;
    }

    public String saveTblNotificationLogEntity(String text, String agentCode, PaytmdeviceidinfoEntity paytmdeviceidinfoEntity) {
        String result = null;
        String notificationResult = null;
        try {
            TblNotificationLogEntity tblNotificationLogEntity = new TblNotificationLogEntity();
            tblNotificationLogEntity.setNotificationType("Leads");
            tblNotificationLogEntity.setNotificationText(text);
            tblNotificationLogEntity.setPaytmdeviceidinfoBynotificationLoginid(paytmdeviceidinfoEntity);
            //   tblNotificationLogEntity.setNotificationLoginid(agentCode);
            tblNotificationLogEntity.setNotificationSenddt(new Timestamp(new Date().getTime()));
            result = manualLeadDao.saveTabNotification(tblNotificationLogEntity);

            if ("done".equals(result)) {
                // comment this code for testing
                notificationResult = sendNotification("Leads", text, paytmdeviceidinfoEntity.getDeviceId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "err";
        }
        return notificationResult;
    }

    private String sendNotification(String notificationType, String notificationText, String deviceId) {
        String googleAppId = "AIzaSyBOW6Q1BE1Z7saniilA415tKISi1c4npyw";
        String messageKey = "message";
        String result = "";

        try {

            Sender sender = new Sender(googleAppId);
            Message message = new Message.Builder().timeToLive(30)
                    .delayWhileIdle(true)
                    .addData(messageKey, notificationText)
                    .addData("title", "Leads")
                    .addData("time", new Date().toString()).build();
            logger.info("device Id >>>   " + deviceId);
            Result result1 = sender.send(message, deviceId, 1);
            result = result1.toString();
            logger.info("Notification send successfully to the Agent >>>  " + result);
            System.out.println(result);
        } catch (Exception e) {
            logger.error("Notification not send to Agent>>>  ", e);
            e.printStackTrace();
        }
        return result;
    }
}
