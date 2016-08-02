package com.softage.paytm.service;

import com.softage.paytm.models.AllocationMastEntity;

/**
 * Created by SS0085 on 03-02-2016.
 */
public interface AllocationService {
    public AllocationMastEntity findByPrimaryKey(int id);
    public AllocationMastEntity findById(String agentCode, String jobid);
    public String updateAllocationMastEntity(AllocationMastEntity allocationMastEntity);
    public String updateKycAllocation(String AgentCode,String JobId, String remarksCode, String kycStatus);
}
