package com.softage.paytm.service;

import com.softage.paytm.models.TelecallMastEntity;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */
public interface PostCallingService {

    public String saveCallingData(Map<String,String> map);
    public String sendsmsService();
    public String updateTeleCall(TelecallMastEntity telecallMastEntity);
    public TelecallMastEntity getByPrimaryKey(String phoneNumber);





}
