package com.softage.paytm.service;

/**
 * Created by SS0090 on 7/18/2016.
 */
public interface FtpDetailsService {

    public String saveFTPData(String custNumber, String imgPath, int pageNo, String createdBy, int qcStatus);
}
