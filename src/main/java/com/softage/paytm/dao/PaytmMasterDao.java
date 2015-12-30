package com.softage.paytm.dao;

import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;

import java.util.List;

/**
 * Created by SS0085 on 22-12-2015.
 */
public interface PaytmMasterDao {

    public void savePaytmMaster(List<PaytmMastEntity> paytmMastEntity);
    public PaytmMastEntity getPaytmMastData();
    public  List telecallingScreen(String userName);
    public List<StateMasterEntity> getStatemaster();
    public List<CallStatusMasterEntity> getStatusList();

}
