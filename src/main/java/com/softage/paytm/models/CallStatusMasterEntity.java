package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "call_status_master")
@Cacheable
public class CallStatusMasterEntity {
    private String csmCode;
    private String statusText;

    @Id
    @Column(name = "Csm_code", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCsmCode() {
        return csmCode;
    }

    public void setCsmCode(String csmCode) {
        this.csmCode = csmCode;
    }

    @Basic
    @Column(name = "Status_Text", nullable = false, insertable = true, updatable = true, length = 50)
    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallStatusMasterEntity that = (CallStatusMasterEntity) o;

        if (csmCode != null ? !csmCode.equals(that.csmCode) : that.csmCode != null) return false;
        if (statusText != null ? !statusText.equals(that.statusText) : that.statusText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = csmCode != null ? csmCode.hashCode() : 0;
        result = 31 * result + (statusText != null ? statusText.hashCode() : 0);
        return result;
    }
}
