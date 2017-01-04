package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "telecall_mast")
@Cacheable
public class TelecallMastEntity {
    private String tmCustomerPhone;
    private byte tmAttempts;
    private String tmLastAttemptBy;
    private Timestamp tmLastAttemptDateTime;
    private String tmLastCallStatus;
    private String tmTeleCallStatus;

    @Id
    @Column(name = "TM_CustomerPhone", nullable = false, insertable = false, updatable = false, length = 10)
    public String getTmCustomerPhone() {
        return tmCustomerPhone;
    }

    public void setTmCustomerPhone(String tmCustomerPhone) {
        this.tmCustomerPhone = tmCustomerPhone;
    }

    @Basic
    @Column(name = "tm_attempts", nullable = false, insertable = true, updatable = true)
    public byte getTmAttempts() {
        return tmAttempts;
    }

    public void setTmAttempts(byte tmAttempts) {
        this.tmAttempts = tmAttempts;
    }

    @Basic
    @Column(name = "TM_lastAttemptBy", nullable = false, insertable = true, updatable = true, length = 70)
    public String getTmLastAttemptBy() {
        return tmLastAttemptBy;
    }

    public void setTmLastAttemptBy(String tmLastAttemptBy) {
        this.tmLastAttemptBy = tmLastAttemptBy;
    }

    @Basic
    @Column(name = "TM_LastAttemptDateTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getTmLastAttemptDateTime() {
        return tmLastAttemptDateTime;
    }

    public void setTmLastAttemptDateTime(Timestamp tmLastAttemptDateTime) {
        this.tmLastAttemptDateTime = tmLastAttemptDateTime;
    }

    @Basic
    @Column(name = "TM_LastCallStatus", nullable = false, insertable = true, updatable = true, length = 30)
    public String getTmLastCallStatus() {
        return tmLastCallStatus;
    }

    public void setTmLastCallStatus(String tmLastCallStatus) {
        this.tmLastCallStatus = tmLastCallStatus;
    }

    @Basic
    @Column(name = "TM_TeleCallStatus", nullable = false, insertable = true, updatable = true, length = 10)
    public String getTmTeleCallStatus() {
        return tmTeleCallStatus;
    }

    public void setTmTeleCallStatus(String tmTeleCallStatus) {
        this.tmTeleCallStatus = tmTeleCallStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelecallMastEntity that = (TelecallMastEntity) o;

        if (tmAttempts != that.tmAttempts) return false;
        if (tmCustomerPhone != null ? !tmCustomerPhone.equals(that.tmCustomerPhone) : that.tmCustomerPhone != null)
            return false;
        if (tmLastAttemptBy != null ? !tmLastAttemptBy.equals(that.tmLastAttemptBy) : that.tmLastAttemptBy != null)
            return false;
        if (tmLastAttemptDateTime != null ? !tmLastAttemptDateTime.equals(that.tmLastAttemptDateTime) : that.tmLastAttemptDateTime != null)
            return false;
        if (tmLastCallStatus != null ? !tmLastCallStatus.equals(that.tmLastCallStatus) : that.tmLastCallStatus != null)
            return false;
        if (tmTeleCallStatus != null ? !tmTeleCallStatus.equals(that.tmTeleCallStatus) : that.tmTeleCallStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tmCustomerPhone != null ? tmCustomerPhone.hashCode() : 0;
        result = 31 * result + (int) tmAttempts;
        result = 31 * result + (tmLastAttemptBy != null ? tmLastAttemptBy.hashCode() : 0);
        result = 31 * result + (tmLastAttemptDateTime != null ? tmLastAttemptDateTime.hashCode() : 0);
        result = 31 * result + (tmLastCallStatus != null ? tmLastCallStatus.hashCode() : 0);
        result = 31 * result + (tmTeleCallStatus != null ? tmTeleCallStatus.hashCode() : 0);
        return result;
    }

    private Collection<TelecallLogEntity> telecallLogsByTmCustomerPhone;

    @OneToMany(mappedBy = "telecallMastByTcCustomerphone")
    public Collection<TelecallLogEntity> getTelecallLogsByTmCustomerPhone() {
        return telecallLogsByTmCustomerPhone;
    }

    public void setTelecallLogsByTmCustomerPhone(Collection<TelecallLogEntity> telecallLogsByTmCustomerPhone) {
        this.telecallLogsByTmCustomerPhone = telecallLogsByTmCustomerPhone;
    }

  /*  private PaytmMastEntity paytmMastByTmCustomerPhone;

    @OneToOne
    @JoinColumn(name = "TM_CustomerPhone", referencedColumnName = "CustomerPhone", nullable = false)
    public PaytmMastEntity getPaytmMastByTmCustomerPhone() {
        return paytmMastByTmCustomerPhone;
    }

    public void setPaytmMastByTmCustomerPhone(PaytmMastEntity paytmMastByTmCustomerPhone) {
        this.paytmMastByTmCustomerPhone = paytmMastByTmCustomerPhone;
    }*/
}
