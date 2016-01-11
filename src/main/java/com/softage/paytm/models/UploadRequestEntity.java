package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "upload_request")
@Cacheable
public class UploadRequestEntity {
    private int reqCode;
    private String callProc;
    private String uploadType;
    private Collection<UploadTableEntity> uploadTablesByReqCode;

    @Id
    @GeneratedValue
    @Column(name = "Req_Code", nullable = false, insertable = true, updatable = true)
    public int getReqCode() {
        return reqCode;
    }

    public void setReqCode(int reqCode) {
        this.reqCode = reqCode;
    }

    @Basic
    @Column(name = "Call_Proc", nullable = false, insertable = true, updatable = true, length = 50)
    public String getCallProc() {
        return callProc;
    }

    public void setCallProc(String callProc) {
        this.callProc = callProc;
    }

    @Basic
    @Column(name = "Upload_Type", nullable = false, insertable = true, updatable = true, length = 50)
    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadRequestEntity that = (UploadRequestEntity) o;

        if (reqCode != that.reqCode) return false;
        if (callProc != null ? !callProc.equals(that.callProc) : that.callProc != null) return false;
        if (uploadType != null ? !uploadType.equals(that.uploadType) : that.uploadType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reqCode;
        result = 31 * result + (callProc != null ? callProc.hashCode() : 0);
        result = 31 * result + (uploadType != null ? uploadType.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "uploadRequestByReqCode")
    public Collection<UploadTableEntity> getUploadTablesByReqCode() {
        return uploadTablesByReqCode;
    }

    public void setUploadTablesByReqCode(Collection<UploadTableEntity> uploadTablesByReqCode) {
        this.uploadTablesByReqCode = uploadTablesByReqCode;
    }


}
