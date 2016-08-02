package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 03-02-2016.
 */
@Service
public class AllocationServiceImp implements AllocationService {
    @Autowired
    private AllocationDao allocationDao;
    @Override
    public AllocationMastEntity findByPrimaryKey(int id) {
        return allocationDao.findByPrimaryKey(id);
    }

    @Override
    public AllocationMastEntity findById(String agentCode, String jobid) {
        return allocationDao.findById(agentCode,jobid);
    }


    @Override
    public String updateAllocationMastEntity(AllocationMastEntity allocationMastEntity) {
        String result=null;
        for(int i=1; i<=4; i++) {
            result= allocationDao.updateAllocationMastEntity(allocationMastEntity);
        }
        return result;
    }

    @Override
    public String updateKycAllocation(String AgentCode, String JobId, String remarksCode, String kycStatus) {
        return allocationDao.updateKycAllocation(AgentCode,JobId,remarksCode,kycStatus);
    }
}
