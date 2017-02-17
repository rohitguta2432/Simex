package com.softage.paytm.service;

import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
public interface ManualLeadService {

    public List getAgentLeadDetails();
    public List<PaytmagententryEntity> getAgentCode();
    public String updateAgentsBycustUid(int customerId,String AgentCode,String lastAgent);
}
