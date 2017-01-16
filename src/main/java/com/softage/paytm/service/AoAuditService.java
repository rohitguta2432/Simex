package com.softage.paytm.service;

import com.softage.paytm.models.AoAuditEntity;
import org.json.simple.JSONObject;

/**
 * Created by SS0090 on 1/12/2017.
 */
public interface AoAuditService {
    public JSONObject getAoAuditDetails(String spokecode);
    public String saveAuditEntity(AoAuditEntity aoAuditEntity);
    public JSONObject getFormRecievingDetails(String mobile_number,String spokecode);
}
