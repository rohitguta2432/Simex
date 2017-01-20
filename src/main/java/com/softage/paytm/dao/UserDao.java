package com.softage.paytm.dao;

import com.softage.paytm.models.EmplogintableEntity;
import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 09-01-2016.
 */
public interface UserDao {
    public EmplogintableEntity getUserByEmpcode(String empCode);
    public EmplogintableEntity getUserByToken(String token);
    public EmplogintableEntity getUserByEmpNumber(String empNumber);
    public JSONObject getEmpFtpDetailsDao(int circleCode);
    public JSONObject getStatus(String mobileno);
    public String updateAgentStatus(EmplogintableEntity emplogintableEntity);
    public EmplogintableEntity getUserByOldPassword(String oldpassword,String user);
    public String UpdateLastThreePassword(EmplogintableEntity emplogintableEntity,String updatedpassword);
    public String updateAttaptStatus(EmplogintableEntity emplogintableEntity);
}
