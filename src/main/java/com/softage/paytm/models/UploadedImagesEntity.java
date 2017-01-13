package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by SS0090 on 1/4/2017.
 */
@Entity
@Table(name = "tbl_uploaded_images")
public class UploadedImagesEntity {

    private int imageId;
    private String imagePath;
    private TblScan tblScan;
    private Integer scan_id;
    private Timestamp uploadedon;


    @Id
    @GeneratedValue
    @Column(name = "image_id",nullable = false,insertable = true,updatable = true)
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "imagePath", nullable = false,insertable = true,updatable = true)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic
    @Column(name = "uploadedon", nullable = true,insertable = true,updatable = true)
    public Timestamp getUploadedon() {
        return uploadedon;
    }

    public void setUploadedon(Timestamp uploadedon) {
        this.uploadedon = uploadedon;
    }

    @Basic
    @Column(name = "scan_id", nullable = true, insertable = false, updatable =false)
    public Integer getScan_id() {
        return scan_id;
    }

    public void setScan_id(Integer scan_id) {
        this.scan_id = scan_id;
    }


    @ManyToOne
    @JoinColumn(name = "scan_id",referencedColumnName = "scan_id")
    public TblScan getTblScan() {
        return tblScan;
    }

    public void setTblScan(TblScan tblScan) {
        this.tblScan = tblScan;
    }


}
