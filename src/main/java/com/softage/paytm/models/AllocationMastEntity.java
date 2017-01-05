package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "allocation_mast")
@Cacheable(false)
public class AllocationMastEntity {
    private int id;
    private Timestamp allocationDatetime;
    private String confirmation;
    private String confirmationAllowed;
    private Timestamp confirmationDatetime;
    private Long confirmationSmsid;
    private String finalConfirmation;
    private String importBy;
    private Timestamp importDate;
    private String kycCollected;
    private Timestamp smsSendDatetime;
    private Timestamp visitDateTime;
    private String customerPhone;
    private String agentCode;
    private String remarksCode;
    private Long appointmentId;
    private String spokeCode;
    private PaytmagententryEntity paytmagententryByAgentCode;
    private PaytmcustomerDataEntity paytmcustomerDataByCustomerPhone;
    private RemarkMastEntity remarkMastByRemarksCode;
    private AppointmentMastEntity appointmentMastByAppointmentId;
    private Collection<DataentryEntity> dataentriesById;

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Allocation_datetime", nullable = false, insertable = true, updatable = true)
    public Timestamp getAllocationDatetime() {
        return allocationDatetime;
    }

    public void setAllocationDatetime(Timestamp allocationDatetime) {
        this.allocationDatetime = allocationDatetime;
    }

    @Basic
    @Column(name = "confirmation", nullable = true, insertable = true, updatable = true, length = 1)
    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    @Basic
    @Column(name = "spoke_code", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSpokeCode() {
        return spokeCode;
    }

    public void setSpokeCode(String spokeCode) {
        this.spokeCode = spokeCode;
    }

    @Basic
    @Column(name = "confirmation_allowed", nullable = true, insertable = true, updatable = true, length = 1)
    public String getConfirmationAllowed() {
        return confirmationAllowed;
    }

    public void setConfirmationAllowed(String confirmationAllowed) {
        this.confirmationAllowed = confirmationAllowed;
    }

    @Basic
    @Column(name = "confirmation_datetime", nullable = false, insertable = true, updatable = true)
    public Timestamp getConfirmationDatetime() {
        return confirmationDatetime;
    }

    public void setConfirmationDatetime(Timestamp confirmationDatetime) {
        this.confirmationDatetime = confirmationDatetime;
    }

    @Basic
    @Column(name = "confirmation_SMSID", nullable = true, insertable = true, updatable = true)
    public Long getConfirmationSmsid() {
        return confirmationSmsid;
    }

    public void setConfirmationSmsid(Long confirmationSmsid) {
        this.confirmationSmsid = confirmationSmsid;
    }

    @Basic
    @Column(name = "final_confirmation", nullable = true, insertable = true, updatable = true, length = 1)
    public String getFinalConfirmation() {
        return finalConfirmation;
    }

    public void setFinalConfirmation(String finalConfirmation) {
        this.finalConfirmation = finalConfirmation;
    }

    @Basic
    @Column(name = "ImportBy", nullable = false, insertable = true, updatable = true, length = 20)
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
    @Column(name = "Kyc_Collected", nullable = false, insertable = true, updatable = true, length = 2)
    public String getKycCollected() {
        return kycCollected;
    }

    public void setKycCollected(String kycCollected) {
        this.kycCollected = kycCollected;
    }

    @Basic
    @Column(name = "Sms_Send_datetime", nullable = false, insertable = true, updatable = true)
    public Timestamp getSmsSendDatetime() {
        return smsSendDatetime;
    }

    public void setSmsSendDatetime(Timestamp smsSendDatetime) {
        this.smsSendDatetime = smsSendDatetime;
    }

    @Basic
    @Column(name = "Visit_DateTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(Timestamp visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllocationMastEntity that = (AllocationMastEntity) o;

        if (id != that.id) return false;
        if (allocationDatetime != null ? !allocationDatetime.equals(that.allocationDatetime) : that.allocationDatetime != null)
            return false;
        if (confirmation != null ? !confirmation.equals(that.confirmation) : that.confirmation != null) return false;
        if (confirmationAllowed != null ? !confirmationAllowed.equals(that.confirmationAllowed) : that.confirmationAllowed != null)
            return false;
        if (confirmationDatetime != null ? !confirmationDatetime.equals(that.confirmationDatetime) : that.confirmationDatetime != null)
            return false;
        if (confirmationSmsid != null ? !confirmationSmsid.equals(that.confirmationSmsid) : that.confirmationSmsid != null)
            return false;
        if (finalConfirmation != null ? !finalConfirmation.equals(that.finalConfirmation) : that.finalConfirmation != null)
            return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (kycCollected != null ? !kycCollected.equals(that.kycCollected) : that.kycCollected != null) return false;
        if (smsSendDatetime != null ? !smsSendDatetime.equals(that.smsSendDatetime) : that.smsSendDatetime != null)
            return false;
        if (visitDateTime != null ? !visitDateTime.equals(that.visitDateTime) : that.visitDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (allocationDatetime != null ? allocationDatetime.hashCode() : 0);
        result = 31 * result + (confirmation != null ? confirmation.hashCode() : 0);
        result = 31 * result + (confirmationAllowed != null ? confirmationAllowed.hashCode() : 0);
        result = 31 * result + (confirmationDatetime != null ? confirmationDatetime.hashCode() : 0);
        result = 31 * result + (confirmationSmsid != null ? confirmationSmsid.hashCode() : 0);
        result = 31 * result + (finalConfirmation != null ? finalConfirmation.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (kycCollected != null ? kycCollected.hashCode() : 0);
        result = 31 * result + (smsSendDatetime != null ? smsSendDatetime.hashCode() : 0);
        result = 31 * result + (visitDateTime != null ? visitDateTime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "CustomerPhone", nullable = false, insertable = false, updatable = false, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "Agent_Code", nullable = false, insertable =false, updatable = false, length = 10)
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    @Basic
    @Column(name = "Remarks_Code", nullable = false, insertable = false, updatable = false, length = 6)
    public String getRemarksCode() {
        return remarksCode;
    }

    public void setRemarksCode(String remarksCode) {
        this.remarksCode = remarksCode;
    }

    @Basic
    @Column(name = "Appointment_id", nullable = true, insertable = false, updatable = false)
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @ManyToOne
    @JoinColumn(name = "Agent_Code", referencedColumnName = "Acode", nullable = false)
    public PaytmagententryEntity getPaytmagententryByAgentCode() {
        return paytmagententryByAgentCode;
    }

    public void setPaytmagententryByAgentCode(PaytmagententryEntity paytmagententryByAgentCode) {
        this.paytmagententryByAgentCode = paytmagententryByAgentCode;
    }

    @ManyToOne
    @JoinColumn(name = "CustomerPhone", referencedColumnName = "PCD_CustomerPhone", nullable = false)
    public PaytmcustomerDataEntity getPaytmcustomerDataByCustomerPhone() {
        return paytmcustomerDataByCustomerPhone;
    }

    public void setPaytmcustomerDataByCustomerPhone(PaytmcustomerDataEntity paytmcustomerDataByCustomerPhone) {
        this.paytmcustomerDataByCustomerPhone = paytmcustomerDataByCustomerPhone;
    }



    @ManyToOne
    @JoinColumn(name = "Remarks_Code", referencedColumnName = "Remarks_Code", nullable = false)
    public RemarkMastEntity getRemarkMastByRemarksCode() {
        return remarkMastByRemarksCode;
    }

    public void setRemarkMastByRemarksCode(RemarkMastEntity remarkMastByRemarksCode) {
        this.remarkMastByRemarksCode = remarkMastByRemarksCode;
    }

    @ManyToOne
    @JoinColumn(name = "Appointment_id", referencedColumnName = "Appointment_id")
    public AppointmentMastEntity getAppointmentMastByAppointmentId() {
        return appointmentMastByAppointmentId;
    }

    public void setAppointmentMastByAppointmentId(AppointmentMastEntity appointmentMastByAppointmentId) {
        this.appointmentMastByAppointmentId = appointmentMastByAppointmentId;
    }
    @OneToMany(mappedBy = "allocationMastByAllocationId",cascade =CascadeType.ALL )
    public Collection<DataentryEntity> getDataentriesById() {
        return dataentriesById;
    }

    public void setDataentriesById(Collection<DataentryEntity> dataentriesById) {
        this.dataentriesById = dataentriesById;
    }
}
