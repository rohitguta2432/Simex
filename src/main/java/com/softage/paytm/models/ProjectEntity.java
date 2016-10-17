package com.softage.paytm.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by SS0087 on 03-10-2016.
 */

@Entity
@Table(name = "project")
public class ProjectEntity {

    private long projectId;
    private String ProjectName;
    private Timestamp createTime;
    private CompanyEntity companyEntity ;
    private String registredBy;


}
