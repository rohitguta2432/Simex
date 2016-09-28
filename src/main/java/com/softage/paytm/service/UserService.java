package com.softage.paytm.service;

import com.softage.paytm.models.EmplogintableEntity;
import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 09-01-2016.
 */
public interface UserService {
    public EmplogintableEntity getUserByEmpcode(String empCode);
    public JSONObject getEmpFtpDetails(int circleCode);
    public JSONObject getStatus(String mobileno);
    public String updateAgentStatus(EmplogintableEntity emplogintableEntity);
}
