package com.softage.paytm.dao;

import com.softage.paytm.models.AgentpinmasterEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;

import java.util.List;

/**
 * Created by SS0085 on 30-12-2015.
 */
public interface AgentPaytmDao {
    public String saveAgent(PaytmagententryEntity paytmagententryEntity);
    public String saveAgentPinMaster(AgentpinmasterEntity agentpinmasterEntity);
    public List<String> getAgentPinMastList(String pincode);
    public AgentpinmasterEntity getByPinandAcode(String pincode,String agentcode);
    public String saveEmployee(EmplogintableEntity emplogintableEntity);
    public String updateEmployee(EmplogintableEntity emplogintableEntity);
    public PaytmagententryEntity findByPrimaryKey(String agentCode);
    public PaytmagententryEntity findByPincode(String pincode);
    public String saveAgentLocation(String agentCode,String CustomerNumber,String location,double lati,double longi,int cust_uid);
}
