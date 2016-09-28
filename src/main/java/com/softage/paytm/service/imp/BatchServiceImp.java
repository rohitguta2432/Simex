package com.softage.paytm.service.imp;

import com.softage.paytm.dao.BatchDao;
import com.softage.paytm.service.BatchService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 29-08-2016.
 */
@Service
public class BatchServiceImp implements BatchService {
    @Autowired
    private BatchDao batchDao;


    @Override
    public JSONObject saveBatch(int inwordfrom, int inwordto, int totaldoc, int circle, String createdby) {
        JSONObject jsonObject=null;
        JSONObject retuenobj=new JSONObject();
        jsonObject =  batchDao.saveBatch(inwordfrom,inwordto,totaldoc,circle,createdby);
      /*  String result=null;
        int batchno=(Integer)jsonObject.get("batch");
              for (int uid=inwordfrom; uid<=inwordto; uid++ )
              {
                  result = batchDao.createindexing(batchno,uid,circle,createdby);

              }
        retuenobj.put("status",result);*/
        return jsonObject;
    }

    @Override
    public String getinwardfrom(int circle_code) {
        return batchDao.getinwardfrom(circle_code);
    }

    @Override
    public JSONObject getBatchDetails(int circlecode) {


        return batchDao.getBatchDetails(circlecode);
    }

    @Override
    public JSONObject getuserDetails(String mobileno) {
        return batchDao.getuserDetails(mobileno);
    }

    @Override
    public JSONObject updateBatch(String mobileno, String status, int batchNo, int uid, String name, String customerId, String remark, String createdby) {
        return batchDao.updateBatch(mobileno,status,batchNo,uid,name,customerId,remark,createdby);
    }

    @Override
    public JSONObject searchindexng(String mobileno, int batchNo, int uid,int circlecode) {
        return batchDao.searchindexng(mobileno,batchNo,uid,circlecode);
    }
}
