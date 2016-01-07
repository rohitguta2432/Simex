package com.softage.paytm.dao;

import com.softage.paytm.models.ProcessMastEntity;
import com.softage.paytm.models.ReceiverMastEntity;
import com.softage.paytm.models.SmsSendlogEntity;

import java.util.List;

/**
 * Created by SS0085 on 05-01-2016.
 */
public interface SmsSendLogDao {

    public List<SmsSendlogEntity> getSendData();
    public String updateSmsLogData(SmsSendlogEntity smsSendlogEntity);
    public ProcessMastEntity  getByPrimarykey(int processCode);
    public ReceiverMastEntity getbyPrimaryKey(int reciverId);

}
