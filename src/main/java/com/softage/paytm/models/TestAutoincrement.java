package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by SS0085 on 02-01-2016.
 */

@Entity
@Table(name = "Testtable")
public class TestAutoincrement {

    private long id;
    private String phoneNo;
    private Date appointmentDate;
    private Time appointmentTime;

    @Id
    @Column(name = "phoneNo", nullable = false, insertable = true, updatable = true)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }



    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Basic
    @Column(name = "Appointment_Date", nullable = false, insertable = true, updatable = true, length = 10)
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }
    @Basic
   @Column(name = "Appointment_Time", nullable = false, insertable = true, updatable = true, length = 10)
    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAutoincrement)) return false;

        TestAutoincrement that = (TestAutoincrement) o;

        if (id != that.id) return false;
        if (appointmentDate != null ? !appointmentDate.equals(that.appointmentDate) : that.appointmentDate != null)
            return false;
        return !(appointmentTime != null ? !appointmentTime.equals(that.appointmentTime) : that.appointmentTime != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (appointmentDate != null ? appointmentDate.hashCode() : 0);
        result = 31 * result + (appointmentTime != null ? appointmentTime.hashCode() : 0);
        return result;
    }

    /* // public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }*/
//
//    public void setAppointmentTime(Time appointmentTime) {
//        this.appointmentTime = appointmentTime.toString();
//    }

//    @Id
//    @GeneratedValue
//    @Column(name = "Appointment_id", nullable = false, insertable = true, updatable = true)
//    public long getAppointmentId() {
//        return appointmentId;
//    }
//
//    public void setAppointmentId(long appointmentId) {
//        this.appointmentId = appointmentId;
//    }
//
//    @Basic
//    @Column(name = "Appointment_Date", nullable = false, insertable = true, updatable = true, length = 10)
//    public String getAppointmentDate() {
//        return appointmentDate;
//    }
//
//    public void setAppointmentDate(String appointmentDate) {
//        this.appointmentDate = appointmentDate;
//    }
//
//    @Basic
//    @Column(name = "Appointment_Time", nullable = false, insertable = true, updatable = true, length = 16)
//    public String getAppointmentTime() {
//        return appointmentTime;
//    }
//
//




}
