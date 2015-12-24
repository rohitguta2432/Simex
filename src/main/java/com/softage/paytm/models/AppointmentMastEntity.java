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
@Table(name = "appointment_mast")
public class AppointmentMastEntity {
    private long appointmentId;
    private String appointmentDate;
    private String appointmentTime;
    private Timestamp importDate;
    private String customerPhone;
    private Collection<AllocationMastEntity> allocationMastsByAppointmentId;
    private PaytmcustomerDataEntity paytmcustomerDataByCustomerPhone;

  /* // public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }*/

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime.toString();
    }

    @Id
    @Column(name = "Appointment_id", nullable = false, insertable = true, updatable = true)
    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Basic
    @Column(name = "Appointment_Date", nullable = false, insertable = true, updatable = true, length = 10)
    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Basic
    @Column(name = "Appointment_Time", nullable = false, insertable = true, updatable = true, length = 16)
    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Basic
    @Column(name = "Import_Date", nullable = false, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppointmentMastEntity that = (AppointmentMastEntity) o;

        if (appointmentId != that.appointmentId) return false;
        if (appointmentDate != null ? !appointmentDate.equals(that.appointmentDate) : that.appointmentDate != null)
            return false;
        if (appointmentTime != null ? !appointmentTime.equals(that.appointmentTime) : that.appointmentTime != null)
            return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (appointmentId ^ (appointmentId >>> 32));
        result = 31 * result + (appointmentDate != null ? appointmentDate.hashCode() : 0);
        result = 31 * result + (appointmentTime != null ? appointmentTime.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "CustomerPhone", nullable = true, insertable = false, updatable = false, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @OneToMany(mappedBy = "appointmentMastByAppointmentId")
    public Collection<AllocationMastEntity> getAllocationMastsByAppointmentId() {
        return allocationMastsByAppointmentId;
    }

    public void setAllocationMastsByAppointmentId(Collection<AllocationMastEntity> allocationMastsByAppointmentId) {
        this.allocationMastsByAppointmentId = allocationMastsByAppointmentId;
    }
    @ManyToOne
    @JoinColumn(name = "CustomerPhone", referencedColumnName = "PCD_CustomerPhone")
    public PaytmcustomerDataEntity getPaytmcustomerDataByCustomerPhone() {
        return paytmcustomerDataByCustomerPhone;
    }

    public void setPaytmcustomerDataByCustomerPhone(PaytmcustomerDataEntity paytmcustomerDataByCustomerPhone) {
        this.paytmcustomerDataByCustomerPhone = paytmcustomerDataByCustomerPhone;
    }
}
