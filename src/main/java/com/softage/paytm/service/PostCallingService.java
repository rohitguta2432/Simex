package com.softage.paytm.service;

import com.softage.paytm.models.ReOpenTaleCallMaster;
import com.softage.paytm.models.RemarkMastEntity;
import com.softage.paytm.models.TelecallMastEntity;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 31-12-2015.
 */
public interface PostCallingService {

    public String saveCallingData(Map<String,String> map);
    public String sendsmsService();
    public String updateTeleCall(TelecallMastEntity telecallMastEntity);
    public TelecallMastEntity getByPrimaryKey(String phoneNumber);
    public String save(ReOpenTaleCallMaster openTaleCallMaster);
    public RemarkMastEntity getByPrimaryCode(String key);
    public List<RemarkMastEntity> remarkList();
    public JSONObject getAvailableslot(String date,List<String> agents,String time,String datekey);



}
