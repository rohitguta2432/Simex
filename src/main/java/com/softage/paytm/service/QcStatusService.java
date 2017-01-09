package com.softage.paytm.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */

public interface QcStatusService {
    public String saveQcStatus(String mobile_no, String status, String rejected_page, String remarks);
    public String updateQcStatus(String mobile_no, String status, String rejected_page, String remarks);
    public JSONObject getMobileNumber(String spokeCode);
    public JSONObject qcCustomerDetails(String mobileNumber);
    public JSONObject downloadList(String mobileNumber,String todate,String fromdate);


}
