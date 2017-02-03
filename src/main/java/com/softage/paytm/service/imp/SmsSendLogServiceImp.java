package com.softage.paytm.service.imp;

import com.softage.paytm.dao.SmsSendLogDao;
import com.softage.paytm.models.SmsSendlogEntity;
import com.softage.paytm.service.SmsSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 06-02-2017.
 */

@Service
public class SmsSendLogServiceImp implements SmsSendLogService {
    @Autowired
    private SmsSendLogDao smsSendLogDao;



    @Override
    public SmsSendlogEntity getByMobileNumber(String mobileNumber) {
        return smsSendLogDao.getByMobileNumber(mobileNumber);
    }

    @Override
    public String updateSmsLogData(SmsSendlogEntity smsSendlogEntity) {
        return smsSendLogDao.updateSmsLogData(smsSendlogEntity);
    }
}
