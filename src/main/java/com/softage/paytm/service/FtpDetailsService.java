package com.softage.paytm.service;

import com.softage.paytm.models.UploadedImagesEntity;

/**
 * Created by SS0090 on 7/18/2016.
 */
public interface FtpDetailsService {

    public String saveFTPData(String custNumber, String imgPath, int pageNo, String createdBy, int qcStatus);
    public String saveImagesDeetails(UploadedImagesEntity  imagesEntity);
}
