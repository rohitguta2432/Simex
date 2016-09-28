package com.softage.paytm.dao;

import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 29-08-2016.
 */
public interface BatchDao {
    public JSONObject saveBatch(int inwordfrom, int inwordto,int totaldoc,int circle,String createdby);
    public String getinwardfrom(int circlecode);
    public JSONObject getBatchDetails(int circlecode);
    public String createindexing(int batchno,int uid,int circlecode,String createdby);
    public JSONObject getuserDetails(String mobileno);
    public JSONObject updateBatch(String mobileno,String status,int batchNo, int uid,String name,String customerId,String remark,String createdby);
    public JSONObject searchindexng(String mobileno,int batchNo,int uid,int circlecode);
}
