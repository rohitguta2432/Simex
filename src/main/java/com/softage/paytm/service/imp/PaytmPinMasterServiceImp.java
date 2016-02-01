package com.softage.paytm.service.imp;

import com.softage.paytm.dao.PaytmPinMasterDao;
import com.softage.paytm.models.PaytmPinMaster;
import com.softage.paytm.service.PaytmPinMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 21-01-2016.
 */
@Service
public class PaytmPinMasterServiceImp implements PaytmPinMasterService {
    @Autowired
    private PaytmPinMasterDao paytmPinMasterDao;

    @Override
    public PaytmPinMaster getByPincode(int pinCode) {
        return paytmPinMasterDao.getByPincode(pinCode);
    }

    @Override
    public PaytmPinMaster getByPincodeState(int pinCode, String circleName) {
        return paytmPinMasterDao.getByPincodeState(pinCode,circleName);
    }
}
