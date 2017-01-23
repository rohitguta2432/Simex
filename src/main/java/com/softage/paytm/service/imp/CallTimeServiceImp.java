package com.softage.paytm.service.imp;

import com.softage.paytm.dao.CallTimeDao;
import com.softage.paytm.service.CallTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0090 on 7/29/2016.
 */
@Service
public class CallTimeServiceImp implements CallTimeService {

    @Autowired
    private CallTimeDao callTimeDao;

    @Override
    public String insertCallTimeDetails(String customer_number, String callDateTime,int circleCode,String lastcallby,int cust_uid) {
        return callTimeDao.insertCallTimeDetails(customer_number,callDateTime,circleCode,lastcallby,cust_uid);
    }
}
