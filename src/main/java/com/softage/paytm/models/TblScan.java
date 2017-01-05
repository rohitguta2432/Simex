package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by SS0090 on 1/4/2017.
 */
@Entity
@Table(name="tbl_scan")
public class TblScan {

    private int scanid;
    private String customerNumber;
    private Date dataDate;
    private String simNo;
    private String imagePath;
    private String spokeCode;
    private int pageNo;
    private Date createdOn;
    private String createdBy;
    private int auditStatus;
    private CircleAuditEntity circleAuditEntity;
    private AoAuditEntity aoAuditEntity;
    private Collection<UploadedImagesEntity> uploadedImagesEntities;
    private PaytmcustomerDataEntity paytmcustomerDataEntity;
    private SpokeMastEntity spokeMastEntity;
    private String spoke_code;
    private Integer circle_code;
    private CircleMastEntity circleMastEntity;


    @Id
    @GeneratedValue
    @Column(name = "scan_id",nullable = false,insertable = true,updatable = true)
    public int getScanid() {
        return scanid;
    }

    public void setScanid(int scanid) {
        this.scanid = scanid;
    }

    @Basic
    @Column(name = "customer_number",nullable = false,insertable = true,updatable = true)
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Basic
    @Column(name = "data_date",nullable = false,insertable = true,updatable = true)
    public Date getDataDate() {
        return dataDate;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }
    @Basic
    @Column(name = "sim_no",nullable = false,insertable = true,updatable = true)
    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }
    @Basic
    @Column(name = "image_path",nullable = false,insertable = true,updatable = true)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic
    @Column(name = "page_no",nullable = false,insertable = true,updatable = true)
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Basic
    @Column(name = "created_on",nullable = false,insertable = true,updatable = true)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "created_by",nullable = false,insertable = true,updatable = true)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "audit_status",nullable = false,insertable = true,updatable = false)
    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    @OneToMany(mappedBy = "tblScan")
    public Collection<UploadedImagesEntity> getUploadedImagesEntities() {
        return uploadedImagesEntities;
    }

    public void setUploadedImagesEntities(Collection<UploadedImagesEntity> paytmMastsByCirCode) {
        this.uploadedImagesEntities = paytmMastsByCirCode;
    }
    @OneToOne(mappedBy = "tblScan")
    public CircleAuditEntity getCircleAuditEntity() {
        return circleAuditEntity;
    }

    public void setCircleAuditEntity(CircleAuditEntity circleAuditEntity) {
        this.circleAuditEntity = circleAuditEntity;
    }

    @OneToOne(mappedBy = "tblScan")
    public AoAuditEntity getAoAuditEntity() {
        return aoAuditEntity;
    }

    public void setAoAuditEntity(AoAuditEntity aoAuditEntity) {
        this.aoAuditEntity = aoAuditEntity;
    }

    @OneToOne
    @JoinColumn(name = "cust_uid")
    public PaytmcustomerDataEntity getPaytmcustomerDataEntity() {
        return paytmcustomerDataEntity;
    }

    public void setPaytmcustomerDataEntity(PaytmcustomerDataEntity paytmcustomerDataEntity) {
        this.paytmcustomerDataEntity = paytmcustomerDataEntity;
    }

    @Basic
    @Column(name = "spoke_code",nullable = false,insertable = false,updatable = false)
    public String getSpoke_code() {
        return spoke_code;
    }

    public void setSpoke_code(String spoke_code) {
        this.spoke_code = spoke_code;
    }

    @ManyToOne
    @JoinColumn(name = "spoke_code",referencedColumnName = "Spoke_code")
    public SpokeMastEntity getSpokeMastEntity() {
        return spokeMastEntity;
    }

    public void setSpokeMastEntity(SpokeMastEntity spokeMastEntity) {
        this.spokeMastEntity = spokeMastEntity;
    }

    @Basic
    @Column(name = "circle_code",nullable = false,insertable = false,updatable = false)
    public Integer getCircle_code() {
        return circle_code;
    }

    public void setCircle_code(Integer circle_code) {
        this.circle_code = circle_code;
    }

    @ManyToOne
    @JoinColumn(name = "circle_code",referencedColumnName = "Cir_code")
    public CircleMastEntity getCircleMastEntity() {
        return circleMastEntity;
    }

    public void setCircleMastEntity(CircleMastEntity circleMastEntity) {
        this.circleMastEntity = circleMastEntity;
    }
}
