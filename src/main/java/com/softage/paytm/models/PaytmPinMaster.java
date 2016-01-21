package com.softage.paytm.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SS0085 on 21-01-2016.
 */
@Entity
@Table(name="paytm_pinmaster")
public class PaytmPinMaster implements Serializable{

    private int id;
    private int pinCode;
    private String state;
    private String district;

    @Id
    @Basic
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Basic
    @Column(name = "pincode", nullable = false, insertable = true, updatable = true)
    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
    @Basic
    @Column(name = "state", nullable = false, insertable = true, updatable = true)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Basic
    @Column(name = "district", nullable = false, insertable = true, updatable = true)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
