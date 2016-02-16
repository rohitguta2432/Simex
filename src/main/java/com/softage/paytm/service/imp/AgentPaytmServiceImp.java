package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.dao.UserDao;
import com.softage.paytm.models.*;
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

    @Autowired
    private PostCallingDao postCallingDao;
    @Autowired
    private UserDao userDao;

    @Override
    public PaytmagententryEntity findByPrimaryKey(String agentCode) {
        return agentPaytmDao.findByPrimaryKey(agentCode);
    }

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity,CircleMastEntity circleMastEntity) {
        EmplogintableEntity emplogintableEntity=null;

        String msg = agentPaytmDao.saveAgent(paytmagententryEntity);
        if ("done".equalsIgnoreCase(msg)) {
            String result = savePinmaster(paytmagententryEntity);
            emplogintableEntity=userDao.getUserByEmpNumber(paytmagententryEntity.getAphone());
            if ("done".equalsIgnoreCase(result) && emplogintableEntity==null) {
                String msg1 = saveUesr(paytmagententryEntity,circleMastEntity);
            }

        }
        return msg;
    }

    public String savePinmaster(PaytmagententryEntity paytmagententryEntity) {

        AgentpinmasterEntity agentpinmasterEntity = new AgentpinmasterEntity();
        agentpinmasterEntity.setApmAPincode(paytmagententryEntity.getApincode());
        agentpinmasterEntity.setApmImportDate(paytmagententryEntity.getImportdate());
        agentpinmasterEntity.setPaytmagententryByApmAcode(paytmagententryEntity);

        String result = agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
        if ("err".equalsIgnoreCase(result)) {
            for (int i = 0; i <= 5; i++) {
                result = agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
                if ("done".equalsIgnoreCase(result)) {
                    break;
                }
            }

        }

        return result;
    }

    public String saveUesr(PaytmagententryEntity paytmagententryEntity,CircleMastEntity circleMastEntity) {
        String password = null;
        EmplogintableEntity emplogintableEntity = new EmplogintableEntity();
        emplogintableEntity.setEmpCode(paytmagententryEntity.getAcode());
        emplogintableEntity.setEmpName(paytmagententryEntity.getAfullname());
        emplogintableEntity.setEmpPhone(paytmagententryEntity.getAphone());
        password = paytmagententryEntity.getAcode().substring(0, 4) + paytmagententryEntity.getAphone().substring(0, 4);
        emplogintableEntity.setEmpPassword(password);
        emplogintableEntity.setCircleMastByCirCode(circleMastEntity);
        emplogintableEntity.setRoleCode("A1");
        emplogintableEntity.setEmpStatus(true);
        emplogintableEntity.setImportBy(paytmagententryEntity.getImportby());
        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

        String result = agentPaytmDao.saveEmployee(emplogintableEntity);

        String text = "Dear Agent you are Successfully Registered in Softage ,Your Credetial to use Mobile App  UserName "
                + paytmagententryEntity.getAcode() + " Password " + password;
        if ("done".equalsIgnoreCase(result)) {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(2);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(13);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(paytmagententryEntity.getAphone());
            smsSendlogEntity.setReceiverId(paytmagententryEntity.getAcode());
            smsSendlogEntity.setSmsText(text);
            smsSendlogEntity.setSmsDelivered("N");
            smsSendlogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setImportDate(new Timestamp(new Date().getTime()));
            smsSendlogEntity.setProcessMastByProcessCode(processMastEntity);
            smsSendlogEntity.setReceiverMastByReceiverCode(receiverMastEntity);
            postCallingDao.saveSmsSendEntity(smsSendlogEntity);

        }


        return result;
    }

}
