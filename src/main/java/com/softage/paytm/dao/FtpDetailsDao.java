package com.softage.paytm.dao;

import com.softage.paytm.models.TblScan;
import com.softage.paytm.models.UploadedImagesEntity;

/**
 * Created by SS0090 on 7/18/2016.
 */
public interface FtpDetailsDao {

    public String insertFtpDetails(String custNumber, String imgPath, int pageNo, String createdBy, int qcStatus) ;
    public String insertImageUploadDetails(UploadedImagesEntity imagesEntity);

}
