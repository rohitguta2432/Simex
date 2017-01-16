package com.softage.paytm.dao;

import com.softage.paytm.models.*;
import org.json.simple.JSONObject;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by SS0085 on 22-12-2015.
 */
public interface PaytmMasterDao extends CrudRepository<PaytmMastEntity,String> {

    public String savePaytmMaster(List<PaytmMastEntity> paytmMastEntity);
    public JSONObject getPaytmMastData(int cust_uid);
    public  JSONObject telecallingScreen(String userName,int circode);
    public List<StateMasterEntity> getStatemaster();
    public List<CallStatusMasterEntity> getStatusList();
    public PaytmMastEntity getPaytmMaster(String mobileNo);
    public String savePaytmMaster(PaytmMastEntity paytmMastEntity);
    public PaytmMastEntity getPaytmMasterData(int cust_uid);
    public PaytmMastEntity getPaytmMastEntityByDate(String mobile,Date date);
    public PaytmMastEntity getpaytmmasterservice(String cust_uid);
    public PaytmcustomerDataEntity getPaytmCustomerData(int cust_uid);
    public SpokeMastEntity getSpokemast(String spokeCode);
    public AllocationMastEntity getAllocationentity(int custid,int jobid);
}
