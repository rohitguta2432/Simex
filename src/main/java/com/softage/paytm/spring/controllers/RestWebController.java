package com.softage.paytm.spring.controllers;

import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import com.softage.paytm.util.AESEncryption;
import org.apache.commons.lang.ArrayUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


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

    @Autowired
    private SmsSendLogService smsSendLogService;

    @Autowired
    private AcceptedEntryService acceptedEntryService;

    @RequestMapping(value = "/getTest", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject test(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String password = "jkhjkhj#kghhjgjvhjfghfyfh^jfy==ju-@fyu";
        Cipher cipher = null;
        SecretKeySpec key = null;
        AlgorithmParameterSpec spec = null;
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};

        String cryptedText = "a9WX9xEuFVsNT1IcRcOGnw==";

        try {
            String plainText = "Hello World";
            // this for encrytion plainText
            SecretKey secKey = AESEncryption.getSecretEncryptionKey();
            byte[] cipherText = AESEncryption.encryptText(plainText, secKey);
            String encrytedText = AESEncryption.bytesToHex(cipherText);


            // this for decryption encyptedText
            byte[] encyptedByte = DatatypeConverter.parseHexBinary(encrytedText);
            String decryptedText = AESEncryption.decryptText(cipherText, secKey);


        } catch (Exception e) {
            e.printStackTrace();
        }




       /* try {
          //  cryptedText = request.getParameter("cryptedText");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            key = new SecretKeySpec(keyBytes, "AES");
            spec = new IvParameterSpec(iv);


            cipher.init(Cipher.DECRYPT_MODE, key, spec);
          //  byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
            Base64.Decoder decoder =Base64.getDecoder();
            byte[] bytes= decoder.decode(cryptedText);
            byte[] decrypted = cipher.doFinal(bytes);
            String decryptedText = new String(decrypted, "UTF-8");

            System.out.println(decryptedText);

        }catch (Exception e){
            logger.error("",e);
             e.printStackTrace();
        }*/


        EmplogintableEntity emplogintableEntity = userService.getUserByToken("[B@503d88d8");


        String s = request.getServletContext().getRealPath("/");

        System.out.println(request.getServletContext().getRealPath("/"));

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();

        JSONArray arr = new JSONArray();
        arr.add("hello");
        arr.add("Anil");
        jsonObject.put("msg", "this is JSON Testing");
        jsonObject.put("identification", arr);
        jsonObject.put("directoryPath", s);
        jsonObject.put("token", token);
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

    @RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);

        String result = "Invalid Password";
        JSONObject jsonObject = new JSONObject();
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        String dbUser = null;
        String token = null;
        Integer attamptCount = null;
        String leftStatus=null;
        try {
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //         String hashedPassword = passwordEncoder.encode(password);
            if (emplogintableEntity != null) {
                if (!"Y".equalsIgnoreCase(emplogintableEntity.getEmpLeftStatus()))
                {

                    if (emplogintableEntity.getRoleCode().equalsIgnoreCase("A1")) {


                        Timestamp expireDate = emplogintableEntity.getExpireDate();
                        attamptCount = emplogintableEntity.getAttamptCount();
                        if (attamptCount == null) {
                            attamptCount = 0;
                        }
                        Timestamp currentDate = new Timestamp(new Date().getTime());
                        Timestamp lockedDate = emplogintableEntity.getLockedDate();

                        if (attamptCount != 5) {


                            //    if(expireDate==null && currentDate.getTime()>expireDate.getTime()){
                            if (expireDate == null) {

                                Date currentdate1 = new Date();
                                long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
                                currentdate1.setTime(currentdate1.getTime() + expireTime);

                                emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setExpireDate(new Timestamp(currentdate1.getTime()));
                                //    emplogintableEntity.setEmpPassword(hashedPassword);


                                if (password.equals(emplogintableEntity.getEmpPassword())) {
                                    SecureRandom random = new SecureRandom();
                                    byte bytes[] = new byte[20];
                                    random.nextBytes(bytes);
                                    token = bytes.toString();
                                    dbUser = emplogintableEntity.getEmpCode();
                                    result = "success";
                                    emplogintableEntity.setToken(token);
                                    emplogintableEntity.setAttamptCount(0);
                                    emplogintableEntity.setEmpStatus(1);
                                    emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                }
                                agentPaytmService.updatePassword(emplogintableEntity, null);

                            } else if (currentDate.getTime() > expireDate.getTime()) {
                                result = "Password Expired";

                            } else if (password.equals(emplogintableEntity.getEmpPassword())) {
                                dbUser = emplogintableEntity.getEmpCode();
                                SecureRandom random = new SecureRandom();
                                byte bytes[] = new byte[20];
                                random.nextBytes(bytes);
                                token = bytes.toString();
                                emplogintableEntity.setToken(token);
                                emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setAttamptCount(0);
                                emplogintableEntity.setEmpStatus(1);
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                                result = "success";
                            } else {


                                if (attamptCount == 4) {
                                    Timestamp timestamp = new Timestamp(new Date().getTime());
                                    timestamp.setTime(timestamp.getTime() + 60 * 60 * 1000);
                                    emplogintableEntity.setLockedDate(timestamp);
                                    emplogintableEntity.setAttamptCount(attamptCount + 1);
                                } else {
                                    emplogintableEntity.setAttamptCount(attamptCount + 1);
                                }
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                            }
                        } else if (lockedDate.getTime() > currentDate.getTime()) {
                            result = "Your Account has locked! Try after 1 Hour";
                        } else {
                            if (expireDate == null) {

                                Date currentdate1 = new Date();
                                long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
                                currentdate1.setTime(currentdate1.getTime() + expireTime);

                                emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setExpireDate(new Timestamp(currentdate1.getTime()));
                                //    emplogintableEntity.setEmpPassword(hashedPassword);


                                if (password.equals(emplogintableEntity.getEmpPassword())) {
                                    SecureRandom random = new SecureRandom();
                                    byte bytes[] = new byte[20];
                                    random.nextBytes(bytes);
                                    token = bytes.toString();
                                    dbUser = emplogintableEntity.getEmpCode();
                                    result = "success";
                                    emplogintableEntity.setToken(token);
                                    emplogintableEntity.setAttamptCount(0);
                                    emplogintableEntity.setEmpStatus(1);
                                    emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                }
                                userService.updateAttaptStatus(emplogintableEntity);

                            } else if (currentDate.getTime() > expireDate.getTime()) {
                                result = "Password Expired";

                            } else if (password.equals(emplogintableEntity.getEmpPassword())) {
                                dbUser = emplogintableEntity.getEmpCode();
                                SecureRandom random = new SecureRandom();
                                byte bytes[] = new byte[20];
                                random.nextBytes(bytes);
                                token = bytes.toString();
                                emplogintableEntity.setToken(token);
                                emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setAttamptCount(0);
                                emplogintableEntity.setEmpStatus(1);
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                                result = "success";
                            } else {
                                int attamtCount = emplogintableEntity.getAttamptCount();
                                if (attamtCount == 5) {
                                    Timestamp timestamp = new Timestamp(new Date().getTime());
                                    timestamp.setTime(timestamp.getTime() + 60 * 60 * 1000);
                                    emplogintableEntity.setLockedDate(timestamp);
                                } else {
                                    emplogintableEntity.setAttamptCount(attamtCount + 1);
                                }
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                            }
                        }
                    } else {
                        result = "User not Authorised";
                    }
            }else {
                    result = "User Left ";
            }
            }
            else {
                result = "Invalid Credentials";
            }
        } catch (Exception e) {
            result = "error";
            logger.error("", e);
        }
        if (result.equalsIgnoreCase("success")) {
            jsonObject.put("token", token);
        }
        jsonObject.put("status", result);
        return jsonObject;
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
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
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
        } else {
            msg = "NOT AUTHENTICABLE USER";
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
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            Timestamp timestamp = emplogintableEntity.getLastLoginDate();
            long time = (long) timestamp.getTime() + 24 * 60 * 60 * 1000;
            timestamp.setTime(time);
            Timestamp timestamp1 = new Timestamp(new Date().getTime());

            if (timestamp.getTime() > timestamp1.getTime()) {
                try {
                    String agentCode = request.getParameter("AgentCode");
                    String leaddate = request.getParameter("leaddate");
                    Date today = new Date();
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(today.getTime());
                    arrayList = leadsService.getAgentLeads(agentCode, timedeff, currentDate);
                 //   arrayList = leadsService.getAgentLeads(agentCode, timedeff, "2017-02-10");
                    array.addAll(arrayList);

                } catch (Exception e) {
                    logger.error("", e);
                }
                leadsObject.put("authentication", "token valid");
                leadsObject.put("leads", array);
                leadsObject.put("timediff", 1);
                leadsObject.put("starttime", 9);
                leadsObject.put("endtime", 18);
            } else {
                leadsObject.put("authentication", "Password expired");
            }
        } else {
            leadsObject.put("authentication", "Authentication failed due to unauthorised login from another device");
        }

        return leadsObject;

    }


    @RequestMapping(value = "/agentNewLeads", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public JSONArray agentNewLeads(HttpServletRequest request) {
        int timedeff = 1;
        String msg = null;
        JSONArray array = new JSONArray();
        JSONObject leadsObject = new JSONObject();
        String cuurentDate = null;
        List<JSONObject> arrayList = new ArrayList();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            msg = "token valid";
            array.add(msg);
            try {

                String agentCode = request.getParameter("AgentCode");
                String leaddate = request.getParameter("leaddate");
                arrayList = leadsService.getAgentLeads(agentCode, timedeff, leaddate);

                array.addAll(arrayList);


            } catch (Exception e) {

                logger.error("", e);
            }
        } else {
            msg = "Authentication failed due to unauthorised login from another device";
            array.add(msg);
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
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {

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
        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }
        return result;
    }

    @RequestMapping(value = "/AgentAcceptedLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray agentAcceptedLeads(HttpServletRequest request) {
        String Msg = null;
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            String agentCode = request.getParameter("AgentCode");
            List<JSONObject> listjson = leadsService.agentAcceptedLeads(agentCode);
            array.addAll(listjson);
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;
    }

    @RequestMapping(value = "/AgentRejectedLeads", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray agentRejectedLeads(HttpServletRequest request) {
        String Msg = null;
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            String agentCode = request.getParameter("AgentCode");
            List<JSONObject> listjson = leadsService.agentRejectedLeads(agentCode);
            array.addAll(listjson);
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;
    }

    @RequestMapping(value = "/getagentLocation", method = {RequestMethod.GET, RequestMethod.POST})
    public String getagentLocation(HttpServletRequest request) {
        JSONArray array = new JSONArray();
        AllocationMastEntity allocationMastEntity = null;
        int cust_uid = 0;
        String result = null;
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            String agentCode = request.getParameter("agentcode");
            String customerNumber = request.getParameter("customerNumber");
            String location = request.getParameter("location");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String JObId = request.getParameter("jobid");
            int jobid = Integer.parseInt(JObId);

            if (!StringUtils.isEmpty(JObId)) {
                allocationMastEntity = allocationService.findByPrimaryKey(jobid);
                cust_uid = allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();
            }

            double lati = Double.parseDouble(latitude);
            double longi = Double.parseDouble(longitude);
            result = agentPaytmService.saveAgentLocation(agentCode, customerNumber, location, lati, longi, cust_uid);
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }

        return result;
    }









    @RequestMapping(value = "/AcceptedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String acceptedEntry(HttpServletRequest request) {
        AllocationMastEntity allocationMastEntity = null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        PaytmMastEntity paytmMastData = null;
        PaytmcustomerDataEntity paytmcustomerDataEntity = null;
        SpokeMastEntity spokeMastEntity = null;
        String result = null;
        DataentryEntity dataentryEntity1 = null;

        PaytmMastEntity paytmMastEntity = null;
        TblScan tblScan = null;
        String address = "";
        String state = "";
        String emailId = "";
        String city = "";
        String pincode = "";
        int custid = 0;
        CircleMastEntity circleMastEntity = null;
        String spokeCode = "";
        int auditstatus = 0;
        int circlecode = 0;

        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            try {
                String customerid = request.getParameter("customerId");
                int cust_uid = Integer.parseInt(customerid);

                String phoneNumber = request.getParameter("custPhone");
                if (customerid != null) {
                    paytmMastData = paytmMasterService.getPaytmmasterServiceDate(cust_uid);
                    custid = paytmMastData.getCust_uid();
                    address = paytmMastData.getAddress();
                    city = paytmMastData.getCity();
                    state = paytmMastData.getState();
                    pincode = paytmMastData.getPincode();
                    emailId = paytmMastData.getEmail();
                    circlecode = paytmMastData.getCirCode();
                    circleMastEntity = paytmMastData.getCircleMastByCirCode();

                }

                paytmcustomerDataEntity = paytmMasterService.getPaytmCustomerData(cust_uid);

                String custName = request.getParameter("custName");
                String custPOICode = request.getParameter("custPOICode"); // not required
                //String custPOINumber = request.getParameter("custPOINumber"); // not required
                String custPOACode = request.getParameter("custPOACode");  // not requied
                // String custPOANumber = request.getParameter("custPOANumber"); // not reqired
                String agentCode = request.getParameter("agentCode");
                // String gender = request.getParameter("gender");     // not requied
                String jobid = request.getParameter("jobid");
                int jobID = Integer.parseInt(jobid);
                String remarksCode = request.getParameter("remarksCode");

                String coStatus = request.getParameter("subscriberType");
                String Simno = request.getParameter("simno");
                String folderName = request.getParameter("folderName");
                String pageCount = request.getParameter("pageCount");
                int pages = Integer.parseInt(pageCount);

                //checklist data
                String srf = request.getParameter("SRF");
                String poi = request.getParameter("POI");
                String poa = request.getParameter("POA");
                String originalpoi = request.getParameter("OrigPOI");
                String originalpoa = request.getParameter("OrigPOA");
                String originalphoto = request.getParameter("OrigPhoto");
                String srfpc = request.getParameter("SRFPC");
                int originalsrfpc = 0;
                int ipoipc = 0;
                int ipoapc = 0;
                int originalopipc = 0;
                int originalopoapc = 0;
                int originalphotoPC = 0;

                if (StringUtils.isNotBlank(srfpc)) {
                    originalsrfpc = Integer.parseInt(srfpc);
                    String poipc = request.getParameter("POIPC");
                    ipoipc = Integer.parseInt(poipc);
                    String poapc = request.getParameter("POAPC");
                    ipoapc = Integer.parseInt(poapc);
                    String opoipc = request.getParameter("OPOIPC");
                    originalopipc = Integer.parseInt(opoipc);
                    String opoapc = request.getParameter("OPOAPC");
                    originalopoapc = Integer.parseInt(opoapc);
                    String photoPC = request.getParameter("PhotoPC");
                    originalphotoPC = Integer.parseInt(photoPC);
                }

                if (!StringUtils.isEmpty(agentCode)) {

                    paytmagententryEntity = agentPaytmService.findByPrimaryKey(agentCode);
                }
                if (!StringUtils.isEmpty(jobid)) {
                    allocationMastEntity = allocationService.findByPrimaryKey(jobID);
                    spokeCode = allocationMastEntity.getSpokeCode();
                    spokeMastEntity = paytmMasterService.spokeMastEntity(spokeCode);
                }
                if (custPOICode != null) {
                    proofMastEntityPOICode = leadsService.findBykey(custPOICode);
                }
                if (custPOACode != null) {
                    proofMastEntityPOACode = leadsService.findBykey(custPOACode);
                }
                ReasonMastEntity reasonMastEntity = leadsService.findByprimaryKey("ACC");

                if (allocationMastEntity.getKycCollected().toString().equalsIgnoreCase("p")) {

                    dataentryEntity1 = dataEntryService.getdataByUserCustid(cust_uid);
                    if (dataentryEntity1 == null) {

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

                        if (!result.equals("err") && cust_uid != 0) {
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
                            scanresult.setAssignedStatus("U");
                            scanresult.setAoAssignedstatus("U");
                            scanresult.setFormRecievingStatus("P");

                            result = qcservices.SaveScanimages(scanresult);

                        }
                        if (!result.equals("err") && cust_uid != 0) {
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
                        if (result.equalsIgnoreCase("done")) {
                            //     updateRemarkStatus(agentCode, jobid, remarksCode, "Y");
                            //    allocationService.updateKycAllocation(agentCode,jobid,remarksCode,"Y");
                            result = "done";

                        } else {
                            result = "Not found";
                        }

                    } else {
                        result = "customerid already exist";
                    }
                } else {
                    result = "kyc entry already exist";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }
        return result;
    }

    @RequestMapping(value = "/AcceptedEntry1", method = {RequestMethod.GET, RequestMethod.POST})
    public String acceptedEntry1(HttpServletRequest request) {
        AllocationMastEntity allocationMastEntity = null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        PaytmMastEntity paytmMastData = null;
        PaytmcustomerDataEntity paytmcustomerDataEntity = null;
        SpokeMastEntity spokeMastEntity = null;
        String result = null;
        DataentryEntity dataentryEntity1 = null;

        PaytmMastEntity paytmMastEntity = null;
        TblScan tblScan = null;
        String address = "";
        String state = "";
        String emailId = "";
        String city = "";
        String pincode = "";
        int custid = 0;
        CircleMastEntity circleMastEntity = null;
        String spokeCode = "";
        int auditstatus = 0;
        int circlecode = 0;
        int allocationId = 0;

        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            try {
                String customerid = request.getParameter("customerId");
                int cust_uid = Integer.parseInt(customerid);

                String phoneNumber = request.getParameter("custPhone");
                String custName = request.getParameter("custName");
                String custPOICode = request.getParameter("custPOICode"); // not required
                //String custPOINumber = request.getParameter("custPOINumber"); // not required
                String custPOACode = request.getParameter("custPOACode");  // not requied
                // String custPOANumber = request.getParameter("custPOANumber"); // not reqired
                String agentCode = request.getParameter("agentCode");
                // String gender = request.getParameter("gender");     // not requied
                String jobid = request.getParameter("jobid");
                int jobID = Integer.parseInt(jobid);
                String remarksCode = request.getParameter("remarksCode");
                String coStatus = request.getParameter("subscriberType");
                String Simno = request.getParameter("simno");
                String folderName = request.getParameter("folderName");
                String pageCount = request.getParameter("pageCount");
                int pages = Integer.parseInt(pageCount);

                //checklist data
                String srf = request.getParameter("SRF");
                String poi = request.getParameter("POI");
                String poa = request.getParameter("POA");
                String originalpoi = request.getParameter("OrigPOI");
                String originalpoa = request.getParameter("OrigPOA");
                String originalphoto = request.getParameter("OrigPhoto");
                String srfpc = request.getParameter("SRFPC");

                int originalsrfpc = 0;
                int ipoipc = 0;
                int ipoapc = 0;
                int originalopipc = 0;
                int originalopoapc = 0;
                int originalphotoPC = 0;

                if (StringUtils.isNotBlank(srfpc)) {
                    originalsrfpc = Integer.parseInt(srfpc);
                    String poipc = request.getParameter("POIPC");
                    ipoipc = Integer.parseInt(poipc);
                    String poapc = request.getParameter("POAPC");
                    ipoapc = Integer.parseInt(poapc);
                    String opoipc = request.getParameter("OPOIPC");
                    originalopipc = Integer.parseInt(opoipc);
                    String opoapc = request.getParameter("OPOAPC");
                    originalopoapc = Integer.parseInt(opoapc);
                    String photoPC = request.getParameter("PhotoPC");
                    originalphotoPC = Integer.parseInt(photoPC);
                }

                result = acceptedEntryService.insertAcceptedEntryDetails(agentCode, custPOACode, "", custPOICode, "", cust_uid, coStatus,
                        folderName, pages, Simno, "", srf, poa, poi, originalpoa, originalpoi, originalopoapc, originalopipc, originalphoto, originalphotoPC, ipoapc, ipoipc, originalsrfpc);


                if (result.equalsIgnoreCase("done")) {
                //    updateRemarkStatus(agentCode, jobid, remarksCode, "Y");

                    result = "done";

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }
        return result;

    }

    @RequestMapping(value = "/RejectedEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public String rejectedEntry(HttpServletRequest request) {

        TblScan tblScan = null;
        String result = null;
        String address = null;
        String city = null;
        String state = null;
        String pincode = null;
        String emailId = null;
        int circlecode = 0;
        AllocationMastEntity allocationMastEntity = null;
        PaytmagententryEntity paytmagententryEntity = null;
        ProofMastEntity proofMastEntityPOICode = null;
        ProofMastEntity proofMastEntityPOACode = null;
        PaytmMastEntity paytmMastData = null;
        String phonenumber = null;
        DataentryEntity dataentryEntity1 = null;

        int pages = 0;
        int cust_uid = 0;
        int originalsrfpc = 0;
        int ipoipc = 0;
        int ipoapc = 0;
        int originalopipc = 0;
        int originalopoapc = 0;
        int originalphotoPC = 0;


        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            String agentCode = request.getParameter("AgentCode");
            String jobid = request.getParameter("Jobid");
            int jobID = Integer.parseInt(jobid);
            String statusCode = request.getParameter("statusCode");
            String custName = request.getParameter("custName");
            String custPOICode = request.getParameter("custPOICode"); // not required
            //String custPOINumber = request.getParameter("custPOINumber"); // not required
            String custPOACode = request.getParameter("custPOACode");  // not requied
            String remarksCode = request.getParameter("remarksCode");
            String coStatus = request.getParameter("subscriberType");
            String Simno = request.getParameter("simno");
            String folderName = request.getParameter("folderName");
            String pageCount = request.getParameter("pageCount");

            if (StringUtils.isNotBlank(pageCount)) {
                pages = Integer.parseInt(pageCount);
            }

            String customerid = request.getParameter("customerId");
        /*if(StringUtils.isNotBlank(customerid)) {
            int cust_uids = Integer.parseInt(customerid);
        }*/
            String srf = request.getParameter("SRF");
            String poi = request.getParameter("POI");
            String poa = request.getParameter("POA");
            String originalpoi = request.getParameter("OrigPOI");
            String originalpoa = request.getParameter("OrigPOA");
            String originalphoto = request.getParameter("OrigPhoto");
            String srfpc = request.getParameter("SRFPC");
            if (StringUtils.isNotBlank(srfpc)) {
                originalsrfpc = Integer.parseInt(srfpc);
            }
            String poipc = request.getParameter("POIPC");
            if (StringUtils.isNotBlank(poipc)) {
                ipoipc = Integer.parseInt(poipc);
            }
            String poapc = request.getParameter("POAPC");
            if (StringUtils.isNotBlank(poapc)) {
                ipoapc = Integer.parseInt(poapc);
            }
            String opoipc = request.getParameter("OPOIPC");
            if (StringUtils.isNotBlank(opoipc)) {
                originalopipc = Integer.parseInt(opoipc);
            }
            String opoapc = request.getParameter("OPOAPC");
            if (StringUtils.isNotBlank(opoapc)) {
                originalopoapc = Integer.parseInt(opoapc);
            }
            String photoPC = request.getParameter("PhotoPC");
            if (StringUtils.isNotBlank(photoPC)) {
                originalphotoPC = Integer.parseInt(photoPC);
            }
            if (!StringUtils.isEmpty(agentCode)) {
                paytmagententryEntity = agentPaytmService.findByPrimaryKey(agentCode);
            }
            if (!StringUtils.isEmpty(jobid)) {
                allocationMastEntity = allocationService.findByPrimaryKey(jobID);
                cust_uid = allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();
            }
            if (custPOICode != null && StringUtils.isNotBlank(custPOICode)) {
                proofMastEntityPOICode = leadsService.findBykey(custPOICode);
            } else {
                proofMastEntityPOICode = leadsService.findBykey("8");
            }
            if (custPOACode != null && StringUtils.isNotBlank(custPOACode)) {
                proofMastEntityPOACode = leadsService.findBykey(custPOACode);
            } else {
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
                phonenumber = paytmMastData.getCustomerPhone();
            }

            ReasonMastEntity reasonMastEntity = leadsService.findByprimaryKey("REJECT");

            if (allocationMastEntity.getKycCollected().toString().equalsIgnoreCase("p")) {

                dataentryEntity1 = dataEntryService.getdataByUserCustid(cust_uid);
                if (dataentryEntity1 == null) {

                    DataentryEntity dataentryEntity = new DataentryEntity();
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

                    if (!result.equals("err")) {
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


                    paytmMastData.setFinalStatus("close");
                    paytmMasterService.updatePaytmMast(paytmMastData);
                    //  String result=allocationService.updateKycAllocation(agentCode,jobid,statusCode,"N");
                } else {
                    result = "customerId already exist";
                }
            } else {
                result = "kyc already rejected";
            }
        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }

        return result;
    }


    @RequestMapping(value = "/ftpdetails1", method = {RequestMethod.GET, RequestMethod.POST})
    public String ftpDetails1(HttpServletRequest request) {
        String result = "";
        TblScan tblScan = null;
        int cust_uid = 0;
        AllocationMastEntity allocationMastEntity = null;
        System.out.println("Service done ");
        // String customer_number = request.getParameter("customer_no");
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {

            String image_path = request.getParameter("image_path");
            int page_number = Integer.parseInt(request.getParameter("page_number"));
            String created_on = request.getParameter("created_on");
            String created_by = request.getParameter("created_by");
            int qc_status = Integer.parseInt(request.getParameter("qc_status"));
            cust_uid = Integer.parseInt(request.getParameter("customer_no"));

            tblScan = qcservices.getUserScanDetails(cust_uid);
            allocationMastEntity = allocationService.findByCustUid(cust_uid);
            String ImagePath = tblScan.getImagePath();
            Integer jobId = allocationMastEntity.getId();
            int scanid = tblScan.getScanid();
            if (cust_uid == 0) {
                result = "cust_uid is empty";
            } else if (ImagePath == "" || ImagePath.equals(null) || ImagePath == " " || ImagePath.equals("")) {
                tblScan.setImagePath(image_path);
                String result1 = qcservices.updateTblSacnEntity(tblScan);
                if (result1.equalsIgnoreCase("success")) {
                    updateRemarkStatus(allocationMastEntity.getAgentCode(), jobId.toString(), "A", "Y");
                    UploadedImagesEntity imagesEntity = new UploadedImagesEntity();
                    imagesEntity.setImagePath(image_path);
                    imagesEntity.setScan_id(scanid);
                    imagesEntity.setUploadedon(new Timestamp(new Date().getTime()));
                    imagesEntity.setTblScan(tblScan);
                    result = ftpDetailsService.saveImagesDeetails(imagesEntity);
                    if (result.equals("done")) {
                        result = "success";
                        logger.info(" Result   " + result);
                    } else {
                        result = "Fail";
                        logger.info(" Result   " + result);
                    }
                }

            } else {
                //result = ftpDetailsService.saveFTPData(customer_number, image_path, page_number, created_by, qc_status);
                UploadedImagesEntity imagesEntity = new UploadedImagesEntity();
                imagesEntity.setImagePath(image_path);
                imagesEntity.setScan_id(scanid);
                imagesEntity.setUploadedon(new Timestamp(new Date().getTime()));
                imagesEntity.setTblScan(tblScan);
                result = ftpDetailsService.saveImagesDeetails(imagesEntity);
                if (result.equals("done")) {
                    result = "success";
                    logger.info(" Result   " + result);
                } else {
                    result = "Fail";
                    logger.info(" Result   " + result);
                }
            }

        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }
        return result;
    }



    @RequestMapping(value = "/ftpdetails", method = {RequestMethod.GET, RequestMethod.POST})
    public String ftpDetails(HttpServletRequest request) {
        String result = "";
        TblScan tblScan = null;
        int cust_uid = 0;
        AllocationMastEntity allocationMastEntity = null;
        System.out.println("Service done ");
        // String customer_number = request.getParameter("customer_no");
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {

            String image_path = request.getParameter("image_path");
            int page_number = Integer.parseInt(request.getParameter("page_number"));
            String created_on = request.getParameter("created_on");
            String created_by = request.getParameter("created_by");
            int qc_status = Integer.parseInt(request.getParameter("qc_status"));
            cust_uid = Integer.parseInt(request.getParameter("customer_no"));

             result=qcservices.saveImages(image_path,created_by,cust_uid);
                    if (result.equals("done")) {
                        result = "success";
                        logger.info(" Result   " + result);
                    } else {
                        result = "Fail";
                        logger.info(" Result   " + result);
                    }
                }


         else {
            result = "Authentication failed due to unauthorised login from another device";
        }
        return result;
    }

    @RequestMapping(value = "/ValidateCustomerId", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    JSONObject validateCustomer(HttpServletRequest request) {
        String customerPhone = null;
        int cust_uid = 0;
        String result = "false";
        String customerid = null;
        AllocationMastEntity allocationMastEntity = null;
        JSONObject jsonObject = new JSONObject();

        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {

            customerid = request.getParameter("customerid");
            String jobid = request.getParameter("jobid");
            int jobId = Integer.parseInt(jobid);
            try {

                if (customerid != null) {
                    allocationMastEntity = paytmMasterService.getallocationMastEntity(customerid, jobId);

                    cust_uid = allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();


                    PaytmMastEntity paytmMastData = paytmMasterService.getPaytmmasterServiceDate(cust_uid);
                    if (paytmMastData != null) {

                        jsonObject.put("simType", paytmMastData.getSimType());
                        jsonObject.put("customeName", paytmMastData.getUsername());
                        jsonObject.put("coStatus", paytmMastData.getCoStatus());
                        jsonObject.put("cust_uid", paytmMastData.getCust_uid());
                        jsonObject.put("circleCode", paytmMastData.getCirCode());
                        jsonObject.put("cust_uid", paytmMastData.getCust_uid());
                        jsonObject.put("Msg", "Data Found");
                        result = "true";

                    } else {
                        jsonObject.put("Msg", "Data Not Found");
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
                result = "false";
                jsonObject.put("Msg", "Data Not Found");
            }
        } else {
            jsonObject.put("Msg", "Authentication failed due to unauthorised login from another device");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/ftpDocumentDetails", method = {RequestMethod.GET, RequestMethod.POST})
    public String ftpDocumentDetails(HttpServletRequest request) {
        return "done";
    }

    @RequestMapping(value = "/RasonsList", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray reasonList(HttpServletRequest request) {
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            //  msg = "token valid";
            array.add("token valid");
            List<ReasonMastEntity> reasonMastEntities = leadsService.reasonList();
            for (ReasonMastEntity reasonMastEntity : reasonMastEntities) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Code", reasonMastEntity.getReasonCode());
                jsonObject.put("Text", reasonMastEntity.getReasonText());
                array.add(jsonObject);
            }
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;
    }

    @RequestMapping(value = "/RemarkList", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray remarkList(HttpServletRequest request) {

        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            List<RemarkMastEntity> remarkMastEntityList = postCallingService.remarkList();
            for (RemarkMastEntity remarkMastEntity : remarkMastEntityList) {
                if (!remarkMastEntity.getRemarksCode().equalsIgnoreCase("U")) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Code", remarkMastEntity.getRemarksCode());
                    jsonObject.put("Text", remarkMastEntity.getRemarksText());
                    array.add(jsonObject);
                }
            }
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;
    }

    @RequestMapping(value = "/KYCDone", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray kycDone(HttpServletRequest request) {
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            String agentCode = request.getParameter("AgentCode");
            JSONObject result = new JSONObject();
            List<JSONObject> listjson = leadsService.kycDone(agentCode);
            array.addAll(listjson);
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;

    }

    @RequestMapping(value = "/KYCNotDone", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONArray kycNotDone(HttpServletRequest request) {
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            String agentCode = request.getParameter("AgentCode");
            List<JSONObject> listjson = leadsService.kycNotDone(agentCode);
            array.addAll(listjson);
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        return array;
    }

    @RequestMapping(value = "/IdentityProofTypes", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray identifyProofType(HttpServletRequest request) {
        String applicable = "I";
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            List<ProofMastEntity> proofMastEntities = proofService.getProofMastEntity(applicable);

            for (ProofMastEntity proofMastEntity : proofMastEntities) {
                JSONObject json = new JSONObject();
                if (!proofMastEntity.getIdText().equalsIgnoreCase("NA")) {
                    json.put("Applicable", proofMastEntity.getApplicable());
                    json.put("Code", proofMastEntity.getIdCode());
                    json.put("Text", proofMastEntity.getIdText());
                }

                array.add(json);
            }
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
        }
        //  jsonObject.put("ArrayOfProofType",array);
        return array;
    }

    @RequestMapping(value = "/AddressProofTypes", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONArray addressProofType(HttpServletRequest request) {
        String applicable = "A";
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity = userService.getUserByToken(token);
        if (emplogintableEntity != null) {
            array.add("token valid");
            List<ProofMastEntity> proofMastEntities = proofService.getProofMastEntity(applicable);
            for (ProofMastEntity proofMastEntity : proofMastEntities) {
                JSONObject json = new JSONObject();
                if (!proofMastEntity.getIdText().equalsIgnoreCase("NA")) {
                    json.put("Applicable", proofMastEntity.getApplicable());
                    json.put("Code", proofMastEntity.getIdCode());
                    json.put("Text", proofMastEntity.getIdText());
                    array.add(json);
                }
            }
        } else {
            array.add("Authentication failed due to unauthorised login from another device");
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
    public JSONObject employeeNumber(HttpServletRequest request) {
        String phoneNumber = "0";
        int circleCode = 0;
        String userName = null;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String status;
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity1 = userService.getUserByToken(token);
        if (emplogintableEntity1 != null) {

            jsonObject1.put("Msg", "token valid");
            try {
                userName = request.getParameter("username");
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
        } else {
            jsonObject1.put("Msg", "Authentication failed due to unauthorised login from another device");
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
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity1 = userService.getUserByToken(token);
        if (emplogintableEntity1 != null) {
            try {
                String agentcode = request.getParameter("agentcode");
                String status = request.getParameter("status");

                EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(agentcode);
                if (emplogintableEntity != null) {
                    if (status.equals("active")) {
                        emplogintableEntity.setEmpStatus(1);
                        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                        ActivateLogEntity activateLogEntity  =new ActivateLogEntity();
                        activateLogEntity.setAction("active");
                        activateLogEntity.setUserId(agentcode);
                        activateLogEntity.setDateTime(new Timestamp(new Date().getTime()));
                        userService.saveActivateEntity(activateLogEntity);
                    }
                    if (status.equalsIgnoreCase("deactive")) {
                        emplogintableEntity.setEmpStatus(0);
                        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                        ActivateLogEntity activateLogEntity  =new ActivateLogEntity();
                        activateLogEntity.setAction("deactive");
                        activateLogEntity.setUserId(agentcode);
                        activateLogEntity.setDateTime(new Timestamp(new Date().getTime()));
                        userService.saveActivateEntity(activateLogEntity);
                    }
                    System.out.println(emplogintableEntity.getEmpStatus());
                    result = userService.updateAgentStatus(emplogintableEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "err";
            }
        } else {
            result = "Authentication failed due to unauthorised login from another device";
        }

        return result;
    }

    @RequestMapping(value = "/getCustomerValidation", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCustomerValidation(HttpServletRequest request) {
        String name = null;
        JSONArray array = new JSONArray();
        String token = request.getParameter("agentToken");
        EmplogintableEntity emplogintableEntity1 = userService.getUserByToken(token);
        if (emplogintableEntity1 != null) {
            String mobileNo = request.getParameter("mobileNo");
            String agentcode = request.getParameter("agentcode");
            name = leadsService.getCustomerName(mobileNo);
        } else {
            name = "Authentication failed due to unauthorised login from another device";
        }
        return name;

    }
   /* @RequestMapping(value = "/UpdatePassword", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject resetPassword(HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();
        String result = null;
        String useroldpassword=null;
        try {
            String user = request.getParameter("userName");
            String oldpassword = request.getParameter("oldpassword");
            String newpassword = request.getParameter("newpassword");
            Date expireDate = new Date();
            System.out.println("Current Date   " + expireDate);
            long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
            expireDate.setTime(expireDate.getTime() + expireTime);

            System.out.println("Date After 30 Days  " + expireDate);
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
           useroldpassword=emplogintableEntity.getEmpPassword();
            if (oldpassword.equals(useroldpassword)) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(newpassword);
                emplogintableEntity.setEmpPassword(newpassword);
                emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

                emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));
                result = agentPaytmService.updatePassword(emplogintableEntity, newpassword);
            }
            else{
                result="password not matched";
            }

            if (result.equalsIgnoreCase("done")) {
                result = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        jsonObject.put("status", result);

        return jsonObject;
    }*/


    @RequestMapping(value = "/messageResend", method = {RequestMethod.GET, RequestMethod.POST})
    public String messageResend(HttpServletRequest request) {
        String msg = null;
        JSONArray array = new JSONArray();
        String smsText = "";
        String sendResult = "";
        SmsSendlogEntity smsLogEntity = null;
        List<String> customerNumbers = new ArrayList<>();
        String token = request.getParameter("agentToken");
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        EmplogintableEntity emplogintableEntity1 = userService.getUserByToken(token);
        if (emplogintableEntity1 != null) {

            try {
                String jobId = request.getParameter("jobId");
                int job_Id=Integer.parseInt(jobId);
                int cust_uid =0;
                AllocationMastEntity allocationMastEntity=allocationService.findByPrimaryKey(job_Id);
                if(allocationMastEntity!=null) {
                    cust_uid = allocationMastEntity.getPaytmcustomerDataByCustomerPhone().getCust_uid();
                }
                PaytmMastEntity paytmMastEntity = paytmMasterService.getPaytmMastDatas(cust_uid);

                String alternative1 = paytmMastEntity.getAlternatePhone1();
                String alternative2 = paytmMastEntity.getAlternatePhone2();
                String customerNumber = paytmMastEntity.getCustomerPhone();
                customerNumbers.add(alternative1);
                customerNumbers.add(customerNumber);
                customerNumbers.add(alternative2);

                if (alternative1 == null || StringUtils.isBlank(alternative1)) {
                    smsLogEntity = smsSendLogService.getByMobileNumber(customerNumber);
                    smsText = smsLogEntity.getSmsText();

                } else {

                    smsLogEntity = smsSendLogService.getByMobileNumber(alternative1);
                    smsText = smsLogEntity.getSmsText();
                }


                for (String mobileno : customerNumbers) {
                    String result = sendSms(mobileno, smsText);
                    JSONParser parser = new JSONParser();
                    json = (JSONObject) parser.parse(result);
                    sendResult = (String) json.get("status");
                    if (sendResult.equalsIgnoreCase("success")) {
                        break;
                    }
                }


                if ("success".equalsIgnoreCase(sendResult)) {

                    smsLogEntity.setSendDateTime(new Timestamp(new Date().getTime()));
                    smsLogEntity.setDeliveryStatus(sendResult);
                    smsLogEntity.setSmsDelivered("Y");
                    String updateResult = smsSendLogService.updateSmsLogData(smsLogEntity);
                    msg = "Message Sent Successfully..";
                }else {
                    msg = "Message Sent Failed..";
                }


            } catch (Exception e) {
                msg = "Technical error";
                logger.error("Sending Sms...  ", e);
            }

        } else {
            msg = "Authentication failed due to unauthorised login from another device";
        }
        return msg;

    }


    private String sendSms(String mobileno, String text) {
        String result = null;
        SmsSendlogEntity smsSendlogEntity = null;
        BufferedReader in = null;
        HttpURLConnection con = null;
        String url = "http://www.mysmsapp.in/api/push.json";
        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(RequestBuilder.post(url)
                    .addParameter(new BasicNameValuePair("apikey", "5732df1bc7011"))
                    .addParameter(new BasicNameValuePair("sender", "VODAFN"))
                    .addParameter(new BasicNameValuePair("mobileno", mobileno))
                    .addParameter(new BasicNameValuePair("text", text)).build())) {
                InputStream is = closeableHttpResponse.getEntity().getContent();
                in = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                result = response.toString();

                logger.info("sent sms  mobile Number =  " + mobileno);
            } catch (IOException io) {
                logger.error("send sms ", io);
                result = "err";

            }
        } catch (Exception e) {
            logger.error("send sms ", e);
            result = "err";
        }

        return result;


    }


    @RequestMapping(value = "/UpdatePassword", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String resetPassword(HttpServletRequest request, HttpServletResponse response) {

        String result = null;
        String useroldpassword = null;
        String finalpass = null;
        String passCsv = "";
        try {
            String user = request.getParameter("userName");
            String oldpassword = request.getParameter("oldpassword");
            String newpassword = request.getParameter("newpassword");
            Date expireDate = new Date();
            System.out.println("Current Date   " + expireDate);
            long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
            expireDate.setTime(expireDate.getTime() + expireTime);
            System.out.println("Date After 30 Days  " + expireDate);

            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
            if (emplogintableEntity != null) {
                if (oldpassword.equals(emplogintableEntity.getEmpPassword())) {
                    String lastThrePassword = emplogintableEntity.getLastThreePassword();
                    lastThrePassword = (lastThrePassword != null) ? lastThrePassword : "";
                    String[] lastPassArr = lastThrePassword.split(",");

                    if (!Arrays.asList(lastPassArr).contains(newpassword)) {
                        //Old password mismatch
                        String lastThreePassword = emplogintableEntity.getLastThreePassword();
                        if (lastThreePassword != null) {
                            String[] arr = lastThreePassword.split(",");

                            if (arr.length == 3) {
                                arr[0] = arr[1];
                                arr[1] = arr[2];
                                arr[2] = newpassword;
                                passCsv = StringUtils.join(arr, ',');
                            } else {
                                passCsv = StringUtils.isNotBlank(lastThreePassword)
                                        ? lastThreePassword + "," + newpassword : newpassword;
                            }
                        } else {
                            passCsv = newpassword;
                        }
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String hashedPassword = passwordEncoder.encode(newpassword);
                        emplogintableEntity.setEmpPassword(newpassword);
                        emplogintableEntity.setLastThreePassword(passCsv);
                        emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                        emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));
                        result = agentPaytmService.updatePassword(emplogintableEntity, newpassword);

                    } else {
                        result = "New Password can't be last 3 password";
                    }
                } else {
                    result = "old password is not valid";
                }
            } else {
                result = "Invalid User";
            }
        } catch (Exception e) {
            result = "Technical Error";
            e.printStackTrace();
        }
        return result;
    }

}