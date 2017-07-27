package com.softage.paytm.dao;

import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
public interface ManualLeadDao {

    public List getAgentDetails();
    public JSONObject GetAgentsCode(String allocatedTime, String agentPincode);
    public String updateAgentsByCustUid(int CustomerId,String agentCode,String lastAgent,String userId, String newAllocationDateTime);
    public String deAllocateLead(int custId);
    public String getAgentPincode(String agentCode);

}
