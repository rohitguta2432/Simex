package com.softage.paytm.service.imp;

import com.softage.paytm.dao.FtpDetailsDao;
import com.softage.paytm.service.FtpDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0090 on 7/18/2016.
 */
@Service
public class FtpDetailsServiceImp implements FtpDetailsService {
    @Autowired
    private  FtpDetailsDao ftpDetailsDao;

    @Override
    public String saveFTPData(String custNumber, String imgPath, int pageNo, String createdBy, int qcStatus) {
        String result= ftpDetailsDao.insertFtpDetails(custNumber, imgPath, pageNo, createdBy, qcStatus);

        return result;

    }

}
