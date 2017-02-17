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
    public List<PaytmagententryEntity> GetAgentsCode();
    public String updateAgentsByCustUid(int CustomerId,String agentCode,String lastAgent);
}
