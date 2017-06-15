package com.softage.paytm.service.imp;

import antlr.StringUtils;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.softage.paytm.dao.*;
import com.softage.paytm.models.*;
import com.softage.paytm.service.PaytmMasterService;
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
import org.apache.poi.util.StringUtil;
import org.json.simple.JSONObject;
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
import java.util.*;

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
    @Autowired
    private PaytmMasterDao paytmMasterDao;


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
        PaytmMastEntity paytmMastEntity = null;
        try {
            int custId = Integer.parseInt(map.get("custId"));
            paytmMastEntity = paytmMasterDao.getPaytmMasterData(custId);
            TelecallMastEntity telecallMastEntity = postCallingDao.getByReferenceId(custId);
            if (telecallMastEntity == null) {
                for (int i = 1; i <= 5; i++) {
                    telecallMastEntity = postCallingDao.getByReferenceId(custId);
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
            telecallLogEntity.setPaytmMastEntity(paytmMastEntity);
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
                calendar.add(Calendar.DAY_OF_WEEK, 3);
                checkVisitDate = new java.sql.Date(calendar.getTimeInMillis());
            }
            if ("CON".equals(status) && visitDate.getTime() > checkVisitDate.getTime()) {
                byte s = 4;
                tcStatus = "R";
                telecallMastEntity.setTmAttempts(s);
                telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                telecallMastEntity.setTmTeleCallStatus(tcStatus);
                telecallMastEntity.setTmLastCallStatus(map.get("status"));
                postCallingDao.updateTeleCall(telecallMastEntity);
                result = "Customer Rejected because Appointment Date more then 3 days ";
            } else if ("CON".equals(status)) {
                tcStatus = "D";
                // result = saveCustomer(map);
                result = saveCustomerData(map);
                if ("JOB ALLOCATED".equalsIgnoreCase(result)) {
                    result1 = "done";
                }
                if ("NO AGENT AVAILABLE".equalsIgnoreCase(result)) {
                    result1 = "NoAgent";
                }
                if ("err".equalsIgnoreCase(result)) {
                    result = "Record not Submited try again";
                    result1 = "error";
                    tcStatus = "W";
                }
            }
            byte s = (byte) (telecallMastEntity.getTmAttempts() + 1);
            //   TelecallMastEntity telecallMastEntity1=new TelecallMastEntity();
            //     telecallMastEntity.setTmCustomerPhone(map.get("number"));

            if (!"CON".equalsIgnoreCase(status) && s == 4) {

                PaytmMastEntity paytmMastData = paytmMasterDao.getPaytmMasterData(custId);
                paytmMastEntity.setFinalStatus("close");
                paytmMasterDao.updatePaytmMast(paytmMastEntity);


            }


            if ("done".equalsIgnoreCase(result1)) {
                telecallMastEntity.setTmAttempts(s);
                telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                telecallMastEntity.setTmTeleCallStatus(tcStatus);
                telecallMastEntity.setTmLastCallStatus(map.get("status"));
                postCallingDao.updateTeleCall(telecallMastEntity);
            }

            if ("NoAgent".equalsIgnoreCase(result1)) {
                telecallMastEntity.setTmAttempts(s);
                telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                telecallMastEntity.setTmTeleCallStatus("NA");
                telecallMastEntity.setTmLastCallStatus(map.get("status"));
                postCallingDao.updateTeleCall(telecallMastEntity);
                PaytmMastEntity paytmMastData = paytmMasterDao.getPaytmMasterData(custId);
                paytmMastEntity.setFinalStatus("close");
                paytmMasterDao.updatePaytmMast(paytmMastEntity);
            }
        } catch (Exception e) {
            logger.error("job allocate error", e);
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

    @Override
    public JSONObject getAvailableslot(String date, Set<String> agents, String time, String keydate) {

        String result = "Booked";
        boolean flag = false;
        JSONObject returnObj = new JSONObject();
        for (String agent : agents) {
            try {
                String dateTime = date + " " + time + ":00:00";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date convertedDate = dateFormat.parse(dateTime);
                Timestamp datetime = new Timestamp(convertedDate.getTime());
                String result1 = allocationDao.findByAllocationTime(agent, dateTime);
                if (result1.equalsIgnoreCase("Navl")) {
                    flag = true;
                }

               /* AllocationMastEntity allocationMastEntity = allocationDao.findByAllocationTime(agent, datetime);
                if (allocationMastEntity == null) {
                    flag = true;
                }*/
            } catch (Exception e) {
                logger.error("available slot", e);
            }


        }
        if (flag) {
            result = "Available";
        }
        returnObj.put(keydate, result);


        return returnObj;
    }

    private String sendSms(String mobileno, String text) {
        String result = null;
        SmsSendlogEntity smsSendlogEntity = null;
        BufferedReader in = null;
        HttpURLConnection con = null;
        //     String url="";
        //  text="Hell Hai";
        //  String url = "http://etsdom.kapps.in/webapi/softage/api/softage_c2c.py?auth_key=hossoftagepital&customer_number=+918588875378&agent_number=+918882905998";
        // String url="http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=8882905998&text= hello this is test mesg";
        //String url = "http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=" + mobileno + "&text=" + text;
        //   String url = "http://www.mysmsapp.in/api/push";
        // 5732df1bc7011
        //new BasicNameValuePair("apikey", "56274f9a48b66")
        //new BasicNameValuePair("sender", "SPAYTM")

     //   String url = "http://www.mysmsapp.in/api/push.json?apikey=5732df1bc7011&sender=VODAFN&mobileno=" + mobileno + "&text=" + text;



        String url = "http://www.mysmsapp.in/api/push.json";
        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(RequestBuilder.post(url)
                    .addParameter(new BasicNameValuePair("apikey", "5732df1bc7011"))
                    .addParameter(new BasicNameValuePair("sender", "VODAFN"))
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

                logger.info("sent sms  mobile Number =  " + mobileno);
            } catch (IOException io) {
                logger.error("send sms ", io);
                result = "err";

            }
        } catch (Exception e) {
            logger.error("send sms ", e);
            result = "err";
        }

        return result;


    }


    public String saveCustomer(Map<String, String> map) {
        String result = null;
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            int custId = Integer.parseInt(map.get("custId"));
            PaytmcustomerDataEntity paytmcustomerDataEntity = new PaytmcustomerDataEntity();
            paytmcustomerDataEntity.setCust_uid(custId);
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
            paytmcustomerDataEntity.setSimType(map.get("simType"));
            paytmcustomerDataEntity.setCoStatus(map.get("co_status"));
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


    public String saveCustomerData(Map<String, String> map) {
        Map<String, String> allocation_map = new HashMap<String, String>();
        String agentCode = null;
        String appoinmentId = null;
        String confirmationAllowed = "";
        String finalconfirmation = "";
        String custext="";

        PaytmagententryEntity paytmagententryEntity = null;
        String loginId = "";
        int custId = 0;

        String allocationId = null;

        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        String customerAlternativeNo = "";
        String agentMobileNumber = "";
        String result = "";
        String jobNumber = "";
        try {
            custId = Integer.parseInt(map.get("custId"));
            PaytmMastEntity paytmMastEntity = paytmMasterDao.getPaytmMasterData(custId);


            String number = map.get("number");
            String name = map.get("name");
            String status = map.get("status");
            String address = map.get("address");
            String area = map.get("area");
            String city = map.get("city");
            String emailId = map.get("emailId");
            String importby = map.get("importby");
            String importType = map.get("importType");
            String simType = map.get("simType");
            String co_status = map.get("co_status");
            String pincode = map.get("pinCode");
            String state = map.get("state");
            String pcdvisitTime = map.get("visitDate");
            java.util.Date parsedUtilDate = formater.parse(map.get("visitDate"));
            java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
            map.put("visitDate", sqltDate.toString());
            String visitTime = map.get("visitTime");

            appoinmentId = postCallingDao.callJobAllocatedProcedure(map);


            if (appoinmentId.equalsIgnoreCase("error")) {
                for (int i = 0; i < 3; i++) {
                    appoinmentId = postCallingDao.callJobAllocatedProcedure(map);
                    if (!"error".equalsIgnoreCase(jobNumber)) {
                        break;
                    }
                }

            }

            String allocationDate1 = sqltDate + " " + visitTime;
            agentCode = postCallingDao.getAgentCode(pincode, sqltDate, allocationDate1, 0, "0");

            confirmationAllowed = "Y";
            finalconfirmation = "W";

            if (agentCode != null && !(agentCode.equalsIgnoreCase("null"))) {
                paytmagententryEntity = agentPaytmDao.findByPrimaryKey(agentCode);
                agentMobileNumber = paytmagententryEntity.getAphone();
                allocation_map.put("appointmentID", appoinmentId);
                allocation_map.put("agentcode", agentCode);
                allocation_map.put("custUID", map.get("custId"));
                allocation_map.put("mobileNo", number);
                //map.put("allocationTime",);  use now()
                allocation_map.put("visitDatetime", allocationDate1);
                allocation_map.put("importBy", importby);
                //map.put("importDatetime") use now()
                allocation_map.put("confirmationDatetime", allocationDate1);
                // allocation_map.put("sendSMSDatetime",) use now()
                allocation_map.put("finalConfirmation", "W");
                allocation_map.put("confirmation", "W");
                allocation_map.put("confirmationAllowed", "Y");
                allocation_map.put("kycCollected", "P");
                allocation_map.put("remarkCode", "U");
                allocation_map.put("spokeCode", paytmagententryEntity.getAspokecode());
                jobNumber = postCallingDao.JobAllocatedProcedure(allocation_map);
                if (jobNumber.equalsIgnoreCase("error")) {
                    for (int i = 0; i < 3; i++) {
                        jobNumber = postCallingDao.JobAllocatedProcedure(allocation_map);
                        if (!"error".equalsIgnoreCase(jobNumber)) {
                            break;
                        }
                    }

                }


                if (paytmMastEntity != null) {
                    customerAlternativeNo = paytmMastEntity.getAlternatePhone1();
                    if (customerAlternativeNo != null && !customerAlternativeNo.equalsIgnoreCase("")) {
                        number = customerAlternativeNo;
                    }
                }

                String text = "Dear Agent Job No- " + jobNumber + "" +
                        " , Your visit is fixed at " + pcdvisitTime
                        + " " + visitTime + " with " + name + " Address- " +
                        "" + address + " " + pincode + " Contact nos- " +
                        "" + paytmMastEntity.getAlternatePhone1() + " , " + number + " Please See Leads in App ";
               /* String custext = "Dear Customer  Your CustomerId - " + custId+ " with Request Number " + paytmMastEntity.getCustomerPhone()
                       + " ,   Agent visit date " + pcdvisitTime
                        + "  Time " + visitTime + " Please Available with all documents";

                        'Dear Customer, your request for sim replacement of mobile no.966366666 has
                         been confirmed. Your ref. id is 1012100000 and our agent will reach on 07/02/2017 15:00 hrs.
              kindly be ready with your photo ID & address proof.

*/
               /* String custext = "Dear Customer, your request for SIM replacement for Mobile no "
                        + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                        pcdvisitTime + " " + visitTime + ":00 hrs, kindly be ready with your photo ID & address proof ";*/

/*
                String custext = "Dear Customer, your request for SIM replacement for Mobile no "
                        + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                        pcdvisitTime + " " + visitTime + ":00 hrs, kindly Available";*/

                PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = paytmDeviceDao.getByloginId(agentCode);
                if (paytmdeviceidinfoEntity != null) {
                    loginId = paytmdeviceidinfoEntity.getLoginId();
                }


                if (loginId != null) {
                    String res2 = saveTblNotificationLogEntity(text, agentCode, paytmdeviceidinfoEntity);
                    String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");


                    if (paytmMastEntity.getCirCode() == 13 || paytmMastEntity.getCirCode() == 14) {
                        custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                                pcdvisitTime + " " + visitTime + ":00 hrs, kindly Available";
                        String res3 = saveSmsSendLog(paytmMastEntity.getCustomerPhone(), paytmMastEntity.getCustomerId(), custext, "1", "4");

                    } else {
                        custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                                pcdvisitTime + " " + visitTime + ":00 hrs, kindly be ready with your photo ID & address proof ";

                        if (paytmMastEntity.getAlternatePhone1() == null || paytmMastEntity.getAlternatePhone1() == "") {
                            String res3 = saveSmsSendLog(paytmMastEntity.getCustomerPhone(), paytmMastEntity.getCustomerId(), custext, "1", "4");
                        } else {
                            String res4 = saveSmsSendLog(paytmMastEntity.getAlternatePhone1(), paytmMastEntity.getCustomerId(), custext, "1", "4");
                        }
                    }

                    //  String res3 = saveSmsSendLog("8588998890", map.get("custId"), custext, "1", "4");
                } else {
                    String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");
                    if (paytmMastEntity.getCirCode() == 13 || paytmMastEntity.getCirCode() == 14) {
                       custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                                pcdvisitTime + " " + visitTime + ":00 hrs, kindly Available";

                            String res3 = saveSmsSendLog(paytmMastEntity.getCustomerPhone(), paytmMastEntity.getCustomerId(), custext, "1", "4");
                    } else {
                     custext = "Dear Customer, your request for SIM replacement for Mobile no "
                                + paytmMastEntity.getCustomerPhone() + " has been confirmed. Your Reference ID id " + custId + " Our Agent will be visiting on " +
                                pcdvisitTime + " " + visitTime + ":00 hrs, kindly be ready with your photo ID & address proof ";
                        if (paytmMastEntity.getAlternatePhone1() == null || paytmMastEntity.getAlternatePhone1() == "") {
                            String res3 = saveSmsSendLog(paytmMastEntity.getCustomerPhone(), paytmMastEntity.getCustomerId(), custext, "1", "4");
                        } else {
                            String res4 = saveSmsSendLog(paytmMastEntity.getAlternatePhone1(), paytmMastEntity.getCustomerId(), custext, "1", "4");
                        }
                    }


                    //  String res3 = saveSmsSendLog("8588998890", map.get("custId"), custext, "1", "4");
                }

                result = "JOB ALLOCATED";
                logger.info("Job Allocated to AgentCode  " + agentCode);


            } else {
                result = "NO AGENT AVAILABLE";
            }

        } catch (Exception e) {
            logger.error("Job Allocated error", e);
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
            logger.error("error to save Appointmantdata", e);
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
        int custid = 0;
        String loginId = null;
        String customerNo = "";
        long appointmentId = 0;
        PaytmMastEntity paytmMastEntity = null;

        AppointmentMastEntity appointmentMastEntity = null;


        if (paytmcustomerDataEntity != null) {
            custid = paytmcustomerDataEntity.getCust_uid();

            appointmentMastEntity = postCallingDao.getByCustId(custid);
            if (appointmentMastEntity == null) {
                for (int i = 1; i <= 5; i++) {
                    appointmentMastEntity = postCallingDao.getByCustId(custid);
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

            if ("0".equals(agentCode)) {
                try {
                    String allocationDate1 = date + " " + time;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date convertedDate = dateFormat.parse(allocationDate1);
                    agentCode = postCallingDao.getAgentCode(pinCode, date, allocationDate1, 0, agentCode);
                    confirmationAllowed = "Y";
                    finalconfirmation = "W";
                } catch (Exception e) {
                    agentCode = null;
                    e.printStackTrace();
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
                        int cust_uid = allocationMastEntity1.getPaytmcustomerDataByCustomerPhone().getCust_uid();
                        paytmMastEntity = paytmMasterDao.getPaytmMasterData(cust_uid);

                    }

                    String text = "Dear Agent Job No-" + jobNumber + "" +
                            ", Your visit is fixed at " + date
                            + " " + time + "with " + name + " Address-" +
                            "" + address + " " + pinCode + "Contact no-" +
                            "" + customerNo + " Please See Leads in App";
                    String custext = "Dear Customer  Your CustomerId-" + paytmMastEntity.getCust_uid() + "" +
                            ",   Agent visit dateTime " + date
                            + " " + time + " Please Available with ...... ";


                    PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = paytmDeviceDao.getByloginId(agentCode);
                    if (paytmdeviceidinfoEntity != null) {
                        loginId = paytmdeviceidinfoEntity.getLoginId();
                    }

                    if (loginId != null) {
                        String res2 = saveTblNotificationLogEntity(text, agentCode, paytmdeviceidinfoEntity);
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");
                        //     String res3 =  saveSmsSendLog(paytmMastEntity.getCustomerPhone(),paytmMastEntity.getCustomerId(),custext,"1","4");
                        String res3 = saveSmsSendLog("8588998890", paytmMastEntity.getCustomerId(), custext, "1", "4");
                    } else {
                        String res = saveSmsSendLog(agentMobileNumber, agentCode, text, "2", "2");
                        //   String res3 =  saveSmsSendLog(paytmMastEntity.getCustomerPhone(),paytmMastEntity.getCustomerId(),custext,"1","4");
                        String res3 = saveSmsSendLog("8588998890", paytmMastEntity.getCustomerId(), custext, "1", "4");
                    }

                    result = "JOB ALLOCATED";
                } catch (Exception e) {
                    logger.error("job allocated error ", e);
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
            allocationMastEntity.setCustomerPhone(paytmcustomerDataEntity.getPcdCustomerPhone());
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
            //we have to make it dyanamic now use static
            allocationMastEntity.setSpokeCode(paytmagententryEntity.getAspokecode());

            result = allocationDao.saveAllocation(allocationMastEntity);
            if (result == "err") {
                for (int i = 1; i <= 5; i++) {
                    result = allocationDao.saveAllocation(allocationMastEntity);
                    if ("done".equals(result)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Save Allocation ", e);
            result = "err";

        }
        return result;

    }

    public String saveSmsSendLog(String agentMobileNumber, String agentCode, String text, String reciveCode1, String processCode1) {

        String result = null;
        try {
            int reciveCode = Integer.parseInt(reciveCode1);
            int processCode = Integer.parseInt(processCode1);
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(reciveCode);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(processCode);
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
        String notificationResult = null;
        try {
            TblNotificationLogEntity tblNotificationLogEntity = new TblNotificationLogEntity();
            tblNotificationLogEntity.setNotificationType("Leads");
            tblNotificationLogEntity.setNotificationText(text);
            tblNotificationLogEntity.setPaytmdeviceidinfoBynotificationLoginid(paytmdeviceidinfoEntity);
            //   tblNotificationLogEntity.setNotificationLoginid(agentCode);
            tblNotificationLogEntity.setNotificationSenddt(new Timestamp(new Date().getTime()));
            result = postCallingDao.saveTabNotification(tblNotificationLogEntity);

            if ("done".equals(result)) {
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