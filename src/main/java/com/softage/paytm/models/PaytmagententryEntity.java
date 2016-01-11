package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "paytmagententry")
@Cacheable
public class PaytmagententryEntity {
    private String acode;
    private int aId;
    private String aStatus;
    private String aalternateNo;
    private String aavailslot;
    private String aemailId;
    private String afullname;
    private String aphone;
    private String apincode;
    private String aspokecode;
    private String empcode;
    private String importby;
    private Timestamp importdate;
    private String leftmarkBy;
    private Timestamp leftmarkDate;
    private String mulitplePin;
    private Collection<AgentpinmasterEntity> agentpinmastersByAcode;
    private Collection<AllocationMastEntity> allocationMastsByAcode;


    @Id
    @Column(name = "Acode", nullable = false, insertable = true, updatable = true, length = 10)
    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AId", nullable = false, insertable = true, updatable = true)
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Basic
    @Column(name = "AStatus", nullable = true, insertable = true, updatable = true, length = 1)
    public String getaStatus() {
        return aStatus;
    }

    public void setaStatus(String aStatus) {
        this.aStatus = aStatus;
    }

    @Basic
    @Column(name = "Aalternate_no", nullable = true, insertable = true, updatable = true, length = 10)
    public String getAalternateNo() {
        return aalternateNo;
    }

    public void setAalternateNo(String aalternateNo) {
        this.aalternateNo = aalternateNo;
    }

    @Basic
    @Column(name = "Aavailslot", nullable = false, insertable = true, updatable = true, length = 20)
    public String getAavailslot() {
        return aavailslot;
    }

    public void setAavailslot(String aavailslot) {
        this.aavailslot = aavailslot;
    }

    @Basic
    @Column(name = "AemailID", nullable = true, insertable = true, updatable = true, length = 30)
    public String getAemailId() {
        return aemailId;
    }

    public void setAemailId(String aemailId) {
        this.aemailId = aemailId;
    }

    @Basic
    @Column(name = "Afullname", nullable = false, insertable = true, updatable = true, length = 50)
    public String getAfullname() {
        return afullname;
    }

    public void setAfullname(String afullname) {
        this.afullname = afullname;
    }

    @Basic
    @Column(name = "Aphone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    @Basic
    @Column(name = "Apincode", nullable = false, insertable = true, updatable = true, length = 6)
    public String getApincode() {
        return apincode;
    }

    public void setApincode(String apincode) {
        this.apincode = apincode;
    }

    @Basic
    @Column(name = "Aspokecode", nullable = true, insertable = true, updatable = true, length = 50)
    public String getAspokecode() {
        return aspokecode;
    }

    public void setAspokecode(String aspokecode) {
        this.aspokecode = aspokecode;
    }

    @Basic
    @Column(name = "Empcode", nullable = false, insertable = true, updatable = true, length = 10)
    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    @Basic
    @Column(name = "importby", nullable = false, insertable = true, updatable = true, length = 20)
    public String getImportby() {
        return importby;
    }

    public void setImportby(String importby) {
        this.importby = importby;
    }

    @Basic
    @Column(name = "importdate", nullable = false, insertable = true, updatable = true)
    public Timestamp getImportdate() {
        return importdate;
    }

    public void setImportdate(Timestamp importdate) {
        this.importdate = importdate;
    }

    @Basic
    @Column(name = "LeftmarkBy", nullable = true, insertable = true, updatable = true, length = 10)
    public String getLeftmarkBy() {
        return leftmarkBy;
    }

    public void setLeftmarkBy(String leftmarkBy) {
        this.leftmarkBy = leftmarkBy;
    }

    @Basic
    @Column(name = "LeftmarkDate", nullable = true, insertable = true, updatable = true)
    public Timestamp getLeftmarkDate() {
        return leftmarkDate;
    }

    public void setLeftmarkDate(Timestamp leftmarkDate) {
        this.leftmarkDate = leftmarkDate;
    }

    @Basic
    @Column(name = "MulitplePin", nullable = true, insertable = true, updatable = true, length = 1)
    public String getMulitplePin() {
        return mulitplePin;
    }

    public void setMulitplePin(String mulitplePin) {
        this.mulitplePin = mulitplePin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaytmagententryEntity that = (PaytmagententryEntity) o;

        if (aId != that.aId) return false;
        if (acode != null ? !acode.equals(that.acode) : that.acode != null) return false;
        if (aStatus != null ? !aStatus.equals(that.aStatus) : that.aStatus != null) return false;
        if (aalternateNo != null ? !aalternateNo.equals(that.aalternateNo) : that.aalternateNo != null) return false;
        if (aavailslot != null ? !aavailslot.equals(that.aavailslot) : that.aavailslot != null) return false;
        if (aemailId != null ? !aemailId.equals(that.aemailId) : that.aemailId != null) return false;
        if (afullname != null ? !afullname.equals(that.afullname) : that.afullname != null) return false;
        if (aphone != null ? !aphone.equals(that.aphone) : that.aphone != null) return false;
        if (apincode != null ? !apincode.equals(that.apincode) : that.apincode != null) return false;
        if (aspokecode != null ? !aspokecode.equals(that.aspokecode) : that.aspokecode != null) return false;
        if (empcode != null ? !empcode.equals(that.empcode) : that.empcode != null) return false;
        if (importby != null ? !importby.equals(that.importby) : that.importby != null) return false;
        if (importdate != null ? !importdate.equals(that.importdate) : that.importdate != null) return false;
        if (leftmarkBy != null ? !leftmarkBy.equals(that.leftmarkBy) : that.leftmarkBy != null) return false;
        if (leftmarkDate != null ? !leftmarkDate.equals(that.leftmarkDate) : that.leftmarkDate != null) return false;
        if (mulitplePin != null ? !mulitplePin.equals(that.mulitplePin) : that.mulitplePin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acode != null ? acode.hashCode() : 0;
        result = 31 * result + aId;
        result = 31 * result + (aStatus != null ? aStatus.hashCode() : 0);
        result = 31 * result + (aalternateNo != null ? aalternateNo.hashCode() : 0);
        result = 31 * result + (aavailslot != null ? aavailslot.hashCode() : 0);
        result = 31 * result + (aemailId != null ? aemailId.hashCode() : 0);
        result = 31 * result + (afullname != null ? afullname.hashCode() : 0);
        result = 31 * result + (aphone != null ? aphone.hashCode() : 0);
        result = 31 * result + (apincode != null ? apincode.hashCode() : 0);
        result = 31 * result + (aspokecode != null ? aspokecode.hashCode() : 0);
        result = 31 * result + (empcode != null ? empcode.hashCode() : 0);
        result = 31 * result + (importby != null ? importby.hashCode() : 0);
        result = 31 * result + (importdate != null ? importdate.hashCode() : 0);
        result = 31 * result + (leftmarkBy != null ? leftmarkBy.hashCode() : 0);
        result = 31 * result + (leftmarkDate != null ? leftmarkDate.hashCode() : 0);
        result = 31 * result + (mulitplePin != null ? mulitplePin.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "paytmagententryByApmAcode")
    public Collection<AgentpinmasterEntity> getAgentpinmastersByAcode() {
        return agentpinmastersByAcode;
    }

    public void setAgentpinmastersByAcode(Collection<AgentpinmasterEntity> agentpinmastersByAcode) {
        this.agentpinmastersByAcode = agentpinmastersByAcode;
    }


    @OneToMany(mappedBy = "paytmagententryByAgentCode")
    public Collection<AllocationMastEntity> getAllocationMastsByAcode() {
        return allocationMastsByAcode;
    }

    public void setAllocationMastsByAcode(Collection<AllocationMastEntity> allocationMastsByAcode) {
        this.allocationMastsByAcode = allocationMastsByAcode;
    }

}
