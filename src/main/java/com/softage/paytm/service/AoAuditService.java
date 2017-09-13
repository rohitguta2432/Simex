package com.softage.paytm.service;

import com.softage.paytm.models.AoAuditEntity;
import com.softage.paytm.models.TblScan;
import org.json.simple.JSONObject;

/**
 * Created by SS0090 on 1/12/2017.
 */
public interface AoAuditService {
    public JSONObject getAoAuditDetails(String spokecode,String empcode);
    public JSONObject getAoAuditDetailsByCircleCode(int circle_code,String empcode);
    public JSONObject getAoAuditDetailsByCircleCodeBasedONPhoneNo(int circle_code,String empcode,String PhoneNo);
    public String saveAuditEntity(AoAuditEntity aoAuditEntity);
    public JSONObject getFormRecievingDetails(String mobile_number,String spokecode);
    public String checkAoAssignedTo(int tblScan,String empcode);
    public String insertAoAuditValues(String dob,String name,String otherReason,String photo,String sign,Integer scanid,Integer auditStatus);
}
