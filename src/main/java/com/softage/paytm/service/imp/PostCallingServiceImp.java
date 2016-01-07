package com.softage.paytm.service.imp;

import com.softage.paytm.dao.*;
import com.softage.paytm.models.*;
import com.softage.paytm.service.PostCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    private  AllocationDao allocationDao;
    @Autowired
    private PaytmDeviceDao paytmDeviceDao;
    @Autowired
    private SmsSendLogDao smsSendLogDao;



    private static final Logger logger = LoggerFactory.getLogger(PostCallingServiceImp.class);
    @Override
    public String saveCallingData(Map<String, String> map) {
                String status =map.get("status");
                String tcStatus="U";
                String result=null;

                TelecallMastEntity telecallMastEntity = postCallingDao.getByPrimaryKey(map.get("number"));
                TelecallLogEntity telecallLogEntity =new TelecallLogEntity();
                telecallLogEntity.setTcCustomerphone(map.get("number"));
                telecallLogEntity.setTcCallBy(map.get("importby"));
                telecallLogEntity.setTcCallStatus(map.get("status"));
                telecallLogEntity.setTcCallTime(new Timestamp(new Date().getTime()));
                telecallLogEntity.setTelecallMastByTcCustomerphone(telecallMastEntity);
                result= postCallingDao.saveTeleCallLog(telecallLogEntity);
               if ("CON".equals(status)){
                   tcStatus="D";
                   result=saveCustomer(map);
               }
                byte s=(byte)(telecallMastEntity.getTmAttempts()+1);
           //   TelecallMastEntity telecallMastEntity1=new TelecallMastEntity();
         //     telecallMastEntity.setTmCustomerPhone(map.get("number"));
               if("done".equalsIgnoreCase(result)) {
                   telecallMastEntity.setTmAttempts(s);
                   telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
                   telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
                   telecallMastEntity.setTmTeleCallStatus(tcStatus);
                   telecallMastEntity.setTmLastCallStatus(map.get("status"));
                   postCallingDao.updateTeleCall(telecallMastEntity);
               }
        return result;
    }

    @Override
    public String sendsmsService() {
        String result=null;
        List<SmsSendlogEntity> smsSendlogEntityList=null;
        try {
            smsSendlogEntityList = smsSendLogDao.getSendData();
            for (SmsSendlogEntity smsSendlogEntity:smsSendlogEntityList) {

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
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sms not send  ",e);
        }
        return  result;
    }
      private String sendSms(String mobileno,String text){
          String result=null;
          SmsSendlogEntity smsSendlogEntity=null;
          //  String url = "http://etsdom.kapps.in/webapi/softage/api/softage_c2c.py?auth_key=hossoftagepital&customer_number=+918588875378&agent_number=+918882905998";
          //  String url="http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno=8882905998&text= hello this is test mesg";
          String url="http://www.mysmsapp.in/api/push?apikey=56274f9a48b66&route=trans5&sender=SPAYTM&mobileno="+mobileno+"&text="+ text;
          try {
              URL obj = new URL(url);
              HttpURLConnection con = (HttpURLConnection) obj.openConnection();
              con.setRequestMethod("GET");
              int responseCode = con.getResponseCode();
              System.out.println("\nSending 'GET' request to URL : " + url);
              logger.info("\nSending 'GET' request to URL : " + url);
              System.out.println("Response Code :" + responseCode);
              logger.info("Response Code :" + responseCode);
              BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
              String inputLine;
              StringBuffer response = new StringBuffer();

              while ((inputLine = in.readLine()) != null) {
                  response.append(inputLine);
              }
              in.close();
              result=response.toString();
          } catch (Exception e){
              logger.error("enable to send message  ",e);
              result="err";
              e.printStackTrace();
          }
          return  result;


      }


    public String saveCustomer(Map<String,String> map){
        String result=null;
        DateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
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
            java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
            paytmcustomerDataEntity.setPcdVisitDate(sqltDate);
            paytmcustomerDataEntity.setPcdVisitTIme(new Time(Integer.parseInt(map.get("visitTime")),0,0));
            result = postCallingDao.savePaytmCustomer(paytmcustomerDataEntity);
            if (result!=null&&"done".equalsIgnoreCase(result)){
                result=saveAppoinment(map,paytmcustomerDataEntity);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
    public String saveAppoinment(Map<String,String> map,PaytmcustomerDataEntity paytmcustomerDataEntity){
        String result=null;
        DateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
        try {
            AppointmentMastEntity appointmentMastEntity = new AppointmentMastEntity();
            Date parsedUtilDate = formater.parse(map.get("visitDate"));
            java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
            appointmentMastEntity.setAppointmentDate(sqltDate);
            appointmentMastEntity.setAppointmentTime(new Time(Integer.parseInt(map.get("visitTime")),0,0));
            appointmentMastEntity.setImportDate(new Timestamp(new Date().getTime()));
            appointmentMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);
            result = postCallingDao.saveAppointment(appointmentMastEntity);
            if("done".equalsIgnoreCase(result)){
              jobAllocated(paytmcustomerDataEntity);
            }

        }catch (Exception e){
           e.printStackTrace();
            logger.error("error to save Appointmantdata");
        }
        return  result;
    }

public String jobAllocated(PaytmcustomerDataEntity paytmcustomerDataEntity){
     String result=null;
     String name=null;
     String address=null;
     String city=null;
     String area=null;
     String pinCode=null;
     java.sql.Date date=null;
     Time time=null;
     java.sql.Date date1=null;
     String agentCode="0";
     String confirmationAllowed="";
     String finalconfirmation="";
     int maxAllocation=15;
     String loginId=null;
     String customerNo="";
     long appointmentId=0;
     AppointmentMastEntity appointmentMastEntity=null;

              if(paytmcustomerDataEntity!=null){
                   customerNo=paytmcustomerDataEntity.getPcdCustomerPhone();
                   appointmentMastEntity= postCallingDao.getByCustomerNuber(customerNo);
               }
              if (appointmentMastEntity!=null){
              appointmentId=appointmentMastEntity.getAppointmentId();
              }

       long appointmentId1= postCallingDao.checkAppointmentId(appointmentId);
       if(appointmentId1==0){

   //   Map<String,Object> dataMap= postCallingDao.getData(appointmentId,mobileNo);

                      name= paytmcustomerDataEntity.getPcdName();
                      address=paytmcustomerDataEntity.getPcdAddress();
                      area=  paytmcustomerDataEntity.getPcdArea();
                      city=   paytmcustomerDataEntity.getPcdCity();
                      pinCode=  paytmcustomerDataEntity.getPcdPincode();
                      date= paytmcustomerDataEntity.getPcdVisitDate();
                      time= paytmcustomerDataEntity.getPcdVisitTIme();
                        Calendar calendar= Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_WEEK,3);
                        java.sql.Date loopdate=new java.sql.Date(calendar.getTimeInMillis());

                        while (date.getTime()<=loopdate.getTime()){
                            Calendar calendar1= Calendar.getInstance();
                            calendar1.setTime(new Date());
                            calendar1.add(Calendar.DAY_OF_WEEK,1);
                            date1=new java.sql.Date(calendar1.getTimeInMillis());
                            if("0".equals(agentCode)){
                                agentCode= postCallingDao.getAgentCode(pinCode,date,date1,maxAllocation,agentCode);
                                confirmationAllowed="Y";
                                finalconfirmation="W";
                            } else {
                               agentCode= postCallingDao.getAgentCode(pinCode,date,date1,maxAllocation,agentCode);
                                confirmationAllowed="N";
                                finalconfirmation="W";
                            }

                            if (agentCode==null){
                                calendar.add(Calendar.DATE,1);
                                date=new java.sql.Date(calendar.getTimeInMillis());
                            } else {
                                break;
                            }
                        }
               if(agentCode!=null){
                   try {
                       PaytmagententryEntity paytmagententryEntity =agentPaytmDao.findByPrimaryKey(agentCode);
                       String agentMobileNumber = paytmagententryEntity.getAphone();
                       String result1=saveAllocationMast(confirmationAllowed,finalconfirmation,date,time,appointmentMastEntity,paytmagententryEntity,paytmcustomerDataEntity);
                       AllocationMastEntity allocationMastEntity1 = allocationDao.findByAgentCode(appointmentId,agentCode);
                       int jobNumber = allocationMastEntity1.getId();
                       String text="Dear Agent Job No-"+jobNumber+"" +
                               ", Your visit is fixed at "+date
                               +" "+time+"with "+name+" Address-" +
                               ""+address+" "+pinCode+"Contact no-" +
                               ""+customerNo+" Please See Leads in App";

                       PaytmdeviceidinfoEntity paytmdeviceidinfoEntity=  paytmDeviceDao.getByloginId(agentCode);
                        if (paytmdeviceidinfoEntity!=null){
                          loginId=paytmdeviceidinfoEntity.getLoginId();
                        }

                       if(loginId!=null){
                           String res2=saveTblNotificationLogEntity(text,agentCode);
                       }else {
                           String res=saveSmsSendLog(agentMobileNumber,agentCode,text);
                       }
                    result="JOB ALLOCATED";
                   }catch (Exception e){
                    e.printStackTrace();
                   }
               }else {
                    result="NO AGENT AVAILABLE";
               }
       }
       else
       {
        result= "JOB ALREADY ALLOCATED";
       }



return  null;
}

public String saveAllocationMast(String confirmationAllowed,String finalconfirmation,Date date,Time time,AppointmentMastEntity appointmentMastEntity,PaytmagententryEntity paytmagententryEntity,PaytmcustomerDataEntity paytmcustomerDataEntity){
    String vistDate = date.toString();
    String visitTime = time.toString();
    String result =null;
    try {
        String allocationDate1 = vistDate + " " + visitTime;
        RemarkMastEntity remarkMastEntity = postCallingDao.getByPrimaryCode("U");
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
        result = allocationDao.saveAllocation(allocationMastEntity);
    }catch (Exception e){
        result="err";

    }
    return   result;

}

    public String saveSmsSendLog(String agentMobileNumber,String agentCode,String text){

        String result=null;
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
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            result =postCallingDao.saveSmsSendEntity(smsSendlogEntity);
        }catch (Exception e){
            e.printStackTrace();
            result="err";
        }
       return  result;
    }

     public String saveTblNotificationLogEntity(String text,String agentCode){
         String result=null;
        try {
              TblNotificationLogEntity tblNotificationLogEntity = new TblNotificationLogEntity();
              tblNotificationLogEntity.setNotificationType("Leads");
              tblNotificationLogEntity.setNotificationText(text);
              tblNotificationLogEntity.setNotificationLoginid(agentCode);
              tblNotificationLogEntity.setNotificationSenddt(new Timestamp(new Date().getTime()));
              result=postCallingDao.saveTabNotification(tblNotificationLogEntity);
            } catch (Exception e){
                e.printStackTrace();
              result="err";
         }
         return result;
     }
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