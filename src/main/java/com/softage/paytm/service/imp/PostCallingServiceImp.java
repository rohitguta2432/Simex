package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.dao.PaytmDeviceDao;
import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.models.*;
import com.softage.paytm.service.PostCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */

@Service
public class PostCallingServiceImp implements PostCallingService {
    @Autowired
    public PostCallingDao postCallingDao;
    @Autowired
    public AgentPaytmDao agentPaytmDao;
    @Autowired
    public  AllocationDao allocationDao;
    @Autowired
    public PaytmDeviceDao paytmDeviceDao;



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
                telecallLogEntity.setTelecallMastByTcCustomerphone(telecallMastEntity);
                postCallingDao.saveTeleCallLog(telecallLogEntity);
               if ("CON".equals(status)){
                   tcStatus="D";
                   result=saveCustomer(map);
               }
              byte s=(byte)(telecallMastEntity.getTmAttempts()+1);
              TelecallMastEntity telecallMastEntity1=new TelecallMastEntity();
              telecallMastEntity.setTmCustomerPhone(map.get("number"));
              telecallMastEntity.setTmAttempts(s);
              telecallMastEntity.setTmLastAttemptBy(map.get("importby"));
              telecallMastEntity.setTmLastAttemptDateTime(new Timestamp(new Date().getTime()));
              telecallMastEntity.setTmTeleCallStatus(tcStatus);
              telecallMastEntity.setTmLastCallStatus(map.get("status"));
              postCallingDao.updateTeleCall(telecallMastEntity1);




        return result;
    }

    public String saveCustomer(Map<String,String> map){
        String result=null;
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
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
            paytmcustomerDataEntity.setPcdVisitTIme(Time.valueOf(map.get("visitTime")));
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
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            AppointmentMastEntity appointmentMastEntity = new AppointmentMastEntity();
            Date parsedUtilDate = formater.parse(map.get("visitDate"));
            java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
            appointmentMastEntity.setAppointmentDate(sqltDate);
            appointmentMastEntity.setAppointmentTime(Time.valueOf(map.get("visitTime")));
            appointmentMastEntity.setImportDate(new Timestamp(new Date().getTime()));
            appointmentMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);
            result = postCallingDao.saveAppointment(appointmentMastEntity);

        }catch (Exception e){

        }
        return  result;
    }

public String jobAllocated(long appointmentId,String mobileNo,PaytmcustomerDataEntity paytmcustomerDataEntity,AppointmentMastEntity appointmentMastEntity){
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
    String confirmationAllowed=null;
    String finalconfirmation=null;
    int maxAllocation=15;
    String loginId=null;

       long appointmentId1= postCallingDao.checkAppointmentId(appointmentId);
       if(appointmentId1==0){

      Map<String,Object> dataMap= postCallingDao.getData(appointmentId,mobileNo);

                        name=(String) dataMap.get("name");
                        address=(String)dataMap.get("address");
                        area=(String)dataMap.get("area");
                        city=(String)dataMap.get("city");
                        pinCode=(String)dataMap.get("pinCode");
                        date=(java.sql.Date)dataMap.get("visitDate");
                        time=(Time)dataMap.get("visitTime");
                        Calendar calendar= Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DATE,3);
                        java.sql.Date loopdate=new java.sql.Date(calendar.getTimeInMillis());

                        while (date.getTime()<=loopdate.getTime()){
                            calendar.add(Calendar.DATE,1);
                            date1=new java.sql.Date(calendar.getTimeInMillis());
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

                PaytmagententryEntity paytmagententryEntity =agentPaytmDao.findByPrimaryKey(agentCode);
                   try {
                       String agentMobileNumber = paytmagententryEntity.getAphone();
                       String vistDate = date.toString();
                       String visitTime = time.toString();
                       String allocationDate1 = vistDate + "" + visitTime;
                       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd'T'HH:mm:ss'Z'");
                       Date convertedDate = dateFormat.parse(allocationDate1);
                       AllocationMastEntity allocationMastEntity = new AllocationMastEntity();
                       allocationMastEntity.setAppointmentMastByAppointmentId(appointmentMastEntity);
                       allocationMastEntity.setPaytmagententryByAgentCode(paytmagententryEntity);
                       allocationMastEntity.setPaytmcustomerDataByCustomerPhone(paytmcustomerDataEntity);
                       allocationMastEntity.setAllocationDatetime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setVisitDateTime(new Timestamp(convertedDate.getTime()));
                       allocationMastEntity.setImportBy("Afjal");
                       allocationMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                       allocationMastEntity.setConfirmation(confirmationAllowed);
                       allocationMastEntity.setFinalConfirmation(finalconfirmation);
                       String result1 = allocationDao.saveAllocation(allocationMastEntity);

                       AllocationMastEntity allocationMastEntity1 = allocationDao.findByAgentCode(appointmentId,agentCode);
                       int jobNumber = allocationMastEntity1.getId();
                       String text="Dear Agent Job No-"+jobNumber+"" +
                               ", Your visit is fixed at "+date
                               +" "+time+"with "+name+" Address-" +
                               ""+address+" "+pinCode+"Contact no-" +
                               ""+mobileNo+" Please See Leads in App";
                       ProcessMastEntity  processMastEntity=new ProcessMastEntity();
                       PaytmdeviceidinfoEntity paytmdeviceidinfoEntity=  paytmDeviceDao.getByloginId(agentCode);
                        if (paytmagententryEntity!=null){
                          loginId=paytmdeviceidinfoEntity.getLoginId();
                        }

                       processMastEntity.setProcessCode(2);
                       processMastEntity.setProcess("Processing");
                       SmsSendlogEntity smsSendlogEntity= new SmsSendlogEntity();
                       smsSendlogEntity.setMobileNumber(agentMobileNumber);
                       smsSendlogEntity.setReceiverId(agentCode);
                       smsSendlogEntity.setReceiverCode(2);
                       smsSendlogEntity.setSmsText(text);
                       smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
                       smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
                       TblNotificationLogEntity tblNotificationLogEntity=new TblNotificationLogEntity();
                       tblNotificationLogEntity.setNotificationType("Leads");
                       tblNotificationLogEntity.setNotificationText(text);
                       tblNotificationLogEntity.setNotificationLoginid(agentCode);
                       tblNotificationLogEntity.setNotificationSenddt(new Timestamp(new Date().getTime()));
                       if(loginId!=null){
                           postCallingDao.saveTabNotification(tblNotificationLogEntity);
                       }else {
                           postCallingDao.saveSmsSendEntity(smsSendlogEntity);
                       }

                   }catch (Exception e){
                    e.printStackTrace();
                   }
               }
       }
       else
       {
        result= "JOB ALREADY ALLOCATED";
       }



return  null;
}



}
