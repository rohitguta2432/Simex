package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "tbl_notification_log")
public class TblNotificationLogEntity {
    private long tblId;
    private Timestamp notificationSenddt;
    private String notificationText;
    private String notificationType;

    @Id
    @Column(name = "tbl_id", nullable = false, insertable = true, updatable = true)
    public long getTblId() {
        return tblId;
    }

    public void setTblId(long tblId) {
        this.tblId = tblId;
    }
/*

    @Basic
    @Column(name = "notification_senddt", nullable = false, insertable = true, updatable = true)
    public Timestamp getNotificationSenddt() {
        return notificationSenddt;
    }
*/

    public void setNotificationSenddt(Timestamp notificationSenddt) {
        this.notificationSenddt = notificationSenddt;
    }

  /*  @Basic
    @Column(name = "notification_text", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getNotificationText() {
        return notificationText;
    }
*/
   /* @Basic
    @Column(name = "notification_text", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getNotificationText() {
        return notificationText;
    }
*/
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    @Basic
    @Column(name = "notification_type", nullable = true, insertable = true, updatable = true, length = 20)
    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblNotificationLogEntity that = (TblNotificationLogEntity) o;

        if (tblId != that.tblId) return false;
        if (notificationSenddt != null ? !notificationSenddt.equals(that.notificationSenddt) : that.notificationSenddt != null)
            return false;
        if (notificationText != null ? !notificationText.equals(that.notificationText) : that.notificationText != null)
            return false;
        if (notificationType != null ? !notificationType.equals(that.notificationType) : that.notificationType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tblId ^ (tblId >>> 32));
        result = 31 * result + (notificationSenddt != null ? notificationSenddt.hashCode() : 0);
        result = 31 * result + (notificationText != null ? notificationText.hashCode() : 0);
        result = 31 * result + (notificationType != null ? notificationType.hashCode() : 0);
        return result;
    }    private String notificationLoginid;

    @Basic
    @Column(name = "notification_loginid", nullable = true, insertable = true, updatable = true, length = 30)
    public String getNotificationLoginid() {
        return notificationLoginid;
    }

    public void setNotificationLoginid(String notificationLoginid) {
        this.notificationLoginid = notificationLoginid;
    }
}
