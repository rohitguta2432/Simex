package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "circle_mast")
@Cacheable
public class CircleMastEntity {
    private int cirCode;
    private String chMobileNumber;
    private String circleName;
    private String dailerApi;
    private String dailerIp;
    private String importBy;
    private Timestamp importDate;
    private Collection<EmplogintableEntity> emplogintablesByCirCode;
    private Collection<PaytmMastEntity> paytmMastsByCirCode;

    @Id
    @Column(name = "Cir_code", nullable = false, insertable = false, updatable = false)
    public int getCirCode() {
        return cirCode;
    }

    public void setCirCode(int cirCode) {
        this.cirCode = cirCode;
    }

    @Basic
    @Column(name = "CHMobileNumber", nullable = true, insertable = true, updatable = true, length = 10)
    public String getChMobileNumber() {
        return chMobileNumber;
    }

    public void setChMobileNumber(String chMobileNumber) {
        this.chMobileNumber = chMobileNumber;
    }

    @Basic
    @Column(name = "Circle_Name", nullable = false, insertable = true, updatable = true, length = 20)
    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    @Basic
    @Column(name = "Dailer_API", nullable = true, insertable = true, updatable = true, length = 200)
    public String getDailerApi() {
        return dailerApi;
    }

    public void setDailerApi(String dailerApi) {
        this.dailerApi = dailerApi;
    }

    @Basic
    @Column(name = "Dailer_IP", nullable = true, insertable = true, updatable = true, length = 50)
    public String getDailerIp() {
        return dailerIp;
    }

    public void setDailerIp(String dailerIp) {
        this.dailerIp = dailerIp;
    }

    @Basic
    @Column(name = "Import_by", nullable = false, insertable = true, updatable = true, length = 20)
    public String getImportBy() {
        return importBy;
    }

    public void setImportBy(String importBy) {
        this.importBy = importBy;
    }

    @Basic
    @Column(name = "Import_Date", nullable = false, insertable = true, updatable = true)
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

        CircleMastEntity that = (CircleMastEntity) o;

        if (cirCode != that.cirCode) return false;
        if (chMobileNumber != null ? !chMobileNumber.equals(that.chMobileNumber) : that.chMobileNumber != null)
            return false;
        if (circleName != null ? !circleName.equals(that.circleName) : that.circleName != null) return false;
        if (dailerApi != null ? !dailerApi.equals(that.dailerApi) : that.dailerApi != null) return false;
        if (dailerIp != null ? !dailerIp.equals(that.dailerIp) : that.dailerIp != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cirCode;
        result = 31 * result + (chMobileNumber != null ? chMobileNumber.hashCode() : 0);
        result = 31 * result + (circleName != null ? circleName.hashCode() : 0);
        result = 31 * result + (dailerApi != null ? dailerApi.hashCode() : 0);
        result = 31 * result + (dailerIp != null ? dailerIp.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "circleMastByCirCode")
    public Collection<EmplogintableEntity> getEmplogintablesByCirCode() {
        return emplogintablesByCirCode;
    }

    public void setEmplogintablesByCirCode(Collection<EmplogintableEntity> emplogintablesByCirCode) {
        this.emplogintablesByCirCode = emplogintablesByCirCode;
    }



    @OneToMany(mappedBy = "circleMastByCirCode")
    public Collection<PaytmMastEntity> getPaytmMastsByCirCode() {
        return paytmMastsByCirCode;
    }

    public void setPaytmMastsByCirCode(Collection<PaytmMastEntity> paytmMastsByCirCode) {
        this.paytmMastsByCirCode = paytmMastsByCirCode;
    }
}
