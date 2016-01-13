package com.softage.paytm.dao;

import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0085 on 22-12-2015.
 */
public interface PaytmMasterDao {

    public String savePaytmMaster(List<PaytmMastEntity> paytmMastEntity);
    public JSONObject getPaytmMastData(String mobileNo);
    public  JSONObject telecallingScreen(String userName,int circode);
    public List<StateMasterEntity> getStatemaster();
    public List<CallStatusMasterEntity> getStatusList();

}
