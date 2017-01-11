package com.softage.paytm.service;


import com.softage.paytm.models.AuditStatusEntity;
import com.softage.paytm.models.CircleAuditEntity;
import com.softage.paytm.models.TblScan;
import com.softage.paytm.models.TblcustDocDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */

public interface QcStatusService {
    public String saveQcStatus(String mobile_no, String status, String rejected_page, String remarks);
    public String updateQcStatus(String mobile_no, String status, String rejected_page, String remarks);
    public JSONObject getMobileNumber(Integer circode,String empcode);
    public JSONObject qcCustomerDetails(String mobileNumber);
    public JSONObject downloadList(String mobileNumber,String todate,String fromdate);
    public AuditStatusEntity getAuditStatusEntity(int status);
    public TblScan getScanTableEntity(int scanID);
    public String saveCircleAuditEntity(CircleAuditEntity circleAuditEntity);

    public TblScan getUserScanDetails(int cust_uid);
    public String updateTblSacnEntity(TblScan tblScan);
    public String SaveScanimages(TblScan scantbl);
    public String savetbldocdetails(TblcustDocDetails tblcustDocDetails);




}
