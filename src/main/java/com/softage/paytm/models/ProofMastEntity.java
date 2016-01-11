package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "proof_mast")
@Cacheable
public class ProofMastEntity {
    private String idCode;
    private String applicable;
    private String idStatus;
    private String idText;
    private String importBy;
    private Timestamp importDate;
    private Collection<DataentryEntity> dataentriesByIdCode;

    @Id
    @Column(name = "Id_Code", nullable = false, insertable = true, updatable = true, length = 10)
    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    @Basic
    @Column(name = "Applicable", nullable = false, insertable = true, updatable = true, length = 2)
    public String getApplicable() {
        return applicable;
    }

    public void setApplicable(String applicable) {
        this.applicable = applicable;
    }

    @Basic
    @Column(name = "Id_Status", nullable = false, insertable = true, updatable = true, length = 1)
    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }

    @Basic
    @Column(name = "Id_Text", nullable = true, insertable = true, updatable = true, length = 100)
    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProofMastEntity that = (ProofMastEntity) o;

        if (idCode != null ? !idCode.equals(that.idCode) : that.idCode != null) return false;
        if (applicable != null ? !applicable.equals(that.applicable) : that.applicable != null) return false;
        if (idStatus != null ? !idStatus.equals(that.idStatus) : that.idStatus != null) return false;
        if (idText != null ? !idText.equals(that.idText) : that.idText != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCode != null ? idCode.hashCode() : 0;
        result = 31 * result + (applicable != null ? applicable.hashCode() : 0);
        result = 31 * result + (idStatus != null ? idStatus.hashCode() : 0);
        result = 31 * result + (idText != null ? idText.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "proofMastByCusPoiCode")
    public Collection<DataentryEntity> getDataentriesByIdCode() {
        return dataentriesByIdCode;
    }

    public void setDataentriesByIdCode(Collection<DataentryEntity> dataentriesByIdCode) {
        this.dataentriesByIdCode = dataentriesByIdCode;
    }
}
