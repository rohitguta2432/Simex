package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "timeslot")
@Cacheable
public class TimeslotEntity {
    private int id;
    private String timeSlot;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TimeSlot", nullable = true, insertable = true, updatable = true, length = 40)
    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeslotEntity that = (TimeslotEntity) o;

        if (id != that.id) return false;
        if (timeSlot != null ? !timeSlot.equals(that.timeSlot) : that.timeSlot != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
        return result;
    }
}
