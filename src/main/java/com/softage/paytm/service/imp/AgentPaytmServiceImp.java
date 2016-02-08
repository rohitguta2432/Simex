package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.models.AgentpinmasterEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.service.AgentPaytmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by SS0085 on 30-12-2015.
 */
@Service
public class AgentPaytmServiceImp implements AgentPaytmService {


    @Autowired
    private AgentPaytmDao agentPaytmDao;
    @Override
    public PaytmagententryEntity findByPrimaryKey(String agentCode) {
        return agentPaytmDao.findByPrimaryKey(agentCode);
    }

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity) {

        String msg=agentPaytmDao.saveAgent(paytmagententryEntity);
        if ("done".equalsIgnoreCase(msg)){
             String result =savePinmaster(paytmagententryEntity);
             if ("done".equalsIgnoreCase(result)){
                String msg1 =saveUesr(paytmagententryEntity);
             }

        }
        return msg;
    }

public String savePinmaster(PaytmagententryEntity paytmagententryEntity){

    AgentpinmasterEntity agentpinmasterEntity=new AgentpinmasterEntity();
    agentpinmasterEntity.setApmAPincode(paytmagententryEntity.getApincode());
    agentpinmasterEntity.setApmImportDate(paytmagententryEntity.getImportdate());
    agentpinmasterEntity.setPaytmagententryByApmAcode(paytmagententryEntity);

     String result =agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
    if("err".equalsIgnoreCase(result)){
        for (int i=0; i<=5; i++) {
            result = agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
            if ("done".equalsIgnoreCase(result)){
                break;
            }
        }

    }

    return result;
}
    public String saveUesr(PaytmagententryEntity paytmagententryEntity){
        EmplogintableEntity emplogintableEntity=new EmplogintableEntity();
        emplogintableEntity.setEmpCode(paytmagententryEntity.getAcode());
        emplogintableEntity.setEmpName(paytmagententryEntity.getAfullname());
        emplogintableEntity.setEmpPhone(paytmagententryEntity.getAphone());
        emplogintableEntity.setEmpPassword(paytmagententryEntity.getAcode().substring(0,4)+paytmagententryEntity.getAphone().substring(0,4));
        emplogintableEntity.setRoleCode("A1");
        emplogintableEntity.setEmpStatus(true);
        emplogintableEntity.setImportBy("Afjal Ali");
        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

        String result= agentPaytmDao.saveEmployee(emplogintableEntity);


         return result;
    }

}
