package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "remark_mast")
public class RemarkMastEntity {
    private String remarksCode;
    private int id;
    private String importBy;
    private Timestamp importDate;
    private String remarksText;
    private Collection<AllocationMastEntity> allocationMastsByRemarksCode;

    @Id
    @Column(name = "Remarks_Code", nullable = false, insertable = true, updatable = true, length = 6)
    public String getRemarksCode() {
        return remarksCode;
    }

    public void setRemarksCode(String remarksCode) {
        this.remarksCode = remarksCode;
    }

    @Basic
    @Column(name = "Id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ImportBy", nullable = false, insertable = true, updatable = true, length = 30)
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
    @Column(name = "Remarks_Text", nullable = false, insertable = true, updatable = true, length = 100)
    public String getRemarksText() {
        return remarksText;
    }

    public void setRemarksText(String remarksText) {
        this.remarksText = remarksText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemarkMastEntity that = (RemarkMastEntity) o;

        if (id != that.id) return false;
        if (remarksCode != null ? !remarksCode.equals(that.remarksCode) : that.remarksCode != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (remarksText != null ? !remarksText.equals(that.remarksText) : that.remarksText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = remarksCode != null ? remarksCode.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (remarksText != null ? remarksText.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "remarkMastByRemarksCode")
    public Collection<AllocationMastEntity> getAllocationMastsByRemarksCode() {
        return allocationMastsByRemarksCode;
    }

    public void setAllocationMastsByRemarksCode(Collection<AllocationMastEntity> allocationMastsByRemarksCode) {
        this.allocationMastsByRemarksCode = allocationMastsByRemarksCode;
    }

}
