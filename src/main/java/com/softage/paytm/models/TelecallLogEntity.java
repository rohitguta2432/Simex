package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "telecall_log")
@Cacheable
public class TelecallLogEntity {
    private int tcId;
    private String tcCallBy;
    private String tcCallStatus;
    private Timestamp tcCallTime;
    private Integer cust_uid;
    private PaytmMastEntity paytmMastEntity;

    @Id
    @GeneratedValue
    @Column(name = "TC_Id", nullable = false, insertable = true, updatable = true)
    public int getTcId() {
        return tcId;
    }

    public void setTcId(int tcId) {
        this.tcId = tcId;
    }

    @Basic
    @Column(name = "TC_CallBy", nullable = false, insertable = true, updatable = true, length = 70)
    public String getTcCallBy() {
        return tcCallBy;
    }

    public void setTcCallBy(String tcCallBy) {
        this.tcCallBy = tcCallBy;
    }

    @Basic
    @Column(name = "TC_CallStatus", nullable = false, insertable = true, updatable = true, length = 30)
    public String getTcCallStatus() {
        return tcCallStatus;
    }

    public void setTcCallStatus(String tcCallStatus) {
        this.tcCallStatus = tcCallStatus;
    }

    @Basic
    @Column(name = "TC_CallTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getTcCallTime() {
        return tcCallTime;
    }

    public void setTcCallTime(Timestamp tcCallTime) {
        this.tcCallTime = tcCallTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelecallLogEntity that = (TelecallLogEntity) o;

        if (tcId != that.tcId) return false;
        if (tcCallBy != null ? !tcCallBy.equals(that.tcCallBy) : that.tcCallBy != null) return false;
        if (tcCallStatus != null ? !tcCallStatus.equals(that.tcCallStatus) : that.tcCallStatus != null) return false;
        if (tcCallTime != null ? !tcCallTime.equals(that.tcCallTime) : that.tcCallTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tcId;
        result = 31 * result + (tcCallBy != null ? tcCallBy.hashCode() : 0);
        result = 31 * result + (tcCallStatus != null ? tcCallStatus.hashCode() : 0);
        result = 31 * result + (tcCallTime != null ? tcCallTime.hashCode() : 0);
        return result;
    }

    private String tcCustomerphone;

    @Basic
    @Column(name = "TC_Customerphone", nullable = true, insertable = false, updatable = false, length = 10)
    public String getTcCustomerphone() {
        return tcCustomerphone;
    }

    public void setTcCustomerphone(String tcCustomerphone) {
        this.tcCustomerphone = tcCustomerphone;
    }

/*    private TelecallMastEntity telecallMastByTcCustomerphone;

    @ManyToOne
    @JoinColumn(name = "TC_Customerphone", referencedColumnName = "TM_CustomerPhone")
    public TelecallMastEntity getTelecallMastByTcCustomerphone() {
        return telecallMastByTcCustomerphone;
    }

    public void setTelecallMastByTcCustomerphone(TelecallMastEntity telecallMastByTcCustomerphone) {
        this.telecallMastByTcCustomerphone = telecallMastByTcCustomerphone;
    }*/

    @Basic
    @Column(name = "cust_uid",nullable = false,insertable = false,updatable = false)
    public Integer getCust_uid() {
        return cust_uid;
    }

    public void setCust_uid(Integer cust_uid) {
        this.cust_uid = cust_uid;
    }

    @ManyToOne
    @JoinColumn(name = "cust_uid",referencedColumnName = "cust_uid")
    public PaytmMastEntity getPaytmMastEntity() {
        return paytmMastEntity;
    }

    public void setPaytmMastEntity(PaytmMastEntity paytmMastEntity) {
        this.paytmMastEntity = paytmMastEntity;
    }
}
