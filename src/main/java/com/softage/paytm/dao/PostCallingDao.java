package com.softage.paytm.dao;

import com.softage.paytm.models.*;

import java.sql.Date;
import java.util.List;
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
    public TelecallMastEntity getByReferenceId(int id);
    public long checkAppointmentId(long appointmentid);
    public Map<String,Object> getData(long appointmentid,String mobileNo);
    public String getAgentCode(String pinCode,Date date,String date1,int maxAllocation,String agentCode);
    public String saveSmsSendEntity(SmsSendlogEntity smsSendlogEntity);
    public String saveTabNotification(TblNotificationLogEntity tblNotificationLogEntity);
    public AppointmentMastEntity getByCustomerNuber(String customerNumber);
    public AppointmentMastEntity getByCustId(int custId);
    public AppointmentMastEntity getByAppointmentId(long appointmentId);
    public RemarkMastEntity getByPrimaryCode(String key);
    public List<RemarkMastEntity> remarkList();
    public ProcessMastEntity getProcessByCode(int code);
    public ReceiverMastEntity getRecivedByCode(int code);
    public String save(ReOpenTaleCallMaster openTaleCallMaster);

    public String callJobAllocatedProcedure(Map<String,String> map);

}
