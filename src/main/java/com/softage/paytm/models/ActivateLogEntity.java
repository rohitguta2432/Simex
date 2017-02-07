package com.softage.paytm.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by SS0085 on 07-02-2017.
 */
@Entity
@Table(name = "agent_action_log")
public class ActivateLogEntity {

    private int id;
    private String agentcode;
    private Timestamp dateTime;
    private String action;



    @Id
    @GeneratedValue
    @Column(name="id",nullable = false,insertable = true,updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Basic
    @Column(name = "agent_code",nullable = true,insertable = true,updatable = true)
    public String getUserId() {
        return agentcode;
    }

    public void setUserId(String agentcode) {
        this.agentcode = agentcode;
    }
    @Basic
    @Column(name = "action_date_time",nullable = true,insertable = true,updatable = true)
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
    @Basic
    @Column(name = "action",nullable = true,insertable = true,updatable = true)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ActivateLogEntity{" +
                "id=" + id +
                ", userId='" + agentcode + '\'' +
                ", dateTime=" + dateTime +
                ", action='" + action + '\'' +
                '}';
    }
}


