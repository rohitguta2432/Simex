package com.softage.paytm.dao;

import com.softage.paytm.models.DataentryEntity;

/**
 * Created by SS0085 on 03-02-2016.
 */
public interface DataEntryDao {
    public String saveDataEntry(DataentryEntity dataentryEntity);

    public DataentryEntity getuserById(int cust_uid);
    public String deleteExistEntry(int cust_uid);
}
