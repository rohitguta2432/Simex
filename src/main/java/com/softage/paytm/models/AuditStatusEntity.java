package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by SS0090 on 1/11/2017.
 */
@Entity
@Table(name = "auditStatus_mast")
public class AuditStatusEntity {

    private Integer statusid;
    private String status;
    private Collection<TblScan> tblScanCollection;


    @Id
    @GeneratedValue
    @Column(name = "status_id",nullable = false,length = 10,insertable = true,updatable = true)
    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }



    @Basic
    @Column(name = "status",length = 100,nullable = false,insertable = true,updatable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "auditStatusEntity")
    public Collection<TblScan> getTblScanCollection() {
        return tblScanCollection;
    }

    public void setTblScanCollection(Collection<TblScan> tblScanCollection) {
        this.tblScanCollection = tblScanCollection;
    }
}
