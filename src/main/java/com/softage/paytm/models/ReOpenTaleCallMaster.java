package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-01-2016.
 */
@Entity
@Table(name="reopenTale_CallMaster")
public class ReOpenTaleCallMaster {

    private int id;
    private String tmCustomerPhone;
    private byte tmAttempts;
    private String tmLastAttemptBy;
    private Timestamp tmLastAttemptDateTime;
    private String tmLastCallStatus;
    private String tmTeleCallStatus;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = false, length = 40)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TM_CustomerPhone", nullable = false, insertable = true, updatable = false, length = 10)
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
    public int hashCode() {
        int result = tmCustomerPhone != null ? tmCustomerPhone.hashCode() : 0;
        result = 31 * result + (int) tmAttempts;
        result = 31 * result + (tmLastAttemptBy != null ? tmLastAttemptBy.hashCode() : 0);
        result = 31 * result + (tmLastAttemptDateTime != null ? tmLastAttemptDateTime.hashCode() : 0);
        result = 31 * result + (tmLastCallStatus != null ? tmLastCallStatus.hashCode() : 0);
        result = 31 * result + (tmTeleCallStatus != null ? tmTeleCallStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReOpenTaleCallMaster{" +
                "id=" + id +
                ", tmCustomerPhone='" + tmCustomerPhone + '\'' +
                ", tmAttempts=" + tmAttempts +
                ", tmLastAttemptBy='" + tmLastAttemptBy + '\'' +
                ", tmLastAttemptDateTime=" + tmLastAttemptDateTime +
                ", tmLastCallStatus='" + tmLastCallStatus + '\'' +
                ", tmTeleCallStatus='" + tmTeleCallStatus + '\'' +
                '}';
    }
}
