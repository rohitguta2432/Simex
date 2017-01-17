package com.softage.paytm.spring.controllers;

import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private FtpDetailsService ftpDetailsService;
    @Autowired
    private QcStatusService qcservices;

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

    @RequestMapping(value = "/validateEmployee", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
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

   /* @RequestMapping(value = "/EmployeeNumber", method = {RequestMethod.GET, RequestMethod.POST})
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
    }*/


    @RequestMapping(value = "/UpdateDeviceInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String UpdateDeviceInfo(HttpServletRequest request, HttpServletResponse response) {
        String msg = "0";
        PaytmdeviceidinfoEntity paytmdeviceidinfoEntity1 = null;
        try {
            String loginId = request.getParameter("loginid");
            String deviceId = request.getParameter("deviceId");
            String importby = request.getParameter("importby");
            PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = new PaytmdeviceidinfoEntity();
            paytmdeviceidinfoEntity.setDeviceId(deviceId);
            paytmdeviceidinfoEntity.setLoginId(loginId);
            paytmdeviceidinfoEntity.setImportBy(importby);
            paytmdeviceidinfoEntity.setImportDate(new Timestamp(new Date().getTime()));
            paytmdeviceidinfoEntity1 = paytmDeviceService.getByloginId(loginId);
            if (paytmdeviceidinfoEntity1 == null) {
                msg = paytmDeviceService.saveDevice(paytmdeviceidinfoEntity);
            } else {
                paytmdeviceidinfoEntity1.setDeviceId(deviceId);
                paytmdeviceidinfoEntity1.setImportBy(importby);
                msg = paytmDeviceService.updateDevice(paytmdeviceidinfoEntity1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return msg;

    }

    @RequestMapping(value = "/AgentLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject agentLeads(HttpServletRequest request) {
        int timedeff = 1;
        JSONArray array = new JSONArray();
        JSONObject leadsObject = new JSONObject();
        String cuurentDate = null;
        List<JSONObject> arrayList = new ArrayList();
        try {
            String agentCode = request.getParameter("AgentCode");
            String leaddate = request.getParameter("leaddate");
            arrayList = leadsService.getAgentLeads(agentCode, timedeff, leaddate);
            array.addAll(arrayList);

        } catch (Exception e) {
            logger.error("", e);
        }
        leadsObject.put("leads", array);
        leadsObject.put("timediff", 1);
        leadsObject.put("starttime", 9);
        leadsObject.put("endtime", 18);
        return leadsObject;

    }


    @RequestMapping(value = "/agentNewLeads", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public JSONArray agentNewLeads(HttpServletRequest request) {
        int timedeff = 1;
        JSONArray array = new JSONArray();
        JSONObject leadsObject = new JSONObject();
        String cuurentDate = null;
        List<JSONObject> arrayList = new ArrayList();
        try {
            String agentCode = request.getParameter("AgentCode");
            String leaddate = request.getParameter("leaddate");
            arrayList = leadsService.getAgentLeads(agentCode, timedeff, leaddate);
            array.addAll(arrayList);

        } catch (Exception e) {
            logger.error("", e);
        }
     /*   leadsObject.put("leads",array);
        leadsObject.put("timediff",1);
        leadsObject.put("starttime",9);
        leadsObject.put("endtime",18);*/
        return array;

    }


    @RequestMapping(value = "/UpdateLeadStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateLeadStatus(HttpServletRequest request) {
        String result = null;
        String agentCode = request.getParameter("AgentCode");
        String jobid = request.getParameter("Jobid");
        String response1 = request.getParameter("response");
        boolean response = Boolean.parseBoolean(response1);


        for (int i = 1; i <= 5; i++) {

            result = leadsService.updateLeadStatus(agentCode, jobid, response);
            if (result.equalsIgnoreCase("1")) {
                break;
            }
        }

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
    @RequestMapping(value = "/getagentLocation", method = {RequestMethod.GET, RequestMethod.POST})
    public String getagentLocation(HttpServletRequest request) {
        JSONArray array = new JSONArray();

        String agentCode = request.getParameter("agentcode");
        String customerNumber = request.getParameter("customerNumber");
        String location = request.getParameter("location");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        double lati = Double.parseDouble(latitude);
        double longi = Double.parseDouble(longitude);
        String result = agentPaytmService.saveAgentLocation(agentCode, customerNumber, location, lati, longi);
        return result;
    }
    @RequestMapping(value = "/AcceptedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String acceptedEntry(HttpServletRequest request) {
        AllocationMastEntity allocationMastEntity = null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        PaytmMastEntity paytmMastData=null;
        PaytmcustomerDataEntity paytmcustomerDataEntity=null;
        SpokeMastEntity spokeMastEntity=null;
        String result = null;

        PaytmMastEntity paytmMastEntity = null;
        TblScan tblScan=null;
        String address = "";
        String state = "";
        String emailId = "";
        String city = "";
        String pincode = "";
        int custid=0;
        CircleMastEntity circleMastEntity = null;
        String spokecode="";
        int auditstatus=0;
        int circlecode=0;
        try {
            String customerid = request.getParameter("customerId");
            int cust_uid=Integer.parseInt(customerid);

            String phoneNumber = request.getParameter("custPhone");
            if (customerid != null) {
                paytmMastData = paytmMasterService.getPaytmmasterServiceDate(cust_uid);
                custid=paytmMastData.getCust_uid();
                address = paytmMastData.getAddress();
                city = paytmMastData.getCity();
                state = paytmMastData.getState();
                pincode = paytmMastData.getPincode();
                emailId = paytmMastData.getEmail();
                circlecode=paytmMastData.getCirCode();
                circleMastEntity=paytmMastData.getCircleMastByCirCode();

            }
            spokeMastEntity=paytmMasterService.spokeMastEntity("DELCIR001");
            paytmcustomerDataEntity =paytmMasterService.getPaytmCustomerData(cust_uid);

            String custName = request.getParameter("custName");
            String custPOICode = request.getParameter("custPOICode"); // not required
            //String custPOINumber = request.getParameter("custPOINumber"); // not required
            String custPOACode = request.getParameter("custPOACode");  // not requied
           // String custPOANumber = request.getParameter("custPOANumber"); // not reqired
            String agentCode = request.getParameter("agentCode");
           // String gender = request.getParameter("gender");     // not requied
            String jobid = request.getParameter("jobid");
            int jobID=Integer.parseInt(jobid);
            String remarksCode = request.getParameter("remarksCode");

            String coStatus = request.getParameter("subscriberType");
            String Simno = request.getParameter("simno");
            String folderName=request.getParameter("folderName");
            String pageCount=request.getParameter("pageCount");
            int pages=Integer.parseInt(pageCount);

            //checklist data
            String srf=request.getParameter("SRF");
            String poi=request.getParameter("POI");
            String poa=request.getParameter("POA");
            String originalpoi=request.getParameter("OrigPOI");
            String originalpoa=request.getParameter("OrigPOA");
            String originalphoto=request.getParameter("OrigPhoto");
            String srfpc=request.getParameter("SRFPC");
            int originalsrfpc=Integer.parseInt(srfpc);
            String poipc=request.getParameter("POIPC");
            int ipoipc=Integer.parseInt(poipc);
            String poapc=request.getParameter("POAPC");
            int ipoapc=Integer.parseInt(poapc);
            String opoipc=request.getParameter("OPOIPC");
            int originalopipc=Integer.parseInt(opoipc);
            String opoapc=request.getParameter("OPOAPC");
            int originalopoapc=Integer.parseInt(opoapc);
            String photoPC=request.getParameter("PhotoPC");
            int originalphotoPC=Integer.parseInt(photoPC);
           if (!StringUtils.isEmpty(agentCode)) {
                paytmagententryEntity = agentPaytmService.findByPrimaryKey(agentCode);
            }
            if (!StringUtils.isEmpty(jobid)) {
                allocationMastEntity = allocationService.findByPrimaryKey(jobID);

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
            dataentryEntity.setReasonMastByRejectionResion(reasonMastEntity);
          //  dataentryEntity.setRejectionResion("ACC");
            dataentryEntity.setProofMastByCcusPOACode(proofMastEntityPOACode);
             dataentryEntity.setCusPOACode(custPOACode);
            dataentryEntity.setProofMastByCusPoiCode(proofMastEntityPOICode);
            dataentryEntity.setCusPoiCode(custPOICode);
            dataentryEntity.setCusAdd(address);
            dataentryEntity.setCusArea("");
            dataentryEntity.setCusCity(city);
            dataentryEntity.setCusEmailId("");
            dataentryEntity.setCusName(custName);
            dataentryEntity.setCusPincode(pincode);
            //dataentryEntity.setCusPoaNumber(custPOANumber);
           // dataentryEntity.setCusPoiNumber(custPOINumber);
            dataentryEntity.setCusState(state);
            dataentryEntity.setCustomerPhone(phoneNumber);
            dataentryEntity.setDateOfCollection(new Timestamp(new Date().getTime()));
            dataentryEntity.setDocStatus(coStatus);
            dataentryEntity.setEntryBy(agentCode);
            dataentryEntity.setEntryDateTime(new Timestamp(new Date().getTime()));
            //dataentryEntity.setGender(gender);
            dataentryEntity.setCustomerId(cust_uid);
            dataentryEntity.setSim_no(Simno);
            dataentryEntity.setFolder_name(folderName);
            dataentryEntity.setPage_count(pages);
            dataentryEntity.setAllocationMastByAllocationId(allocationMastEntity);
            dataentryEntity.setPaytmagententryByAgentCode(paytmagententryEntity);
            result = dataEntryService.saveDataEntry(dataentryEntity);

           if(!result.equals("err")) {
               TblScan scanresult = new TblScan();
               AuditStatusEntity auditStatusEntity = qcservices.getAuditStatusEntity(1);
               scanresult.setPaytmcustomerDataEntity(paytmcustomerDataEntity);
               scanresult.setAuditStatusEntity(auditStatusEntity);
               scanresult.setSimNo(Simno);
               scanresult.setCreatedBy("System");
               scanresult.setCreatedOn(new Timestamp(new Date().getTime()));
               scanresult.setCustomerNumber(phoneNumber);
               scanresult.setImagePath("");
               scanresult.setCircleMastEntity(circleMastEntity);
               scanresult.setPageNo(pages);
               scanresult.setSpokeMastEntity(spokeMastEntity);
               scanresult.setDataDate(new Timestamp(new Date().getTime()));


               result = qcservices.SaveScanimages(scanresult);

           }
            if(!result.equals("err")) {
                TblcustDocDetails tblcustDocDetails = new TblcustDocDetails();
                tblcustDocDetails.setCust_uid(cust_uid);
                tblcustDocDetails.setcPOA(poa);
                tblcustDocDetails.setcPOI(poi);
                tblcustDocDetails.setOrigPoi(originalpoi);
                tblcustDocDetails.setOrigPoa(originalpoa);
                tblcustDocDetails.setSRF(srf);
                tblcustDocDetails.setPhoto(originalphoto);
                tblcustDocDetails.setPoiPc(ipoipc);
                tblcustDocDetails.setPoaPc(ipoapc);
                tblcustDocDetails.setOrigpoiPc(originalopipc);
                tblcustDocDetails.setOrigpoaPc(originalopoapc);
                tblcustDocDetails.setSrfPc(originalsrfpc);
                tblcustDocDetails.setPhotoPc(originalphotoPC);
                result = qcservices.savetbldocdetails(tblcustDocDetails);
            }
            if ("done".equals(result)) {
                updateRemarkStatus(agentCode, jobid, remarksCode, "Y");
                //   allocationService.updateKycAllocation(agentCode,jobid,remarksCode,"Y");
                result = "done";

            }
            else
            {
                result = "Not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/RejectedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String rejectedEntry(HttpServletRequest request) {

        TblScan tblScan=null;
        String result=null;
        String address=null;
        String city=null;
        String state=null;
        String pincode=null;
        String emailId=null;
        int circlecode=0;
        AllocationMastEntity allocationMastEntity=null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        PaytmMastEntity paytmMastData=null;
        String phonenumber=null;
        int pages=0;
        int cust_uid=0;
        int originalsrfpc=0;
        int ipoipc=0;
        int ipoapc=0;
        int originalopipc=0;
        int originalopoapc=0;
        int originalphotoPC=0;

        String agentCode = request.getParameter("AgentCode");
        String jobid = request.getParameter("Jobid");
        int jobID=Integer.parseInt(jobid);
        String statusCode = request.getParameter("statusCode");
        String custName = request.getParameter("custName");
        String custPOICode = request.getParameter("custPOICode"); // not required
        //String custPOINumber = request.getParameter("custPOINumber"); // not required
        String custPOACode = request.getParameter("custPOACode");  // not requied
        String remarksCode = request.getParameter("remarksCode");
        String coStatus = request.getParameter("subscriberType");
        String Simno = request.getParameter("simno");
        String folderName=request.getParameter("folderName");
        String pageCount=request.getParameter("pageCount");

        if(StringUtils.isNotBlank(pageCount)){
             pages=Integer.parseInt(pageCount);
        }

        String customerid=request.getParameter("customerId");
        /*if(StringUtils.isNotBlank(customerid)) {
            int cust_uids = Integer.parseInt(customerid);
        }*/
        String srf=request.getParameter("SRF");
        String poi=request.getParameter("POI");
        String poa=request.getParameter("POA");
        String originalpoi=request.getParameter("OrigPOI");
        String originalpoa=request.getParameter("OrigPOA");
        String originalphoto=request.getParameter("OrigPhoto");
        String srfpc=request.getParameter("SRFPC");
        if(StringUtils.isNotBlank(srfpc)) {
           originalsrfpc = Integer.parseInt(srfpc);
        }
        String poipc=request.getParameter("POIPC");
        if (StringUtils.isNotBlank(poipc)) {
           ipoipc = Integer.parseInt(poipc);
        }
        String poapc=request.getParameter("POAPC");
        if(StringUtils.isNotBlank(poapc)) {
            ipoapc = Integer.parseInt(poapc);
        }
        String opoipc=request.getParameter("OPOIPC");
        if(StringUtils.isNotBlank(opoipc)) {
             originalopipc = Integer.parseInt(opoipc);
        }
        String opoapc=request.getParameter("OPOAPC");
        if(StringUtils.isNotBlank(opoapc)) {
            originalopoapc = Integer.parseInt(opoapc);
        }
        String photoPC=request.getParameter("PhotoPC");
        if(StringUtils.isNotBlank(photoPC)) {
            originalphotoPC = Integer.parseInt(photoPC);
        }
        if (!StringUtils.isEmpty(agentCode)) {
            paytmagententryEntity = agentPaytmService.findByPrimaryKey(agentCode);
        }
        if (!StringUtils.isEmpty(jobid)) {
            allocationMastEntity = allocationService.findByPrimaryKey(jobID);
         cust_uid= allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();
        }
        if (custPOICode != null && StringUtils.isNotBlank(custPOICode)) {
            proofMastEntityPOICode = leadsService.findBykey(custPOICode);
        }else{
            proofMastEntityPOICode = leadsService.findBykey("8");
        }
        if (custPOACode != null && StringUtils.isNotBlank(custPOACode) ) {
            proofMastEntityPOACode = leadsService.findBykey(custPOACode);
        }else {
            proofMastEntityPOACode = leadsService.findBykey("8");
        }

        if (cust_uid != 0) {
            paytmMastData = paytmMasterService.getPaytmmasterServiceDate(cust_uid);
           /* custid = paytmMastData.getCust_uid();*/
            address = paytmMastData.getAddress();
            city = paytmMastData.getCity();
            state = paytmMastData.getState();
            pincode = paytmMastData.getPincode();
            emailId = paytmMastData.getEmail();
            circlecode = paytmMastData.getCirCode();
            phonenumber=paytmMastData.getCustomerPhone();
        }

        ReasonMastEntity reasonMastEntity = leadsService.findByprimaryKey("REJECT");
        DataentryEntity dataentryEntity=new DataentryEntity();
        dataentryEntity.setReasonMastByRejectionResion(reasonMastEntity);
        //  dataentryEntity.setRejectionResion("ACC");
        dataentryEntity.setProofMastByCcusPOACode(proofMastEntityPOACode);
        dataentryEntity.setProofMastByCusPoiCode(proofMastEntityPOICode);
        dataentryEntity.setCusAdd(address);
        dataentryEntity.setCusArea("");
        dataentryEntity.setCusCity(city);
        dataentryEntity.setCusEmailId("");
        dataentryEntity.setCusName(custName);
        dataentryEntity.setCusPincode(pincode);
        //dataentryEntity.setCusPoaNumber(custPOANumber);
        // dataentryEntity.setCusPoiNumber(custPOINumber);
        dataentryEntity.setCusState(state);
        dataentryEntity.setCustomerPhone(phonenumber);
        dataentryEntity.setDateOfCollection(new Timestamp(new Date().getTime()));
        dataentryEntity.setDocStatus(coStatus);
        dataentryEntity.setEntryBy(agentCode);
        dataentryEntity.setEntryDateTime(new Timestamp(new Date().getTime()));
        //dataentryEntity.setGender(gender);
        dataentryEntity.setCustomerId(cust_uid);
        dataentryEntity.setSim_no(Simno);
        dataentryEntity.setFolder_name(folderName);
        dataentryEntity.setPage_count(pages);
        dataentryEntity.setAllocationMastByAllocationId(allocationMastEntity);
        dataentryEntity.setPaytmagententryByAgentCode(paytmagententryEntity);
        result = dataEntryService.saveDataEntry(dataentryEntity);

        if(!result.equals("err")) {
            TblcustDocDetails tblcustDocDetails = new TblcustDocDetails();
            tblcustDocDetails.setCust_uid(cust_uid);
            tblcustDocDetails.setcPOA(poa);
            tblcustDocDetails.setcPOI(poi);
            tblcustDocDetails.setOrigPoi(originalpoi);
            tblcustDocDetails.setOrigPoa(originalpoa);
            tblcustDocDetails.setSRF(srf);
            tblcustDocDetails.setPhoto(originalphoto);
            tblcustDocDetails.setPoiPc(ipoipc);
            tblcustDocDetails.setPoaPc(ipoapc);
            tblcustDocDetails.setOrigpoiPc(originalopipc);
            tblcustDocDetails.setOrigpoaPc(originalopoapc);
            tblcustDocDetails.setSrfPc(originalsrfpc);
            tblcustDocDetails.setPhotoPc(originalphotoPC);

            result = qcservices.savetbldocdetails(tblcustDocDetails);
        }
        result = updateRemarkStatus(agentCode, jobid, statusCode, "N");
        //  String result=allocationService.updateKycAllocation(agentCode,jobid,statusCode,"N");
        return result;
    }

    @RequestMapping(value = "/ftpdetails", method = {RequestMethod.GET, RequestMethod.POST})
    public String ftpDetails(HttpServletRequest request) {
        String result = "";
        TblScan tblScan=null;
        int cust_uid=0;
        System.out.println("Service done ");
       // String customer_number = request.getParameter("customer_no");
        String image_path = request.getParameter("image_path");
        int page_number = Integer.parseInt(request.getParameter("page_number"));
        String created_on = request.getParameter("created_on");
        String created_by = request.getParameter("created_by");
        int qc_status = Integer.parseInt(request.getParameter("qc_status"));
        cust_uid=Integer.parseInt(request.getParameter("customer_no"));

        tblScan=qcservices.getUserScanDetails(cust_uid);
        String ImagePath=tblScan.getImagePath();
        int scanid=tblScan.getScanid();
        if(cust_uid==0)
          {
            result = "cust_uid is empty";
        } else if (ImagePath == "" || ImagePath.equals(null) || ImagePath == " " || ImagePath.equals("")) {
            tblScan.setImagePath(image_path);
            qcservices.updateTblSacnEntity(tblScan);
            UploadedImagesEntity imagesEntity=new UploadedImagesEntity();
            imagesEntity.setImagePath(image_path);
            imagesEntity.setScan_id(scanid);
            imagesEntity.setUploadedon(new Timestamp(new Date().getTime()));
            imagesEntity.setTblScan(tblScan);
            result=ftpDetailsService.saveImagesDeetails(imagesEntity);
            if (result.equals("done")) {
                result = "success";
                logger.info(" Result   " + result);
            } else {
                result = "Fail";
                logger.info(" Result   " + result);
            }
        } else {
            //result = ftpDetailsService.saveFTPData(customer_number, image_path, page_number, created_by, qc_status);
        UploadedImagesEntity imagesEntity=new UploadedImagesEntity();
            imagesEntity.setImagePath(image_path);
            imagesEntity.setScan_id(scanid);
            imagesEntity.setUploadedon(new Timestamp(new Date().getTime()));
            imagesEntity.setTblScan(tblScan);
            result=ftpDetailsService.saveImagesDeetails(imagesEntity);
            if (result.equals("done")) {
                result = "success";
                logger.info(" Result   " + result);
            } else {
                result = "Fail";
                logger.info(" Result   " + result);
            }
        }
        return result;
    }


    @RequestMapping(value = "/ValidateCustomerId", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    JSONObject validateCustomer(@RequestParam(value = "jobid") String jobid,
                                @RequestParam(value = "customerid") String customerid) {
        String customerPhone = null;
        int cust_uid=0;
        String result = "false";
        AllocationMastEntity allocationMastEntity=null;
        JSONObject jsonObject = new JSONObject();

        int custid=Integer.parseInt(customerid);
        int jobId=Integer.parseInt(jobid);
        try {

            if (customerid != null) {
                allocationMastEntity=paytmMasterService.getallocationMastEntity(customerid,jobId);

              cust_uid= allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();


                PaytmMastEntity paytmMastData = paytmMasterService.getPaytmmasterServiceDate(cust_uid);
                if (paytmMastData != null) {

                    jsonObject.put("simType", paytmMastData.getSimType());
                    jsonObject.put("customeName", paytmMastData.getUsername());
                    jsonObject.put("coStatus", paytmMastData.getCoStatus());
                    jsonObject.put("cust_uid",paytmMastData.getCust_uid());
                    jsonObject.put("circleCode",paytmMastData.getCirCode());
                    jsonObject.put("cust_uid",paytmMastData.getCust_uid());
                    jsonObject.put("Msg", "Data Found");
                    result = "true";

                } else {
                    jsonObject.put("Msg", "Data Not Found");
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            result = "false";
            jsonObject.put("Msg","Data Not Found");
        }

        return jsonObject;
    }


    @RequestMapping(value = "/ftpDocumentDetails", method = {RequestMethod.GET, RequestMethod.POST})
    public String ftpDocumentDetails(HttpServletRequest request) {


        return "done";
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
            if (!remarkMastEntity.getRemarksCode().equalsIgnoreCase("U")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Code", remarkMastEntity.getRemarksCode());
                jsonObject.put("Text", remarkMastEntity.getRemarksText());
                array.add(jsonObject);
            }
        }
        return array;
    }
    @RequestMapping(value = "/KYCDone", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray kycDone(@RequestParam(value = "AgentCode") String agentCode) {
        JSONArray array = new JSONArray();
JSONObject result=new JSONObject();
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

    @RequestMapping(value = "/IdentityProofTypes", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray identifyProofType() {
        String applicable = "I";
        JSONObject jsonObject = new JSONObject();
        List<ProofMastEntity> proofMastEntities = proofService.getProofMastEntity(applicable);
        JSONArray array = new JSONArray();
        for (ProofMastEntity proofMastEntity : proofMastEntities) {
            JSONObject json = new JSONObject();
            json.put("Applicable", proofMastEntity.getApplicable());
            json.put("Code", proofMastEntity.getIdCode());
            json.put("Text", proofMastEntity.getIdText());

            array.add(json);
        }
        //  jsonObject.put("ArrayOfProofType",array);
        return array;
    }

    @RequestMapping(value = "/AddressProofTypes", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray addressProofType() {
        String applicable = "A";
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        List<ProofMastEntity> proofMastEntities = proofService.getProofMastEntity(applicable);
        for (ProofMastEntity proofMastEntity : proofMastEntities) {
            JSONObject json = new JSONObject();
            json.put("Applicable", proofMastEntity.getApplicable());
            json.put("Code", proofMastEntity.getIdCode());
            json.put("Text", proofMastEntity.getIdText());
            array.add(json);
        }
        return array;
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
                if ("done".equals(status)) {
                    result = "done";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/EmployeeNumber", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject employeeNumber(@RequestParam(value = "username") String userName) {
        String phoneNumber = "0";
        int circleCode = 0;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String status;
        try {
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(userName);
            if (emplogintableEntity != null) {
                phoneNumber = emplogintableEntity.getEmpPhone();
                circleCode = emplogintableEntity.getCirCode();
                Integer intstatus = emplogintableEntity.getEmpStatus();
                status = intstatus.toString();
                jsonObject = userService.getEmpFtpDetails(circleCode);

                jsonObject1.put("phoneNumber", phoneNumber);
                jsonObject1.put("circleCode", circleCode);
                jsonObject1.put("FtpDetails", jsonObject);
                jsonObject1.put("status", status);

            } else {
                phoneNumber = "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
            phoneNumber = "0";
        }
        return jsonObject1;
    }

    @RequestMapping(value = "/updateStatusAgent", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateStausAgent(HttpServletRequest request) {

        String userName = "system";
        String agentNo = "";
        String result = null;
        HttpSession session = request.getSession(false);
        logger.info("update Agent >>>>> wait");
        try {
            String agentcode = request.getParameter("agentcode");
            String status = request.getParameter("status");

            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(agentcode);
            if (emplogintableEntity != null) {
                if (status.equals("active")) {
                    emplogintableEntity.setEmpStatus(1);
                    emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                }
                if (status.equalsIgnoreCase("deactive")) {
                    emplogintableEntity.setEmpStatus(0);
                    emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                }
                System.out.println(emplogintableEntity.getEmpStatus());
                result = userService.updateAgentStatus(emplogintableEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "err";
        }

        return result;
    }

    @RequestMapping(value = "/getCustomerValidation", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCustomerValidation(@RequestParam(value = "mobileNo") String mobileNo, @RequestParam(value = "agentcode") String agentcode) {
        JSONArray array = new JSONArray();
        String name = leadsService.getCustomerName(mobileNo);

        return name;

    }

    @RequestMapping(value = "/UpdatePassword", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject resetPassword(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);
        JSONObject jsonObject = new JSONObject();
        String result = null;
        try {
            String user = request.getParameter("userName");
            String password = request.getParameter("password");
            Date expireDate = new Date();
            System.out.println("Current Date   " + expireDate);
            long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
            expireDate.setTime(expireDate.getTime() + expireTime);

            System.out.println("Date After 30 Days  " + expireDate);


            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            emplogintableEntity.setEmpPassword(password);
            emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

            emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));
            result = agentPaytmService.updatePassword(emplogintableEntity, password);
            if (result.equalsIgnoreCase("done")) {
                result = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        jsonObject.put("status", result);

        return jsonObject;
    }
}