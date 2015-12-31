package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "upload_table")
public class UploadTableEntity {
    private int reqId;
    private String acknowledgement;
    private String reqBy;
    private Timestamp reqDate;
    private String uploadFilename;
    private String uploadStatus;
    private Integer reqCode;
    private UploadRequestEntity uploadRequestByReqCode;

    @Id
    @GeneratedValue
    @Column(name = "Req_Id", nullable = false, insertable = true, updatable = true)
    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    @Basic
    @Column(name = "Acknowledgement", nullable = true, insertable = true, updatable = true, length = 100)
    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    @Basic
    @Column(name = "Req_By", nullable = false, insertable = true, updatable = true, length = 20)
    public String getReqBy() {
        return reqBy;
    }

    public void setReqBy(String reqBy) {
        this.reqBy = reqBy;
    }

    @Basic
    @Column(name = "Req_Date", nullable = true, insertable = true, updatable = true)
    public Timestamp getReqDate() {
        return reqDate;
    }

    public void setReqDate(Timestamp reqDate) {
        this.reqDate = reqDate;
    }

    @Basic
    @Column(name = "Upload_Filename", nullable = false, insertable = true, updatable = true, length = 50)
    public String getUploadFilename() {
        return uploadFilename;
    }

    public void setUploadFilename(String uploadFilename) {
        this.uploadFilename = uploadFilename;
    }

    @Basic
    @Column(name = "Upload_Status", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    @Basic
    @Column(name = "Req_Code", nullable = true, insertable = false, updatable = false)
    public Integer getReqCode() {
        return reqCode;
    }

    public void setReqCode(Integer reqCode) {
        this.reqCode = reqCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadTableEntity that = (UploadTableEntity) o;

        if (reqId != that.reqId) return false;
        if (acknowledgement != null ? !acknowledgement.equals(that.acknowledgement) : that.acknowledgement != null)
            return false;
        if (reqBy != null ? !reqBy.equals(that.reqBy) : that.reqBy != null) return false;
        if (reqDate != null ? !reqDate.equals(that.reqDate) : that.reqDate != null) return false;
        if (uploadFilename != null ? !uploadFilename.equals(that.uploadFilename) : that.uploadFilename != null)
            return false;
        if (uploadStatus != null ? !uploadStatus.equals(that.uploadStatus) : that.uploadStatus != null) return false;
        if (reqCode != null ? !reqCode.equals(that.reqCode) : that.reqCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reqId;
        result = 31 * result + (acknowledgement != null ? acknowledgement.hashCode() : 0);
        result = 31 * result + (reqBy != null ? reqBy.hashCode() : 0);
        result = 31 * result + (reqDate != null ? reqDate.hashCode() : 0);
        result = 31 * result + (uploadFilename != null ? uploadFilename.hashCode() : 0);
        result = 31 * result + (uploadStatus != null ? uploadStatus.hashCode() : 0);
        result = 31 * result + (reqCode != null ? reqCode.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Req_Code", referencedColumnName = "Req_Code")
    public UploadRequestEntity getUploadRequestByReqCode() {
        return uploadRequestByReqCode;
    }

    public void setUploadRequestByReqCode(UploadRequestEntity uploadRequestByReqCode) {
        this.uploadRequestByReqCode = uploadRequestByReqCode;
    }

}
