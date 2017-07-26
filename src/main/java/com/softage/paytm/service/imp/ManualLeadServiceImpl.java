package com.softage.paytm.service.imp;

import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.service.ManualLeadService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
@Service
public class ManualLeadServiceImpl implements ManualLeadService {
 @Inject
 private ManualLeadDao manualLeadDao;


    @Override
    public List getAgentLeadDetails() {

        ArrayList<JSONObject> listArray = new ArrayList<JSONObject>();
        try{
            List<Object[]> result= manualLeadDao.getAgentDetails();
            for (Object[] s : result) {
                JSONObject listjson = new JSONObject();
                listjson.put("jobid", s[0]);
                listjson.put("PhoneNumber", s[1]);
                listjson.put("customerid", s[2]);
                listjson.put("agentCode", s[3]);
                listjson.put("agentname", s[4]);
                listjson.put("customeraddress", s[5]);
                listjson.put("customername", s[6]);

                String agentPincode = manualLeadDao.getAgentPincode(s[3].toString());
                listjson.put("agentPincode", agentPincode);

                //added by prakash
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy ");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
                String visitTime  = dateFormat.format(s[7]);
                String visitDate = dateFormat1.format(s[7]);
                String agentVisitTime = dateFormat2.format(s[7]);

                List<String> dateList = new ArrayList<String>();
                dateList.add(visitDate);
                for(int i = 1;i<3;i++){
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateFormat1.parse(visitDate));
                    c.add(Calendar.DATE, i);
                    String nextDay = dateFormat1.format(c.getTime());
                    dateList.add(nextDay);
                }
                listjson.put("allocatedTime", visitTime);
                listjson.put("allocationDate", dateList);
                listjson.put("allocationTime", agentVisitTime);

                listArray.add(listjson);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return listArray;
    }



    @Override
    public JSONObject getAgentCode(String allocatedTime,String agentPincode) {
        return manualLeadDao.GetAgentsCode(allocatedTime, agentPincode);
    }

    @Override
    public String updateAgentsBycustUid(int customerId, String AgentCode,String lastAgent,String userId,String newAllocationDateTime) {
        return manualLeadDao.updateAgentsByCustUid(customerId,AgentCode,lastAgent,userId, newAllocationDateTime);
    }

    @Override
    public JSONObject deAllocateLead(int custId) {

        String result = manualLeadDao.deAllocateLead(custId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultMessage",result);
        return resultJson;
    }


    @Override
    public JSONObject getAllocationDateList(int custCode) {

        JSONObject dateJson = new JSONObject();
        List dateList = manualLeadDao.getAllocationDateList(custCode);
        dateJson.put("dateList",dateList);
        return dateJson;
    }
}
