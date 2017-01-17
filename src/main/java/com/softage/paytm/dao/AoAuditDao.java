package com.softage.paytm.dao;

import com.softage.paytm.models.AoAuditEntity;
import org.json.simple.JSONObject;

/**
 * Created by SS0090 on 1/12/2017.
 */
public interface AoAuditDao {
    public JSONObject getAoAuditDetails(String spoke,String empcode);
    public String saveAoAuditEntity(AoAuditEntity aoAuditEntity);
    public JSONObject getFormRecievingDetails(String mobileNumber,String spokecode);
}
