package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0097 on 1/16/2017.
 */
@Entity
@Table(name="tbl_custDocDetails")
public class TblcustDocDetails {
    private int Doc_id;
    private int cust_uid;


@Id
@GeneratedValue
@Column(name="Doc_id",nullable=false,insertable=true,updatable = true,length = 11)
    public int getDoc_id() {
        return Doc_id;
    }

    public void setDoc_id(int doc_id) {
        Doc_id = doc_id;
    }

    private String cPOI;
    private String cPOA;
    private String origPoi;


    @Basic
    @Column(name = "cust_uid",nullable = false,insertable = true,updatable = true,length=)
    public int getCust_uid() {
        return cust_uid;
    }

    public void setCust_uid(int cust_uid) {
        this.cust_uid = cust_uid;
    }

    @Basic
    @Column(name = "cPOI",nullable = false,insertable = true,updatable = true)
    public String getcPOI() {
        return cPOI;
    }

    public void setcPOI(String cPOI) {
        this.cPOI = cPOI;
    }


    @Basic
    @Column(name = "cPOA",nullable = false,insertable = true,updatable = true)
    public String getcPOA() {
        return cPOA;
    }

    public void setcPOA(String cPOA) {
        this.cPOA = cPOA;
    }

    @Basic
    @Column(name = "OrigPoi",nullable = false,insertable = true,updatable = true)
    public String getOrigPoi() {
        return origPoi;
    }

    public void setOrigPoi(String origPoi) {
        this.origPoi = origPoi;
    }

    @Basic
    @Column(name = "OrigPoa",nullable = false,insertable = true,updatable = true)
    public String getOrigPoa() {
        return origPoa;
    }

    public void setOrigPoa(String origPoa) {
        this.origPoa = origPoa;
    }

    @Basic
    @Column(name = "SRF",nullable = false,insertable = true,updatable = true)
    public String getSRF() {
        return SRF;
    }

    public void setSRF(String SRF) {
        this.SRF = SRF;
    }

    @Basic
    @Column(name = "photo",nullable = false,insertable = true,updatable = true)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Basic
    @Column(name = "poiPc",nullable = false,insertable = true,updatable = true)
    public int getPoiPc() {
        return poiPc;
    }

    public void setPoiPc(int poiPc) {
        this.poiPc = poiPc;
    }

    @Basic
    @Column(name = "poaPc",nullable = false,insertable = true,updatable = true)
    public int getPoaPc() {
        return poaPc;
    }

    public void setPoaPc(int poaPc) {
        this.poaPc = poaPc;
    }

    @Basic
    @Column(name = "origPoiPc",nullable = false,insertable = true,updatable = true)
    public int getOrigpoiPc() {
        return origpoiPc;
    }

    public void setOrigpoiPc(int origpoiPc) {
        this.origpoiPc = origpoiPc;
    }
    @Basic
    @Column(name = "origPoaPc",nullable = false,insertable = true,updatable = true)
    public int getOrigpoaPc() {
        return origpoaPc;
    }

    public void setOrigpoaPc(int origpoaPc) {
        this.origpoaPc = origpoaPc;
    }


    @Basic
    @Column(name = "SrfPc",nullable = false,insertable = true,updatable = true)
    public int getSrfPc() {
        return SrfPc;
    }

    public void setSrfPc(int srfPc) {
        SrfPc = srfPc;
    }

    @Basic
    @Column(name = "PhotoPc",nullable = false,insertable = true,updatable = true)
    public int getPhotoPc() {
        return PhotoPc;
    }

    public void setPhotoPc(int photoPc) {
        PhotoPc = photoPc;
    }

    private String origPoa;
    private String SRF;
    private String photo;
    private int poiPc;
    private int poaPc;
    private int origpoiPc;
    private int origpoaPc;
    private int SrfPc;
    private int PhotoPc;

}
