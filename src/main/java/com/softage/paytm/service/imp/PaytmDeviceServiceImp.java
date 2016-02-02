package com.softage.paytm.service.imp;

import com.softage.paytm.dao.PaytmDeviceDao;
import com.softage.paytm.models.PaytmdeviceidinfoEntity;
import com.softage.paytm.service.PaytmDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 02-02-2016.
 */
@Service
public class PaytmDeviceServiceImp implements PaytmDeviceService {
    @Autowired
    private PaytmDeviceDao paytmDeviceDao;

    @Override
    public String saveDevice(PaytmdeviceidinfoEntity paytmdeviceidinfoEntity) {
        return paytmDeviceDao.saveDevice(paytmdeviceidinfoEntity);
    }
}
