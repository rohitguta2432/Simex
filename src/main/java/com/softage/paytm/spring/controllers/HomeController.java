package com.softage.paytm.spring.controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.TIFFLZWDecoder;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.itextpdf.text.pdf.codec.TiffWriter;
import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
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
    @Autowired
    private QcStatusService qcStatusService;

    @Autowired
    private CallTimeService callTimeService;

    @Autowired
    private BatchService batchService;

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

        String  session1   = (String)request.getAttribute(user);
         if(session1!=null){
             HttpSession session = request.getSession();
             if(session!=null){
                 session.invalidate();
             }
         }
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
        int reopenCount = 0;
        String importBy = "System";

        TelecallMastEntity telecallMastEntity = null;
        PaytmMastEntity paytmMastEntity = null;
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
            System.out.println("  file name  "+filename);
            String extension  =filename.split("\\.")[1];

            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10000);
            String serverPath = System.getenv("JBOSS_BASE_DIR");
            logger.info("server path>>>>>>>   " + serverPath);
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

            if(extension.equalsIgnoreCase("csv")) {
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
                            paytmMastEntity = paytmMasterService.getPaytmMaster(customerData[3]);
                            if (telecallMastEntity != null) {

                                ReOpenTaleCallMaster reOpenTaleCallMaster = new ReOpenTaleCallMaster();
                                reOpenTaleCallMaster.setTmCustomerPhone(telecallMastEntity.getTmCustomerPhone());
                                reOpenTaleCallMaster.setTmAttempts(telecallMastEntity.getTmAttempts());
                                reOpenTaleCallMaster.setTmLastAttemptBy(telecallMastEntity.getTmLastAttemptBy());
                                reOpenTaleCallMaster.setTmLastAttemptDateTime(telecallMastEntity.getTmLastAttemptDateTime());
                                reOpenTaleCallMaster.setTmLastCallStatus(telecallMastEntity.getTmLastCallStatus());
                                reOpenTaleCallMaster.setTmTeleCallStatus(telecallMastEntity.getTmTeleCallStatus());
                                String msg = callingService.save(reOpenTaleCallMaster);
                                logger.info("Customer Reopen>>>>>>> " + msg);
                                byte attempt = 1;
                                telecallMastEntity.setTmAttempts(attempt);
                                reopenCount++;
                                String msg1 = callingService.updateTeleCall(telecallMastEntity);
                                logger.info("TeleCallMastEntity Successfully Updated>>>>>>>>  " + msg1);
                            }
                        }
                        if ((customerData[12].length() == 6 && StringUtils.isNumeric(customerData[12]))) {
                            int pincode = Integer.parseInt(customerData[12]);
                            paytmPinMaster = pinMasterService.getByPincode(pincode);

                        }
                        if (!StringUtils.isEmpty(customerData[1]) && (customerData[3].length() == 10 && StringUtils.isNumeric(customerData[3])) && telecallMastEntity == null && paytmMastEntity == null) {
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
                            map.put("importBy", importBy);
                            list.add(map);
                            successCount++;
                        } else if (paytmPinMaster == null && (customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3]) || paytmMastEntity != null)) {
                            json.put("CustomerID", customerData[1]);
                            json.put("Resion", "check pincode valid for current exits circle and it should be 6 digit and numeric , mobile should be 10 digit and numeric and not duplicate");
                            json1.put("rejectedRecord" + count, json);

                        } else if (paytmPinMaster == null) {
                            json.put("CustomerID", customerData[1]);
                            json.put("Resion", "This PinCode is not exist with current list of Agent so please add = " + customerData[12]);
                            json1.put("rejectedRecord" + count, json);
                        } else if ((customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3]) || paytmMastEntity != null)) {
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
                rejectCount = (count - 1) - successCount - reopenCount;
                if ("done".equalsIgnoreCase(result)) {
                    result = "Successfully Uploded Customer  = " + successCount + " Reopen Customer  = " + reopenCount + " Rejected Customer  =" + rejectCount;
                }
                jsonObject.put("status", "success");
            } else {

                jsonObject =uploadExcel(serverFile,importBy,paytmMasterService,pinMasterService);
                return jsonObject;

            }

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


    @RequestMapping(value = "/uploadAgent", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadAgent(MultipartHttpServletRequest request, HttpServletResponse response) {
        BufferedReader br = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String result = null;
        File serverFile = null;
        File serverFile1 = null;
        PaytmPinMaster paytmPinMaster = null;
        String line = "";
        int rejectCount = 0;
        int reopenCount = 0;
        String importBy = "System";

        TelecallMastEntity telecallMastEntity = null;
        PaytmMastEntity paytmMastEntity = null;
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
            System.out.println("  file name  "+filename);
            String extension  =filename.split("\\.")[1];

            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10000);
            String serverPath = System.getenv("JBOSS_BASE_DIR");
            logger.info("server path>>>>>>>   " + serverPath);
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

            if(extension.equalsIgnoreCase("csv")) {
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
                            paytmMastEntity = paytmMasterService.getPaytmMaster(customerData[3]);
                            if (telecallMastEntity != null) {

                                ReOpenTaleCallMaster reOpenTaleCallMaster = new ReOpenTaleCallMaster();
                                reOpenTaleCallMaster.setTmCustomerPhone(telecallMastEntity.getTmCustomerPhone());
                                reOpenTaleCallMaster.setTmAttempts(telecallMastEntity.getTmAttempts());
                                reOpenTaleCallMaster.setTmLastAttemptBy(telecallMastEntity.getTmLastAttemptBy());
                                reOpenTaleCallMaster.setTmLastAttemptDateTime(telecallMastEntity.getTmLastAttemptDateTime());
                                reOpenTaleCallMaster.setTmLastCallStatus(telecallMastEntity.getTmLastCallStatus());
                                reOpenTaleCallMaster.setTmTeleCallStatus(telecallMastEntity.getTmTeleCallStatus());
                                String msg = callingService.save(reOpenTaleCallMaster);
                                logger.info("Customer Reopen>>>>>>> " + msg);
                                byte attempt = 1;
                                telecallMastEntity.setTmAttempts(attempt);
                                reopenCount++;
                                String msg1 = callingService.updateTeleCall(telecallMastEntity);
                                logger.info("TeleCallMastEntity Successfully Updated>>>>>>>>  " + msg1);
                            }
                        }
                        if ((customerData[12].length() == 6 && StringUtils.isNumeric(customerData[12]))) {
                            int pincode = Integer.parseInt(customerData[12]);
                            paytmPinMaster = pinMasterService.getByPincode(pincode);

                        }
                        if (!StringUtils.isEmpty(customerData[1]) && (customerData[3].length() == 10 && StringUtils.isNumeric(customerData[3])) && telecallMastEntity == null && paytmMastEntity == null) {
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
                            map.put("importBy", importBy);
                            list.add(map);
                            successCount++;
                        } else if (paytmPinMaster == null && (customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3]) || paytmMastEntity != null)) {
                            json.put("CustomerID", customerData[1]);
                            json.put("Resion", "check pincode valid for current exits circle and it should be 6 digit and numeric , mobile should be 10 digit and numeric and not duplicate");
                            json1.put("rejectedRecord" + count, json);

                        } else if (paytmPinMaster == null) {
                            json.put("CustomerID", customerData[1]);
                            json.put("Resion", "This PinCode is not exist with current list of Agent so please add = " + customerData[12]);
                            json1.put("rejectedRecord" + count, json);
                        } else if ((customerData[3].length() != 10 || !StringUtils.isNumeric(customerData[3]) || paytmMastEntity != null)) {
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
                rejectCount = (count - 1) - successCount - reopenCount;
                if ("done".equalsIgnoreCase(result)) {
                    result = "Successfully Uploded Customer  = " + successCount + " Reopen Customer  = " + reopenCount + " Rejected Customer  =" + rejectCount;
                }
                jsonObject.put("status", "success");
            } else {

                jsonObject =uploadAgent(serverFile,importBy,paytmMasterService,pinMasterService);
                return jsonObject;

            }

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







    @RequestMapping(value = "/saveCircle", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String saveCircle() {

        CircleMastEntity circleMastEntity = new CircleMastEntity();
        circleMastEntity.setCirCode(21);
        circleMastEntity.setCircleName("TestCircle1");
        circleMastEntity.setImportBy("Afjal");
        circleMastEntity.setImportDate(new Timestamp(new Date().getTime()));
        CircleMastEntity circleMastEntity1 = circleJPATestService.save(circleMastEntity);
        System.out.println(circleMastEntity1);

        return "done";
    }


    @RequestMapping(value = "/downloadList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject downloadList(HttpServletRequest request, HttpServletResponse response ) {

                 String mobilenumber  =request.getParameter("mobilenumber");
        JSONObject jsonObject=new JSONObject();
        try {
            if (mobilenumber == null||mobilenumber.equalsIgnoreCase("undefined")) {
                mobilenumber = "";
            }
            String to = request.getParameter("todate");
            String from = request.getParameter("fromdate");
            if (!to.equalsIgnoreCase("undefined") && !to.isEmpty() & !to.equalsIgnoreCase("")) {
                from = from.substring(6, 10) + "-" + from.substring(3, 5) + "-" + from.substring(0, 2);
                to = to.substring(6, 10) + "-" + to.substring(3, 5) + "-" + to.substring(0, 2);
            }else {
                from="";
                to="";
            }

            jsonObject = qcStatusService.downloadList(mobilenumber, to, from);
        }catch (Exception e){
            e.printStackTrace();
        }
                  return  jsonObject;

        }

    @RequestMapping(value = "/downloadPdf", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void downloadPdf(HttpServletRequest request, HttpServletResponse response ) {
        String mobileNo = request.getParameter("mobileNo");
        File localPath = null;
        FileInputStream fis=null;
        OutputStream outStream=null;
        StringBuffer strbr=new StringBuffer();
        String imagePath="";
        int circleCode=4;
   //   String  filename="9068225200.pdf";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                circleCode = (Integer) session.getAttribute("cirCode");
            }

            String serverPath = System.getenv("JBOSS_BASE_DIR");
            logger.info("server path>>>>>>>   " + serverPath);
            /*File dir1 = new File(serverPath + File.separator + "RejectedFile");*/
              localPath = new File(serverPath + File.separator + "downloadpdf");
            if (!localPath.exists()) {
                localPath.mkdirs();
            }

             String path=localPath.toString()+File.separator+ mobileNo + ".pdf";

             File localpdf=new File(path);
            JSONObject jsonObject = qcStatusService.qcCustomerDetails(mobileNo);
            String ftpImagePath = (String) jsonObject.get("imagePath");

            String[] paths = ftpImagePath.split("/");
            for (int i = 1; i < paths.length - 1; i++) {
                strbr = strbr.append(paths[i]);
                strbr.append("/");
            }

                imagePath = strbr.toString();
           //     imagePath=imagePath+File.separator;
                localPath=new File(localPath+File.separator);
                String result  =copyFtpToLocal(userService,path,imagePath,mobileNo,circleCode);
                if(result.equalsIgnoreCase("done")) {
                    localPath = new File(localPath + File.separator + mobileNo + ".pdf");
                    FileInputStream inStream = new FileInputStream(localPath);
                    String mimeType = "application/octet-stream";
                    response.setContentType(mimeType);
                    //     response.setContentLength((int) savePath.length());

                    // forces download
                    String headerKey = "Content-Disposition";
                    String headerValue = String.format("attachment; filename=\"%s\"", localPath.getName());
                    response.setHeader(headerKey, headerValue);

                    // obtains response's output stream
                    outStream = response.getOutputStream();

                    byte[] buffer1 = new byte[4096];
                    int bytesRead1 = -1;

                    while ((bytesRead1 = inStream.read(buffer1)) != -1) {
                        outStream.write(buffer1, 0, bytesRead1);
                    }


                    System.out.println("File downloaded");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
            try{
                fis.close();
                outStream.close();

            }catch (Exception e){

            }

        }
          return;

    }


    public  static String copyFtpToLocal(UserService userService,String localPath,String fptPath,String mobileNumber,int circle){

          String ftpHost = "122.15.90.140";
          String username = "administrator";
          String password = "softage@tchad";
           FTPClient ftpClient = new FTPClient();
           FileOutputStream fos = null;
           String result=null;



        try{
            JSONObject jsonObject1 = userService.getEmpFtpDetails(circle);
            String ftpIP = (String) jsonObject1.get("ftpIp");
            String userName1 = (String) jsonObject1.get("userName");
            String password1 = (String) jsonObject1.get("password");
            logger.info("FPT File IP   "+ftpIP);
            if (ftpIP != null && userName1 != null && password1 != null) {
                ftpHost = ftpIP;
                username = userName1;
                password = password1;

            }

            ftpClient.connect(ftpHost);
            boolean login = ftpClient.login(username, password);
            if (login) {
                logger.info("Successfully Connected to FTP Server");
            } else {
                System.out.println("Unable to Connected to Server..... ");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(fptPath);
            fos = new FileOutputStream(localPath);
            ftpClient.retrieveFile(mobileNumber+".pdf", fos);
            fos.flush();
            result="done";

        }catch (Exception e){
            result="err";
            e.printStackTrace();

        }finally {
            try{
                fos.close();
                ftpClient.disconnect();

            }catch (Exception e){
                e.printStackTrace();
                logger.error("",e);
            }
        }




       return result;
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
        JSONObject json = new JSONObject();
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
            String dateList2[] = new String[3];
            List<String> dateList = new ArrayList<String>();
            List<String> dateListReject = new ArrayList<String>();
            for (int i = 0; i < 7; i++) {

                dateList1[i] = format.format(date.getTime());
                date.add(Calendar.DATE, 1);
                dateList.add(dateList1[i]);
            }

            for (int i = 0; i < 3; i++) {

                dateList2[i] = format.format(date.getTime());
                date.add(Calendar.DATE, 1);
                dateListReject.add(dateList1[i]);
            }
            jsonObject.put("teleData", teleJson);
            jsonObject.put("stateList", stateList);
            jsonObject.put("statusList", statusList);
            jsonObject.put("dateList", dateListReject);
            jsonObject.put("dateList1", dateList);
            jsonObject.put("paytmmastjson", json);
            logger.info("telecalling screen data fatch sucessfully>>>>>>>>");
        } catch (Exception e) {
            logger.error("", e);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/agentRegistration", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject agentRegistration(HttpServletRequest request) {
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        PaytmPinMaster paytmPinMaster = null;
        int circleCode = 0;
        CircleMastEntity circleMastEntity = null;
        try {

            String userName = "system";
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
                circleCode = (Integer) session.getAttribute("cirCode");
                if (circleCode != 0) {
                    circleMastEntity = circleService.findByPrimaryKey(circleCode);
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
            int pincode1 = Integer.parseInt(pincode);
            paytmPinMaster = pinMasterService.getByPincodeState(pincode1, circleOffice);

           PaytmagententryEntity   paytmagententryEntity1 = agentPaytmService.findByPrimaryKey(agentCode);

      //      PaytmagententryEntity   paytmagententryEntity2 = agentPaytmService.findByPincode(pincode);

            if (paytmPinMaster == null) {
                msg = " Pincode not valid for this Circle try with valid Pincode ";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "error");
                return jsonObject;
            }

         /*   if(paytmagententryEntity2!=null){

              if(!paytmagententryEntity2.getAcode().equalsIgnoreCase(agentCode)){
                  msg = "This Pincode bind with other Agent So please enter Correct Pincode";
                  jsonObject.put("msg", msg);
                  jsonObject.put("status", "error");
                  return jsonObject;

                }

            }*/

            if(multipin.equals("N") && paytmagententryEntity1!=null){
                msg = "Agent Already Available if you want to Insert select Multiple pin option";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "error");
                return jsonObject;

            }
            if(paytmagententryEntity1!=null){
               String result =  agentPaytmService.saveAgentPinMaster1(paytmagententryEntity);
                if (result.equals("done")) {
                    msg = "Agent Insert with Multiple Pin";
                    jsonObject.put("msg", msg);
                    jsonObject.put("status", "success");

                }else {
                    msg = "Agent not Registered ";
                    jsonObject.put("msg", msg);
                    jsonObject.put("status", "error");
                }
                return jsonObject;
            }



            msg = agentPaytmService.saveAgent(paytmagententryEntity, circleMastEntity);
            if ("done".equalsIgnoreCase(msg)) {
                msg = "Agent Succesfully Registered";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "success");
            } else {
                msg = "Agent not Registered Try Again ";
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


    @RequestMapping(value = "/registration", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject registration(HttpServletRequest request) {
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        PaytmPinMaster paytmPinMaster = null;
        int circleCode = 0;
        CircleMastEntity circleMastEntity = null;
        String password=null;
        try {

            String userName = "system";
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
            }
            String empName = request.getParameter("name");
            String empcode = request.getParameter("empcode");
            String mobileNo = request.getParameter("phone");
            String circlecode1 = request.getParameter("circle_office");
            String empType = request.getParameter("empType");
            String password1 = request.getParameter("password");

            circleCode = Integer.parseInt(circlecode1);
            if (circleCode != 0) {
                circleMastEntity = circleService.findByPrimaryKey(circleCode);
            }

            EmplogintableEntity emplogintableEntity = new EmplogintableEntity();
            emplogintableEntity.setEmpCode(empcode);
            emplogintableEntity.setEmpName(empName);
            emplogintableEntity.setEmpPhone(mobileNo);
            password = empcode.substring(0, 4) + mobileNo.substring(0, 4);
            emplogintableEntity.setEmpPassword(password);
            emplogintableEntity.setCircleMastByCirCode(circleMastEntity);
            emplogintableEntity.setRoleCode(empType);
            emplogintableEntity.setEmpStatus(1);
            emplogintableEntity.setImportBy(userName);
            emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

             msg=   agentPaytmService.saveEmployee(emplogintableEntity);

            if ("done".equalsIgnoreCase(msg)) {
                msg = "Agent Succesfully Registered";
                jsonObject.put("msg", msg);
                jsonObject.put("status", "success");
            } else {
                msg = "Agent not Registered Try Again ";
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
        logger.info("calling to customer>>>>> wait");
        if (session != null) {
            userName = (String) session.getAttribute("name");
            agentNo = userService.getUserByEmpcode(userName).getEmpPhone();
        }

        String number = request.getParameter("customer_number");

        String result = customerCalling(number, agentNo);

        return "success";
    }




    public static JSONObject uploadExcel( File path,String importBy,PaytmMasterService paytmMasterService,PaytmPinMasterService pinMasterService) {

        Row row=null;

        PaytmMastEntity paytmMastEntity=null;
        PaytmPinMaster paytmPinMaster=null;
        JSONObject jsonObject = new JSONObject();
        String result="File Not Uploaded";

        JSONObject json1=new JSONObject();
        int count=0;
        int successCount=0;
        int rejectCount=0;
        FileInputStream file=null;

        try
        {
            file = new FileInputStream(path);

            ArrayList<Map<String,String>> list=new ArrayList<>();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(int i=0; i<1; i++){

               row =sheet.getRow(i);
                String customerNumber = row.getCell(0).getStringCellValue().trim();
                String appointmantDate = row.getCell(1).getStringCellValue().trim();
                String createdDate = row.getCell(2).getStringCellValue().trim();
                String name = row.getCell(3).getStringCellValue().trim();
                String mobileNumber = row.getCell(4).getStringCellValue().trim();

                 String address = row.getCell(5).getStringCellValue().trim();

                String leadStage = row.getCell(6).getStringCellValue().trim();

                String leadSubStage  = row.getCell(7).getStringCellValue().trim();

                String pincode = row.getCell(8).getStringCellValue().trim();

                String city = row.getCell(9).getStringCellValue().trim();

           //     String circle = row.getCell(10).getStringCellValue().trim();




            }

                for(int i=1; i<sheet.getPhysicalNumberOfRows(); i++) {
                    JSONObject json=new JSONObject();
                    System.out.println("row start");
                    row = sheet.getRow(i);
                    System.out.println("get row start");
                    if (row != null && row.getCell(0) != null) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        System.out.println(i);
                        String customerId = row.getCell(0).getStringCellValue().trim();
                    //    String appointmantDate = row.getCell(1).getStringCellValue().trim();
                      //  String createdDate = row.getCell(2).getStringCellValue().trim();
                        String name = row.getCell(3).getStringCellValue().trim();
                        String  mobileNumber = row.getCell(4).getStringCellValue().trim();

                        String address = row.getCell(5).getStringCellValue().trim();

                        String leadStage = row.getCell(6).getStringCellValue().trim();

                        String leadSubStage  = row.getCell(7).getStringCellValue().trim();

                        String pincode = row.getCell(8).getStringCellValue().trim();

                        String city = row.getCell(9).getStringCellValue().trim();



                        if (mobileNumber.length() == 10 && StringUtils.isNumeric(mobileNumber)) {
                            paytmMastEntity = paytmMasterService.getPaytmMaster(mobileNumber);

                        }
                        if ((pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                            int pincode1 = Integer.parseInt(pincode);
                            paytmPinMaster = pinMasterService.getByPincode(pincode1);

                        }
                        if(paytmMastEntity==null && (pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                            map.put("customerID", customerId);
                            map.put("name", name);
                            map.put("mobileNumber", mobileNumber);
                            map.put("address", address);
                            map.put("pincode", pincode);
                            map.put("city", city);
                            map.put("importBy", importBy);
                            list.add(map);
                            successCount++;

                            if (paytmPinMaster==null){
                                json.put("CustomerID", customerId);
                                json.put("Resion", "This Customer Pincode not match with Softage Circles PinCode .");
                                json1.put("rejectedRecord" + count, json);
                            }
                        }else if(paytmMastEntity!=null) {
                            json.put("CustomerID", customerId);
                            json.put("Resion", "Duplicate Customers");
                            json1.put("rejectedRecord" + count, json);

                        } else if((pincode.length() == 6 && StringUtils.isNumeric(pincode))){
                            json.put("CustomerID", customerId);
                            json.put("Resion", "PinCode not valid so please check It.");
                            json1.put("rejectedRecord" + count, json);

                        }




                        }
                    count++;
                    }



            jsonObject.put("rejectedRecord", json1);

            System.out.println("list   " + list);
            rejectCount = (count - 1) - successCount ;

            jsonObject.put("status", "success");

      //          result = paytmMasterService.savePaytmMasterExcel(list);
                if ("done".equalsIgnoreCase(result)) {
                    result = "Successfully Uploded Customer  = " + successCount + " Reopen Customer  = " + "" + " Rejected Customer  =" + rejectCount;
                }
        }
        catch (Exception e)
        {
            result="File Not Uploaded";
            e.printStackTrace();

        }
        finally {
            try {
                file.close();

            }catch (Exception e){

            }
        }
        jsonObject.put("Message",result);
        return jsonObject;
    }

    public static JSONObject uploadAgent( File path,String importBy,PaytmMasterService paytmMasterService,PaytmPinMasterService pinMasterService) {

        Row row=null;

        PaytmMastEntity paytmMastEntity=null;
        PaytmPinMaster paytmPinMaster=null;
        JSONObject jsonObject = new JSONObject();
        String result="File Not Uploaded";

        JSONObject json1=new JSONObject();
        int count=0;
        int successCount=0;
        int rejectCount=0;
        FileInputStream file=null;

        try
        {
            file = new FileInputStream(path);

            ArrayList<Map<String,String>> list=new ArrayList<>();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(int i=0; i<1; i++){

                row =sheet.getRow(i);
                String customerNumber = row.getCell(0).getStringCellValue().trim();
                String appointmantDate = row.getCell(1).getStringCellValue().trim();
                String createdDate = row.getCell(2).getStringCellValue().trim();
                String name = row.getCell(3).getStringCellValue().trim();
                String mobileNumber = row.getCell(4).getStringCellValue().trim();

                String address = row.getCell(5).getStringCellValue().trim();

                String leadStage = row.getCell(6).getStringCellValue().trim();

                String leadSubStage  = row.getCell(7).getStringCellValue().trim();

                String pincode = row.getCell(8).getStringCellValue().trim();

                String city = row.getCell(9).getStringCellValue().trim();

                //     String circle = row.getCell(10).getStringCellValue().trim();




            }

            for(int i=1; i<sheet.getPhysicalNumberOfRows(); i++) {
                JSONObject json=new JSONObject();
                System.out.println("row start");
                row = sheet.getRow(i);
                System.out.println("get row start");
                if (row != null && row.getCell(0) != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    System.out.println(i);
                    String customerId = row.getCell(0).getStringCellValue().trim();
                    //    String appointmantDate = row.getCell(1).getStringCellValue().trim();
                    //  String createdDate = row.getCell(2).getStringCellValue().trim();
                    String name = row.getCell(3).getStringCellValue().trim();
                    String  mobileNumber = row.getCell(4).getStringCellValue().trim();

                    String address = row.getCell(5).getStringCellValue().trim();

                    String leadStage = row.getCell(6).getStringCellValue().trim();

                    String leadSubStage  = row.getCell(7).getStringCellValue().trim();

                    String pincode = row.getCell(8).getStringCellValue().trim();

                    String city = row.getCell(9).getStringCellValue().trim();



                    if (mobileNumber.length() == 10 && StringUtils.isNumeric(mobileNumber)) {
                        paytmMastEntity = paytmMasterService.getPaytmMaster(mobileNumber);

                    }
                    if ((pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                        int pincode1 = Integer.parseInt(pincode);
                        paytmPinMaster = pinMasterService.getByPincode(pincode1);

                    }
                    if(paytmMastEntity==null && (pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                        map.put("customerID", customerId);
                        map.put("name", name);
                        map.put("mobileNumber", mobileNumber);
                        map.put("address", address);
                        map.put("pincode", pincode);
                        map.put("city", city);
                        map.put("importBy", importBy);
                        list.add(map);
                        successCount++;

                        if (paytmPinMaster==null){
                            json.put("CustomerID", customerId);
                            json.put("Resion", "This Customer Pincode not match with Softage Circles PinCode .");
                            json1.put("rejectedRecord" + count, json);
                        }
                    }else if(paytmMastEntity!=null) {
                        json.put("CustomerID", customerId);
                        json.put("Resion", "Duplicate Customers");
                        json1.put("rejectedRecord" + count, json);

                    } else if((pincode.length() == 6 && StringUtils.isNumeric(pincode))){
                        json.put("CustomerID", customerId);
                        json.put("Resion", "PinCode not valid so please check It.");
                        json1.put("rejectedRecord" + count, json);

                    }

                }
                count++;
            }



            jsonObject.put("rejectedRecord", json1);

            System.out.println("list   " + list);
            rejectCount = (count - 1) - successCount ;

            jsonObject.put("status", "success");

            result = paytmMasterService.savePaytmMasterExcel(list);

            if ("done".equalsIgnoreCase(result)) {
                result = "Successfully Uploded Customer  = " + successCount + " Reopen Customer  = " + "" + " Rejected Customer  =" + rejectCount;
            }
        }
        catch (Exception e)
        {
            result="File Not Uploaded";
            e.printStackTrace();

        }
        finally {
            try {
                file.close();

            }catch (Exception e){

            }
        }
        jsonObject.put("Message",result);
        return jsonObject;
    }


    @RequestMapping(value = "/getPdfUrl", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getPdfUrl(HttpServletRequest request) {

        String localPath = null;
        String ftpHost = "122.15.90.140";
        String port = "";
        String username = "administrator";
        String password = "softage@tchad";
        String path1 = "";
        String userName = "system";
        String agentNo = "";
        String ftpPath="";
        String url = "";
        int circleCode=4;
        String imagePath = "/F6Images/20072016/4/KYCNumber1445/";
        JSONObject json = new JSONObject();
        StringBuffer strbr=new StringBuffer("/");
        String customerNumber = request.getParameter("customer_Number");

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
                circleCode = (Integer) session.getAttribute("cirCode");

            }
            JSONObject jsonObject1 = userService.getEmpFtpDetails(circleCode);
            String ftpIP = (String) jsonObject1.get("ftpIp");
            String userName1 = (String) jsonObject1.get("userName");
            String password1 = (String) jsonObject1.get("password");
            if (ftpIP != null && userName1 != null && password1 != null) {
                ftpHost = ftpIP;
                username = userName1;
                password = password1;

            }
            url = "ftp://" + username + ":" + password + "@" + ftpHost;
            String serverPath = System.getenv("JBOSS_BASE_DIR");
            logger.info("server path>>>>>>>   " + serverPath);
            /*File dir1 = new File(serverPath + File.separator + "RejectedFile");*/
            File createdir = new File(serverPath + File.separator + "QCImages");
            if (!createdir.exists()) {
                createdir.mkdirs();
            }
            JSONObject jsonObject = qcStatusService.qcCustomerDetails(customerNumber);
            String ftpImagePath = (String) jsonObject.get("imagePath");

            String[] paths = ftpImagePath.split("/");
            for (int i = 1; i < paths.length - 1; i++) {
                strbr = strbr.append(paths[i]);
                strbr.append("/");
            }

            imagePath = strbr.toString();
            localPath = createdir.toString() + File.separator;
            //    imagePath = "/F6Images/20072016/4/KYCNumber1445/";


            File deleteDir = new File(localPath);
            File[] files = deleteDir.listFiles();

            if (imagePath != null && imagePath.length() > 4) {
                // delete exits file


                for (File file : files) {
                    boolean flag = file.delete();
                    if (flag) {
                        logger.info(" file delete " + file.getName());
                    } else {
                        logger.info(" file not delete " + file.getName());
                    }

                }

                String status = generatePdf(imagePath, customerNumber, localPath, username, password, ftpHost);
                ftpPath = url + imagePath + customerNumber + ".pdf";
                System.out.println("pdfurl = "+ftpPath);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        json.put("status", "success");
        //    json.put("url","http:/www.sanface.com/pdf/test.pdf");
        json.put("url", ftpPath);
        return json;
    }

    public static String generatePdf(String path, String kycNumber, String localPath, String user, String password, String ip) {



/*String user="poc";
		String password="P0@C@0@1234";
		String ip="182.19.6.70";*/
        String port = "";
        File file = null;
        boolean status = false;
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Document document = new Document();

        try {
            ftpClient.connect(ip);
            boolean login = ftpClient.login(user, password);
            if (login) {
                logger.info("Successfully Connected to FTP Server");
            } else {
                System.out.println("Unable to Connected to Server..... ");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            FTPFile[] list = ftpClient.listFiles();

            if (list.length > 0) {

                for (FTPFile ftpFile : list) {
                    System.out.println(ftpFile.getName());
                    String filedelete = ftpFile.getName();
                    String[] files = filedelete.split("\\.");
                    if (files[1].equalsIgnoreCase("pdf")) {
                        boolean flag = ftpClient.deleteFile(filedelete);
                        System.out.println(flag);
                    } else {

                        fos = new FileOutputStream(localPath + ftpFile.getName());
                        ftpClient.retrieveFile(ftpFile.getName(), fos);

                    }
                }
            }
            System.out.println("all file download>>>>");

            String[] IMAGES = {""};


            file = new File(localPath);

            IMAGES = file.list();


            String output = localPath + kycNumber + ".pdf";
            fos = new FileOutputStream(output);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            for (String image : IMAGES) {
                String[] cancelFile = image.split("\\.");
                if (!cancelFile[1].equalsIgnoreCase("pdf")) {
                    Image image2 = Image.getInstance(localPath + image);
                    System.out.println("image  " + image);
                    document.setPageSize(image2);
                    document.newPage();
                    image2.setAbsolutePosition(0, 0);
                    document.add(image2);
                }
            }
            document.close();
            logger.info("pdf genrated>>>>>>>  ");


            // pdf upload on ftpserver

            File file1 = new File(output);
            fis = new FileInputStream(file1);
            ftpClient.storeFile(path + File.separator + kycNumber + ".pdf", fis);
            System.out.println("done>>>>>>>");
            status = true;


        } catch (Exception e) {
            e.printStackTrace();
            status = false;

        } finally {
            try {
                fos.close();
                fis.close();
            } catch (Exception e) {
                logger.error(" files not close ");
            }
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();

                } catch (IOException f) {
                    // do nothing as file is already saved to server
                }
            }

            return "";

        }


    }


    private String customerCalling(String mobileNo, String agentNumber) {
        String msg = null;
        BufferedReader in = null;
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
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                ;
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

    @RequestMapping(value = "/getAllCircle", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getAllCircle(HttpServletRequest request) {
        int circleCode = 4;
        JSONObject jsonObject = new JSONObject();
        JSONArray list=new JSONArray();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                circleCode = (Integer) session.getAttribute("cirCode");
            }


            List<CircleMastEntity> circles = circleService.getCircleList();

            for (CircleMastEntity circle :circles) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("name",circle.getCircleName());
                jsonObject1.put("code",circle.getCirCode());
                list.add(jsonObject1);
            }

            jsonObject.put("circles", list);

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
        String importType = "Admin";
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
        jsonObject.put("msg", result);
        return jsonObject;
    }


    @RequestMapping(value = "/againCallingStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject againCallingStatus(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        String importby = "System";
        JSONObject jsonObject = new JSONObject();
        String message="";
        int cirCode=0;
        String role=null;

    try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");
            }

            String mobileNo = request.getParameter("mobileNo");
            String callingDate= request.getParameter("visit_date");
            String callingTime= request.getParameter("visit_time");
            String dateTime = callingDate.substring(6, 10) + "-" + callingDate.substring(3, 5) + "-" + callingDate.substring(0, 2)+" "+callingTime+":00";

             result=callTimeService.insertCallTimeDetails(mobileNo,dateTime,cirCode,importby);

        if (result.equals("done")){
            message="success";
        }else {
            message="error";
        }

        } catch (Exception e) {
            e.printStackTrace();

            logger.error("error to posting data ", e);
        }
        jsonObject.put("status", "success");
        return jsonObject;
    }


    @RequestMapping(value = "/postCallingStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject postCallingStatus(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        String importby = "System";
        JSONObject jsonObject = new JSONObject();
        String message="";
        String importType = "Admin";

        int cirCode=0;
        String role=null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            if(role.equals("ADM")){
                importType = "Admin";
            }

            if(role.equals("A1")){
                importType = "Agent";
            }
            if(role.equals("HR")){
                importType = "HR";
            }
           importType = "Admin";
            String mobileNo = request.getParameter("mobileNo");
            String status = request.getParameter("status");
            String callingDate= request.getParameter("visit_date");
            String callingTime= request.getParameter("visit_time");

            if(status.equals("2-CB")){
                String dateTime = callingDate.substring(6, 10) + "-" + callingDate.substring(3, 5) + "-" + callingDate.substring(0, 2)+" "+callingTime+":00";
                result=callTimeService.insertCallTimeDetails(mobileNo,dateTime,cirCode,importby);
            }


            System.out.println("callingTime "+callingDate +"callingTime "+callingTime );



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

    @RequestMapping(value = "/qcstatus" ,method ={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public JSONObject getQcStatus(HttpServletRequest request){
        String result="";
        JSONObject jsonObject=new JSONObject();
        String mobileNo=request.getParameter("mobileNo");
        String status=request.getParameter("status");
        String rejectedPage=request.getParameter("rejectedPage");
        String remarks=request.getParameter("remarks");
        String message=qcStatusService.updateQcStatus(mobileNo,status,rejectedPage,remarks);
        //JSONObject mobileNum=qcStatusService.getMobileNumber();
       // String mobileNumber= (String) mobileNum.get("mobile");
      //  JSONObject imgPath=qcStatusService.qcCustomerDetails(mobileNumber);
      //  String img=(String)imgPath.get(1);

         String message1=qcStatusService.saveQcStatus(mobileNo,status,rejectedPage,remarks);
        if(message.equals("done") && status.equals("A")){
            result="QC status successfully inserted successfully";
            jsonObject.put("result",result);
            jsonObject.put("mobile",mobileNo);
         //   jsonObject.put("imagePath",img);
            logger.info(" Result   " + result);
        }
        else if (message.equals("done") && status.equals("R")){
            result="QC status successfully rejected successfully";
            jsonObject.put("result",result);
            jsonObject.put("mobile",mobileNo);
      //      jsonObject.put("imagePath",img);
            logger.info(" Result   " + result);
        }
        else{
            result="Unable to insert data";
            jsonObject.put("result",result);
            jsonObject.put("mobile",mobileNo);
           // jsonObject.put("imagePath",img);
            logger.info(" Result   " + result);
        }
        return jsonObject;

    }


    @RequestMapping(value = "/getCustomer",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject getCustomerMobileNumber(){

        return  qcStatusService.getMobileNumber();
    }



    @RequestMapping(value = "/getInwordFrom",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject getInwordFrom(HttpServletRequest request ){
        JSONObject json=new JSONObject();
        String importby=null;
        int cirCode=0;
        String role=null;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }

            String getinwardfrom = batchService.getinwardfrom(cirCode);
            json.put("inWordFrom", getinwardfrom);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  json;
    }

    @RequestMapping(value = "/createBatch",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject createBatch(HttpServletRequest request,HttpServletResponse response){
        String inWordFrom = request.getParameter("inWordFrom");
        String inWordTo = request.getParameter("inWordTo");
        String importby=null;
        int cirCode=0;
        String role=null;
        JSONObject  jsonObject=null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }

            int inFrom=Integer.parseInt(inWordFrom);
            int inTO=Integer.parseInt(inWordTo);
            int totaldoc=inTO-inFrom+1;
            jsonObject  = batchService.saveBatch(inFrom,inTO,totaldoc,cirCode,importby);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
        }

        return  jsonObject;
    }



    @RequestMapping(value = "/indexCustomerValidation",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject indexCustomerValidation(HttpServletRequest request,HttpServletResponse response){
        String mobileno = request.getParameter("mobileno");
        String importby=null;
        int cirCode=0;
        String role=null;
        JSONObject  jsonObject=null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            jsonObject=batchService.getuserDetails(mobileno);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
        }

        return  jsonObject;
    }


    @RequestMapping(value = "/getBatchDetails",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject getBatchDetails(HttpServletRequest request,HttpServletResponse response){
        String importby=null;
        int cirCode=0;
        String role=null;
        JSONObject  jsonObject=null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            jsonObject  = batchService.getBatchDetails(cirCode);

            jsonObject.put("status","done");

        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
        }

        return  jsonObject;
    }


    @RequestMapping(value = "/updateindexing",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject updateindexing(HttpServletRequest request,HttpServletResponse response){
        String importby=null;
        int cirCode=0;
        String role=null;
        JSONObject  jsonObject=null;
        try {



            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
        //    var data = '&mobileno=' + $scope.mobileno +'&status=N' + '&batchno=' + $scope.batchno +'&uid=' + $scope.uid+'&emp_name=' + $scope.emp_name+'&customerId=' + $scope.customerId+'&remarks=' + $scope.user_comment;
            String mobileNo= request.getParameter("mobileno");
            String status= request.getParameter("status");
            String batchno1= request.getParameter("batchno");
            int batchno=Integer.parseInt(batchno1);
            String uid1= request.getParameter("uid");
            int uid=Integer.parseInt(uid1);
            String name= request.getParameter("emp_name");
            String customerId= request.getParameter("customerId");
            String remarks= request.getParameter("remarks");
            jsonObject=batchService.updateBatch(mobileNo,status,batchno,uid,name,customerId,remarks,importby);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
        }

        return  jsonObject;
    }


    @RequestMapping(value = "/searchIndexing",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  JSONObject searchIndexing(HttpServletRequest request,HttpServletResponse response){
        String importby=null;
        int cirCode=0;
        String role=null;
        JSONObject  jsonObject=null;
        int batchno=0;
        int uid=0;
        try {



            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            //    var data = '&mobileno=' + $scope.mobileno +'&status=N' + '&batchno=' + $scope.batchno +'&uid=' + $scope.uid+'&emp_name=' + $scope.emp_name+'&customerId=' + $scope.customerId+'&remarks=' + $scope.user_comment;
            String mobileNo= request.getParameter("cust_number");
            if(mobileNo.equalsIgnoreCase("undefined")){
                mobileNo="";
            }
            String batchno1= request.getParameter("batchSearch");
            if(!batchno1.equalsIgnoreCase("undefined") &&!batchno1.equalsIgnoreCase("")&&!batchno1.isEmpty()){
                 batchno=Integer.parseInt(batchno1);
            }
            String uid1= request.getParameter("uidnoSearch");
            System.out.println(uid1.isEmpty());
            if(!uid1.equalsIgnoreCase("undefined")&&!uid1.equalsIgnoreCase("")&&!uid1.isEmpty()){
                uid=Integer.parseInt(uid1);
            }
       //     jsonObject=batchService.updateBatch(mobileNo,status,batchno,uid,name,customerId,remarks,importby);

            //we have
            jsonObject=batchService.searchindexng(mobileNo,batchno,uid,cirCode);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
        }

        return  jsonObject;
    }


    @RequestMapping(value = "/testTiff",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  String TestTiff(HttpServletRequest request,HttpServletResponse response){
        File file=null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Document document = new Document();
        String localPath="D:/PaytmProject/PehchaanSVN/attchment/tiffFile/";

   try {
    String[] IMAGES = {""};
    file = new File(localPath);

    IMAGES = file.list();


    String output = "D:/PaytmProject/PehchaanSVN/attchment/tiffFile.pdf";
    fos = new FileOutputStream(output);
    PdfWriter writer = PdfWriter.getInstance(document, fos);
    TiffWriter write=new TiffWriter();

    write.writeFile(fos);
   /*  writer.open();
    document.open();
    for (String image : IMAGES) {
            Image image2 = Image.getInstance(localPath + image);
            System.out.println("image  " + image);
            document.setPageSize(image2);
            document.newPage();
            image2.setAbsolutePosition(0, 0);
            document.add(image2);

    }
    document.close();*/
    logger.info("pdf genrated>>>>>>>  ");
}catch (Exception e){

}

        return "";
    }



}