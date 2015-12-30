package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.service.AgentPaytmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 30-12-2015.
 */
@Service
public class AgentPaytmServiceImp implements AgentPaytmService {
    @Autowired
    private AgentPaytmDao agentPaytmDao;

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity) {

        String msg=agentPaytmDao.saveAgent(paytmagententryEntity);
        return msg;
    }
}
