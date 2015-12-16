package com.softage.paytm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="EmpLoginTable")
public class Employee {
	
	@Id
	@Column(name="EmpPhone" ,unique=true)
	private String EmpPhone;
	
	private String Emp_code ;
	private String Emp_Name;
	private String EmpPassword;
	private String Role_Code;
	private String Emp_Status;
	private String ImportBy;
	private String ImportDate;
	private long Cir_code;
	private String Roles;
	private String ssoScope;
	
	public String getSsoScope() { return ssoScope; }
	public void setSsoScope(String ssoScope){ ssoScope = this.ssoScope; }
	public String getEmp_code() {
		return Emp_code;
	}
	public void setEmp_code(String emp_code) {
		Emp_code = emp_code;
	}
	public String getEmp_Name() {
		return Emp_Name;
	}
	public void setEmp_Name(String emp_Name) {
		Emp_Name = emp_Name;
	}
	public String getEmpPhone() {
		return EmpPhone;
	}
	public void setEmpPhone(String empPhone) {
		EmpPhone = empPhone;
	}
	public String getEmpPassword() {
		return EmpPassword;
	}
	public void setEmpPassword(String empPassword) {
		EmpPassword = empPassword;
	}
	public String getRole_Code() {
		return Role_Code;
	}
	public void setRole_Code(String role_Code) {
		Role_Code = role_Code;
	}
	public String getEmp_Status() {
		return Emp_Status;
	}
	public void setEmp_Status(String emp_Status) {
		Emp_Status = emp_Status;
	}
	public String getImportBy() {
		return ImportBy;
	}
	public void setImportBy(String importBy) {
		ImportBy = importBy;
	}
	public String getImportDate() {
		return ImportDate;
	}
	public void setImportDate(String importDate) {
		ImportDate = importDate;
	}
	public long getCir_code() {
		return Cir_code;
	}
	public void setCir_code(long cir_code) {
		Cir_code = cir_code;
	}
	public String getRoles() {
		return Roles;
	}
	public void setRoles(String roles) {
		Roles = roles;
	}
	
	
	
	
}
