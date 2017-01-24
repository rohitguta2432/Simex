package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "emplogintable")
public class EmplogintableEntity {
    private String empPhone;
    private String empCode;
    private String empName;
    private String empPassword;
    private Integer empStatus;
    private String importBy;
    private String token;
    private Timestamp lastLoginDate;
    private Timestamp lockedDate;
    private  Integer attamptCount;
    private Timestamp importDate;
    private Timestamp expireDate;
    private String roles;
    private Integer cirCode;
    private String spoke_code;
    private CircleMastEntity circleMastByCirCode;
    private String roleCode;
    private String lastThreePassword;


    @Basic
    @Column(name = "lastThreePassword", nullable = false, insertable = true, updatable = true)
    public String getLastThreePassword() {
        return lastThreePassword;
    }

    public void setLastThreePassword(String lastThreePassword) {
        this.lastThreePassword = lastThreePassword;
    }

    @Basic
    @Column(name = "Emp_Status", nullable = false, insertable = true, updatable = true)
    public Integer getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Integer empStatus) {
        this.empStatus = empStatus;
    }

    @Id
    @Column(name = "EmpPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    @Basic
    @Column(name = "Emp_Code", nullable = false, insertable = true, updatable = true, length = 30)
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Basic
    @Column(name = "Emp_Name", nullable = false, insertable = true, updatable = true, length = 70)
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Basic
    @Column(name = "EmpPassword", nullable = false, insertable = true, updatable = true, length = 20)
    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

   /* @Basic
    @Column(name = "Emp_Status", nullable = false, insertable = true, updatable = true)
    public boolean isEmpStatus() {
        return empStatus;
    }*/

    /*public void setEmpStatus(boolean empStatus) {
        this.empStatus = empStatus;
    }*/

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
    @Column(name = "token", nullable = true, insertable = true, updatable = true, length = 50)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "last_login", nullable = true, insertable = true, updatable = true, length = 30)
    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Basic
    @Column(name = "locked_date", nullable = true, insertable = true, updatable = true, length = 30)
    public Timestamp getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Timestamp lockedDate) {
        this.lockedDate = lockedDate;
    }
    @Basic
    @Column(name = "attampt_count", nullable = true, insertable = true, updatable = true, length = 4)
    public Integer getAttamptCount() {
        return attamptCount;
    }

    public void setAttamptCount(Integer attamptCount) {
        this.attamptCount = attamptCount;
    }

    @Basic
    @Column(name = "expireDate", nullable = true, insertable = true, updatable = true)
    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }


    @Basic
    @Column(name = "Roles", nullable = true, insertable = true, updatable = true, length = 10)
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "Cir_code", nullable = true, insertable = false, updatable =false)
    public Integer getCirCode() {
        return cirCode;
    }

    public void setCirCode(Integer cirCode) {
        this.cirCode = cirCode;
    }


    @Basic
    @Column(name = "spoke_code", nullable = true, insertable = true, updatable = true, length = 10)
    public String getSpoke_code() {
        return spoke_code;
    }

    public void setSpoke_code(String spoke_code) {
        this.spoke_code = spoke_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmplogintableEntity that = (EmplogintableEntity) o;

        if (empStatus != that.empStatus) return false;
        if (empPhone != null ? !empPhone.equals(that.empPhone) : that.empPhone != null) return false;
        if (empCode != null ? !empCode.equals(that.empCode) : that.empCode != null) return false;
        if (empName != null ? !empName.equals(that.empName) : that.empName != null) return false;
        if (empPassword != null ? !empPassword.equals(that.empPassword) : that.empPassword != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;
        if (cirCode != null ? !cirCode.equals(that.cirCode) : that.cirCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = empPhone != null ? empPhone.hashCode() : 0;
        result = 31 * result + (empCode != null ? empCode.hashCode() : 0);
        result = 31 * result + (empName != null ? empName.hashCode() : 0);
        result = 31 * result + (empPassword != null ? empPassword.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (cirCode != null ? cirCode.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Cir_code", referencedColumnName = "Cir_code")
    public CircleMastEntity getCircleMastByCirCode() {
        return circleMastByCirCode;
    }

    public void setCircleMastByCirCode(CircleMastEntity circleMastByCirCode) {
        this.circleMastByCirCode = circleMastByCirCode;
    }
    @Basic
    @Column(name = "Role_Code", nullable = true, insertable = true, updatable = true, length = 6)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
