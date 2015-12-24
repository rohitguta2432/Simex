package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "sms_log")
@IdClass(SmsLogEntityPK.class)
public class SmsLogEntity {
    private Timestamp psdSmsDateTime;
    private String psdCustomerPhone;
    private int psdId;
    private Timestamp psdImportDateTime;
    private String psdSmsText;

    @Id
    @Column(name = "PSD_SMSDateTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getPsdSmsDateTime() {
        return psdSmsDateTime;
    }

    public void setPsdSmsDateTime(Timestamp psdSmsDateTime) {
        this.psdSmsDateTime = psdSmsDateTime;
    }

    @Id
    @Column(name = "PSD_CustomerPhone", nullable = false, insertable = true, updatable = true, length = 15)
    public String getPsdCustomerPhone() {
        return psdCustomerPhone;
    }

    public void setPsdCustomerPhone(String psdCustomerPhone) {
        this.psdCustomerPhone = psdCustomerPhone;
    }

    @Basic
    @Column(name = "PSD_Id", nullable = false, insertable = true, updatable = true)
    public int getPsdId() {
        return psdId;
    }

    public void setPsdId(int psdId) {
        this.psdId = psdId;
    }

    @Basic
    @Column(name = "PSD_ImportDateTime", nullable = true, insertable = true, updatable = true)
    public Timestamp getPsdImportDateTime() {
        return psdImportDateTime;
    }

    public void setPsdImportDateTime(Timestamp psdImportDateTime) {
        this.psdImportDateTime = psdImportDateTime;
    }

    @Basic
    @Column(name = "PSD_SMSText", nullable = false, insertable = true, updatable = true, length = 250)
    public String getPsdSmsText() {
        return psdSmsText;
    }

    public void setPsdSmsText(String psdSmsText) {
        this.psdSmsText = psdSmsText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsLogEntity that = (SmsLogEntity) o;

        if (psdId != that.psdId) return false;
        if (psdSmsDateTime != null ? !psdSmsDateTime.equals(that.psdSmsDateTime) : that.psdSmsDateTime != null)
            return false;
        if (psdCustomerPhone != null ? !psdCustomerPhone.equals(that.psdCustomerPhone) : that.psdCustomerPhone != null)
            return false;
        if (psdImportDateTime != null ? !psdImportDateTime.equals(that.psdImportDateTime) : that.psdImportDateTime != null)
            return false;
        if (psdSmsText != null ? !psdSmsText.equals(that.psdSmsText) : that.psdSmsText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = psdSmsDateTime != null ? psdSmsDateTime.hashCode() : 0;
        result = 31 * result + (psdCustomerPhone != null ? psdCustomerPhone.hashCode() : 0);
        result = 31 * result + psdId;
        result = 31 * result + (psdImportDateTime != null ? psdImportDateTime.hashCode() : 0);
        result = 31 * result + (psdSmsText != null ? psdSmsText.hashCode() : 0);
        return result;
    }
}
