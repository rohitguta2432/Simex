package com.softage.paytm.dao;

import com.softage.paytm.models.AllocationMastEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by SS0085 on 02-01-2016.
 */
public interface AllocationDao extends CrudRepository<AllocationMastEntity ,Integer> {

    public String saveAllocation(AllocationMastEntity allocationMastEntity);
    public AllocationMastEntity findByAgentCode(long apointmantId,String agentCode);
    public String updateAllocationMastEntity(AllocationMastEntity allocationMastEntity);
    public AllocationMastEntity findById(String agentCode, String jobid);
    public AllocationMastEntity findByPrimaryKey(int id);
    public String allocationMastEntityUpdate(String jobId,String agentCode,String response);
    public String updateKycAllocation(String AgentCode,String JobId, String remarksCode, String kycStatus);
}
