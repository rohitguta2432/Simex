package com.softage.paytm.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "dataentry")
@Cacheable
public class DataentryEntity implements Serializable {
    private String customerPhone;
    private String cusAdd;
    private String cusArea;
    private String cusCity;
    private String cusDob;
    private String cusEmailId;
    private String cusName;
    private String cusPincode;
    private String cusPoaNumber;
    private String cusPoiNumber;
    private String cusState;
    private String customerId;
    private Timestamp dateOfCollection;
    private String docStatus;
    private String entryBy;
    private Timestamp entryDateTime;
    private String gender;
    private int id;
    private String cusPoiCode;
    private String agentCode;
    private int allocationId;
    private String cusPOACode;
    private String rejectionResion;
    private PaytmagententryEntity paytmagententryByAgentCode;
    private AllocationMastEntity allocationMastByAllocationId;
    private ProofMastEntity proofMastByCcusPOACode;
    private ProofMastEntity proofMastByCusPoiCode;
    private ReasonMastEntity reasonMastByRejectionResion;

    @Basic
    @Id
    @Column(name = "CustomerPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "CusAdd", nullable = false, insertable = true, updatable = true, length = 250)
    public String getCusAdd() {
        return cusAdd;
    }

    public void setCusAdd(String cusAdd) {
        this.cusAdd = cusAdd;
    }

    @Basic
    @Column(name = "CusArea", nullable = false, insertable = true, updatable = true, length = 50)
    public String getCusArea() {
        return cusArea;
    }

    public void setCusArea(String cusArea) {
        this.cusArea = cusArea;
    }

    @Basic
    @Column(name = "CusCity", nullable = false, insertable = true, updatable = true, length = 30)
    public String getCusCity() {
        return cusCity;
    }

    public void setCusCity(String cusCity) {
        this.cusCity = cusCity;
    }

    @Basic
    @Column(name = "CusDOB", nullable = false, insertable = true, updatable = true, length = 20)
    public String getCusDob() {
        return cusDob;
    }

    public void setCusDob(String cusDob) {
        this.cusDob = cusDob;
    }

    @Basic
    @Column(name = "CusEmailID", nullable = false, insertable = true, updatable = true, length = 70)
    public String getCusEmailId() {
        return cusEmailId;
    }

    public void setCusEmailId(String cusEmailId) {
        this.cusEmailId = cusEmailId;
    }

    @Basic
    @Column(name = "CusName", nullable = false, insertable = true, updatable = true, length = 70)
    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    @Basic
    @Column(name = "CusPincode", nullable = false, insertable = true, updatable = true, length = 6)
    public String getCusPincode() {
        return cusPincode;
    }

    public void setCusPincode(String cusPincode) {
        this.cusPincode = cusPincode;
    }

    @Basic
    @Column(name = "CusPOANumber", nullable = true, insertable = true, updatable = true, length = 20)
    public String getCusPoaNumber() {
        return cusPoaNumber;
    }

    public void setCusPoaNumber(String cusPoaNumber) {
        this.cusPoaNumber = cusPoaNumber;
    }

    @Basic
    @Column(name = "CusPOINumber", nullable = true, insertable = true, updatable = true, length = 20)
    public String getCusPoiNumber() {
        return cusPoiNumber;
    }

    public void setCusPoiNumber(String cusPoiNumber) {
        this.cusPoiNumber = cusPoiNumber;
    }

    @Basic
    @Column(name = "CusState", nullable = false, insertable = true, updatable = true, length = 30)
    public String getCusState() {
        return cusState;
    }

    public void setCusState(String cusState) {
        this.cusState = cusState;
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
    @Column(name = "Date_of_collection", nullable = true, insertable = true, updatable = true)
    public Timestamp getDateOfCollection() {
        return dateOfCollection;
    }

    public void setDateOfCollection(Timestamp dateOfCollection) {
        this.dateOfCollection = dateOfCollection;
    }

    @Basic
    @Column(name = "DocStatus", nullable = true, insertable = true, updatable = true, length = 1)
    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    @Basic
    @Column(name = "EntryBy", nullable = false, insertable = true, updatable = true, length = 30)
    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    @Basic
    @Column(name = "EntryDateTime", nullable = true, insertable = true, updatable = true)
    public Timestamp getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(Timestamp entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    @Basic
    @Column(name = "gender", nullable = false, insertable = true, updatable = true, length = 10)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Id
    @Basic
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataentryEntity that = (DataentryEntity) o;

        if (id != that.id) return false;
        if (customerPhone != null ? !customerPhone.equals(that.customerPhone) : that.customerPhone != null)
            return false;
        if (cusAdd != null ? !cusAdd.equals(that.cusAdd) : that.cusAdd != null) return false;
        if (cusArea != null ? !cusArea.equals(that.cusArea) : that.cusArea != null) return false;
        if (cusCity != null ? !cusCity.equals(that.cusCity) : that.cusCity != null) return false;
        if (cusDob != null ? !cusDob.equals(that.cusDob) : that.cusDob != null) return false;
        if (cusEmailId != null ? !cusEmailId.equals(that.cusEmailId) : that.cusEmailId != null) return false;
        if (cusName != null ? !cusName.equals(that.cusName) : that.cusName != null) return false;
        if (cusPincode != null ? !cusPincode.equals(that.cusPincode) : that.cusPincode != null) return false;
        if (cusPoaNumber != null ? !cusPoaNumber.equals(that.cusPoaNumber) : that.cusPoaNumber != null) return false;
        if (cusPoiNumber != null ? !cusPoiNumber.equals(that.cusPoiNumber) : that.cusPoiNumber != null) return false;
        if (cusState != null ? !cusState.equals(that.cusState) : that.cusState != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (dateOfCollection != null ? !dateOfCollection.equals(that.dateOfCollection) : that.dateOfCollection != null)
            return false;
        if (docStatus != null ? !docStatus.equals(that.docStatus) : that.docStatus != null) return false;
        if (entryBy != null ? !entryBy.equals(that.entryBy) : that.entryBy != null) return false;
        if (entryDateTime != null ? !entryDateTime.equals(that.entryDateTime) : that.entryDateTime != null)
            return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerPhone != null ? customerPhone.hashCode() : 0;
        result = 31 * result + (cusAdd != null ? cusAdd.hashCode() : 0);
        result = 31 * result + (cusArea != null ? cusArea.hashCode() : 0);
        result = 31 * result + (cusCity != null ? cusCity.hashCode() : 0);
        result = 31 * result + (cusDob != null ? cusDob.hashCode() : 0);
        result = 31 * result + (cusEmailId != null ? cusEmailId.hashCode() : 0);
        result = 31 * result + (cusName != null ? cusName.hashCode() : 0);
        result = 31 * result + (cusPincode != null ? cusPincode.hashCode() : 0);
        result = 31 * result + (cusPoaNumber != null ? cusPoaNumber.hashCode() : 0);
        result = 31 * result + (cusPoiNumber != null ? cusPoiNumber.hashCode() : 0);
        result = 31 * result + (cusState != null ? cusState.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (dateOfCollection != null ? dateOfCollection.hashCode() : 0);
        result = 31 * result + (docStatus != null ? docStatus.hashCode() : 0);
        result = 31 * result + (entryBy != null ? entryBy.hashCode() : 0);
        result = 31 * result + (entryDateTime != null ? entryDateTime.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Basic
    @Column(name = "CusPOICode", nullable = false, insertable = false, updatable = false, length = 10)
    public String getCusPoiCode() {
        return cusPoiCode;
    }

    public void setCusPoiCode(String cusPoiCode) {
        this.cusPoiCode = cusPoiCode;
    }

    @ManyToOne
    @JoinColumn(name = "CusPOICode", referencedColumnName = "Id_Code", nullable = false)
    public ProofMastEntity getProofMastByCusPoiCode() {
        return proofMastByCusPoiCode;
    }

    public void setProofMastByCusPoiCode(ProofMastEntity proofMastByCusPoiCode) {
        this.proofMastByCusPoiCode = proofMastByCusPoiCode;
    }
    @Basic
    @Column(name = "AgentCode", nullable = false, insertable = false, updatable = false, length = 10)
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    @Basic
    @Column(name = "Allocation_id", nullable = false, insertable = false, updatable = false, length = 10)
    public int getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(int allocationId) {
        this.allocationId = allocationId;
    }
    @ManyToOne
    @JoinColumn(name = "AgentCode", referencedColumnName = "Acode", nullable = false)
    public PaytmagententryEntity getPaytmagententryByAgentCode() {
        return paytmagententryByAgentCode;
    }

    public void setPaytmagententryByAgentCode(PaytmagententryEntity paytmagententryByAgentCode) {
        this.paytmagententryByAgentCode = paytmagententryByAgentCode;
    }
    @ManyToOne
    @JoinColumn(name = "Allocation_id", referencedColumnName = "id", nullable = false)
    public AllocationMastEntity getAllocationMastByAllocationId() {
        return allocationMastByAllocationId;
    }

    public void setAllocationMastByAllocationId(AllocationMastEntity allocationMastByAllocationId) {
        this.allocationMastByAllocationId = allocationMastByAllocationId;
    }
    @Basic
    @Column(name = "CusPOACode",  nullable = false, insertable = false, updatable = false, length = 10)
    public String getCusPOACode() {
        return cusPOACode;
    }

    public void setCusPOACode(String cusPOACode) {
        this.cusPOACode = cusPOACode;
    }
    @Basic
    @Column(name = "rejection_reason",  nullable = false, insertable = false, updatable = false, length = 100)
    public String getRejectionResion() {
        return rejectionResion;
    }

    public void setRejectionResion(String rejectionResion) {
        this.rejectionResion = rejectionResion;
    }

    @ManyToOne
    @JoinColumn(name = "rejection_reason", referencedColumnName = "Reason_code", nullable = false)
    public ReasonMastEntity getReasonMastByRejectionResion() {
        return reasonMastByRejectionResion;
    }

    public void setReasonMastByRejectionResion(ReasonMastEntity reasonMastByRejectionResion) {
        this.reasonMastByRejectionResion = reasonMastByRejectionResion;
    }

    @ManyToOne
    @JoinColumn(name = "CusPOACode", referencedColumnName = "Id_Code", nullable = false)
    public ProofMastEntity getProofMastByCcusPOACode() {
        return proofMastByCcusPOACode;
    }

    public void setProofMastByCcusPOACode(ProofMastEntity proofMastByCcusPOACode) {
        this.proofMastByCcusPOACode = proofMastByCcusPOACode;
    }
}
