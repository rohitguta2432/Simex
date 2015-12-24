package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "paytmdeviceidinfo")
public class PaytmdeviceidinfoEntity {
    private long id;
    private String deviceId;
    private String importBy;
    private Timestamp importDate;
    private String loginId;
    private TblNotificationLogEntity tblNotificationLogByLoginId;

    @Id
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DeviceId", nullable = false, insertable = true, updatable = true, length = 200)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
    @Column(name = "LoginId", nullable = true, insertable = false, updatable = false,length = 30)
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaytmdeviceidinfoEntity that = (PaytmdeviceidinfoEntity) o;

        if (id != that.id) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (loginId != null ? !loginId.equals(that.loginId) : that.loginId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (loginId != null ? loginId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "LoginId", referencedColumnName = "notification_loginid")
    public TblNotificationLogEntity getTblNotificationLogByLoginId() {
        return tblNotificationLogByLoginId;
    }

    public void setTblNotificationLogByLoginId(TblNotificationLogEntity tblNotificationLogByLoginId) {
        this.tblNotificationLogByLoginId = tblNotificationLogByLoginId;
    }
}
