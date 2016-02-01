package com.softage.paytm.dao;

import com.softage.paytm.models.PaytmPinMaster;

/**
 * Created by SS0085 on 21-01-2016.
 */
public interface PaytmPinMasterDao {
    public PaytmPinMaster getByPincode(int pinCode);
    public PaytmPinMaster getByPincodeState(int pinCode,String circleName);
}
