package com.softage.paytm.dao;


import com.softage.paytm.models.AuditStatusEntity;
import com.softage.paytm.models.CircleAuditEntity;
import com.softage.paytm.models.TblScan;
import com.softage.paytm.models.TblcustDocDetails;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by SS0090 on 7/21/2016.
 */

public interface QcStatusDao {
    public String qcStatusSave(String mobile_no, String status, String rejected_page, String remarks);
    public String qcStatusUpdate(String mobile_no, String status, String rejected_page, String remarks);
    public JSONObject qcGetMobileNumber(Integer circode,String empcode);
    public JSONObject qcGetCustomerDetails(String mobileNum);
    public JSONObject downloadList(String mobileNumber,String todate,String fromdate);
    public AuditStatusEntity getAuditStatusEntity(int status);
    public TblScan getScanTableEntity(int scanid);
    public String saveCircleAuditEntity(CircleAuditEntity circleAuditEntity);
    public TblScan getScanDetails(int cust_uid);
    public String updateTblScanEntity(TblScan tblScan);
    public String savetblscan(TblScan savesimages);
    public String saveTblDocdetails(TblcustDocDetails tblcustDocDetails);

}
