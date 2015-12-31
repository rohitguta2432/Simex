package com.softage.paytm.service;

import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 21-12-2015.
 */
public interface PaytmMasterService {


    public void savePaytmMaster(List<Map<String,String>> paytmList);

    public JSONObject getPaytmMastData(String mobileNo);
    public  List telecallingScreen(String userName);
    public List<StateMasterEntity> getStateList();
    public List<CallStatusMasterEntity> getStatusList();

}
