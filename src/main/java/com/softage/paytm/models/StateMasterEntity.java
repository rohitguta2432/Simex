package com.softage.paytm.models;

import javax.persistence.*;

/**
 * Created by SS0085 on 23-12-2015.
 */
@Entity
@Table(name = "state_master")
public class StateMasterEntity {
    private String stateCode;
    private String stateName;

    @Id
    @Column(name = "State_code", nullable = false, insertable = true, updatable = true, length = 2)
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Basic
    @Column(name = "State_Name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateMasterEntity that = (StateMasterEntity) o;

        if (stateCode != null ? !stateCode.equals(that.stateCode) : that.stateCode != null) return false;
        if (stateName != null ? !stateName.equals(that.stateName) : that.stateName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stateCode != null ? stateCode.hashCode() : 0;
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        return result;
    }
}
