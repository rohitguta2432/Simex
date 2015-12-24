package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "paytmcustomer_data")
public class PaytmcustomerDataEntity {
    private String pcdCustomerPhone;
    private String allocationStatus;
    private String pcdAddress;
    private String pcdArea;
    private String pcdCity;
    private String pcdEmailId;
    private int pcdId;
    private String pcdImportBy;
    private Timestamp pcdImportDate;
    private String pcdImportType;
    private String pcdLandmark;
    private String pcdName;
    private String pcdPincode;
    private String pcdState;
    private String pcdVisitDate;
    private String pcdVisitTIme;
    private Collection<AllocationMastEntity> allocationMastsByPcdCustomerPhone;
    private Collection<AppointmentMastEntity> appointmentMastsByPcdCustomerPhone;

 /*   public void setPcdVisitDate(String pcdVisitDate) {
        this.pcdVisitDate = pcdVisitDate;
    }

    public void setPcdVisitTIme(String pcdVisitTIme) {
        this.pcdVisitTIme = pcdVisitTIme;
    }*/

    @Id
    @Column(name = "PCD_CustomerPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getPcdCustomerPhone() {
        return pcdCustomerPhone;
    }

    public void setPcdCustomerPhone(String pcdCustomerPhone) {
        this.pcdCustomerPhone = pcdCustomerPhone;
    }

    @Basic
    @Column(name = "Allocation_Status", nullable = true, insertable = true, updatable = true, length = 2)
    public String getAllocationStatus() {
        return allocationStatus;
    }

    public void setAllocationStatus(String allocationStatus) {
        this.allocationStatus = allocationStatus;
    }

    @Basic
    @Column(name = "PCD_Address", nullable = false, insertable = true, updatable = true, length = 150)
    public String getPcdAddress() {
        return pcdAddress;
    }

    public void setPcdAddress(String pcdAddress) {
        this.pcdAddress = pcdAddress;
    }

    @Basic
    @Column(name = "PCD_Area", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPcdArea() {
        return pcdArea;
    }

    public void setPcdArea(String pcdArea) {
        this.pcdArea = pcdArea;
    }

    @Basic
    @Column(name = "PCD_City", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPcdCity() {
        return pcdCity;
    }

    public void setPcdCity(String pcdCity) {
        this.pcdCity = pcdCity;
    }

    @Basic
    @Column(name = "PCD_EmailID", nullable = true, insertable = true, updatable = true, length = 150)
    public String getPcdEmailId() {
        return pcdEmailId;
    }

    public void setPcdEmailId(String pcdEmailId) {
        this.pcdEmailId = pcdEmailId;
    }

    @Basic
    @Column(name = "PCD_Id", nullable = false, insertable = true, updatable = true)
    public int getPcdId() {
        return pcdId;
    }

    public void setPcdId(int pcdId) {
        this.pcdId = pcdId;
    }

    @Basic
    @Column(name = "PCD_ImportBy", nullable = false, insertable = true, updatable = true, length = 20)
    public String getPcdImportBy() {
        return pcdImportBy;
    }

    public void setPcdImportBy(String pcdImportBy) {
        this.pcdImportBy = pcdImportBy;
    }

    @Basic
    @Column(name = "PCD_ImportDate", nullable = false, insertable = true, updatable = true)
    public Timestamp getPcdImportDate() {
        return pcdImportDate;
    }

    public void setPcdImportDate(Timestamp pcdImportDate) {
        this.pcdImportDate = pcdImportDate;
    }

    @Basic
    @Column(name = "PCD_ImportType", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPcdImportType() {
        return pcdImportType;
    }

    public void setPcdImportType(String pcdImportType) {
        this.pcdImportType = pcdImportType;
    }

    @Basic
    @Column(name = "PCD_Landmark", nullable = true, insertable = true, updatable = true, length = 150)
    public String getPcdLandmark() {
        return pcdLandmark;
    }

    public void setPcdLandmark(String pcdLandmark) {
        this.pcdLandmark = pcdLandmark;
    }

    @Basic
    @Column(name = "PCD_Name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPcdName() {
        return pcdName;
    }

    public void setPcdName(String pcdName) {
        this.pcdName = pcdName;
    }

    @Basic
    @Column(name = "PCD_Pincode", nullable = false, insertable = true, updatable = true, length = 10)
    public String getPcdPincode() {
        return pcdPincode;
    }

    public void setPcdPincode(String pcdPincode) {
        this.pcdPincode = pcdPincode;
    }

    @Basic
    @Column(name = "PCD_State", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPcdState() {
        return pcdState;
    }

    public void setPcdState(String pcdState) {
        this.pcdState = pcdState;
    }

    @Basic
    @Column(name = "PCD_VisitDate", nullable = false, insertable = true, updatable = true, length = 10)
    public String getPcdVisitDate() {
        return pcdVisitDate;
    }

    public void setPcdVisitDate(String pcdVisitDate) {
        this.pcdVisitDate = pcdVisitDate;
    }

    @Basic
    @Column(name = "PCD_VisitTIme", nullable = false, insertable = true, updatable = true, length = 16)
    public String getPcdVisitTIme() {
        return pcdVisitTIme;
    }

    public void setPcdVisitTIme(String pcdVisitTIme) {
        this.pcdVisitTIme = pcdVisitTIme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaytmcustomerDataEntity that = (PaytmcustomerDataEntity) o;

        if (pcdId != that.pcdId) return false;
        if (pcdCustomerPhone != null ? !pcdCustomerPhone.equals(that.pcdCustomerPhone) : that.pcdCustomerPhone != null)
            return false;
        if (allocationStatus != null ? !allocationStatus.equals(that.allocationStatus) : that.allocationStatus != null)
            return false;
        if (pcdAddress != null ? !pcdAddress.equals(that.pcdAddress) : that.pcdAddress != null) return false;
        if (pcdArea != null ? !pcdArea.equals(that.pcdArea) : that.pcdArea != null) return false;
        if (pcdCity != null ? !pcdCity.equals(that.pcdCity) : that.pcdCity != null) return false;
        if (pcdEmailId != null ? !pcdEmailId.equals(that.pcdEmailId) : that.pcdEmailId != null) return false;
        if (pcdImportBy != null ? !pcdImportBy.equals(that.pcdImportBy) : that.pcdImportBy != null) return false;
        if (pcdImportDate != null ? !pcdImportDate.equals(that.pcdImportDate) : that.pcdImportDate != null)
            return false;
        if (pcdImportType != null ? !pcdImportType.equals(that.pcdImportType) : that.pcdImportType != null)
            return false;
        if (pcdLandmark != null ? !pcdLandmark.equals(that.pcdLandmark) : that.pcdLandmark != null) return false;
        if (pcdName != null ? !pcdName.equals(that.pcdName) : that.pcdName != null) return false;
        if (pcdPincode != null ? !pcdPincode.equals(that.pcdPincode) : that.pcdPincode != null) return false;
        if (pcdState != null ? !pcdState.equals(that.pcdState) : that.pcdState != null) return false;
        if (pcdVisitDate != null ? !pcdVisitDate.equals(that.pcdVisitDate) : that.pcdVisitDate != null) return false;
        if (pcdVisitTIme != null ? !pcdVisitTIme.equals(that.pcdVisitTIme) : that.pcdVisitTIme != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pcdCustomerPhone != null ? pcdCustomerPhone.hashCode() : 0;
        result = 31 * result + (allocationStatus != null ? allocationStatus.hashCode() : 0);
        result = 31 * result + (pcdAddress != null ? pcdAddress.hashCode() : 0);
        result = 31 * result + (pcdArea != null ? pcdArea.hashCode() : 0);
        result = 31 * result + (pcdCity != null ? pcdCity.hashCode() : 0);
        result = 31 * result + (pcdEmailId != null ? pcdEmailId.hashCode() : 0);
        result = 31 * result + pcdId;
        result = 31 * result + (pcdImportBy != null ? pcdImportBy.hashCode() : 0);
        result = 31 * result + (pcdImportDate != null ? pcdImportDate.hashCode() : 0);
        result = 31 * result + (pcdImportType != null ? pcdImportType.hashCode() : 0);
        result = 31 * result + (pcdLandmark != null ? pcdLandmark.hashCode() : 0);
        result = 31 * result + (pcdName != null ? pcdName.hashCode() : 0);
        result = 31 * result + (pcdPincode != null ? pcdPincode.hashCode() : 0);
        result = 31 * result + (pcdState != null ? pcdState.hashCode() : 0);
        result = 31 * result + (pcdVisitDate != null ? pcdVisitDate.hashCode() : 0);
        result = 31 * result + (pcdVisitTIme != null ? pcdVisitTIme.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "paytmcustomerDataByCustomerPhone")
    public Collection<AllocationMastEntity> getAllocationMastsByPcdCustomerPhone() {
        return allocationMastsByPcdCustomerPhone;
    }

    public void setAllocationMastsByPcdCustomerPhone(Collection<AllocationMastEntity> allocationMastsByPcdCustomerPhone) {
        this.allocationMastsByPcdCustomerPhone = allocationMastsByPcdCustomerPhone;
    }



    @OneToMany(mappedBy = "paytmcustomerDataByCustomerPhone")
    public Collection<AppointmentMastEntity> getAppointmentMastsByPcdCustomerPhone() {
        return appointmentMastsByPcdCustomerPhone;
    }

    public void setAppointmentMastsByPcdCustomerPhone(Collection<AppointmentMastEntity> appointmentMastsByPcdCustomerPhone) {
        this.appointmentMastsByPcdCustomerPhone = appointmentMastsByPcdCustomerPhone;
    }
}
