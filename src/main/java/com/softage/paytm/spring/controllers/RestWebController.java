package com.softage.paytm.spring.controllers;

import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by SS0085 on 01-02-2016.
 */

@RestController
@RequestMapping(value = "/REST")
public class RestWebController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PaytmDeviceService paytmDeviceService;
    @Autowired
    private LeadsService leadsService;
    @Autowired
    private AgentPaytmService agentPaytmService;
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private DataEntryService dataEntryService;
    @Autowired
    private PostCallingService postCallingService;
    @Autowired
    private ProofService proofService;
    @Autowired
    private PaytmMasterService paytmMasterService;

    @RequestMapping(value = "/getTest", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject test() {
        JSONObject jsonObject = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.add("hello");
        arr.add("Anil");
        jsonObject.put("msg", "this is JSON Testing");
        jsonObject.put("identification", arr);
        return jsonObject;
    }

    @RequestMapping(value = "/validateEmployee", method = {RequestMethod.GET, RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    public String validateEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            String empcode = request.getParameter("username");
            String pass = request.getParameter("password");
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(empcode);
            if (emplogintableEntity != null) {
                String password = emplogintableEntity.getEmpPassword();
                if (pass.equals(password)) {
                    return "true";
                }
                return "false";
            } else {
                return "false";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping(value = "/EmployeeNumber", method = {RequestMethod.GET, RequestMethod.POST})
    public String employeeNumber(@RequestParam(value = "username") String userName) {
        String phoneNumber="0";
        try {
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(userName);
            if (emplogintableEntity != null) {
                phoneNumber = emplogintableEntity.getEmpPhone();

            } else {
                phoneNumber="0";
            }

        } catch (Exception e) {
            e.printStackTrace();
            phoneNumber="0";
        }
        return phoneNumber;
    }


    @RequestMapping(value = "/UpdateDeviceInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String UpdateDeviceInfo(HttpServletRequest request, HttpServletResponse response) {
        String msg = "0";
        try {
            String loginId = request.getParameter("loginid");
            String deviceId = request.getParameter("deviceId");
            String importby = request.getParameter("importby");
            PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = new PaytmdeviceidinfoEntity();
            paytmdeviceidinfoEntity.setDeviceId(deviceId);
            paytmdeviceidinfoEntity.setLoginId(loginId);
            paytmdeviceidinfoEntity.setImportBy(importby);
            paytmdeviceidinfoEntity.setImportDate(new Timestamp(new Date().getTime()));
            msg = paytmDeviceService.saveDevice(paytmdeviceidinfoEntity);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return msg;

    }

    @RequestMapping(value = "/AgentLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray agentLeads(HttpServletRequest request) {
        JSONArray array = new JSONArray();
        try {
            String agentCaode = request.getParameter("AgentCode");
            List<JSONObject> arrayList = leadsService.getAgentLeads(agentCaode);
            array.addAll(arrayList);


        } catch (Exception e) {
            logger.error("", e);
        }
        return array;

    }

    @RequestMapping(value = "/UpdateLeadStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateLeadStatus(HttpServletRequest request) {

        String agentCode = request.getParameter("AgentCode");
        String jobid = request.getParameter("Jobid");
        String response1 = request.getParameter("response");
        boolean response = Boolean.parseBoolean(response1);
        String result = leadsService.updateLeadStatus(agentCode, jobid, response);

        return result;
    }

    @RequestMapping(value = "/AgentAcceptedLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray agentAcceptedLeads(@RequestParam(value = "AgentCode") String agentCode) {
        JSONArray array = new JSONArray();
        List<JSONObject> listjson = leadsService.agentAcceptedLeads(agentCode);
        array.addAll(listjson);
        return array;

    }

    @RequestMapping(value = "/AgentRejectedLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray agentRejectedLeads(@RequestParam(value = "AgentCode") String agentCode) {
        JSONArray array = new JSONArray();
        List<JSONObject> listjson = leadsService.agentRejectedLeads(agentCode);
        array.addAll(listjson);
        return array;
    }

    @RequestMapping(value = "/AcceptedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String acceptedEntry(HttpServletRequest request) {
        AllocationMastEntity allocationMastEntity = null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        String result = "98833";
        PaytmMastEntity paytmMastEntity=null;
        String address="";
        String state="";
        String emailId="";
        String city="";
        String pincode="";


        try {
            String phoneNumber = request.getParameter("custPhone");
            if (phoneNumber!=null){
                paytmMastEntity =paytmMasterService.getPaytmMaster(phoneNumber);
                if (paytmMastEntity!=null){
                    address=paytmMastEntity.getAddressStreet1();
                    city=paytmMastEntity.getCity();
                    state=paytmMastEntity.getState();
                    pincode=paytmMastEntity.getPincode();
                    emailId=paytmMastEntity.getEmail();
                }
            }
            String custName = request.getParameter("custName");
            String dob = request.getParameter("dob");
            String custPOICode = request.getParameter("custPOICode");
            String custPOINumber = request.getParameter("custPOINumber");
            String custPOACode = request.getParameter("custPOACode");
            String custPOANumber = request.getParameter("custPOANumber");
            String agentCode = request.getParameter("agentCode");
            String gender = request.getParameter("gender");
            String jobid = request.getParameter("jobid");
            String remarksCode = request.getParameter("remarksCode");
            String customerId = request.getParameter("customerId");
            if (!StringUtils.isEmpty(agentCode)) {
                paytmagententryEntity = agentPaytmService.findByPrimaryKey(agentCode);
            }
            if (!StringUtils.isEmpty(jobid)) {
                allocationMastEntity = allocationService.findByPrimaryKey(Integer.parseInt(jobid));
            }

            if (custPOICode != null) {
                proofMastEntityPOICode = leadsService.findBykey(custPOICode);
            }
            if (custPOACode != null) {
                proofMastEntityPOACode = leadsService.findBykey(custPOACode);
            }
            ReasonMastEntity reasonMastEntity = leadsService.findByprimaryKey("ACC");
            DataentryEntity dataentryEntity = new DataentryEntity();
            //  dataentryEntity.setReasonMastByRejectionResion(reasonMastEntity);
            dataentryEntity.setRejectionResion("ACC");
            dataentryEntity.setProofMastByCcusPOACode(proofMastEntityPOACode);
            //  dataentryEntity.setCusPOACode(custPOACode);
            dataentryEntity.setProofMastByCusPoiCode(proofMastEntityPOICode);
            // dataentryEntity.setCusPoiCode(custPOICode);
            dataentryEntity.setCusAdd(address);
            dataentryEntity.setCusArea("");
            dataentryEntity.setCusCity(city);
            dataentryEntity.setCusDob(dob);
            dataentryEntity.setCusEmailId(emailId);
            dataentryEntity.setCusName(custName);
            dataentryEntity.setCusPincode(pincode);
            dataentryEntity.setCusPoaNumber(custPOANumber);
            dataentryEntity.setCusPoiNumber(custPOINumber);
            dataentryEntity.setCusState(state);
            dataentryEntity.setCustomerPhone(phoneNumber);
            dataentryEntity.setDateOfCollection(new Timestamp(new Date().getTime()));
            dataentryEntity.setDocStatus("");
            dataentryEntity.setEntryBy(agentCode);
            dataentryEntity.setEntryDateTime(new Timestamp(new Date().getTime()));
            dataentryEntity.setGender(gender);
            dataentryEntity.setCustomerId(customerId);
            dataentryEntity.setAllocationMastByAllocationId(allocationMastEntity);
            dataentryEntity.setPaytmagententryByAgentCode(paytmagententryEntity);
            result = dataEntryService.saveDataEntry(dataentryEntity);
            if ("done".equals(result)) {
                updateRemarkStatus(agentCode, jobid, remarksCode, "Y");
                result="0";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/RejectedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String rejectedEntry(HttpServletRequest request){

          String  agentCode= request.getParameter("AgentCode");
          String  jobid= request.getParameter("Jobid");
          String  statusCode= request.getParameter("statusCode");
          String result=updateRemarkStatus(agentCode,jobid,statusCode,"N");
          return result;
    }

    @RequestMapping(value = "/ValidateCustomerId", method = {RequestMethod.GET, RequestMethod.POST})
    public String validateCustomer(@RequestParam(value = "jobid") String jobid,
                                   @RequestParam(value = "customerid") String customerid){
        String customerPhone=null;
        String result="false";

        try{
          AllocationMastEntity allocationMastEntity = allocationService.findByPrimaryKey(Integer.parseInt(jobid));
            if(allocationMastEntity!=null){
                    customerPhone=allocationMastEntity.getCustomerPhone();
                if (customerPhone!=null){
                    PaytmMastEntity paytmMastData  = paytmMasterService.getPaytmMaster(customerPhone);
                    if(paytmMastData!=null){
                             if (paytmMastData.getCustomerId().equals(customerid)){
                                 result="true";
                             }
                    }
                }
            } else {
                result="false";
            }

        }catch (Exception e){
            logger.error("",e);
              result="false";

        }

        return result;
    }


    @RequestMapping(value = "/RasonsList", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray reasonList() {
        JSONArray array = new JSONArray();
        List<ReasonMastEntity> reasonMastEntities = leadsService.reasonList();
        for (ReasonMastEntity reasonMastEntity : reasonMastEntities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Code", reasonMastEntity.getReasonCode());
            jsonObject.put("Text", reasonMastEntity.getReasonText());
            array.add(jsonObject);
        }
        return array;
    }

    @RequestMapping(value = "/RemarkList", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray remarkList() {
        JSONArray array = new JSONArray();
        List<RemarkMastEntity> remarkMastEntityList = postCallingService.remarkList();
        for (RemarkMastEntity remarkMastEntity : remarkMastEntityList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Code", remarkMastEntity.getRemarksCode());
            jsonObject.put("Text", remarkMastEntity.getRemarksText());
            array.add(jsonObject);
        }
        return array;
    }

    @RequestMapping(value = "/KYCDone", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray kycDone(@RequestParam(value = "AgentCode") String agentCode) {
        JSONArray array = new JSONArray();
        List<JSONObject> listjson = leadsService.kycDone(agentCode);
        array.addAll(listjson);
        return array;

    }

    @RequestMapping(value = "/KYCNotDone", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray kycNotDone(@RequestParam(value = "AgentCode") String agentCode) {

        JSONArray array = new JSONArray();
        List<JSONObject> listjson = leadsService.kycNotDone(agentCode);
        array.addAll(listjson);
        return array;
    }

    @RequestMapping(value = "/IdentityProofTypes", method = {RequestMethod.GET, RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray identifyProofType(){
        String applicable="I";
        JSONObject jsonObject=new JSONObject();
        List<ProofMastEntity>  proofMastEntities=proofService.getProofMastEntity(applicable);
        JSONArray array=new JSONArray();
        for (ProofMastEntity proofMastEntity:proofMastEntities){
            JSONObject json=new JSONObject();
            json.put("Applicable",proofMastEntity.getApplicable());
            json.put("Code",proofMastEntity.getIdCode());
            json.put("Text",proofMastEntity.getIdText());
            array.add(json);
        }
        //  jsonObject.put("ArrayOfProofType",array);
          return  array;
    }
    @RequestMapping(value = "/AddressProofTypes", method = {RequestMethod.GET, RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray addressProofType(){
        String applicable="A";
        JSONObject jsonObject=new JSONObject();
        JSONArray array=new JSONArray();
        List<ProofMastEntity>  proofMastEntities =proofService.getProofMastEntity(applicable);
        for (ProofMastEntity proofMastEntity:proofMastEntities){
            JSONObject json=new JSONObject();
            json.put("Applicable",proofMastEntity.getApplicable());
            json.put("Code",proofMastEntity.getIdCode());
            json.put("Text",proofMastEntity.getIdText());
            array.add(json);
        }
        return  array;
    }

    private String updateRemarkStatus(String AgentCode, String JobId, String remarksCode, String kycStatus) {
        String result = "";
        try {
            AllocationMastEntity allocationMastEntity = allocationService.findById(AgentCode, JobId);
            RemarkMastEntity remarkMastEntity = postCallingService.getByPrimaryCode(remarksCode);
            if (allocationMastEntity != null) {
                allocationMastEntity.setRemarkMastByRemarksCode(remarkMastEntity);
                allocationMastEntity.setKycCollected(kycStatus);
                allocationMastEntity.setVisitDateTime(new Timestamp(new Date().getTime()));
                String status = allocationService.updateAllocationMastEntity(allocationMastEntity);
                if ("done".equals(status)){
                    result="1";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}