package com.softage.paytm.service;

import com.softage.paytm.models.AgentpinmasterEntity;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by SS0085 on 30-12-2015.
 */
public interface AgentPaytmService {

    public String saveAgent(PaytmagententryEntity paytmagententryEntity,CircleMastEntity circleMastEntity);
    public String saveEmployee(EmplogintableEntity emplogintableEntity);
    public PaytmagententryEntity findByPrimaryKey(String agentCode);
    public PaytmagententryEntity findByPincode(String pincode);
    public String saveAgentPinMaster1(PaytmagententryEntity paytmagententryEntity);
    public String saveAgentLocation(String agentCode,String CustomerNumber,String location,double lati,double longi);
    public String saveBulkAgent(List<Map<String,String>> agentList,int circleCode);
    public List<String> getAgentPinMastList(String pincode);


}
