package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "role_mast")
public class RoleMastEntity {
    private String roleCode;
    private String importBy;
    private Timestamp importDate;
    private String roleDescription;
    private int roleId;
    private boolean roleStatus;

  /*  @Basic
    @Column(name = "Role_Status", nullable = false, insertable = true, updatable = true)
    public byte getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(byte roleStatus) {
        this.roleStatus = roleStatus;
    }*/

    @Id
    @Column(name = "Role_Code", nullable = false, insertable = true, updatable = true, length = 6)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Basic
    @Column(name = "ImportBy", nullable = false, insertable = true, updatable = true, length = 30)
    public String getImportBy() {
        return importBy;
    }

    public void setImportBy(String importBy) {
        this.importBy = importBy;
    }

    @Basic
    @Column(name = "ImportDate", nullable = false, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "Role_Description", nullable = true, insertable = true, updatable = true, length = 40)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Basic
    @Column(name = "Role_Id", nullable = false, insertable = true, updatable = true)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "Role_Status", nullable = false, insertable = true, updatable = true)
    public boolean isRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(boolean roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleMastEntity that = (RoleMastEntity) o;

        if (roleId != that.roleId) return false;
        if (roleStatus != that.roleStatus) return false;
        if (roleCode != null ? !roleCode.equals(that.roleCode) : that.roleCode != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (roleDescription != null ? !roleDescription.equals(that.roleDescription) : that.roleDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleCode != null ? roleCode.hashCode() : 0;
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (roleDescription != null ? roleDescription.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + (roleStatus ? 1 : 0);
        return result;
    }
}
