package com.softage.paytm.service;

        import com.softage.paytm.models.ProofMastEntity;
        import com.softage.paytm.models.ReasonMastEntity;
        import org.json.simple.JSONArray;
        import org.json.simple.JSONObject;

        import java.util.List;

/**
 * Created by SS0085 on 02-02-2016.
 */
public interface LeadsService {

    public List<JSONObject> getAgentLeads(String agentCode);
    public String updateLeadStatus(String agentCode,String jobid,boolean response);
    public List<JSONObject> agentAcceptedLeads(String agentCode);
    public List<JSONObject> kycDone(String agentCode);
    public List<JSONObject> kycNotDone(String agentCode);
    public List<JSONObject> agentRejectedLeads(String agentCode);
    public ProofMastEntity findBykey(String key);
    public ReasonMastEntity findByprimaryKey(String key);
    public List<ReasonMastEntity> reasonList();
}
