package com.softage.paytm.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "receiver_mast")
public class ReceiverMastEntity {
    private int receiverCode;
    private String receiver;
    private Collection<SmsSendlogEntity> smsSendlogsByReceiverCode;

    @Id
    @Column(name = "Receiver_Code", nullable = false, insertable = true, updatable = true)
    public int getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(int receiverCode) {
        this.receiverCode = receiverCode;
    }

    @Basic
    @Column(name = "Receiver", nullable = false, insertable = true, updatable = true, length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiverMastEntity that = (ReceiverMastEntity) o;

        if (receiverCode != that.receiverCode) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = receiverCode;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "receiverMastByReceiverCode")
    public Collection<SmsSendlogEntity> getSmsSendlogsByReceiverCode() {
        return smsSendlogsByReceiverCode;
    }

    public void setSmsSendlogsByReceiverCode(Collection<SmsSendlogEntity> smsSendlogsByReceiverCode) {
        this.smsSendlogsByReceiverCode = smsSendlogsByReceiverCode;
    }

}
