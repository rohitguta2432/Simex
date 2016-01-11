package com.softage.paytm.dao;

import com.softage.paytm.models.EmplogintableEntity;

/**
 * Created by SS0085 on 09-01-2016.
 */
public interface UserDao {
    public EmplogintableEntity getUserByEmpcode(String empCode);
}
