package com.softage.paytm.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Integer statusID;
    private AuditStatusEntity auditStatusEntity;
    private String assignedStatus;
    private Date assignedDatetime;
    private String assignedTo;
    private String formRecievingStatus;
    private Date lastAssignedTimeAo;
    private String assignedToAo;
    private String aoAssignedstatus;
    private Timestamp documentRecieveDatetime;

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
    @Column(name = "request_date",nullable = false,insertable = true,updatable = true)
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
    @Column(name = "audit_status",nullable = false,insertable = false,updatable = false)
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

    @ManyToOne
    @JoinColumn(name = "audit_status",referencedColumnName = "status_id")
    public AuditStatusEntity getAuditStatusEntity() {
        return auditStatusEntity;
    }

    public void setAuditStatusEntity(AuditStatusEntity auditStatusEntity) {
        this.auditStatusEntity = auditStatusEntity;
    }

    @Basic
    @Column(name = "assigned_status",insertable = true,updatable = true,nullable = true)
    public String getAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(String assignedStatus) {
        this.assignedStatus = assignedStatus;
    }

    @Basic
    @Column(name = "assigned_datetime",nullable = true,insertable = true,updatable = true)
    public Date getAssignedDatetime() {
        return assignedDatetime;
    }

    public void setAssignedDatetime(Date assignedDatetime) {
        this.assignedDatetime = assignedDatetime;
    }


    @Basic
    @Column(name = "assigned_to",nullable = true,updatable = true,insertable = true)
    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Basic
    @Column(name = "form_status",nullable = true,insertable = true,updatable = true)
    public String getFormRecievingStatus() {
        return formRecievingStatus;
    }

    public void setFormRecievingStatus(String formRecievingStatus) {
        this.formRecievingStatus = formRecievingStatus;
    }


    @Basic
    @Column(name = "last_assignedTime_Ao",nullable = true,insertable = true,updatable = true)
    public Date getLastAssignedTimeAo() {
        return lastAssignedTimeAo;
    }

    public void setLastAssignedTimeAo(Date lastAssignedTimeAo) {
        this.lastAssignedTimeAo = lastAssignedTimeAo;
    }

    @Basic
    @Column(name = "ao_assignedTo",nullable =true,insertable = true,updatable = true)
    public String getAssignedToAo() {
        return assignedToAo;
    }

    public void setAssignedToAo(String assignedToAo) {
        this.assignedToAo = assignedToAo;
    }

    @Basic
    @Column(name = "aoAssignedStatus",nullable = true,insertable = true,updatable = true)
    public String getAoAssignedstatus() {
        return aoAssignedstatus;
    }

    public void setAoAssignedstatus(String aoAssignedstatus) {
        this.aoAssignedstatus = aoAssignedstatus;
    }

    @Basic
    @Column(name = "document_uploadtime",nullable = true,insertable = true,updatable = true)
    public Timestamp getDocumentRecieveDatetime() {
        return documentRecieveDatetime;
    }

    public void setDocumentRecieveDatetime(Timestamp documentRecieveDatetime) {
        this.documentRecieveDatetime = documentRecieveDatetime;
    }
}
