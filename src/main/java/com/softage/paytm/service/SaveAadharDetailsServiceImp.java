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
    public String insertAadharDetails(String cust_id, String aadharNo, String residentName, String dob, String gender,
                                      String mobNo, String emailId, String careOf, String landmark, String locality,
                                      String vtc, String district, String hNo, String street, String postOffice, String subDistrict, String state, String pincode) {
        return saveAadharDetailsDao.saveAadharDetailData(cust_id, aadharNo,residentName, dob, gender, mobNo, emailId, careOf,landmark,locality,
                vtc, district, hNo,street,postOffice,subDistrict,state,pincode);
    }
}
