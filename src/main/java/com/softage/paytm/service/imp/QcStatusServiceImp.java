package com.softage.paytm.service.imp;

import com.softage.paytm.dao.QcStatusDao;
import com.softage.paytm.service.QcStatusService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */
@Service
public class QcStatusServiceImp implements QcStatusService {
    @Autowired
    private QcStatusDao qcStatusDao;


    @Override
    public String saveQcStatus(String mobile_no, String status, String rejected_page, String remarks) {
        String result=qcStatusDao.qcStatusSave(mobile_no,rejected_page,rejected_page,remarks);
        return result;
    }

    @Override
    public String updateQcStatus(String mobile_no, String status, String rejected_page, String remarks) {
       String result=qcStatusDao.qcStatusUpdate(mobile_no,status,rejected_page,remarks);
        return result;
    }

    @Override
    public JSONObject getMobileNumber() {
        JSONObject result=qcStatusDao.qcGetMobileNumber();
        return result;
    }

    @Override
    public JSONObject qcCustomerDetails(String mobileNumber) {
        JSONObject jsonObject=new JSONObject();
        jsonObject=qcStatusDao.qcGetCustomerDetails(mobileNumber);
        return jsonObject;
    }


}
