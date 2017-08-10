package com.softage.paytm.dao;

public interface SaveAadharDetailsDao {
    public String saveAadharDetailData(String cust_id, String aadharNo, String residentName, String dob, String gender,
                                         String mobNo, String emailId, String careOf, String landmark, String locality,
                                        String vtc, String district, String hNo, String street, String postOffice, String subDistrict, String state, String pincode
    );
}


