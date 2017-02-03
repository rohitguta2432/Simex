package com.softage.paytm.service;

import com.softage.paytm.models.SmsSendlogEntity;

/**
 * Created by SS0085 on 06-02-2017.
 */
public interface SmsSendLogService {
    public SmsSendlogEntity getByMobileNumber(String mobileNumber);
    public String updateSmsLogData(SmsSendlogEntity smsSendlogEntity);
}
