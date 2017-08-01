package com.softage.paytm.service.imp;

import com.softage.paytm.dao.MobileAppDao;
import com.softage.paytm.service.MobileAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 01-08-2017.
 */

@Service
public class MobileAppServiceImp implements MobileAppService{

    @Autowired
    private MobileAppDao mobileAppDao;

    @Override
    public String test(String value) {
        return mobileAppDao.test(value);
    }
}
