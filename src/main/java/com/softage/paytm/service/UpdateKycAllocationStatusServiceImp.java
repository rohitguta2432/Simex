package com.softage.paytm.service;

import com.softage.paytm.dao.UpdateKycAllocationStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateKycAllocationStatusServiceImp implements UpdateKycAllocationStatusService {

    @Autowired
    UpdateKycAllocationStatusDao updateKycAllocationStatusDao;


    @Override
    public String updateKycAllocationStatus(String cust_id ,String status,String jobId,String agent_Code) {
        return updateKycAllocationStatusDao.updateKycStatus(cust_id,status,jobId,agent_Code);
    }

}
