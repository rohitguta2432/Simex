package com.softage.paytm.dao;

public interface UpdateKycAllocationStatusDao {
    public String updateKycStatus(String cust_id ,String status,String jobId, String agent_code);
}
