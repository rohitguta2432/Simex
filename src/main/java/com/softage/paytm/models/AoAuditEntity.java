package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0090 on 1/4/2017.
 */
@Entity
@Table(name = "tbl_ao_audit")
public class AoAuditEntity {

    private int aoid;
    private String nameMatched;
    private String photoMatched;
    private String signMatched;
    private String dobMatched;
    private String otherReason;
    private Integer auditStatus;
    private TblScan tblScan;
    private Timestamp aoauditdatetime;

    @Id
    @GeneratedValue
    @Column(name = "aoid",nullable = false,insertable = true,updatable = true)
    public int getAoid() {
        return aoid;
    }

    public void setAoid(int aoid) {
        this.aoid = aoid;
    }

    @Basic
    @Column(name = "name_matched",nullable = false,insertable = true,updatable = true)
    public String getNameMatched() {
        return nameMatched;
    }

    public void setNameMatched(String nameMatched) {
        this.nameMatched = nameMatched;
    }

    @Basic
    @Column(name = "photo_matched",nullable = false,insertable = true,updatable = true)
    public String getPhotoMatched() {
        return photoMatched;
    }

    public void setPhotoMatched(String photoMatched) {
        this.photoMatched = photoMatched;
    }

    @Basic
    @Column(name = "sign_matched",nullable = false,insertable = true,updatable = true)
    public String getSignMatched() {
        return signMatched;
    }

    public void setSignMatched(String signMatched) {
        this.signMatched = signMatched;
    }

    @Basic
    @Column(name = "dob_matched",nullable = false,insertable = true,updatable = true)
    public String getDobMatched() {
        return dobMatched;
    }

    public void setDobMatched(String dobMatched) {
        this.dobMatched = dobMatched;
    }

    @Basic
    @Column(name = "other_reason",nullable = false,insertable = true,updatable = true)
    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    @OneToOne
    @JoinColumn(name = "scan_id")
    public TblScan getTblScan() {
        return tblScan;
    }

    public void setTblScan(TblScan tblScan) {
        this.tblScan = tblScan;
    }

    @Basic
    @Column(name = "aoAudit_status",nullable = true,insertable = true,updatable = true)
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Basic
    @Column(name = "aoauditdatetime",nullable = true,insertable = true,updatable = true)
    public Timestamp getAoauditdatetime() {
        return aoauditdatetime;
    }

    public void setAoauditdatetime(Timestamp aoauditdatetime) {
        this.aoauditdatetime = aoauditdatetime;
    }
}
