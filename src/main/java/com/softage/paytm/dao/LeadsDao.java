package com.softage.paytm.dao;

import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.ProofMastEntity;
import com.softage.paytm.models.ReasonMastEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0085 on 02-02-2016.
 */
public interface LeadsDao {
    public List<JSONObject> getAgentLeads(String agentCode);
    public String jobConfirmtocustomer(long appointmentId,String customerPhone,String agentCode);
    public List<JSONObject> agentAcceptedLeads(String agentCode);
    public List<JSONObject> agentRejectedLeads(String agentCode);
    public List<JSONObject> kycDone(String agentCode);
    public List<JSONObject> kycNotDone(String agentCode);
    public ProofMastEntity findBykey(String key);
    public ReasonMastEntity findByprimaryKey(String key);
    public List<ReasonMastEntity> reasonList();

}
