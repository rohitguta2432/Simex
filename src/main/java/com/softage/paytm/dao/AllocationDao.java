package com.softage.paytm.dao;

import com.softage.paytm.models.AllocationMastEntity;

/**
 * Created by SS0085 on 02-01-2016.
 */
public interface AllocationDao {

    public String saveAllocation(AllocationMastEntity allocationMastEntity);
    public AllocationMastEntity findByAgentCode(long apointmantId,String agentCode);
}
