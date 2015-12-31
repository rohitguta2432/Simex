package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "agentpinmaster")
public class AgentpinmasterEntity {
    private long apmId;
    private String apmAPincode;
    private Timestamp apmImportDate;
    private String apmAcode;
    private PaytmagententryEntity paytmagententryByApmAcode;
    public void setApmId(int apmId) {
        this.apmId = apmId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "APM_ID", nullable = false, insertable = true, updatable = true)
    public long getApmId() {
        return apmId;
    }

    public void setApmId(long apmId) {
        this.apmId = apmId;
    }

    @Basic
    @Column(name = "APM_APincode", nullable = false, insertable = true, updatable = true, length = 10)
    public String getApmAPincode() {
        return apmAPincode;
    }

    public void setApmAPincode(String apmAPincode) {
        this.apmAPincode = apmAPincode;
    }

    @Basic
    @Column(name = "APM_ImportDate", nullable = false, insertable = true, updatable = true)
    public Timestamp getApmImportDate() {
        return apmImportDate;
    }

    public void setApmImportDate(Timestamp apmImportDate) {
        this.apmImportDate = apmImportDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentpinmasterEntity that = (AgentpinmasterEntity) o;

        if (apmId != that.apmId) return false;
        if (apmAPincode != null ? !apmAPincode.equals(that.apmAPincode) : that.apmAPincode != null) return false;
        if (apmImportDate != null ? !apmImportDate.equals(that.apmImportDate) : that.apmImportDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (apmId ^ (apmId >>> 32));
        result = 31 * result + (apmAPincode != null ? apmAPincode.hashCode() : 0);
        result = 31 * result + (apmImportDate != null ? apmImportDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "APM_Acode", nullable = false, insertable = false, updatable = false, length = 10)
    public String getApmAcode() {
        return apmAcode;
    }

    public void setApmAcode(String apmAcode) {
        this.apmAcode = apmAcode;
    }

    @ManyToOne
    @JoinColumn(name = "APM_Acode", referencedColumnName = "Acode", nullable = false)
    public PaytmagententryEntity getPaytmagententryByApmAcode() {
        return paytmagententryByApmAcode;
    }

    public void setPaytmagententryByApmAcode(PaytmagententryEntity paytmagententryByApmAcode) {
        this.paytmagententryByApmAcode = paytmagententryByApmAcode;
    }


}
