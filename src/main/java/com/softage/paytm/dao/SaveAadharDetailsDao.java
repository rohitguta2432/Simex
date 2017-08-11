package com.softage.paytm.dao;

public interface SaveAadharDetailsDao {
    public String saveAadharDetailData(int cust_id, int aadharNo, String residentName, String dob, String gender,
                                         String mobNo, String emailId, String careOf, String landmark, String locality,
                                        String vtc, String district, String hNo, String street, String postOffice, String subDistrict, String state, int pin,String uploadedBy,
                                       String uploadedOn
    );
}


