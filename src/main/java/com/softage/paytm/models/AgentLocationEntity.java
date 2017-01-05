package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by SS0090 on 1/5/2017.
 */
@Entity
@Table(name = "tbl_agentlocation")
public class AgentLocationEntity {

    private Integer id;
    private String agentCode;
    private String customerNumber;
    private String location;
    private Date importDate;
    private Double longitude;
    private Double latitude;
    private PaytmcustomerDataEntity paytmcustomerDataEntity;

    @Id
    @GeneratedValue
    @Column(name="id",nullable = false,insertable = true,updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "agent_code",nullable = false,insertable = true,updatable = true)
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    @Basic
    @Column(name = "customer_number",nullable = false,insertable = true,updatable = true)
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }


    @Basic
    @Column(name = "location",nullable = false,insertable = true,updatable = true)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "import_date", nullable = false,insertable = true,updatable = true)
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "longitude",nullable = false,insertable = true,updatable = true)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude",nullable = false,insertable = true,updatable = true)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @OneToOne
    @JoinColumn(name = "cust_uid")
    public PaytmcustomerDataEntity getPaytmcustomerDataEntity() {
        return paytmcustomerDataEntity;
    }

    public void setPaytmcustomerDataEntity(PaytmcustomerDataEntity paytmcustomerDataEntity) {
        this.paytmcustomerDataEntity = paytmcustomerDataEntity;
    }
}
