package com.softage.paytm.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by SS0085 on 04-01-2017.
 */
@Embeddable
public class CustomerKey implements Serializable {


    private String customerPhone;

    private Date dataDate;


    @Column(name = "CustomerPhone", nullable = false, insertable = true, updatable = true, length = 10)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    @Column(name = "dataDate", nullable = false, insertable = true, updatable = true)
    public Date getDataDate() {
        return dataDate;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }
}
