package com.softage.paytm.service;

import com.softage.paytm.dao.AcceptedEntryDao;
import com.softage.paytm.dao.SaveAadharDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveAadharDetailsServiceImp implements SaveAadharDetailsService {

    @Autowired
    private SaveAadharDetailsDao saveAadharDetailsDao;

    @Override
    public String insertAadharDetails(int cust_id, int aadharNo, String residentName, String dob, String gender,
                                      String mobNo, String emailId, String careOf, String landmark, String locality,
                                      String vtc, String district, String hNo, String street, String postOffice, String subDistrict, String state, int pin,String uploadedBy,String uploadedOn) {
        return saveAadharDetailsDao.saveAadharDetailData(cust_id, aadharNo,residentName, dob, gender, mobNo, emailId, careOf,landmark,locality,
                vtc, district, hNo,street,postOffice,subDistrict,state,pin,uploadedBy,uploadedOn);
    }
}
