package com.softage.paytm.service;

import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.PaytmagententryEntity;

/**
 * Created by SS0085 on 30-12-2015.
 */
public interface AgentPaytmService {

    public String saveAgent(PaytmagententryEntity paytmagententryEntity,CircleMastEntity circleMastEntity);
    public PaytmagententryEntity findByPrimaryKey(String agentCode);

}
