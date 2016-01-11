package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "process_mast")
@Cacheable
public class ProcessMastEntity {
    private int processCode;
    private String process;
    private Collection<SmsSendlogEntity> smsSendlogsByProcessCode;

    @Id
    @Column(name = "Process_Code", nullable = false, insertable = true, updatable = true)
    public int getProcessCode() {
        return processCode;
    }

    public void setProcessCode(int processCode) {
        this.processCode = processCode;
    }

    @Basic
    @Column(name = "Process", nullable = false, insertable = true, updatable = true, length = 50)
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessMastEntity that = (ProcessMastEntity) o;

        if (processCode != that.processCode) return false;
        if (process != null ? !process.equals(that.process) : that.process != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = processCode;
        result = 31 * result + (process != null ? process.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "processMastByProcessCode")
    public Collection<SmsSendlogEntity> getSmsSendlogsByProcessCode() {
        return smsSendlogsByProcessCode;
    }

    public void setSmsSendlogsByProcessCode(Collection<SmsSendlogEntity> smsSendlogsByProcessCode) {
        this.smsSendlogsByProcessCode = smsSendlogsByProcessCode;
    }

}
