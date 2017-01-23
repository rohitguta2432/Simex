package com.softage.paytm.service;

/**
 * Created by SS0090 on 7/29/2016.
 */

public interface CallTimeService {
    public String insertCallTimeDetails(String customer_number, String callDateTime,int circleCode,String lastcallby,int cust_uid);
}
