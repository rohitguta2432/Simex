package com.softage.paytm.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */

public class SmsLogEntityPK implements Serializable {
    private Timestamp psdSmsDateTime;
    private String psdCustomerPhone;

    @Column(name = "PSD_SMSDateTime", nullable = false, insertable = true, updatable = true)
    @Id
    public Timestamp getPsdSmsDateTime() {
        return psdSmsDateTime;
    }

    public void setPsdSmsDateTime(Timestamp psdSmsDateTime) {
        this.psdSmsDateTime = psdSmsDateTime;
    }

    @Column(name = "PSD_CustomerPhone", nullable = false, insertable = true, updatable = true, length = 15)
    @Id
    public String getPsdCustomerPhone() {
        return psdCustomerPhone;
    }

    public void setPsdCustomerPhone(String psdCustomerPhone) {
        this.psdCustomerPhone = psdCustomerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsLogEntityPK that = (SmsLogEntityPK) o;

        if (psdSmsDateTime != null ? !psdSmsDateTime.equals(that.psdSmsDateTime) : that.psdSmsDateTime != null)
            return false;
        if (psdCustomerPhone != null ? !psdCustomerPhone.equals(that.psdCustomerPhone) : that.psdCustomerPhone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = psdSmsDateTime != null ? psdSmsDateTime.hashCode() : 0;
        result = 31 * result + (psdCustomerPhone != null ? psdCustomerPhone.hashCode() : 0);
        return result;
    }
}
