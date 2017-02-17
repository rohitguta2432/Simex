package com.softage.paytm.service.imp;

import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.service.ManualLeadService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
@Service
public class ManualLeadServiceImpl implements ManualLeadService {
 @Inject
 private ManualLeadDao manualLeadDao;


    @Override
    public List getAgentLeadDetails() {
        return manualLeadDao.getAgentDetails();
    }

    @Override
    public List<PaytmagententryEntity> getAgentCode() {
        return manualLeadDao.GetAgentsCode();
    }

    @Override
    public String updateAgentsBycustUid(int customerId, String AgentCode,String lastAgent) {
        return manualLeadDao.updateAgentsByCustUid(customerId,AgentCode,lastAgent);
    }
}
