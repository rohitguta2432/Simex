package com.softage.paytm.service;

import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 21-12-2015.
 */
public interface PaytmMasterService {


    public String savePaytmMaster(List<Map<String,String>> paytmList);
    public String savePaytmMasterExcel(List<Map<String,String>> paytmList);
    public JSONObject getPaytmMastData(String mobileNo);
    public PaytmMastEntity getPaytmMaster(String mobileNo);
    public JSONObject telecallingScreen(String userName,int cirCode);
    public List<StateMasterEntity> getStateList();
    public List<CallStatusMasterEntity> getStatusList();
    public void uploadRejectedData(List<Map<String, String>> list,File filename);
    public PaytmMastEntity getPaytmMastData(int cust_uid);

}
