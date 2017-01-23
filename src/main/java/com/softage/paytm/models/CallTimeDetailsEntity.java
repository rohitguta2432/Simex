package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by SS0090 on 1/5/2017.
 */
@Entity
@Table(name = "tbl_calltimedetails")
public class CallTimeDetailsEntity {

    private Integer id;
    private String customer_number;
    private Timestamp call_datetime;
    private Integer call_status;
    private Integer circleCode;
    private String lastcallBy;
    private PaytmMastEntity paytmcustomerDataEntity;

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false,insertable = true,updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_number",nullable = false,insertable = true,updatable = true)
    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    @Basic
    @Column(name = "call_datetime",nullable = false,insertable = true,updatable = true)
    public Timestamp getCall_datetime() {
        return call_datetime;
    }

    public void setCall_datetime(Timestamp call_datetime) {
        this.call_datetime = call_datetime;
    }

    @Basic
    @Column(name = "call_status",nullable = true,insertable = true,updatable = true)
    public Integer getCall_status() {
        return call_status;
    }

    public void setCall_status(Integer call_status) {
        this.call_status = call_status;
    }

    @Basic
    @Column(name = "circleCode",nullable = true,insertable = true,updatable = true)
    public Integer getCircleCode() {
        return circleCode;
    }

    public void setCircleCode(Integer circleCode) {
        this.circleCode = circleCode;
    }

    @Basic
    @Column(name = "lastcallBy",nullable = false,insertable = true,updatable = true)
    public String getLastcallBy() {
        return lastcallBy;
    }

    public void setLastcallBy(String lastcallBy) {
        this.lastcallBy = lastcallBy;
    }

    @OneToOne
    @JoinColumn(name = "cust_uid")
    public PaytmMastEntity getPaytmcustomerDataEntity() {
        return paytmcustomerDataEntity;
    }

    public void setPaytmcustomerDataEntity(PaytmMastEntity paytmcustomerDataEntity) {
        this.paytmcustomerDataEntity = paytmcustomerDataEntity;
    }
}
