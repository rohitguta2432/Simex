package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "sms_sendlog")
public class SmsSendlogEntity {
    private int sslId;
    private String mobileNumber;
    private Integer receiverCode;
    private String smsText;
    private Timestamp sendDateTime;
    private Integer processCode;
    private Timestamp importDate;
    private String deliveryStatus;
    private String smsDelivered;
    private String receiverId;
    private ProcessMastEntity processMastByProcessCode;
    private ReceiverMastEntity receiverMastByReceiverCode;

    @Id
    @Column(name = "SSL_Id", nullable = false, insertable = true, updatable = true)
    public int getSslId() {
        return sslId;
    }

    public void setSslId(int sslId) {
        this.sslId = sslId;
    }

    @Basic
    @Column(name = "MobileNumber", nullable = false, insertable = true, updatable = true, length = 10)
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "Receiver_Code", nullable = true, insertable =false, updatable =false)
    public Integer getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(Integer receiverCode) {
        this.receiverCode = receiverCode;
    }

    @Basic
    @Column(name = "SMS_Text", nullable = true, insertable = true, updatable = true, length = 300)
    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    @Basic
    @Column(name = "Send_DateTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(Timestamp sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    @Basic
    @Column(name = "Process_code", nullable = true, insertable = false, updatable = false)
    public Integer getProcessCode() {
        return processCode;
    }

    public void setProcessCode(Integer processCode) {
        this.processCode = processCode;
    }

    @Basic
    @Column(name = "Import_date", nullable = false, insertable = true, updatable = true)
    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    @Basic
    @Column(name = "Delivery_Status", nullable = true, insertable = true, updatable = true, length = 200)
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Basic
    @Column(name = "SMS_Delivered", nullable = true, insertable = true, updatable = true, length = 1)
    public String getSmsDelivered() {
        return smsDelivered;
    }

    public void setSmsDelivered(String smsDelivered) {
        this.smsDelivered = smsDelivered;
    }

    @Basic
    @Column(name = "Receiver_ID", nullable = true, insertable = true, updatable = true, length = 10)
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsSendlogEntity that = (SmsSendlogEntity) o;

        if (sslId != that.sslId) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (receiverCode != null ? !receiverCode.equals(that.receiverCode) : that.receiverCode != null) return false;
        if (smsText != null ? !smsText.equals(that.smsText) : that.smsText != null) return false;
        if (sendDateTime != null ? !sendDateTime.equals(that.sendDateTime) : that.sendDateTime != null) return false;
        if (processCode != null ? !processCode.equals(that.processCode) : that.processCode != null) return false;
        if (importDate != null ? !importDate.equals(that.importDate) : that.importDate != null) return false;
        if (deliveryStatus != null ? !deliveryStatus.equals(that.deliveryStatus) : that.deliveryStatus != null)
            return false;
        if (smsDelivered != null ? !smsDelivered.equals(that.smsDelivered) : that.smsDelivered != null) return false;
        if (receiverId != null ? !receiverId.equals(that.receiverId) : that.receiverId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sslId;
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (receiverCode != null ? receiverCode.hashCode() : 0);
        result = 31 * result + (smsText != null ? smsText.hashCode() : 0);
        result = 31 * result + (sendDateTime != null ? sendDateTime.hashCode() : 0);
        result = 31 * result + (processCode != null ? processCode.hashCode() : 0);
        result = 31 * result + (importDate != null ? importDate.hashCode() : 0);
        result = 31 * result + (deliveryStatus != null ? deliveryStatus.hashCode() : 0);
        result = 31 * result + (smsDelivered != null ? smsDelivered.hashCode() : 0);
        result = 31 * result + (receiverId != null ? receiverId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Process_code", referencedColumnName = "Process_Code")
    public ProcessMastEntity getProcessMastByProcessCode() {
        return processMastByProcessCode;
    }

    public void setProcessMastByProcessCode(ProcessMastEntity processMastByProcessCode) {
        this.processMastByProcessCode = processMastByProcessCode;
    }


    @ManyToOne
    @JoinColumn(name = "Receiver_Code", referencedColumnName = "Receiver_Code")
    public ReceiverMastEntity getReceiverMastByReceiverCode() {
        return receiverMastByReceiverCode;
    }

    public void setReceiverMastByReceiverCode(ReceiverMastEntity receiverMastByReceiverCode) {
        this.receiverMastByReceiverCode = receiverMastByReceiverCode;
    }

}
