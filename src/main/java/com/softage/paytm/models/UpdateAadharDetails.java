package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0068 on 8/8/2017.
 */
@Entity
@Table(name = "tbl_aadhar_details")
public class UpdateAadharDetails{

    private int id;
    private int customerId;
    private int aadharNo;
    private String mobNo;
    private String residentName;
    private String dateOfBirth;
    private String gender;
    private String imagePath;
    private String identity;
    private String emailId;
    private String placeOfOrigin;
    private String careOf;
    private String landmark;
    private String locality;
    private String vtc;
    private String district;
    private String HouseNo;
    private String street;
    private String postOffice;
    private String subDistrict;
    private String state;
    private int pincode;
    private String uploadedBy;
    private String uploadedOn;

    @Id
    //@javax.persistence.Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "cust_id",nullable = false,insertable = true,updatable = true)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    @Column(name = "aadhar_no",nullable = false,insertable = true,updatable = true)
    public int getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(int aadharNo) {
        this.aadharNo = aadharNo;
    }
    @Column(name = "mob_no",nullable = false,insertable = true,updatable = true)
    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }
    @Column(name = "res_name",nullable = false,insertable = true,updatable = true)
    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }
    @Column(name = "date_of_birth",nullable = false,insertable = true,updatable = true)
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    @Column(name = "gender",nullable = false,insertable = true,updatable = true)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    @Column(name = "image_path",nullable = false,insertable = true,updatable = true)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    @Column(name = "identity",nullable = false,insertable = true,updatable = true)
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    @Column(name = "email_id",nullable = false,insertable = true,updatable = true)
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    @Column(name = "place_of_origin",nullable = false,insertable = true,updatable = true)
    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }
    @Column(name = "care_of",nullable = false,insertable = true,updatable = true)
    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }
    @Column(name = "landmark",nullable = false,insertable = true,updatable = true)
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
    @Column(name = "locality",nullable = false,insertable = true,updatable = true)
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
    @Column(name = "vtc",nullable = false,insertable = true,updatable = true)
    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }
    @Column(name = "district",nullable = false,insertable = true,updatable = true)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    @Column(name = "house_no",nullable = false,insertable = true,updatable = true)
    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }
    @Column(name = "street",nullable = false,insertable = true,updatable = true)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    @Column(name = "post_office",nullable = false,insertable = true,updatable = true)
    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }
    @Column(name = "sub_district",nullable = false,insertable = true,updatable = true)
    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }
    @Column(name = "state",nullable = false,insertable = true,updatable = true)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Column(name = "pincode",nullable = false,insertable = true,updatable = true)
    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    @Column(name = "upload_by",nullable = false,insertable = true,updatable = true)
    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    @Column(name = "upload_On",nullable = false,insertable = true,updatable = true)
    public String getUploadedOn() {
        return uploadedOn;
    }
    public void setUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
    }
}
