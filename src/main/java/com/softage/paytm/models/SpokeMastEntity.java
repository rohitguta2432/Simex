package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "spoke_mast")
@Cacheable
public class SpokeMastEntity {
    private String spokeCode;
    private Integer cirCode;
    private String circle;
    private String importBy;
    private Timestamp importDate;
    private String inchargeMobileNo;
    private String inchargeName;
    private Double latitude;
    private Double longitude;
    private double rpmTat;
    private String spokeAddress;
    private String spokeName;
    private String ssoCategory;
    private String ssoEmail;
    private String zone;

    @Id
    @Column(name = "Spoke_code", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSpokeCode() {
        return spokeCode;
    }

    public void setSpokeCode(String spokeCode) {
        this.spokeCode = spokeCode;
    }

    @Basic
    @Column(name = "Cir_code", nullable = true, insertable = true, updatable = true)
    public Integer getCirCode() {
        return cirCode;
    }

    public void setCirCode(Integer cirCode) {
        this.cirCode = cirCode;
    }

    @Basic
    @Column(name = "Circle", nullable = true, insertable = true, updatable = true, length = 20)
    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    @Basic
    @Column(name = "Import_by", nullable = true, insertable = true, updatable = true, length = 20)
    public String getImportBy() {
        return importBy;
    }

    public void setImportBy(String importBy) {
        this.importBy = importBy;
    }

    @Basic
    @Column(name = "Import_Date", nullable = true, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "InchargeMobileNo", nullable = true, insertable = true, updatable = true, length = 30)
    public String getInchargeMobileNo() {
        return inchargeMobileNo;
    }

    public void setInchargeMobileNo(String inchargeMobileNo) {
        this.inchargeMobileNo = inchargeMobileNo;
    }

    @Basic
    @Column(name = "InchargeName", nullable = true, insertable = true, updatable = true, length = 70)
    public String getInchargeName() {
        return inchargeName;
    }

    public void setInchargeName(String inchargeName) {
        this.inchargeName = inchargeName;
    }

  /*  @Basic
    @Column(name = "Latitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }
*/
    @Basic
    @Column(name = "Latitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "Longitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "RPM_TAT", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getRpmTat() {
        return rpmTat;
    }

    public void setRpmTat(double rpmTat) {
        this.rpmTat = rpmTat;
    }

    @Basic
    @Column(name = "Spoke_Address", nullable = true, insertable = true, updatable = true, length = 150)
    public String getSpokeAddress() {
        return spokeAddress;
    }

    public void setSpokeAddress(String spokeAddress) {
        this.spokeAddress = spokeAddress;
    }

    @Basic
    @Column(name = "Spoke_Name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getSpokeName() {
        return spokeName;
    }

    public void setSpokeName(String spokeName) {
        this.spokeName = spokeName;
    }

    @Basic
    @Column(name = "SSO_CATEGORY", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSsoCategory() {
        return ssoCategory;
    }

    public void setSsoCategory(String ssoCategory) {
        this.ssoCategory = ssoCategory;
    }

    @Basic
    @Column(name = "Sso_Email", nullable = true, insertable = true, updatable = true, length = 150)
    public String getSsoEmail() {
        return ssoEmail;
    }

    public void setSsoEmail(String ssoEmail) {
        this.ssoEmail = ssoEmail;
    }

    @Basic
    @Column(name = "Zone", nullable = true, insertable = true, updatable = true, length = 30)
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpokeMastEntity that = (SpokeMastEntity) o;

        if (Double.compare(that.rpmTat, rpmTat) != 0) return false;
        if (spokeCode != null ? !spokeCode.equals(that.spokeCode) : that.spokeCode != null) return false;
        if (cirCode != null ? !cirCode.equals(that.cirCode) : that.cirCode != null) return false;
        if (circle != null ? !circle.equals(that.circle) : that.circle != null) return false;
        if (importBy != null ? !importBy.equals(that.importBy) : that.importBy != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (inchargeMobileNo != null ? !inchargeMobileNo.equals(that.inchargeMobileNo) : that.inchargeMobileNo != null)
            return false;
        if (inchargeName != null ? !inchargeName.equals(that.inchargeName) : that.inchargeName != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (spokeAddress != null ? !spokeAddress.equals(that.spokeAddress) : that.spokeAddress != null) return false;
        if (spokeName != null ? !spokeName.equals(that.spokeName) : that.spokeName != null) return false;
        if (ssoCategory != null ? !ssoCategory.equals(that.ssoCategory) : that.ssoCategory != null) return false;
        if (ssoEmail != null ? !ssoEmail.equals(that.ssoEmail) : that.ssoEmail != null) return false;
        if (zone != null ? !zone.equals(that.zone) : that.zone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = spokeCode != null ? spokeCode.hashCode() : 0;
        result = 31 * result + (cirCode != null ? cirCode.hashCode() : 0);
        result = 31 * result + (circle != null ? circle.hashCode() : 0);
        result = 31 * result + (importBy != null ? importBy.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (inchargeMobileNo != null ? inchargeMobileNo.hashCode() : 0);
        result = 31 * result + (inchargeName != null ? inchargeName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        temp = Double.doubleToLongBits(rpmTat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (spokeAddress != null ? spokeAddress.hashCode() : 0);
        result = 31 * result + (spokeName != null ? spokeName.hashCode() : 0);
        result = 31 * result + (ssoCategory != null ? ssoCategory.hashCode() : 0);
        result = 31 * result + (ssoEmail != null ? ssoEmail.hashCode() : 0);
        result = 31 * result + (zone != null ? zone.hashCode() : 0);
        return result;
    }    private String ssoCatery;

    @Basic
    @Column(name = "SSO_CATERY", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSsoCatery() {
        return ssoCatery;
    }

    public void setSsoCatery(String ssoCatery) {
        this.ssoCatery = ssoCatery;
    }
}
