package com.softage.paytm.service;

import com.softage.paytm.models.EmplogintableEntity;

/**
 * Created by SS0085 on 09-01-2016.
 */
public interface UserService {
    public EmplogintableEntity getUserByEmpcode(String empCode);
}
