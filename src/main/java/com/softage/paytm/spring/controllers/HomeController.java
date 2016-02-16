package com.softage.paytm.spring.controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Handles requests for the application home page.
 */
@Controller
class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private PaytmMasterService paytmMasterService;
    @Autowired
    private AgentPaytmService agentPaytmService;
    @Autowired
    public CircleService circleService;
    @Autowired
    public PostCallingService postCallingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private PostCallingService callingService;
    @Autowired
    private PaytmPinMasterService pinMasterService;

    @Autowired
    private CircleJPATestService circleJPATestService;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        return "app";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);
        String result = null;
        JSONObject jsonObject = new JSONObject();
        String user = request.getParameter("userName");
        String password = request.getParameter("password");
        String dbUser = null;
        EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
        if (emplogintableEntity != null) {

            if (password.equalsIgnoreCase(emplogintableEntity.getEmpPassword())) {
                dbUser = emplogintableEntity.getEmpCode();
                HttpSession session = request.getSession();
                session.setAttribute("name", user);
                session.setAttribute("role", emplogintableEntity.getRoleCode());
                session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                result = "success";
            }
        } else {
            result = "error";
        }
        jsonObject.put("status", result);
        jsonObject.put("user", dbUser);
        return jsonObject;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject logout(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);
        JSONObject jsonObject = new JSONObject();
        String result = null;
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
            result = "success";
        } else {
            result = "error";
        }
        jsonObject.put("status", result);

        return jsonObject;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        BufferedReader br = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String result = null;
        File serverFile = null;
        File serverFile1 = null;
        PaytmPinMaster paytmPinMaster = null;
        String line = "";
        int rejectCount = 0;
        int reopenCount=0;
        String importBy="System";

        TelecallMastEntity telecallMastEntity = null;
        PaytmMastEntity paytmMastEntity=null;
        String cvsSplitBy = "\\|";

        JSONObject jsonObject = new JSONObject();

        Iterator<String> itr = request.getFileNames();

        MultipartFile mpf = request.getFile(itr.next());

        try {
            //just temporary save file info into ufile


            HttpSession session = request.getSession(false);
            if (session != null) {
                importBy = (String) session.getAttribute("name");
            }
            byte[] bytes = mpf.getBytes();
            mpf.getContentType();
            String filename = mpf.getOriginalFilename();
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10000);
            String serverPath = System.getenv("JBOSS_BASE_DIR");
            /*File dir1 = new File(serverPath + File.separator + "RejectedFile");*/
            File dir = new File(serverPath + File.separator + "FileUploder");
            if (!dir.exists()) {
                dir.mkdirs();
            }
           /* if (!dir1.exists()) {
                dir1.mkdirs();
            }*/
           /* serverFile1 = new File(dir1.getAbsolutePath()
                    + File.separator + filename);*/
            serverFile = new File(dir.getAbsolutePath()
                    + File.separator + filename);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("Server File Location="
                    + serverFile.getAbsolutePath());


		/*File serverFile=null;*/
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            List<Map<String, String>> list1 = new ArrayList<Map<String, String>>();


            br = new BufferedReader(new FileReader(serverFile));
            JSONObject json1 = new JSONObject();
            int count = 0;
            int successCount = 0;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(cvsSplitBy);
                int lent = customerData.length;
                JSONObject json = new JSONObject();
                if (count != 0) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    if (customerData[3].length() == 10 && StringUtils.isNumeric(customerData[3])) {
                        telecallMastEntity = callingService.getByPrimaryKey(customerData[3]);
                        paytmMastEntity= paytmMasterService.getPaytmMaster(customerData[3]);
                        if (telecallMastEntity != null) {

                            ReOpenTaleCallMaster reOpenTaleCallMaster=   new ReOpenTaleCallMaster();
                            reOpenTaleCallMaster.setTmCustomerPhone(telecallMastEntity.getTmCustomerPhone());
                            reOpenTaleCallMaster.setTmAttempts(telecallMastEntity.getTmAttempts());
                            reOpenTaleCallMaster.setTmLastAttemptBy(telecallMastEntity.getTmLastAttemptBy());
                            reOpenTaleCallMaster.setTmLastAttemptDateTime(telecallMastEntity.getTmLastAttemptDateTime());
                            reOpenTaleCallMaster.setTmLastCallStatus(telecallMastEntity.getTmLastCallStatus());
                            reOpenTaleCallMaster.setTmTeleCallStatus(telecallMastEntity.getTmTeleCallStatus());
                            String msg=callingService.save(reOpenTaleCallMaster);
                            logger.info("Customer Reopen>>>>>>> "+msg);
                            byte attempt = 0;
                            telecallMastEntity.setTmAttempts(attempt);
                            reopenCount++;
                            String msg1 = callingService.updateTeleCall(telecallMastEntity);
                            logger.info("TeleCallMastEntity Successfully Updated>>>>>>>>  "+msg1);
                        }
                    }
                    if ((customerData[12].length() == 6 && StringUtils.isNumeric(customerData[12]))) {
                        int pincode = Integer.parseInt(customerData[12]);
                        paytmPinMaster = pinMasterService.getByPincode(pincode);

                    }
                    if (!StringUtils.isEmpty(customerData[1])  && (customerData[3].length() == 10 && StringUtils.isNumeric(customerData[3])) && paytmPinMaster!=null&& telecallMastEntity==null&&paytmMastEntity==null) {
                        System.out.println(customerData[0]);
                        map.put("kycRequestId", customerData[0]);
                        map.put("CustomerID", customerData[1]);
                        map.put("Username", customerData[2]);
                        map.put("CustomerPhone", customerData[3]);
                        map.put("Email", customerData[4]);
                        map.put("AddressID", customerData[5]);
                        map.put("TimeSlot", customerData[6]);
                        map.put("Priority", customerData[7]);
                        map.put("AddressStreet1", customerData[8]);
                        map.put("AddressStreet2", customerData[9]);
                        map.put("City", customerData[10]);
                        map.put("State", customerData[11]);
                        map.put("Pincode", customerData[12]);
                        map.put("AddressPhone", customerData[13]);
                        map.put("VendorName", customerData[14]);
                        map.put("StageId", customerData[15]);
                        map.put("SubStageId", customerData[16]);
                        map.put("CreatedTimestamp", customerData[17]);
                        map.put("importBy",importBy);
                        list.add(map);
                        successCount++;
                    } else if (paytmPinMaster == null && (customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3])||paytmMastEntity!=null)) {
                        json.put("CustomerID", customerData[1]);
                        json.put("Resion", "check pincode valid for current exits circle and it should be 6 digit and numeric , mobile should be 10 digit and numeric and not duplicate");
                        json1.put("rejectedRecord" + count, json);

                    } else if (paytmPinMaster  == null) {
                        json.put("CustomerID", customerData[1]);
                        json.put("Resion", "check pincode valid for current exits circle and it should be 6 digit and numeric");
                        json1.put("rejectedRecord" + count, json);
                    } else if ((customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3])||paytmMastEntity!=null)) {
                        json.put("CustomerID", customerData[1]);
                        json.put("Resion", " mobile should be 10 digit and numeric and not duplicate");
                        json1.put("rejectedRecord" + count, json);
                    }
                }
                count++;

            }
          /*  if (list1.size() > 0) {
                paytmMasterService.uploadRejectedData(list1, serverFile1);

            }*/
            jsonObject.put("rejectedRecord", json1);

            System.out.println("list   " + list);
            result = paytmMasterService.savePaytmMaster(list);
            rejectCount = (count - 1) - successCount-reopenCount;
            if ("done".equalsIgnoreCase("done")) {
                result = "Successfully Uploded Customer  = " + successCount +" Reopen Customer  = "+reopenCount+ " Rejected Customer  =" + rejectCount;
            }
            jsonObject.put("status", "success");
        } catch (FileNotFoundException e) {
            logger.error("File Not Found ", e);
            e.printStackTrace();
            result = "File not Uploaded";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(" ", e);
            result = "File not Uploaded";
        } finally

        {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        jsonObject.put("Message", result);

        return jsonObject;
    }


    @RequestMapping(value = "/saveCircle", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String saveCircle(){

        CircleMastEntity circleMastEntity=new CircleMastEntity();
        circleMastEntity.setCirCode(21);
        circleMastEntity.setCircleName("TestCircle1");
        circleMastEntity.setImportBy("Afjal");
        circleMastEntity.setImportDate(new Timestamp(new Date().getTime()));
       CircleMastEntity circleMastEntity1= circleJPATestService.save(circleMastEntity);
        System.out.println(circleMastEntity1);

        return "done";
    }



   /* it is not use currently*/

    @RequestMapping(value = "/getFilePath", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getFilePath(@RequestParam("name") String name,
                                  @RequestParam("file") MultipartFile file) {

        JSONObject jsonObject = new JSONObject();
        BufferedReader br = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String result = null;
        File serverFile = null;
        String line = "";
        String cvsSplitBy = "\\|";
        try {


            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10000);
            String serverPath = System.getenv("JBOSS_BASE_DIR");
            String name1 = file.getName();
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();

                File dir = new File(serverPath + File.separator + "FileUploder");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Create the file on server
                serverFile = new File(dir.getAbsolutePath()
                        + File.separator + "Test3" + randomInt + ".csv");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

            }


		/*File serverFile=null;*/
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();


            br = new BufferedReader(new FileReader(serverFile));

            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(cvsSplitBy);
                int lent = customerData.length;
                if (count != 0) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    System.out.println(customerData[0]);
                    map.put("kycRequestId", customerData[0]);
                    map.put("CustomerID", customerData[1]);
                    map.put("Username", customerData[2]);
                    map.put("CustomerPhone", customerData[3]);
                    map.put("Email", customerData[4]);
                    map.put("AddressID", customerData[5]);
                    map.put("TimeSlot", customerData[6]);
                    map.put("Priority", customerData[7]);
                    map.put("AddressStreet1", customerData[8]);
                    map.put("AddressStreet2", customerData[9]);
                    map.put("City", customerData[10]);
                    map.put("State", customerData[11]);
                    map.put("Pincode", customerData[12]);
                    System.out.println(customerData[12]);
                    map.put("AddressPhone", customerData[13]);
                    map.put("VendorName", customerData[14]);
                    map.put("StageId", customerData[15]);
                    map.put("SubStageId", customerData[16]);
                    map.put("CreatedTimestamp", customerData[17]);
                    list.add(map);
                }
                count++;

            }
            System.out.println("list   " + list);
            result = paytmMasterService.savePaytmMaster(list);
            if ("done".equalsIgnoreCase(result)) {
                result = "success";
            }
            jsonObject.put("status", "success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = "error";
        } catch (Exception e) {
            e.printStackTrace();
            result = "error";
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }


    @RequestMapping(value = "/telecallingScreen", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject telecallingScreen(HttpServletRequest request) {
        String userName = null;
        int cirCode = 0;
        JSONObject json=new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
                EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(userName);
                if (emplogintableEntity != null) {
                    cirCode = emplogintableEntity.getCirCode();
                }
            }

            JSONObject teleJson = paytmMasterService.telecallingScreen(userName, cirCode);
            List<StateMasterEntity> stateList = paytmMasterService.getStateList();
            List<CallStatusMasterEntity> statusList = paytmMasterService.getStatusList();
            if (teleJson.get("mobileNo").toString() != null && !teleJson.get("mobileNo").toString().equals("")) {
                json = paytmMasterService.getPaytmMastData((String) teleJson.get("mobileNo"));
            }
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Calendar date = Calendar.getInstance();
            String dateList1[] = new String[7];
            List<String> dateList = new ArrayList<String>();
            for (int i = 0; i < 7; i++) {

                dateList1[i] = format.format(date.getTime());
                date.add(Calendar.DATE, 1);
                dateList.add(dateList1[i]);
            }

            jsonObject.put("teleData", teleJson);
            jsonObject.put("stateList", stateList);
            jsonObject.put("statusList", statusList);
            jsonObject.put("dateList", dateList);
            jsonObject.put("paytmmastjson", json);
            logger.info("telecalling screen data fatch sucessfully>>>>>>>>");
        }catch (Exception e){
              logger.error("",e);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/agentRegistration", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject agentRegistration(HttpServletRequest request) {
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        PaytmPinMaster paytmPinMaster=null;
        int circleCode=0;
        CircleMastEntity circleMastEntity=null;
        try {

            String userName = "system";
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
                circleCode=(Integer)session.getAttribute("cirCode");
                if (circleCode!=0){
                    circleMastEntity=circleService.findByPrimaryKey(circleCode);
                }
            }

            String agentName = request.getParameter("agent_name");
            String agentCode = request.getParameter("agent_code");
            String empCode = request.getParameter("employee");
            String mobileNo = request.getParameter("phone");
            String circleOffice = request.getParameter("circle_office");
            String spokeCode = request.getParameter("spoke_code");
            String avalTime = request.getParameter("avl_time");
            String pincode = request.getParameter("pin_code");
            String multipin = request.getParameter("multi_pin");
            String email = request.getParameter("email");
            PaytmagententryEntity paytmagententryEntity = new PaytmagententryEntity();
            paytmagententryEntity.setAfullname(agentName);
            paytmagententryEntity.setAcode(agentCode);
            paytmagententryEntity.setEmpcode(empCode);
            paytmagententryEntity.setAphone(mobileNo);
            paytmagententryEntity.setAspokecode(spokeCode);
            paytmagententryEntity.setAavailslot(avalTime);
            paytmagententryEntity.setApincode(pincode);
            paytmagententryEntity.setMulitplePin(multipin);
            paytmagententryEntity.setAemailId(email);
            paytmagententryEntity.setImportby(userName);
            paytmagententryEntity.setImportdate(new Timestamp(new Date().getTime()));
            int pincode1=Integer.parseInt(pincode);
            paytmPinMaster=pinMasterService.getByPincodeState(pincode1,circleOffice);
            if (paytmPinMaster==null){
                msg=" Pincode not valid for this Circle try with valid Pincode ";
                jsonObject.put("msg",msg);
                jsonObject.put("status","error");
                return jsonObject;
            }

            msg = agentPaytmService.saveAgent(paytmagententryEntity,circleMastEntity);
            if ("done".equalsIgnoreCase(msg)) {
                msg = "Agent Succesfully Registered";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "success");
            } else {
                msg = "Agent not Registered ";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Agent not Registered ";
            jsonObject.put("msg", msg);
            jsonObject.put("status", "error");
        }

        return jsonObject;
    }

    @RequestMapping(value = "/customerCalling", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String Calling(HttpServletRequest request) {

        String userName = "system";
        String agentNo = "";
        HttpSession session = request.getSession(false);
        if (session != null) {
            userName = (String) session.getAttribute("name");
            agentNo = userService.getUserByEmpcode(userName).getEmpPhone();
        }

        String number = request.getParameter("customer_number");

        String result = customerCalling(number, agentNo);

        return "success";
    }


    private String customerCalling(String mobileNo, String agentNumber) {
        String msg = null;
        BufferedReader in=null;
        String url = "http://etsdom.kapps.in/webapi/softage/api/softage_c2c.py?auth_key=hossoftagepital&customer_number=+91" + mobileNo + "&agent_number=+91" + agentNumber;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

              in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            msg = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                in.close();
            }catch (Exception e){
                e.printStackTrace();;
            }
        }
        return msg;
    }

    @RequestMapping(value = "/getCirles", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getCircleName(HttpServletRequest request) {
        int circleCode = 4;
        JSONObject jsonObject = new JSONObject();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                circleCode = (Integer) session.getAttribute("cirCode");
            }


            List<String> circles = circleService.getCirleList(circleCode);
            String circleName = circles.get(0);
            List<String> spokeList = circleService.getSpokeList(circleName);
            jsonObject.put("circles", circles);
            jsonObject.put("spokeList", spokeList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getSpokeCode", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getSpokeCode(@RequestParam(value = "circleName") String circleName) {
        JSONObject jsonObject = new JSONObject();
        List<String> spokeList = circleService.getSpokeList(circleName);
        jsonObject.put("spokeList", spokeList);
        return jsonObject;
    }

    @RequestMapping(value = "/postCalling", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject postCallig(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        String importby = "System";
        String importType="Admin";
        JSONObject jsonObject = new JSONObject();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                importType = (String) session.getAttribute("role");
            }
            String number = request.getParameter("mobileNo");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String area = request.getParameter("area");
            String emailId = request.getParameter("emailId");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String pinCode = request.getParameter("pincode");
            String landMark = request.getParameter("landmark");
            String visitDate = request.getParameter("visitDate");
            String visitTime1 = request.getParameter("visitTime");
            String status = request.getParameter("status");
            Map<String, String> map = new HashMap<String, String>();
            String[] visitTime = visitTime1.split(":");
            System.out.println(visitTime[0]);
            map.put("number", number);
            map.put("name", name);
            map.put("address", address);
            map.put("area", area);
            map.put("emailId", emailId);
            map.put("city", city);
            map.put("state", state);
            map.put("pinCode", pinCode);
            map.put("landmark", landMark);
            map.put("visitDate", visitDate);
            map.put("visitTime", visitTime[0]);
            map.put("status", status);
            map.put("importby", importby);
            map.put("importType", importType);
            result = postCallingService.saveCallingData(map);
            logger.info(" Result   " + result);
           /* if ("done".equalsIgnoreCase(result)) {
                result = "success";
            }*/
        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
            result = "Customer Records not Inserted ";
        }
        jsonObject.put("status", "success");
        jsonObject.put("msg",result);
        return jsonObject;
    }

    @RequestMapping(value = "/postCallingStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject postCallingStatus(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        String importby = "System";
        JSONObject jsonObject = new JSONObject();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
            }
            String importType = "Admin";
            String mobileNo = request.getParameter("mobileNo");
            String status = request.getParameter("status");
            map.put("number", mobileNo);
            map.put("status", status);
            map.put("importby", importby);
            map.put("importType", importType);
            result = postCallingService.saveCallingData(map);
            if ("done".equalsIgnoreCase(result)) {
                result = "success";
            } else {
                result = "error";
            }
        } catch (Exception e) {
            e.printStackTrace();

            logger.error("error to posting data ", e);
        }
        jsonObject.put("status", result);
        return jsonObject;
    }

    @RequestMapping(value = "/getReportsType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getReportTypes() {
        JSONObject jsonObject = new JSONObject();
        List<ReportMastEntity> reportTypes = circleService.getReporttypes();
        jsonObject.put("reportTypes", reportTypes);
        return jsonObject;
    }

    @RequestMapping(value = "/getReports", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getReports(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

	/*	HttpSession session=request.getSession(false);
        if(session!=null){
			String name=(String)session.getAttribute("name");
		}*/

	/*	System.getenv().keySet().forEach(key -> {
			System.out.println(key);
			System.out.println(System.getenv().get(key));
		});*/

        // base directory  JBOSS_BASE_DIR
        //JBoss log directory   JBOSS_LOG_DIR

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String type = request.getParameter("type");
        from = from.substring(6, 10) + "-" + from.substring(3, 5) + "-" + from.substring(0, 2);
        to = to.substring(6, 10) + "-" + to.substring(3, 5) + "-" + to.substring(0, 2);
        jsonObject = reportService.getReports(from, to, type);

        return jsonObject;
    }


/*	public ResponseEntity<ByteArray> downLoadFile(){
		ByteArrayInputStream body = null;
		FileInputStream fis = null;
		//report report service

		try{
			ResponseEntity<ByteArray> responseEntity = new ResponseEntity<ByteArray>(body);
		}catch(FileNotFoundException fiex){

		}catch(Exception ex){

		}finally{

		}
	}*/


}
/*


	 }
*/

// List<Map<?, ?>> data = readObjectsFromCsv(input);
// writeAsJson(data, output);



/*	public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);

		return mappingIterator.readAll();
	}

	public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(file, data);
	}*/

// CSVReader reader = new CSVReader(new FileReader("./file/Kyc_data.csv"), ';', '"', 1);


	/*		if (!file.isEmpty()) {

				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				//  File dir = new File(rootPath + File.separator + "tmpFiles");
				File dir=new File("D:/CSVFile");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name+".csv");
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();


			}
*/

//	@RequestMapping(value = "/getFilePath", method = RequestMethod.GET)
//	@ResponseBody
//	public void getFilePath(@RequestParam("name") String name,
//							@RequestParam("file") MultipartFile file) {
////		 File input = new File("/x/data.csv");
////		 File output = new File("/x/data.json");
//		String csvFile = "D:/CSVFile/TestFile.csv";
//		BufferedReader br = null;
//		String line = "";
//		String cvsSplitBy = ",";
//		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//
//  try{
//		br = new BufferedReader(new FileReader(csvFile));
//
//	  int count=0;
//		while ((line = br.readLine()) != null) {
//			String[] customerData = line.split(cvsSplitBy);
//			if(count!=0 ) {
//				HashMap<String, String> map = new HashMap<String, String>();
//				System.out.println(customerData[0]);
//				map.put("kycRequestId", customerData[0]);
//				map.put("CustomerID", customerData[1]);
//				map.put("Username", customerData[2]);
//				map.put("CustomerPhone", customerData[3]);
//				map.put("Email", customerData[4]);
//				map.put("AddressID", customerData[5]);
//				map.put("TimeSlot", customerData[6]);
//				map.put("Priority", customerData[7]);
//				map.put("AddressStreet1", customerData[8]);
//				map.put("AddressStreet2", customerData[9]);
//				map.put("City", customerData[10]);
//				map.put("State", customerData[11]);
//				map.put("Pincode", customerData[12]);
//				map.put("AddressPhone", customerData[13]);
//				map.put("VendorName", customerData[14]);
//				map.put("StageId", customerData[15]);
//				map.put("SubStageId", customerData[16]);
//				map.put("CreatedTimestamp", customerData[17]);
//				/*map.put("ImportBy", customerData[18]);
//				map.put("ImportDate", customerData[19]);
//				map.put("otp", customerData[20]);
//				map.put("Ref_Code", customerData[21]);*/
//				list.add(map);
//			}
//              count++;
//
//		}
//          System.out.println("list   "+list);
//	  paytmMasterService.savePaytmMaster(list);
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		if (br != null) {
//			try {
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//
//	/*	try {
//
//			CSVReader reader = new CSVReader(new FileReader("data.csv"), ',', '"', 1);
//			//Read all rows at once
//			List<String[]> allRows = reader.readAll();
//
//			//Read CSV line by line and use the string array as you want
//			for (String[] row : allRows) {
//				System.out.println(Arrays.toString(row));
//			}
//		} catch (Exception e) {
//
//		}*/
//	}
/*	@RequestMapping(value = "/getTeleCallData", method = RequestMethod.GET)
	public JSONObject getTeleCallingData(){
		JSONObject jsonObject =new JSONObject();
		PaytmMastEntity paytmMastData=paytmMasterService.getPaytmMastData();
		System.out.println(paytmMastData);
		return  jsonObject;
	}*/