package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.dao.DataEntryDao;
import com.softage.paytm.models.DataentryEntity;
import com.softage.paytm.service.DataEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 03-02-2016.
 */

@Service
public class DataEntryServiceImp implements DataEntryService {
    @Autowired
    private DataEntryDao dataEntryDao;
    @Autowired
    private AllocationDao allocationDao;

    @Override
    public String saveDataEntry(DataentryEntity dataentryEntity) {
        String result=null;
        result= dataEntryDao.saveDataEntry(dataentryEntity);
        return result;
    }

    @Override
    public DataentryEntity getdataByUserCustid(int cust_uid) {
        return dataEntryDao.dataentryEntity(cust_uid);
    }
}
