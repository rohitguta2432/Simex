package com.softage.paytm.dao;

import com.softage.paytm.models.AgentpinmasterEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;

/**
 * Created by SS0085 on 30-12-2015.
 */
public interface AgentPaytmDao {
    public String saveAgent(PaytmagententryEntity paytmagententryEntity);
    public String saveAgentPinMaster(AgentpinmasterEntity agentpinmasterEntity);
    public String saveEmployee(EmplogintableEntity emplogintableEntity);
    public PaytmagententryEntity findByPrimaryKey(String agentCode);
}
