package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "paytm_mast")
@Cacheable
public class PaytmMastEntity {
    private String customerPhone;
    private String addressId;
    private String addressPhone;
    private String addressStreet1;
    private String addressStreet2;
    private String city;
    private String createdTimestamp;
    private String customerId;
    private String email;
    private String importBy;
    private Timestamp importDate;
    private String kycRequestId;
    private String otp;
    private String pincode;
    private String priority;
    private long refCode;
    private String stageId;
    private String state;
    private String subStageId;
    private String timeSlot;
    private String username;
    private String vendorName;
    private Integer cirCode;
    private CircleMastEntity circleMastByCirCode;
    private TelecallMastEntity telecallMastByCustomerPhone;

    @Id
    @Column(name = "CustomerPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "AddressID", nullable = true, insertable = true, updatable = true, length = 50)
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "AddressPhone", nullable = true, insertable = true, updatable = true, length = 15)
    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    @Basic
    @Column(name = "AddressStreet1", nullable = true, insertable = true, updatable = true, length = 250)
    public String getAddressStreet1() {
        return addressStreet1;
    }

    public void setAddressStreet1(String addressStreet1) {
        this.addressStreet1 = addressStreet1;
    }

    @Basic
    @Column(name = "AddressStreet2", nullable = true, insertable = true, updatable = true, length = 250)
    public String getAddressStreet2() {
        return addressStreet2;
    }

    public void setAddressStreet2(String addressStreet2) {
        this.addressStreet2 = addressStreet2;
    }

    @Basic
    @Column(name = "City", nullable = false, insertable = true, updatable = true, length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "CreatedTimestamp", nullable = false, insertable = true, updatable = true, length = 100)
    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Basic
    @Column(name = "CustomerID", nullable = false, insertable = true, updatable = true, length = 20)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "Email", nullable = false, insertable = true, updatable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "ImportBy", nullable = true, insertable = true, updatable = true, length = 20)
    public String getImportBy() {
        return importBy;
    }

    public void setImportBy(String importBy) {
        this.importBy = importBy;
    }

    @Basic
    @Column(name = "ImportDate", nullable = false, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "KycRequestID", nullable = false, insertable = true, updatable = true, length = 10)
    public String getKycRequestId() {
        return kycRequestId;
    }

    public void setKycRequestId(String kycRequestId) {
        this.kycRequestId = kycRequestId;
    }

    @Basic
    @Column(name = "otp", nullable = false, insertable = true, updatable = true, length = 5)
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Basic
    @Column(name = "Pincode", nullable = false, insertable = true, updatable = true, length = 6)
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Basic
    @Column(name = "Priority", nullable = true, insertable = true, updatable = true, length = 30)
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "Ref_Code", nullable = false, insertable = true, updatable = true)
    public long getRefCode() {
        return refCode;
    }

    public void setRefCode(long refCode) {
        this.refCode = refCode;
    }

    @Basic
    @Column(name = "StageId", nullable = true, insertable = true, updatable = true, length = 2)
    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    @Basic
    @Column(name = "State", nullable = false, insertable = true, updatable = true, length = 30)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "SubStageId", nullable = true, insertable = true, updatable = true, length = 2)
    public String getSubStageId() {
        return subStageId;
    }

    public void setSubStageId(String subStageId) {
        this.subStageId = subStageId;
    }

    @Basic
    @Column(name = "TimeSlot", nullable = true, insertable = true, updatable = true, length = 100)
    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Basic
    @Column(name = "Username", nullable = false, insertable = true, updatable = true, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "VendorName", nullable = true, insertable = true, updatable = true, length = 30)
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaytmMastEntity that = (PaytmMastEntity) o;

        if (refCode != that.refCode) return false;
        if (customerPhone != null ? !customerPhone.equals(that.customerPhone) : that.customerPhone != null)
            return false;
        if (addressId != null ? !addressId.equals(that.addressId) : that.addressId != null) return false;
        if (addressPhone != null ? !addressPhone.equals(that.addressPhone) : that.addressPhone != null) return false;
        if (addressStreet1 != null ? !addressStreet1.equals(that.addressStreet1) : that.addressStreet1 != null)
            return false;
        if (addressStreet2 != null ? !addressStreet2.equals(that.addressStreet2) : that.addressStreet2 != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (createdTimestamp != null ? !createdTimestamp.equals(that.createdTimestamp) : that.createdTimestamp != null)
            return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (kycRequestId != null ? !kycRequestId.equals(that.kycRequestId) : that.kycRequestId != null) return false;
        if (otp != null ? !otp.equals(that.otp) : that.otp != null) return false;
        if (pincode != null ? !pincode.equals(that.pincode) : that.pincode != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
        if (stageId != null ? !stageId.equals(that.stageId) : that.stageId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (subStageId != null ? !subStageId.equals(that.subStageId) : that.subStageId != null) return false;
        if (timeSlot != null ? !timeSlot.equals(that.timeSlot) : that.timeSlot != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (vendorName != null ? !vendorName.equals(that.vendorName) : that.vendorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerPhone != null ? customerPhone.hashCode() : 0;
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (addressPhone != null ? addressPhone.hashCode() : 0);
        result = 31 * result + (addressStreet1 != null ? addressStreet1.hashCode() : 0);
        result = 31 * result + (addressStreet2 != null ? addressStreet2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (createdTimestamp != null ? createdTimestamp.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (kycRequestId != null ? kycRequestId.hashCode() : 0);
        result = 31 * result + (otp != null ? otp.hashCode() : 0);
        result = 31 * result + (pincode != null ? pincode.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (int) (refCode ^ (refCode >>> 32));
        result = 31 * result + (stageId != null ? stageId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (subStageId != null ? subStageId.hashCode() : 0);
        result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (vendorName != null ? vendorName.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "Cir_code", nullable = true, insertable = false, updatable = false)
    public Integer getCirCode() {
        return cirCode;
    }

    public void setCirCode(Integer cirCode) {
        this.cirCode = cirCode;
    }

    @ManyToOne
    @JoinColumn(name = "Cir_code", referencedColumnName = "Cir_code")
    public CircleMastEntity getCircleMastByCirCode() {
        return circleMastByCirCode;
    }

    public void setCircleMastByCirCode(CircleMastEntity circleMastByCirCode) {
        this.circleMastByCirCode = circleMastByCirCode;
    }

    @OneToOne(mappedBy = "paytmMastByTmCustomerPhone")
    public TelecallMastEntity getTelecallMastByCustomerPhone() {
        return telecallMastByCustomerPhone;
    }

    public void setTelecallMastByCustomerPhone(TelecallMastEntity telecallMastByCustomerPhone) {
        this.telecallMastByCustomerPhone = telecallMastByCustomerPhone;
    }

    @Override
    public String toString() {
        return "PaytmMastEntity{" +
                "customerPhone='" + customerPhone + '\'' +
                ", addressId='" + addressId + '\'' +
                ", addressPhone='" + addressPhone + '\'' +
                ", addressStreet1='" + addressStreet1 + '\'' +
                ", addressStreet2='" + addressStreet2 + '\'' +
                ", city='" + city + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                ", customerId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", importBy='" + importBy + '\'' +
                ", importDate=" + importDate +
                ", kycRequestId='" + kycRequestId + '\'' +
                ", otp='" + otp + '\'' +
                ", pincode='" + pincode + '\'' +
                ", priority='" + priority + '\'' +
                ", refCode=" + refCode +
                ", stageId='" + stageId + '\'' +
                ", state='" + state + '\'' +
                ", subStageId='" + subStageId + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                ", username='" + username + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", cirCode=" + cirCode +
                ", circleMastByCirCode=" + circleMastByCirCode +
                ", telecallMastByCustomerPhone=" + telecallMastByCustomerPhone +
                '}';
    }
}
