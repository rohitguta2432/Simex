package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AoAuditDao;
import com.softage.paytm.models.AoAuditEntity;
import com.softage.paytm.models.TblScan;
import com.softage.paytm.service.AoAuditService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0090 on 1/12/2017.
 */
@Service
public class AoAuditServiceImp implements AoAuditService {

    @Autowired
    private AoAuditDao aoAuditDao;

    @Override
    public JSONObject getAoAuditDetails(String spokecode,String empcode) {
        return aoAuditDao.getAoAuditDetails(spokecode,empcode);
    }

    @Override
    public String saveAuditEntity(AoAuditEntity aoAuditEntity) {
        return aoAuditDao.saveAoAuditEntity(aoAuditEntity);
    }

    @Override
    public JSONObject getFormRecievingDetails(String mobile_number,String spokecode) {
        return aoAuditDao.getFormRecievingDetails(mobile_number,spokecode);
    }

    @Override
    public String checkAoAssignedTo(TblScan tblScan, String empcode) {
        return aoAuditDao.checkAoAssignedTo(tblScan,empcode);
    }

    @Override
    public String insertAoAuditValues(String dob, String name, String otherReason, String photo, String sign, Integer scanid, Integer auditStatus) {
        return aoAuditDao.insertAoAuditValues(dob,name,otherReason,photo,sign,scanid,auditStatus);
    }
}
