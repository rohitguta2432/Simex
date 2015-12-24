package com.softage.paytm.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "reason_mast")
public class ReasonMastEntity implements Serializable {
    private String reasonCode;
    private int id;
    private String importBy;
    private Timestamp importDate;
    private String reasonStatus;
    private String reasonText;

    @Basic
    @Id
    @Column(name = "Reason_code", nullable = false, insertable = true, updatable = true, length = 10)
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Id
    @Basic
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Import_by", nullable = false, insertable = true, updatable = true, length = 10)
    public String getImportBy() {
        return importBy;
    }

    public void setImportBy(String importBy) {
        this.importBy = importBy;
    }

    @Basic
    @Column(name = "Import_Date", nullable = true, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "Reason_Status", nullable = true, insertable = true, updatable = true, length = 1)
    public String getReasonStatus() {
        return reasonStatus;
    }

    public void setReasonStatus(String reasonStatus) {
        this.reasonStatus = reasonStatus;
    }

    @Basic
    @Column(name = "Reason_Text", nullable = true, insertable = true, updatable = true, length = 100)
    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReasonMastEntity that = (ReasonMastEntity) o;

        if (id != that.id) return false;
        if (reasonCode != null ? !reasonCode.equals(that.reasonCode) : that.reasonCode != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (reasonStatus != null ? !reasonStatus.equals(that.reasonStatus) : that.reasonStatus != null) return false;
        if (reasonText != null ? !reasonText.equals(that.reasonText) : that.reasonText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reasonCode != null ? reasonCode.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (reasonStatus != null ? reasonStatus.hashCode() : 0);
        result = 31 * result + (reasonText != null ? reasonText.hashCode() : 0);
        return result;
    }
}
