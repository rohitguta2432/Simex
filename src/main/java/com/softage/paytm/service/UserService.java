package com.softage.paytm.service;

import com.softage.paytm.models.ActivateLogEntity;
import com.softage.paytm.models.EmplogintableEntity;
import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 09-01-2016.
 */
public interface UserService {
    public EmplogintableEntity getUserByEmpcode(String empCode);
    public EmplogintableEntity getUserByToken(String token);
    public JSONObject getEmpFtpDetails(int circleCode);
    public JSONObject getStatus(String mobileno);
    public String updateAgentStatus(EmplogintableEntity emplogintableEntity);
    public EmplogintableEntity getUserByOldPassword(String OldPassword,String user);
    public String UpdateLastThreePassword(EmplogintableEntity emplogintableEntity,String updatedpassword);
    public String updateAttaptStatus(EmplogintableEntity emplogintableEntity);
    public String getClientCode(String UserType);
    public String saveActivateEntity(ActivateLogEntity activateLogEntity);
}
