package com.softage.paytm.service;

import com.softage.paytm.models.PaytmdeviceidinfoEntity;

/**
 * Created by SS0085 on 02-02-2016.
 */
public interface PaytmDeviceService {

    public String saveDevice(PaytmdeviceidinfoEntity paytmdeviceidinfoEntity);
    public PaytmdeviceidinfoEntity getByloginId(String loginid);
    public String updateDevice(PaytmdeviceidinfoEntity paytmdeviceidinfoEntity);
}
