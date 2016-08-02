package com.softage.paytm.service.imp;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.softage.paytm.dao.*;
import com.softage.paytm.models.*;
import com.softage.paytm.service.PostCallingService;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */

@Service
public class PostCallingServiceImp implements PostCallingService {
    @Autowired
    private PostCallingDao postCallingDao;
    @Autowired
    private AgentPaytmDao agentPaytmDao;
    @Autowired
    private AllocationDao allocationDao;
    @Autowired
    private PaytmDeviceDao paytmDeviceDao;
    @Autowired
    private SmsSendLogDao smsSendLogDao;


    private static final Logger logger = LoggerFactory.getLogger(PostCallingServiceImp.class);

    @Override
    public String saveCallingData(Map<String, String> map) {
        String status = map.get("status");
        String tcStatus = "U";
        String result = null;
        Date parsedUtilDate = null;
        java.sql.Date checkVisitDate = null;
        java.sql.Date visitDate = null;
        String result1 = null;
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            TelecallMastEntity telecallMastEntity = postCallingDao.getByPrimaryKey(map.get("number"));
            if (telecallMastEntity == null) {
                for (int i = 1; i <= 5; i++) {
                    telecallMastEntity = postCallingDao.getByPrimaryKey(map.get("number"));
                    if (telecallMastEntity != null) {
                        break;
                    }
                }
            }
            TelecallLogEntity telecallLogEntity = new TelecallLogEntity();
            telecallLogEntity.setTcCustomerphone(map.get("number"));
            telecallLogEntity.setTcCallBy(map.get("importby"));
            telecallLogEntity.setTcCallStatus(map.get("status"));
            telecallLogEntity.setTcCallTime(new Timestamp(new Date().getTime()));
            telecallLogEntity.setTelecallMastByTcCustomerphone(telecallMastEntity);
            result = postCallingDao.saveTeleCallLog(telecallLogEntity);
            if ("done".equalsIgnoreCase(result)) {
                result1 = "done";
            }
            if (map.get("visitDate") != null) {
                System.out.println(map.get("visitDate"));
                parsedUtilDate = formater.parse(map.get("visitDate"));
                visitDate = new java.sql.Date(parsedUtilDate.getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_WEEK, 5);
                checkVisitDate = new java.sql.Date(calendar.getTimeInMillis());
            }
            if ("CON".equals(status) && visitDate.getTime() > checkVisitDate.getTime()) {
                byte s = 9;
                tcStatus = "D";
                telecallMastEntity.setTmAttempts(s);
                telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                telecallMastEntity.setTmTeleCallStatus(tcStatus);
                telecallMastEntity.setTmLastCallStatus(map.get("status"));
                postCallingDao.updateTeleCall(telecallMastEntity);
                result = "Customer Rejected because Date more then 5 days ";
            } else if ("CON".equals(status)) {
                tcStatus = "D";
                result = saveCustomer(map);
                if ("JOB ALLOCATED".equalsIgnoreCase(result)) {
                    result1 = "done";
                }
                if ("NO AGENT AVAILABLE".equalsIgnoreCase(result)) {
                    result1 = "done";
                }
                if("err".equalsIgnoreCase(result)){
                    result="Record not Submited try again";
                    result1="error";
                }
            }
            byte s = (byte) (telecallMastEntity.getTmAttempts() + 1);
            //   TelecallMastEntity telecallMastEntity1=new TelecallMastEntity();
            //     telecallMastEntity.setTmCustomerPhone(map.get("number"));
            if ("done".equalsIgnoreCase(result1)) {
                telecallMastEntity.setTmAttempts(s);
                telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                telecallMastEntity.setTmTeleCallStatus(tcStatus);
                telecallMastEntity.setTmLastCallStatus(map.get("status"));
                postCallingDao.updateTeleCall(telecallMastEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String sendsmsService() {
        String result = null;
        List<SmsSendlogEntity> smsSendlogEntityList = null;
        try {
            smsSendlogEntityList = smsSendLogDao.getSendData();
            for (SmsSendlogEntity smsSendlogEntity : smsSendlogEntityList) {

                String mobileno = smsSendlogEntity.getMobileNumber();
                String smstext = smsSendlogEntity.getSmsText();
                result = sendSms(mobileno, smstext);
                if (!"err".equalsIgnoreCase(result)) {

                    smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
                    smsSendlogEntity.setDeliveryStatus(result);
                    smsSendlogEntity.setSmsDelivered("Y");
                    smsSendLogDao.updateSmsLogData(smsSendlogEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("sms not send  ", e);
        }
        return result;
    }

    @Override
    public String updateTeleCall(TelecallMastEntity telecallMastEntity) {
        return postCallingDao.updateTeleCall(telecallMastEntity);
    }

    @Override
    public TelecallMastEntity getByPrimaryKey(String phoneNumber) {
        return postCallingDao.getByPrimaryKey(phoneNumber);
    }

    @Override
    public String save(ReOpenTaleCallMaster openTaleCallMaster) {
        return postCallingDao.save(openTaleCallMaster);
    }

    @Override
    public RemarkMastEntity getByPrimaryCode(String key) {
        return postCallingDao.getByPrimaryCode(key);
    }

    @Override
    public List<RemarkMastEntity> remarkList() {
        return postCallingDao.remarkList();
    }

    private String sendSms(String mobileno, String text) {
        String result = null;
        SmsSendlogEntity smsSendlogEntity = null;
        BufferedReader in = null;
        HttpURLConnection con = null;
        //  text="Hell Hai";
        //  String url = "http://etsdom.kapps.in/webapi/softage/api/softage_c2c.py?auth_key=hossoftagepital&customer_number=+918588875378&agent_number=+918882905998";
        // String url="http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=8882905998&text= hello this is test mesg";
        //String url = "http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=" + mobileno + "&text=" + text;
        String url = "http://www.mysmsapp.in/api/push";
        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build())
        {
        try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(RequestBuilder.post(url)
                .addParameter(new BasicNameValuePair("apikey", "56274f9a48b66"))
                .addParameter(new BasicNameValuePair("route", "trans5"))
                .addParameter(new BasicNameValuePair("sender", "SPAYTM"))
                .addParameter(new BasicNameValuePair("mobileno", mobileno))
                .addParameter(new BasicNameValuePair("text", text)).build())) {
            InputStream is = closeableHttpResponse.getEntity().getContent();
            in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            result = response.toString();
        } catch (IOException io) {
            io.printStackTrace();
            result="err";

        }
    }
            catch (Exception e){
                e.printStackTrace();
                result="err";
            }
         /*  URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setReadTimeout(15 * 1000);
     //       con.connect();
            con.setRequestMethod("POST");
            int responseCode = con.getResponseCode();
            logger.info("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code :" + responseCode);
            logger.info("Response Code :" + responseCode);
           in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //    in = new BufferedReader(new InputStreamReader(is);
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            result = response.toString();*/


        return result;


    }


    public String saveCustomer(Map<String, String> map) {
        String result = null;
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            PaytmcustomerDataEntity paytmcustomerDataEntity = new PaytmcustomerDataEntity();
            paytmcustomerDataEntity.setPcdCustomerPhone(map.get("number"));
            paytmcustomerDataEntity.setPcdName(map.get("name"));
            paytmcustomerDataEntity.setAllocationStatus(map.get("status"));
            paytmcustomerDataEntity.setPcdAddress(map.get("address"));
            paytmcustomerDataEntity.setPcdArea(map.get("area"));
            paytmcustomerDataEntity.setPcdCity(map.get("city"));
            paytmcustomerDataEntity.setPcdEmailId(map.get("emailId"));
            paytmcustomerDataEntity.setPcdImportBy(map.get("importby"));
            paytmcustomerDataEntity.setPcdImportDate(new Timestamp(new Date().getTime()));
            paytmcustomerDataEntity.setPcdImportType(map.get("importType"));
            paytmcustomerDataEntity.setPcdLandmark(map.get("landmark"));
            paytmcustomerDataEntity.setPcdPincode(map.get("pinCode"));
            paytmcustomerDataEntity.setPcdState(map.get("state"));
            Date parsedUtilDate = formater.parse(map.get("visitDate"));
            java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
            paytmcustomerDataEntity.setPcdVisitDate(sqltDate);
            System.out.println("Time " + map.get("visitTime"));
            paytmcustomerDataEntity.setPcdVisitTIme(new Time(Integer.parseInt(map.get("visitTime")), 0, 0));
            result = postCallingDao.savePaytmCustomer(paytmcustomerDataEntity);
            if (result != null && "done".equalsIgnoreCase(result)) {
                result = saveAppoinment(map, paytmcustomerDataEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "err";
        }

        return result;
    }

    public String saveAppoinment(Map<String, String> map, PaytmcustomerDataEntity paytmcustomerDataEntity) {
        String result = null;
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            AppointmentMastEntity appointmentMastEntity = new AppointmentMastEntity();
            Date parsedUtilDate = formater.parse(map.get("visitDate"));
            java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
            appointmentMastEntity.setAppointmentDate(sqltDate);
            appointmentMastEntity.setAppointmentTime(new Time(Integer.parseInt(map.get("visitTime")), 0, 0));
            appointmentMastEntity.setImportDate(new Timestamp(new Date().getTime()));
            appointmentMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);

            result = postCallingDao.saveAppointment(appointmentMastEntity);
            if ("err".equalsIgnoreCase(result)) {
                for (int i = 1; i <= 5; i++) {
                    result = postCallingDao.saveAppointment(appointmentMastEntity);
                    if ("done".equals(result)) {
                        break;
                    }
                }
            }
            if ("done".equalsIgnoreCase(result)) {
                result = jobAllocated(paytmcustomerDataEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error to save Appointmantdata");
        }
        return result;
    }

    public String jobAllocated(PaytmcustomerDataEntity paytmcustomerDataEntity) {
        String result = null;
        String name = null;
        String address = null;
        String city = null;
        String area = null;
        String pinCode = null;
        java.sql.Date date = null;
        Time time = null;
        java.sql.Date date1 = null;
        String agentCode = "0";
        String confirmationAllowed = "";
        String finalconfirmation = "";

        int maxAllocation = 15;
        String loginId = null;
        String customerNo = "";
        long appointmentId = 0;

        AppointmentMastEntity appointmentMastEntity = null;

        if (paytmcustomerDataEntity != null) {
            customerNo = paytmcustomerDataEntity.getPcdCustomerPhone();

            appointmentMastEntity = postCallingDao.getByCustomerNuber(customerNo);
            if (appointmentMastEntity == null) {
                for (int i = 1; i <= 5; i++) {
                    appointmentMastEntity = postCallingDao.getByCustomerNuber(customerNo);
                    if (appointmentMastEntity != null) {
                        break;
                    }
                }
            }
        }
        if (appointmentMastEntity != null) {
            appointmentId = appointmentMastEntity.getAppointmentId();
            //        String result1= postCallingDao.callJobAllocatedProcedure(appointmentId, customerNo, "0");
        }


        long appointmentId1 = postCallingDao.checkAppointmentId(appointmentId);
        if (appointmentId1 == 0) {

            //   Map<String,Object> dataMap= postCallingDao.getData(appointmentId,mobileNo);

            name = paytmcustomerDataEntity.getPcdName();
            address = paytmcustomerDataEntity.getPcdAddress();
            area = paytmcustomerDataEntity.getPcdArea();
            city = paytmcustomerDataEntity.getPcdCity();
            pinCode = paytmcustomerDataEntity.getPcdPincode();
            date = paytmcustomerDataEntity.getPcdVisitDate();
            time = paytmcustomerDataEntity.getPcdVisitTIme();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_WEEK, 3);
            java.sql.Date loopdate = new java.sql.Date(calendar.getTimeInMillis());

            while (date.getTime() <= loopdate.getTime()) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                calendar1.add(Calendar.DAY_OF_WEEK, 1);
                date1 = new java.sql.Date(calendar1.getTimeInMillis());

                if ("0".equals(agentCode)) {
                    agentCode = postCallingDao.getAgentCode(pinCode, date, date1, maxAllocation, agentCode);
                    confirmationAllowed = "Y";
                    finalconfirmation = "W";
                } else {
                    agentCode = postCallingDao.getAgentCode(pinCode, date, date1, maxAllocation, agentCode);
                    confirmationAllowed = "N";
                    finalconfirmation = "W";
                }

                if (agentCode == null) {
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date);
                    calendar2.add(Calendar.DATE, 1);
                    date = new java.sql.Date(calendar2.getTimeInMillis());
                } else {
                    break;
                }
            }
            if (agentCode != null) {
                try {
                    int jobNumber = 0;
                    PaytmagententryEntity paytmagententryEntity = agentPaytmDao.findByPrimaryKey(agentCode);
                    String agentMobileNumber = paytmagententryEntity.getAphone();
                    String result1 = saveAllocationMast(confirmationAllowed, finalconfirmation, date, time, appointmentMastEntity, paytmagententryEntity, paytmcustomerDataEntity);

                    AllocationMastEntity allocationMastEntity1 = allocationDao.findByAgentCode(appointmentId, agentCode);
                    if (allocationMastEntity1 == null) {
                        for (int i = 0; i <= 5; i++) {
                            allocationMastEntity1 = allocationDao.findByAgentCode(appointmentId, agentCode);
                            if (allocationMastEntity1 != null) {
                                break;
                            }
                        }
                    }

                    if (allocationMastEntity1 != null) {
                        jobNumber = allocationMastEntity1.getId();
                    }
                    String text = "Dear Agent Job No-" + jobNumber + "" +
                            ", Your visit is fixed at " + date
                            + " " + time + "with " + name + " Address-" +
                            "" + address + " " + pinCode + "Contact no-" +
                            "" + customerNo + " Please See Leads in App";

                    PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = paytmDeviceDao.getByloginId(agentCode);
                    if (paytmdeviceidinfoEntity != null) {
                        loginId = paytmdeviceidinfoEntity.getLoginId();
                    }

                    if (loginId != null) {
                        String res2 = saveTblNotificationLogEntity(text, agentCode, paytmdeviceidinfoEntity);
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text);
                    } else {
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text);
                    }

                    result = "JOB ALLOCATED";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                result = "NO AGENT AVAILABLE";
            }
        } else {
            result = "JOB ALREADY ALLOCATED";
        }


        return result;
    }

    public String saveAllocationMast(String confirmationAllowed, String finalconfirmation, Date date, Time time, AppointmentMastEntity appointmentMastEntity, PaytmagententryEntity paytmagententryEntity, PaytmcustomerDataEntity paytmcustomerDataEntity) {
        String vistDate = date.toString();
        String visitTime = time.toString();
        String result = null;
        try {
            String allocationDate1 = vistDate + " " + visitTime;
            RemarkMastEntity remarkMastEntity = postCallingDao.getByPrimaryCode("U");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date convertedDate = dateFormat.parse(allocationDate1);
            AllocationMastEntity allocationMastEntity = new AllocationMastEntity();
            allocationMastEntity.setAppointmentMastByAppointmentId(appointmentMastEntity);
            allocationMastEntity.setPaytmagententryByAgentCode(paytmagententryEntity);
            allocationMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);
            allocationMastEntity.setAllocationDatetime(new Timestamp(convertedDate.getTime()));
            allocationMastEntity.setVisitDateTime(new Timestamp(convertedDate.getTime()));
            allocationMastEntity.setImportBy(paytmcustomerDataEntity.getPcdImportBy());
            allocationMastEntity.setImportDate(new Timestamp(new Date().getTime()));
            allocationMastEntity.setConfirmationDatetime(new Timestamp(convertedDate.getTime()));
            allocationMastEntity.setConfirmation(confirmationAllowed);
            allocationMastEntity.setFinalConfirmation(finalconfirmation);
            allocationMastEntity.setSmsSendDatetime(new Timestamp(convertedDate.getTime()));
            allocationMastEntity.setKycCollected("P");
            allocationMastEntity.setConfirmation("W");
            allocationMastEntity.setRemarkMastByRemarksCode(remarkMastEntity);

            result = allocationDao.saveAllocation(allocationMastEntity);
            if(result=="err"){
                for (int i = 1; i <= 5; i++) {
                    result = allocationDao.saveAllocation(allocationMastEntity);
                    if ("done".equals(result)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            result = "err";

        }
        return result;

    }

    public String saveSmsSendLog(String agentMobileNumber, String agentCode, String text) {

        String result = null;
        try {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(2);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(2);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(agentMobileNumber);
            smsSendlogEntity.setReceiverId(agentCode);
            smsSendlogEntity.setSmsText(text);
            smsSendlogEntity.setSmsDelivered("N");
            smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setImportDate(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            result = postCallingDao.saveSmsSendEntity(smsSendlogEntity);
        } catch (Exception e) {
            e.printStackTrace();
            result = "err";
        }
        return result;
    }

    public String saveTblNotificationLogEntity(String text, String agentCode, PaytmdeviceidinfoEntity paytmdeviceidinfoEntity) {
        String result = null;
        String notificationResult=null;
        try {
            TblNotificationLogEntity tblNotificationLogEntity = new TblNotificationLogEntity();
            tblNotificationLogEntity.setNotificationType("Leads");
            tblNotificationLogEntity.setNotificationText(text);
            tblNotificationLogEntity.setPaytmdeviceidinfoBynotificationLoginid(paytmdeviceidinfoEntity);
            //   tblNotificationLogEntity.setNotificationLoginid(agentCode);
            tblNotificationLogEntity.setNotificationSenddt(new Timestamp(new Date().getTime()));
            result = postCallingDao.saveTabNotification(tblNotificationLogEntity);

            if ("done".equals(result)) {
                notificationResult=sendNotification("Leads", text, paytmdeviceidinfoEntity.getDeviceId());
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
            logger.info("device Id >>>   "+deviceId);
            Result result1 = sender.send(message, deviceId, 1);
            result = result1.toString();
            logger.info("Notification send successfully to the Agent >>>  "+result);
            System.out.println(result);
        } catch (Exception e) {
            logger.error("Notification not send to Agent>>>  ",e);
            e.printStackTrace();
        }
        return  result;
    }

   /*     String result = null;
        SmsSendlogEntity smsSendlogEntity = null;
        BufferedReader in=null;
        HttpURLConnection con=null;
        //  String url = "http://etsdom.kapps.in/webapi/softage/api/softage_c2c.py?auth_key=hossoftagepital&customer_number=+918588875378&agent_number=+918882905998";
        // String url="http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=8882905998&text= hello this is test mesg";
        String url = "http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=" + mobileno + "&text=" + text;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setReadTimeout(15*1000);
            //           con.connect();
            con.setRequestMethod("POST");
            int responseCode = con.getResponseCode();
            logger.info("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code :" + responseCode);
            logger.info("Response Code :" + responseCode);
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            result = response.toString();
        } catch (Exception e) {
            logger.error("enable to send notification  ", e);
            result = "err";
            e.printStackTrace();
        }finally {
            try{
                in.close();
                //    con.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;*/

}






         /*
                       String vistDate = date.toString();
                       String visitTime = time.toString();
                       String allocationDate1 = vistDate + " " + visitTime;
                       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                       Date convertedDate = dateFormat.parse(allocationDate1);
                       AllocationMastEntity allocationMastEntity = new AllocationMastEntity();
                       allocationMastEntity.setAppointmentMastByAppointmentId(appointmentMastEntity);
                       allocationMastEntity.setPaytmagententryByAgentCode(paytmagententryEntity);

                       allocationMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);
                       allocationMastEntity.setAllocationDatetime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setVisitDateTime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setImportBy("Afjal");
                       allocationMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                       allocationMastEntity.setConfirmationDatetime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setConfirmation(confirmationAllowed);
                       allocationMastEntity.setFinalConfirmation(finalconfirmation);
                       allocationMastEntity.setSmsSendDatetime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setKycCollected("N");
                       allocationMastEntity.setRemarkMastByRemarksCode(remarkMastEntity);
                       String result1 = allocationDao.saveAllocation(allocationMastEntity);*/