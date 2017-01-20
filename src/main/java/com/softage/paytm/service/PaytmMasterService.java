package com.softage.paytm.service;

import com.softage.paytm.models.*;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 21-12-2015.
 */
public interface PaytmMasterService {


    public String savePaytmMaster(List<Map<String,String>> paytmList);
    public String savePaytmMasterExcel(List<Map<String,String>> paytmList);
    public JSONObject getPaytmMastData(int cust_uid);
    public PaytmMastEntity getPaytmMaster(String mobileNo);
    public JSONObject telecallingScreen(String userName,int cirCode);
    public List<StateMasterEntity> getStateList();
    public List<CallStatusMasterEntity> getStatusList();
    public void uploadRejectedData(List<Map<String, String>> list,File filename);
    public PaytmMastEntity getPaytmMastDatas(int cust_uid);
    public PaytmcustomerDataEntity getPaytmCustomerData(int cust_uid);
    public PaytmMastEntity getPaytmMasterByDate(String mobileNo,Date date);
    public PaytmMastEntity getPaytmmasterServiceDate(int cust_uid);
    public SpokeMastEntity spokeMastEntity(String spokeAudit);
    public AllocationMastEntity getallocationMastEntity(String custid,int jobid);

}
