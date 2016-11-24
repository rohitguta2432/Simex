package com.softage.paytm.service.imp;

import com.softage.paytm.dao.AgentPaytmDao;
import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.dao.PostCallingDao;
import com.softage.paytm.dao.UserDao;
import com.softage.paytm.models.*;
import com.softage.paytm.service.AgentPaytmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CircleMastDao circleMastDao;

    @Override
    public PaytmagententryEntity findByPrimaryKey(String agentCode) {
        return agentPaytmDao.findByPrimaryKey(agentCode);
    }

    @Override
    public PaytmagententryEntity findByPincode(String pincode) {
        return agentPaytmDao.findByPincode(pincode);
    }

    @Override
    public String saveAgentPinMaster1(PaytmagententryEntity paytmagententryEntity) {
        String result = savePinmaster(paytmagententryEntity);
        return result;
    }

    @Override
    public String saveAgentLocation(String agentCode, String CustomerNumber, String location,double lati,double longi) {
        return agentPaytmDao.saveAgentLocation(agentCode,CustomerNumber,location,lati,longi);
    }

    @Override
    public String saveBulkAgent(List<Map<String, String>> agentList,int circlecode) {
        for (Map<String, String> map : agentList) {

            try {

                String agentName = map.get("agentName");
                String agentCode = map.get("agentCode");
                String mobileNo = map.get("mobileNo");
                String circle = map.get("circle");
                String pincode = map.get("pincode");
                String spokeCode = map.get("spoke");
                String avalTime = map.get("avialableSlot");
                String importBy = map.get("importBy");
                CircleMastEntity circleMastEntity = circleMastDao.findByPrimaryKey(circle);
                if(circleMastEntity==null){
                    circleMastEntity = circleMastDao.findByPrimaryKey(circlecode);
                }

                PaytmagententryEntity paytmagententryEntity = new PaytmagententryEntity();
                paytmagententryEntity.setAfullname(agentName);
                paytmagententryEntity.setAcode(agentCode);
                paytmagententryEntity.setEmpcode(agentCode);
                paytmagententryEntity.setAphone(mobileNo);
                paytmagententryEntity.setAspokecode(spokeCode);
                paytmagententryEntity.setAavailslot(avalTime);
                paytmagententryEntity.setApincode(pincode);
                paytmagententryEntity.setMulitplePin("Y");
                paytmagententryEntity.setAemailId("");
                paytmagententryEntity.setImportby(importBy);
                paytmagententryEntity.setImportdate(new Timestamp(new Date().getTime()));
                int pincode1 = Integer.parseInt(pincode);

                String result = saveAgent(paytmagententryEntity, circleMastEntity);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    public List<String> getAgentPinMastList(String pincode) {
        return agentPaytmDao.getAgentPinMastList(pincode);
    }

    @Override
    public String updatePassword(EmplogintableEntity emplogintableEntity,String password) {
        String result = agentPaytmDao.updateEmployee(emplogintableEntity);
        if ("err".equalsIgnoreCase(result)) {
            for (int i = 0; i <= 5; i++) {
                result = agentPaytmDao.updateEmployee(emplogintableEntity);
                if ("done".equalsIgnoreCase(result)) {
                    break;
                }
            }

        }

        String text = "Dear Employee Your new Password " + password;
        if ("done".equalsIgnoreCase(result) && password!=null) {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(2);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(13);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(emplogintableEntity.getEmpPhone());
            smsSendlogEntity.setReceiverId(emplogintableEntity.getEmpCode());
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

    @Override
    public String saveAgent(PaytmagententryEntity paytmagententryEntity,CircleMastEntity circleMastEntity) {
        EmplogintableEntity emplogintableEntity=null;

        String msg = agentPaytmDao.saveAgent(paytmagententryEntity);
        if(msg.equalsIgnoreCase("err"))
        {
            for(int i=1; i<=5; i++) {
                msg = agentPaytmDao.saveAgent(paytmagententryEntity);
                if ("done".equalsIgnoreCase(msg)) {
                    break;
                }
            }
        }



        AgentpinmasterEntity agentpinmasterEntity=agentPaytmDao.getByPinandAcode(paytmagententryEntity.getApincode(),paytmagententryEntity.getAcode());
        String result=null;
        if(agentpinmasterEntity==null){
            result = savePinmaster(paytmagententryEntity);
        }
        if ("done".equalsIgnoreCase(msg)) {
           emplogintableEntity=userDao.getUserByEmpNumber(paytmagententryEntity.getAphone());
            if ("done".equalsIgnoreCase(result) && emplogintableEntity==null) {
                String msg1 = saveUesr(paytmagententryEntity,circleMastEntity);
            }

        }
        return msg;
    }

      @Override
      public String saveEmployee(EmplogintableEntity emplogintableEntity,String password) {

        String result = agentPaytmDao.saveEmployee(emplogintableEntity);
        if ("err".equalsIgnoreCase(result)) {
            for (int i = 0; i <= 5; i++) {
                result = agentPaytmDao.saveEmployee(emplogintableEntity);
                if ("done".equalsIgnoreCase(result)) {
                    break;
                }
            }

        }

        String text = "Dear Employee you are Successfully Registered in Softage ,Your Credential   Username "
                + emplogintableEntity.getEmpCode() + " Password " + password;
        if ("done".equalsIgnoreCase(result)) {
            ReceiverMastEntity receiverMastEntity = postCallingDao.getRecivedByCode(2);
            ProcessMastEntity processMastEntity = postCallingDao.getProcessByCode(13);
            SmsSendlogEntity smsSendlogEntity = new SmsSendlogEntity();
            smsSendlogEntity.setMobileNumber(emplogintableEntity.getEmpPhone());
            smsSendlogEntity.setReceiverId(emplogintableEntity.getEmpCode());
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

    public String savePinmaster(PaytmagententryEntity paytmagententryEntity) {

        AgentpinmasterEntity agentpinmasterEntity = new AgentpinmasterEntity();
        agentpinmasterEntity.setApmAPincode(paytmagententryEntity.getApincode());
        agentpinmasterEntity.setApmImportDate(paytmagententryEntity.getImportdate());
        agentpinmasterEntity.setPaytmagententryByApmAcode(paytmagententryEntity);
        String result="";
         AgentpinmasterEntity agentpinmasterEntity1 =agentPaytmDao.getByPinandAcode(paytmagententryEntity.getApincode(),paytmagententryEntity.getAcode());
        if(agentpinmasterEntity1==null){
             result = agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
            if ("err".equalsIgnoreCase(result)) {
                for (int i = 0; i <=7; i++) {
                    result = agentPaytmDao.saveAgentPinMaster(agentpinmasterEntity);
                    if ("done".equalsIgnoreCase(result)) {
                        break;
                    }
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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(password);

        Date expireDate = new Date();

        System.out.println("Current Date   " + expireDate);
        long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
        expireDate.setTime(expireDate.getTime() + expireTime);


        emplogintableEntity.setEmpPassword(hashedPassword);
        emplogintableEntity.setCircleMastByCirCode(circleMastEntity);
        emplogintableEntity.setRoleCode("A1");
        emplogintableEntity.setEmpStatus(1);
        emplogintableEntity.setImportBy(paytmagententryEntity.getImportby());
        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
        emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));

        String result = agentPaytmDao.saveEmployee(emplogintableEntity);
        if ("err".equalsIgnoreCase(result)) {
            for (int i = 0; i <= 5; i++) {
                result = agentPaytmDao.saveEmployee(emplogintableEntity);
                if ("done".equalsIgnoreCase(result)) {
                    break;
                }
            }

        }

        String text = "Dear Agent you are Successfully Registered in Softage ,Your Credential to use Mobile App  Username "
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
