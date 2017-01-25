package com.softage.paytm.dao;

import com.softage.paytm.models.AoAuditEntity;
import com.softage.paytm.models.TblScan;
import org.json.simple.JSONObject;

/**
 * Created by SS0090 on 1/12/2017.
 */
public interface AoAuditDao {
    public JSONObject getAoAuditDetails(String spoke,String empcode);
    public String saveAoAuditEntity(AoAuditEntity aoAuditEntity);
    public JSONObject getFormRecievingDetails(String mobileNumber,String spokecode);
    public String checkAoAssignedTo(TblScan tblScan,String empcode);
    public String insertAoAuditValues(String dob,String name,String otherReason,String photo,String sign,Integer scanid,Integer auditStatus);
}
