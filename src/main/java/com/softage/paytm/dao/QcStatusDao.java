package com.softage.paytm.dao;


import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */

public interface QcStatusDao {
    public String qcStatusSave(String mobile_no, String status, String rejected_page, String remarks);
    public String qcStatusUpdate(String mobile_no, String status, String rejected_page, String remarks);
    public JSONObject qcGetMobileNumber();
    public JSONObject qcGetCustomerDetails(String mobileNum);
}
