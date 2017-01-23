package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "paytm_mast")
@Cacheable

public class PaytmMastEntity {
    private Integer cust_uid;
    private String customerPhone;
    private Date requestDate;
    private String address;
    private String alternatePhone1;
    private String alternatePhone2;
    //private String addressStreet1;
    //private String addressStreet2;
    private String city;
    private String createdTimestamp;
    private String customerId;
    private String email;
    private String importBy;
    private Timestamp importDate;
    private String coID;
   // private String otp;
    private String pincode;
    //private String priority;
    //private long refCode;
    //private String stageId;
    private String state;
    //private String subStageId;
    //private String timeSlot;
    private String username;
    private String vendorName;
    private Integer cirCode;
    private String simType;
    private CircleMastEntity circleMastByCirCode;
    private TelecallMastEntity telecallMastByCustomerPhone;
    private String coStatus;
    private String chReasonDesc;
    private String simPlanDesc;
    private String lotNo;
    private String remarks;
    private Collection<TelecallLogEntity> telecallLogEntity;
    private CallTimeDetailsEntity callTimeDetailsEntity;


    @Id
    @GeneratedValue
    @Column(name = "cust_uid",nullable = false,insertable = true,updatable = true,length = 10)
    public Integer getCust_uid() {
        return cust_uid;
    }

    public void setCust_uid(Integer cust_uid) {
        this.cust_uid = cust_uid;
    }

   @Basic
   @Column(name = "CustomerPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "request_date",nullable = false,insertable = true,updatable = true)
    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }



    @Basic
    @Column(name = "Address", nullable = true, insertable = true, updatable = true, length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Basic
    @Column(name = "alternatePhone1", nullable = true, insertable = true, updatable = true, length = 15)
    public String getAlternatePhone1() {
        return alternatePhone1;
    }

    public void setAlternatePhone1(String alternatePhone1) {
        this.alternatePhone1 = alternatePhone1;
    }

    @Basic
    @Column(name = "alternatePhone2", nullable = true, insertable = true, updatable = true, length = 15)
    public String getAlternatePhone2() {
        return alternatePhone2;
    }

    public void setAlternatePhone2(String alternatePhone2) {
        this.alternatePhone2 = alternatePhone2;
    }

    /*   @Basic
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
    */
    @Basic
    @Column(name = "City", nullable = false, insertable = true, updatable = true, length = 100)
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
    @Column(name = "Email", insertable = true, updatable = true, length = 100)
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
    @Column(name = "sim_type",nullable = false,insertable = true,updatable = true,length = 10)
    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    @Basic
    @Column(name = "co_id", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCoID() {
        return coID;
    }

    public void setCoID(String coID) {
        this.coID = coID;
    }






  /*  @Basic
    @Column(name = "otp", nullable = false, insertable = true, updatable = true, length = 5)
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }*/

    @Basic
    @Column(name = "Pincode", nullable = false, insertable = true, updatable = true, length = 6)
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

/*    @Basic
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
    }*/

    @Basic
    @Column(name = "State", insertable = true, updatable = true, length = 30)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

 /*   @Basic
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
    }*/

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

        //if (refCode != that.refCode) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (alternatePhone1 != null ? !alternatePhone1.equals(that.alternatePhone1) : that.alternatePhone1 != null) return false;
        if (alternatePhone2 != null ? !alternatePhone2.equals(that.alternatePhone2) : that.alternatePhone2 != null) return false;
/*        if (addressStreet1 != null ? !addressStreet1.equals(that.addressStreet1) : that.addressStreet1 != null)
            return false;
        if (addressStreet2 != null ? !addressStreet2.equals(that.addressStreet2) : that.addressStreet2 != null)
            return false;*/
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (createdTimestamp != null ? !createdTimestamp.equals(that.createdTimestamp) : that.createdTimestamp != null)
            return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        //if (kycRequestId != null ? !kycRequestId.equals(that.kycRequestId) : that.kycRequestId != null) return false;
        //if (otp != null ? !otp.equals(that.otp) : that.otp != null) return false;
        if (pincode != null ? !pincode.equals(that.pincode) : that.pincode != null) return false;
       // if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
       // if (stageId != null ? !stageId.equals(that.stageId) : that.stageId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
       // if (subStageId != null ? !subStageId.equals(that.subStageId) : that.subStageId != null) return false;
      //  if (timeSlot != null ? !timeSlot.equals(that.timeSlot) : that.timeSlot != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (vendorName != null ? !vendorName.equals(that.vendorName) : that.vendorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (alternatePhone1 != null ? alternatePhone1.hashCode() : 0);
        result = 31 * result + (alternatePhone2 != null ? alternatePhone2.hashCode() : 0);
 //       result = 31 * result + (addressStreet1 != null ? addressStreet1.hashCode() : 0);
  //      result = 31 * result + (addressStreet2 != null ? addressStreet2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (createdTimestamp != null ? createdTimestamp.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
       // result = 31 * result + (kycRequestId != null ? kycRequestId.hashCode() : 0);
        //result = 31 * result + (otp != null ? otp.hashCode() : 0);
        result = 31 * result + (pincode != null ? pincode.hashCode() : 0);
       // result = 31 * result + (priority != null ? priority.hashCode() : 0);
       // result = 31 * result + (int) (refCode ^ (refCode >>> 32));
      //  result = 31 * result + (stageId != null ? stageId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
      //  result = 31 * result + (subStageId != null ? subStageId.hashCode() : 0);
      //  result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
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

    @Basic
    @Column(name = "co_status",nullable = false,insertable = true,updatable = true,length = 10)
    public String getCoStatus() {
        return coStatus;
    }

    public void setCoStatus(String coStatus) {
        this.coStatus = coStatus;
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


    @OneToMany(mappedBy = "paytmMastEntity")
    public Collection<TelecallLogEntity> getTelecallLogEntity() {
        return telecallLogEntity;
    }

    public void setTelecallLogEntity(Collection<TelecallLogEntity> telecallLogEntity) {
        this.telecallLogEntity = telecallLogEntity;
    }

    @OneToOne(mappedBy = "paytmcustomerDataEntity")
    public CallTimeDetailsEntity getCallTimeDetailsEntity() {
        return callTimeDetailsEntity;
    }

    public void setCallTimeDetailsEntity(CallTimeDetailsEntity callTimeDetailsEntity) {
        this.callTimeDetailsEntity = callTimeDetailsEntity;
    }


    @Override
    public String toString() {
        return "PaytmMastEntity{" +
                ", addressId='" + address + '\'' +
                ", addressPhone1='" + alternatePhone1 + '\'' +
                ", addressPhone2='" + alternatePhone1 + '\'' +
     //           ", addressStreet1='" + addressStreet1 + '\'' +
      //          ", addressStreet2='" + addressStreet2 + '\'' +
                ", city='" + city + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                ", customerId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", importBy='" + importBy + '\'' +
                ", importDate=" + importDate +
               // ", kycRequestId='" + kycRequestId + '\'' +
                //", otp='" + otp + '\'' +
                ", pincode='" + pincode + '\'' +
              //  ", priority='" + priority + '\'' +
              //  ", refCode=" + refCode +
              //  ", stageId='" + stageId + '\'' +
                ", state='" + state + '\'' +
              //  ", subStageId='" + subStageId + '\'' +
              //  ", timeSlot='" + timeSlot + '\'' +
                ", username='" + username + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", cirCode=" + cirCode +
                ", circleMastByCirCode=" + circleMastByCirCode +
                '}';
    }

    @Basic
    @Column(name = "ch_reason_desc",nullable = false,insertable = true,updatable = true)
    public String getChReasonDesc() {
        return chReasonDesc;
    }


    public void setChReasonDesc(String chReasonDesc) {
        this.chReasonDesc = chReasonDesc;
    }
    @Basic
    @Column(name = "sim_plan_desc",nullable = false,insertable = true,updatable = true)
    public String getSimPlanDesc() {
        return simPlanDesc;
    }

    public void setSimPlanDesc(String simPlanDesc) {
        this.simPlanDesc = simPlanDesc;
    }

    @Basic
    @Column(name = "lot_no",nullable = false,insertable = true,updatable = true)
    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    @Basic
    @Column(name = "remarks",nullable = false,insertable = true,updatable = true)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
