package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AllocationDao;
import com.softage.paytm.dao.LeadsDao;
import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.models.AllocationMastEntity;
import com.softage.paytm.models.AppointmentMastEntity;
import com.softage.paytm.models.ProofMastEntity;
import com.softage.paytm.models.ReasonMastEntity;
import com.softage.paytm.service.LeadsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by SS0085 on 02-02-2016.
 */
@Service
public class LeadsServiceImp implements LeadsService {
    @Autowired
    private LeadsDao leadsDao;
    @Autowired
    private AllocationDao allocationDao;
    @Autowired
    private PostCallingDao postCallingDao;

    @Override
    public List<JSONObject> getAgentLeads(String agentCode,int timedeff,String cuurentDate) {
        return leadsDao.getAgentLeads(agentCode,timedeff,cuurentDate);
    }

    @Override
    public String updateLeadStatus(String agentCode, String jobid, boolean response) {
        AllocationMastEntity allocationMastEntity = allocationDao.findById(agentCode, jobid);
        String status = null;
        AppointmentMastEntity appointmentMastEntity = null;
        String msg = "";
        String result="";
        long appointmentid = 0;
        try {
            if (response) {
                status = "Y";
            } else {
                status = "N";
            }
            if (allocationMastEntity != null) {
                appointmentid = allocationMastEntity.getAppointmentId();
                allocationMastEntity.setConfirmation(status);
                allocationMastEntity.setFinalConfirmation(status);
                allocationMastEntity.setConfirmationDatetime(new Timestamp(new Date().getTime()));
                for (int i=1; i<=1; i++) {
                    //result = allocationDao.updateAllocationMastEntity(allocationMastEntity);
                    result=allocationDao.allocationMastEntityUpdate(jobid,agentCode,status);

                }
                if ("done".equalsIgnoreCase(result)) {
                    msg = "1";
                }
            }
            if ("Y".equals(status) && appointmentid != 0) {
                appointmentMastEntity = postCallingDao.getByAppointmentId(appointmentid);
                if (appointmentMastEntity != null) {
                  result = leadsDao.jobConfirmtocustomer(appointmentid, allocationMastEntity.getCustomerPhone(), allocationMastEntity.getAgentCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public List<JSONObject> agentAcceptedLeads(String agentCode) {
        return leadsDao.agentAcceptedLeads(agentCode);
    }

    @Override
    public List<JSONObject> kycDone(String agentCode) {
        return leadsDao.kycDone(agentCode);
    }

    @Override
    public List<JSONObject> kycNotDone(String agentCode) {
        return leadsDao.kycNotDone(agentCode);
    }

    @Override
    public List<JSONObject> agentRejectedLeads(String agentCode) {
        return leadsDao.agentRejectedLeads(agentCode);
    }

    @Override
    public ProofMastEntity findBykey(String key) {
        return leadsDao.findBykey(key);
    }

    @Override
    public ReasonMastEntity findByprimaryKey(String key) {
        return leadsDao.findByprimaryKey(key);
    }

    @Override
    public List<ReasonMastEntity> reasonList() {
        return leadsDao.reasonList();
    }

    @Override
    public String getCustomerName(String mobileNo) {
        return leadsDao.getCustomerName(mobileNo);
    }
}
