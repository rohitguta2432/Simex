package com.softage.paytm.dao;

import com.softage.paytm.models.*;

import java.sql.Date;
import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */
public interface PostCallingDao {

    public String saveTeleCallLog(TelecallLogEntity telecallLogEntity);
    public String savePaytmCustomer(PaytmcustomerDataEntity paytmcustomerDataEntity);
    public String saveAppointment(AppointmentMastEntity appointmentMastEntity);
    public String updateTeleCall(TelecallMastEntity telecallMastEntity);
    public TelecallMastEntity getByPrimaryKey(String phoneNumber);
    public long checkAppointmentId(long appointmentid);
    public Map<String,Object> getData(long appointmentid,String mobileNo);
    public String getAgentCode(String pinCode,Date date,Date date1,int maxAllocation,String agentCode);
    public String saveSmsSendEntity(SmsSendlogEntity smsSendlogEntity);
    public String saveTabNotification(TblNotificationLogEntity tblNotificationLogEntity);
    public AppointmentMastEntity getByCustomerNuber(String customerNumber);
    public RemarkMastEntity getByPrimaryCode(String key);
    public ProcessMastEntity getProcessByCode(int code);
    public ReceiverMastEntity getRecivedByCode(int code);

    public String callJobAllocatedProcedure(long allocationId,String moblieno,String agentcode);

}
