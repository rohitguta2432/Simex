package com.softage.paytm.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by SS0087 on 03-10-2016.
 */

@Entity
@Table(name = "company")
public class CompanyEntity {

    private long compId;
    private String compName;
    private Timestamp createTime;
    private String address;
    private String registredBy;


}
