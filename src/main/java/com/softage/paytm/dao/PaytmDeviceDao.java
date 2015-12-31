package com.softage.paytm.dao;

import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.models.PaytmdeviceidinfoEntity;

/**
 * Created by SS0085 on 02-01-2016.
 */
public interface PaytmDeviceDao {

    public String savePaytmDevice(PaytmdeviceidinfoEntity paytmdeviceidinfoEntity);
    public PaytmdeviceidinfoEntity getByloginId(String loginid);
}
