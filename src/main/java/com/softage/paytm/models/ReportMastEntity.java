package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "report_mast")
public class ReportMastEntity {
    private long reportId;
    private String procName;
    private String reportName;

    @Id
    @Column(name = "Report_Id", nullable = false, insertable = true, updatable = true)
    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "Proc_Name", nullable = false, insertable = true, updatable = true, length = 100)
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    @Basic
    @Column(name = "Report_Name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportMastEntity that = (ReportMastEntity) o;

        if (reportId != that.reportId) return false;
        if (procName != null ? !procName.equals(that.procName) : that.procName != null) return false;
        if (reportName != null ? !reportName.equals(that.reportName) : that.reportName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (reportId ^ (reportId >>> 32));
        result = 31 * result + (procName != null ? procName.hashCode() : 0);
        result = 31 * result + (reportName != null ? reportName.hashCode() : 0);
        return result;
    }
}
