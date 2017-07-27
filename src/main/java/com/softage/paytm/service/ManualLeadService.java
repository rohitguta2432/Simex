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
    public JSONObject getAgentCode(String allocatedTime, String agentPincode);
    public String updateAgentsBycustUid(int customerId,String AgentCode,String lastAgent,String userId, String newAllocationDateTime);
    public JSONObject deAllocateLead(int custId);
}
