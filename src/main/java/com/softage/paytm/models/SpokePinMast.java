package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0085 on 02-02-2017.
 */

@Entity
@Table(name = "spoke_pin_mast")
public class SpokePinMast {
    private int id;
    private String circle;
    private String districtname;
    private String pincode;
    private String areaType;
    private String area;
    private String sccName;
    private String spokeName;
    private String agency;
    private String spokeCode;
    private int cirCode;


    @Id
    @GeneratedValue
    @Column(name="id",nullable = false,insertable = true,updatable = true,length = 20)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "cicle_name",nullable = true,insertable = true,updatable = true,length = 50)
    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }
    @Basic
    @Column(name = "district_name",nullable = true,insertable = true,updatable = true,length = 200)
    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }
    @Basic
    @Column(name = "pincode",nullable = true,insertable = true,updatable = true,length = 6)
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    @Basic
    @Column(name = "area_type",nullable = true,insertable = true,updatable = true,length = 30)
    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    @Basic
    @Column(name = "area",nullable = true,insertable = true,updatable = true,length = 100)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "scc_name",nullable = true,insertable = true,updatable = true,length = 100)
    public String getSccName() {
        return sccName;
    }

    public void setSccName(String sccName) {
        this.sccName = sccName;
    }
    @Basic
    @Column(name = "spoke_name",nullable = true,insertable = true,updatable = true,length = 200)
    public String getSpokeName() {
        return spokeName;
    }

    public void setSpokeName(String spokeName) {
        this.spokeName = spokeName;
    }
    @Basic
    @Column(name = "agency_name",nullable = true,insertable = true,updatable = true,length = 50)
    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    @Basic
    @Column(name = "spoke_code",nullable = true,insertable = true,updatable = true,length = 30)
    public String getSpokeCode() {
        return spokeCode;
    }

    public void setSpokeCode(String spokeCode) {
        this.spokeCode = spokeCode;
    }
    @Basic
    @Column(name = "cir_code",nullable = true,insertable = true,updatable = true,length = 10)
    public int getCirCode() {
        return cirCode;
    }

    public void setCirCode(int cirCode) {
        this.cirCode = cirCode;
    }

    @Override
    public String toString() {
        return "SpokePinMast{" +
                "id=" + id +
                ", circle='" + circle + '\'' +
                ", districtname='" + districtname + '\'' +
                ", pincode='" + pincode + '\'' +
                ", areaType='" + areaType + '\'' +
                ", sccName='" + sccName + '\'' +
                ", spokeName='" + spokeName + '\'' +
                ", agency='" + agency + '\'' +
                ", spokeCode='" + spokeCode + '\'' +
                ", cirCode=" + cirCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpokePinMast)) return false;

        SpokePinMast that = (SpokePinMast) o;

        if (id != that.id) return false;
        if (cirCode != that.cirCode) return false;
        if (circle != null ? !circle.equals(that.circle) : that.circle != null) return false;
        if (districtname != null ? !districtname.equals(that.districtname) : that.districtname != null) return false;
        if (pincode != null ? !pincode.equals(that.pincode) : that.pincode != null) return false;
        if (areaType != null ? !areaType.equals(that.areaType) : that.areaType != null) return false;
        if (sccName != null ? !sccName.equals(that.sccName) : that.sccName != null) return false;
        if (spokeName != null ? !spokeName.equals(that.spokeName) : that.spokeName != null) return false;
        if (agency != null ? !agency.equals(that.agency) : that.agency != null) return false;
        return !(spokeCode != null ? !spokeCode.equals(that.spokeCode) : that.spokeCode != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (circle != null ? circle.hashCode() : 0);
        result = 31 * result + (districtname != null ? districtname.hashCode() : 0);
        result = 31 * result + (pincode != null ? pincode.hashCode() : 0);
        result = 31 * result + (areaType != null ? areaType.hashCode() : 0);
        result = 31 * result + (sccName != null ? sccName.hashCode() : 0);
        result = 31 * result + (spokeName != null ? spokeName.hashCode() : 0);
        result = 31 * result + (agency != null ? agency.hashCode() : 0);
        result = 31 * result + (spokeCode != null ? spokeCode.hashCode() : 0);
        result = 31 * result + cirCode;
        return result;
    }
}
