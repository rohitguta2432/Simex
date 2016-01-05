package com.softage.paytm.dao;

import com.softage.paytm.models.SmsSendlogEntity;

/**
 * Created by SS0085 on 05-01-2016.
 */
public interface SmsSendLogDao {

    public SmsSendlogEntity getSendData();
    public String updateSmsLogData(SmsSendlogEntity smsSendlogEntity);

}
