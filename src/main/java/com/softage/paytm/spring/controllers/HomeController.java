package com.softage.paytm.spring.controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.TIFFLZWDecoder;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.itextpdf.text.pdf.codec.TiffWriter;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.softage.paytm.models.*;
import com.softage.paytm.service.*;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.format.DateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
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

    @Autowired
    private AoAuditService aoAuditService;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        return "app";
    }
/*
   // simple user login without expire date
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

        String session1 = (String) request.getAttribute(user);
        if (session1 != null) {
            HttpSession session = request.getSession();
            if (session != null) {
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
*/


    // this code use for password encryption






    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);

        String result = "Invalid Credentials";
        JSONObject jsonObject = new JSONObject();
        String user = request.getParameter("userName");
        String password = request.getParameter("password");
        String dbUser = null;
        String token = null;
        Integer attamptCount=null;
        try {
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //         String hashedPassword = passwordEncoder.encode(password);
            if (emplogintableEntity != null) {
                if (!"Y".equalsIgnoreCase(emplogintableEntity.getEmpLeftStatus())) {
                    if (!emplogintableEntity.getRoleCode().equalsIgnoreCase("A1")) {


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
                                    emplogintableEntity.setToken(token);
                                    emplogintableEntity.setAttamptCount(0);
                                    emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                    dbUser = emplogintableEntity.getEmpCode();
                                    HttpSession session = request.getSession();
                                    session.setAttribute("name", user);
                                    session.setAttribute("role", emplogintableEntity.getRoleCode());
                                    session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                                    session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
                                    result = "success";
                                }
                                agentPaytmService.updatePassword(emplogintableEntity, null);

                            } else if (currentDate.getTime() > expireDate.getTime()) {
                                result = "expirePassword";

                            } else if (password.equals(emplogintableEntity.getEmpPassword())) {
                                dbUser = emplogintableEntity.getEmpCode();
                                SecureRandom random = new SecureRandom();
                                byte bytes[] = new byte[20];
                                random.nextBytes(bytes);
                                token = bytes.toString();
                                emplogintableEntity.setToken(token);
                                emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setAttamptCount(0);
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                                dbUser = emplogintableEntity.getEmpCode();
                                HttpSession session = request.getSession();
                                session.setAttribute("name", user);
                                session.setAttribute("role", emplogintableEntity.getRoleCode());
                                session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                                session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
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
                                    emplogintableEntity.setToken(token);
                                    emplogintableEntity.setAttamptCount(0);
                                    emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                    dbUser = emplogintableEntity.getEmpCode();
                                    HttpSession session = request.getSession();
                                    session.setAttribute("name", user);
                                    session.setAttribute("role", emplogintableEntity.getRoleCode());
                                    session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                                    session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
                                    result = "success";
                                }
                                userService.updateAttaptStatus(emplogintableEntity);

                            } else if (currentDate.getTime() > expireDate.getTime()) {
                                result = "expirePassword";

                            } else if (password.equals(emplogintableEntity.getEmpPassword())) {
                                dbUser = emplogintableEntity.getEmpCode();
                                SecureRandom random = new SecureRandom();
                                byte bytes[] = new byte[20];
                                random.nextBytes(bytes);
                                token = bytes.toString();
                                emplogintableEntity.setToken(token);
                                emplogintableEntity.setLastLoginDate(new Timestamp(new Date().getTime()));
                                emplogintableEntity.setAttamptCount(0);
                                agentPaytmService.updatePassword(emplogintableEntity, null);
                                dbUser = emplogintableEntity.getEmpCode();
                                HttpSession session = request.getSession();
                                session.setAttribute("name", user);
                                session.setAttribute("role", emplogintableEntity.getRoleCode());
                                session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                                session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
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
                } else {
                    result = "User Left";
                }
            }else {
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
        jsonObject.put("user", dbUser);
        return jsonObject;
    }



/*

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);


        String result = null;
        JSONObject jsonObject = new JSONObject();
        String user = request.getParameter("userName");
        String password = request.getParameter("password");
        String dbUser = null;

        try {
            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            String session1 = (String) request.getAttribute(user);
            if (session1 != null) {
                HttpSession session = request.getSession();
                if (session != null) {
                    session.invalidate();
                }
            }
            if (emplogintableEntity != null) {

                Timestamp expireDate = emplogintableEntity.getExpireDate();
                Timestamp currentDate = new Timestamp(new Date().getTime());
                //    if(expireDate==null && currentDate.getTime()>expireDate.getTime()){
                if (expireDate == null) {

                    Date currentdate1 = new Date();
                    long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
                    currentdate1.setTime(currentdate1.getTime() + expireTime);

                    emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                    emplogintableEntity.setExpireDate(new Timestamp(currentdate1.getTime()));
                    //    emplogintableEntity.setEmpPassword(hashedPassword);
                    agentPaytmService.updatePassword(emplogintableEntity, null);

                    if (password.equalsIgnoreCase(emplogintableEntity.getEmpPassword())) {
                        dbUser = emplogintableEntity.getEmpCode();
                        HttpSession session = request.getSession();
                        session.setAttribute("name", user);
                        session.setAttribute("role", emplogintableEntity.getRoleCode());
                        session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                        session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
                        result = "success";
                    }


                } else if (currentDate.getTime() > expireDate.getTime()) {
                    result = "expirePassword";

                } else if (password.equalsIgnoreCase(emplogintableEntity.getEmpPassword())) {
                    dbUser = emplogintableEntity.getEmpCode();
                    HttpSession session = request.getSession();
                    session.setAttribute("name", user);
                    session.setAttribute("role", emplogintableEntity.getRoleCode());
                    session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                    session.setAttribute("spoke_code", emplogintableEntity.getSpoke_code());
                    result = "success";
                }
            } else {
                result = "error";
            }
        } catch (Exception e) {
            result = "error";
            logger.error("", e);
        }
        jsonObject.put("status", result);
        jsonObject.put("user", dbUser);
        return jsonObject;
    }
*/

   /*
    // this code use for password encryption
    @RequestMapping(value = "/login1", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject login1(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);
        String result = null;
        JSONObject jsonObject = new JSONObject();
        String user = request.getParameter("userName");
        String password = request.getParameter("password");
        String dbUser = null;
        EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        String session1 = (String) request.getAttribute(user);
        if (session1 != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
        }
        if (emplogintableEntity != null) {

            Timestamp expireDate = emplogintableEntity.getExpireDate();
            Timestamp currentDate = new Timestamp(new Date().getTime());
            //    if(expireDate==null && currentDate.getTime()>expireDate.getTime()){
            if (expireDate == null ) {

                Date currentdate1= new Date();
                long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
                currentdate1.setTime(currentdate1.getTime() + expireTime);

                emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                emplogintableEntity.setExpireDate(new Timestamp(currentdate1.getTime()));
                emplogintableEntity.setEmpPassword(hashedPassword);
                agentPaytmService.updatePassword(emplogintableEntity,null);

                if(passwordEncoder.matches(password, emplogintableEntity.getEmpPassword()) || password.equalsIgnoreCase(emplogintableEntity.getEmpPassword())) {
                    dbUser = emplogintableEntity.getEmpCode();
                    HttpSession session = request.getSession();
                    session.setAttribute("name", user);
                    session.setAttribute("role", emplogintableEntity.getRoleCode());
                    session.setAttribute("cirCode", emplogintableEntity.getCirCode());
                    result = "success";
                }


            } else if(currentDate.getTime()>expireDate.getTime()){
                result = "expirePassword";

            } else if(passwordEncoder.matches(password, emplogintableEntity.getEmpPassword()) || password.equalsIgnoreCase(emplogintableEntity.getEmpPassword())) {
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
*/
   @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
   @ResponseBody
   public JSONObject resetPassword(HttpServletRequest request, HttpServletResponse response) {

       // JSONObject jsonObject = new JSONObject();
       String result = null;
       String useroldpassword = null;
       String finalpass = null;
       String passCsv="";
       JSONObject jsonObject=new JSONObject();
       try {
           String user = request.getParameter("userName");
           String oldpassword = request.getParameter("oldpassword");
           String newpassword = request.getParameter("password");


           Date expireDate = new Date();
           System.out.println("Current Date   " + expireDate);
           long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
           expireDate.setTime(expireDate.getTime() + expireTime);
           System.out.println("Date After 30 Days  " + expireDate);

           EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
           if (emplogintableEntity != null) {
               if (oldpassword.equals(emplogintableEntity.getEmpPassword())) {
                   String lastThrePassword = emplogintableEntity.getLastThreePassword();
                   lastThrePassword = (lastThrePassword!=null)?lastThrePassword:"";
                   String[] lastPassArr  = lastThrePassword.split(",");
                    /*for(String pass:lastPassArr){

                    }
                    *///Arrays.asList(lastPassArr).contains(newpassword)
                   if (!Arrays.asList(lastPassArr).contains(newpassword) && (!oldpassword.equals(newpassword))) {
                       //Old password mismatch
                       String lastThreePassword = emplogintableEntity.getLastThreePassword();
                       if(lastThreePassword!=null) {
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
                       }else{
                           passCsv=newpassword;
                       }
                       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                       String hashedPassword = passwordEncoder.encode(newpassword);
                       emplogintableEntity.setEmpPassword(newpassword);
                       emplogintableEntity.setLastThreePassword(passCsv);
                       emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));
                       emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));
                       result = agentPaytmService.updatePassword(emplogintableEntity, newpassword);
                       if (result.equalsIgnoreCase("done")) {
                           result = "success";
                       }

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
           logger.error("reset Password error ",e);
           result = "Technical Error";

       }
       jsonObject.put("status", result);

       return jsonObject;

   }








   /* @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject resetPassword(HttpServletRequest request, HttpServletResponse response) {
        //logger.info("Welcome home! The client locale is {}.", locale);
        JSONObject jsonObject = new JSONObject();
        String result = null;
        try {
            String user = request.getParameter("userName");
            String password = request.getParameter("password");
            String oldpassword=request.getParameter("oldpassword");
            Date expireDate = new Date();
            System.out.println("Current Date   " + expireDate);
            long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
            expireDate.setTime(expireDate.getTime() + expireTime);

            System.out.println("Date After 30 Days  " + expireDate);


            EmplogintableEntity emplogintableEntity = userService.getUserByEmpcode(user);
            if(emplogintableEntity!=null) {
                 if(emplogintableEntity.getEmpPassword().equals(oldpassword)) {
                     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                     String hashedPassword = passwordEncoder.encode(password);
                     emplogintableEntity.setEmpPassword(password);
                     emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

                     emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));
                     result = agentPaytmService.updatePassword(emplogintableEntity, password);
                     if (result.equalsIgnoreCase("done")) {
                         result = "success";
                     }
                 }else{
                     result="Old Password not valid";
                 }
            }else{
                result="Old Password not valid";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        jsonObject.put("status", result);

        return jsonObject;
    }*/

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
            int circlecode=(Integer)session.getAttribute("cirCode");
            if (importBy != null) {

                byte[] bytes = mpf.getBytes();
                mpf.getContentType();
                String filename = mpf.getOriginalFilename();
                System.out.println("  file name  " + filename);
                String extension = filename.split("\\.")[1];

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(10000);
                String serverPath = System.getenv("JBOSS_BASE_DIR");
                logger.info("server path>>>>>>>   " + serverPath);
            /*File dir1 = new File(serverPath + File.separator + "RejectedFile");*/
                File dir = new File(serverPath + File.separator + "FileUploder");
                if (!dir.exists()) {
                    dir.mkdirs();
                }


                File[] files=   dir.listFiles();
                for (File file : files) {

                    long diff = new Date().getTime() - file.lastModified();

                    if (diff > 24 * 60 * 60 * 1000) {
                        file.delete();
                    }
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


                if (extension.equalsIgnoreCase("csv")) {
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
                            if (!StringUtils.isEmpty(customerData[1]) && (customerData[3].length() == 10 && StringUtils.isNumeric(customerData[3])) && telecallMastEntity == null && paytmMastEntity == null ) {
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
                    if(circlecode==4) {
                        jsonObject = uploadExcel(serverFile, importBy, paytmMasterService, pinMasterService);
                        return jsonObject;
                    }
                    if(circlecode==13 || circlecode==14){
                        jsonObject = uploadExcelUP(serverFile, importBy, paytmMasterService, pinMasterService);
                        return jsonObject;
                    }
                }
            } else {
                jsonObject.put("authencation", "failed");
            }

        } catch (FileNotFoundException e) {
            logger.error("File Not Found ", e);
            result = "File not Uploaded";
        } catch (Exception e) {
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
        int circleCode = 0;

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
                circleCode = (Integer) session.getAttribute("cirCode");
            }

            if (circleCode != 0) {
                byte[] bytes = mpf.getBytes();
                mpf.getContentType();
                String filename = mpf.getOriginalFilename();
                System.out.println("  file name  " + filename);
                String extension = filename.split("\\.")[1];

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

                if (extension.equalsIgnoreCase("csv")) {
                    // we are getting  csv  file of agent then have to write code here

                } else {

                    jsonObject = uploadAgent(serverFile, importBy, agentPaytmService, circleCode);
                    return jsonObject;

                }
            } else {

                jsonObject.put("authentication", "failed");
            }
        } catch (FileNotFoundException e) {
            logger.error("File Not Found ", e);

            result = "File not Uploaded";
        } catch (Exception e) {

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
    public JSONObject downloadList(HttpServletRequest request, HttpServletResponse response) {

        String mobilenumber = request.getParameter("mobilenumber");
        JSONObject jsonObject = new JSONObject();
        try {
            if (mobilenumber == null || mobilenumber.equalsIgnoreCase("undefined")) {
                mobilenumber = "";
            }
            String to = request.getParameter("todate");
            String from = request.getParameter("fromdate");
            if (!to.equalsIgnoreCase("undefined") && !to.isEmpty() & !to.equalsIgnoreCase("")) {
                from = from.substring(6, 10) + "-" + from.substring(3, 5) + "-" + from.substring(0, 2);
                to = to.substring(6, 10) + "-" + to.substring(3, 5) + "-" + to.substring(0, 2);
            } else {
                from = "";
                to = "";
            }

            jsonObject = qcStatusService.downloadList(mobilenumber, to, from);
        } catch (Exception e) {
           logger.error("",e);
        }
        return jsonObject;

    }

    @RequestMapping(value = "/downloadPdf", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void downloadPdf(HttpServletRequest request, HttpServletResponse response) {
        String mobileNo = request.getParameter("mobileNo");
        File localPath = null;
        FileInputStream fis = null;
        OutputStream outStream = null;
        StringBuffer strbr = new StringBuffer();
        String imagePath = "";
        int circleCode = 4;
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

            String path = localPath.toString() + File.separator + mobileNo + ".pdf";

            File localpdf = new File(path);
            JSONObject jsonObject = qcStatusService.qcCustomerDetails(mobileNo);
            String ftpImagePath = (String) jsonObject.get("imagePath");

            String[] paths = ftpImagePath.split("/");
            for (int i = 1; i < paths.length - 1; i++) {
                strbr = strbr.append(paths[i]);
                strbr.append("/");
            }

            imagePath = strbr.toString();
            //     imagePath=imagePath+File.separator;
            localPath = new File(localPath + File.separator);
            String result = copyFtpToLocal(userService, path, imagePath, mobileNo, circleCode);
            if (result.equalsIgnoreCase("done")) {
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
            try {
                fis.close();
                outStream.close();

            } catch (Exception e) {

            }

        }
        return;

    }


    public static String copyFtpToLocal(UserService userService, String localPath, String fptPath, String mobileNumber, int circle) {

        String ftpHost = "";
        String username = "";
        String password = "";
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        String result = null;


        try {
            JSONObject jsonObject1 = userService.getEmpFtpDetails(circle);
            String ftpIP = (String) jsonObject1.get("ftpIp");
            String userName1 = (String) jsonObject1.get("userName");
            String password1 = (String) jsonObject1.get("password");
            logger.info("FPT File IP   " + ftpIP);
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
            ftpClient.retrieveFile(mobileNumber + ".pdf", fos);
            fos.flush();
            result = "done";

        } catch (Exception e) {
            result = "err";
            e.printStackTrace();

        } finally {
            try {
                fos.close();
                ftpClient.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("", e);
            }
        }


        return result;
    }





   /* it is not use currently*/

    @RequestMapping(value = "/getFilePath", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getFilePath(@RequestParam("name") String name,
                                  @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession session) {


        session = request.getSession();
        String Username = (String) session.getAttribute("name");

        JSONObject jsonObject = new JSONObject();
        BufferedReader br = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String result = null;
        File serverFile = null;
        String line = "";
        String cvsSplitBy = "\\|";

        if (Username != null) {
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
        } else {
            jsonObject.put("authentication", "faiied");
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
            if (userName != null) {

                JSONObject teleJson = paytmMasterService.telecallingScreen(userName, cirCode);
                List<StateMasterEntity> stateList = paytmMasterService.getStateList();
                List<CallStatusMasterEntity> statusList = paytmMasterService.getStatusList();
                if (teleJson.get("cust_uid") != null && !teleJson.get("cust_uid").equals("")) {
                    String custid = (String) teleJson.get("cust_uid");
                    int cust_uid = Integer.parseInt(custid);
                    json = paytmMasterService.getPaytmMastData(cust_uid);
                /*json = paytmMasterService.getPaytmMastData((String) teleJson.get("mobileNo"));*/
                }
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar date = Calendar.getInstance();
                Calendar date1 = Calendar.getInstance();
                String dateList1[] = new String[7];
                String dateList2[] = new String[3];
                List<String> dateList = new ArrayList<String>();
                List<String> dateListReject = new ArrayList<String>();
                for (int i = 0; i < 7; i++) {
                    dateList1[i] = format.format(date1.getTime());
                    date1.add(Calendar.DATE, 1);
                    dateList.add(dateList1[i]);
                }
                for (int i = 0; i < 3; i++) {
                    dateList2[i] = format.format(date.getTime());
                    date.add(Calendar.DATE, 1);
                    dateListReject.add(dateList2[i]);
                }

                jsonObject.put("teleData", teleJson);
                jsonObject.put("stateList", stateList);
                jsonObject.put("statusList", statusList);
                jsonObject.put("dateList", dateListReject);
                jsonObject.put("dateList1", dateList);
                jsonObject.put("paytmmastjson", json);
            } else {
                jsonObject.put("authentication", "failed");
            }

            logger.info("telecalling screen data fetch sucessfully>>>>>>>>");
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

            if (userName != null) {

                String agentName = request.getParameter("agent_name");
                String agentCode = request.getParameter("agent_code");
                String empCode = "";
                String mobileNo = request.getParameter("phone");
                String circleOffice = request.getParameter("circle_office");
                String spokeCode = request.getParameter("spoke_code");
                String avalTime = request.getParameter("avl_time");
                String pincode = request.getParameter("pin_code");
                String multipin = "M";
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

                PaytmagententryEntity paytmagententryEntity1 = agentPaytmService.findByPrimaryKey(agentCode);

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

                if (paytmagententryEntity1 != null) {
                    String result = agentPaytmService.saveAgentPinMaster1(paytmagententryEntity);
                    if (result.equals("done")) {
                        msg = "Agent Succesfully Registered";
                        jsonObject.put("msg", msg);
                        jsonObject.put("status", "success");

                    } else {
                        msg = "Agent Already Registred with Same Pincode";
                        jsonObject.put("msg", msg);
                        jsonObject.put("status", "success");
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


            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            logger.error("registration error ",e);
            msg = "Agent not Registered ";
            jsonObject.put("msg", msg);
            jsonObject.put("status", "error");


        }


        return jsonObject;
    }

    @RequestMapping(value = "/agentRegistration1", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject agentRegistration1(HttpServletRequest request) {
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

            if (userName != null) {

                String agentName = request.getParameter("agent_name");
                String agentCode = request.getParameter("agent_code");
                String empCode = "";
                String mobileNo = request.getParameter("phone");
                String circleOffice = request.getParameter("circle_office");
                String spokeCode = request.getParameter("spoke_code");
                String avalTime = request.getParameter("avl_time");
             //   String pincode = request.getParameter("pin_code");
                String multipin = "M";
                String email = request.getParameter("email");
                PaytmagententryEntity paytmagententryEntity = new PaytmagententryEntity();
                paytmagententryEntity.setAfullname(agentName);
                paytmagententryEntity.setAcode(agentCode);
                paytmagententryEntity.setEmpcode(empCode);
                paytmagententryEntity.setAphone(mobileNo);
                paytmagententryEntity.setAspokecode(spokeCode);
                paytmagententryEntity.setAavailslot(avalTime);

                paytmagententryEntity.setMulitplePin(multipin);
                paytmagententryEntity.setAemailId(email);
                paytmagententryEntity.setImportby(userName);
                paytmagententryEntity.setImportdate(new Timestamp(new Date().getTime()));
                List<String> pincodeList=circleService.getBySpokeCode(spokeCode);
                int count=0;
                if(pincodeList.size()>0) {
                    for (String agentPincode : pincodeList) {
                        count++;
                        paytmagententryEntity.setApincode(agentPincode);

                        PaytmagententryEntity paytmagententryEntity1 = agentPaytmService.findByPrimaryKey(agentCode);

                        if (paytmagententryEntity1 != null) {
                            String result = agentPaytmService.saveAgentPinMaster1(paytmagententryEntity);
                            if (result.equals("done")) {
                                count++;
                                msg = "Agent Succesfully Registered with Number of Pincode = " + count;
                                jsonObject.put("msg", msg);
                                jsonObject.put("status", "success");


                            } else {
                                msg = "Agent Already Registred with Same Pincodes";
                                jsonObject.put("msg", msg);
                                jsonObject.put("status", "success");
                            }

                        } else {
                            msg = agentPaytmService.saveAgent(paytmagententryEntity, circleMastEntity);
                            if ("done".equalsIgnoreCase(msg)) {
                                msg = "Agent Succesfully Registered";
                                jsonObject.put("msg", msg);
                                jsonObject.put("status", "success");
                                count++;
                            } else {
                                msg = "Agent not Registered Try Again ";
                                jsonObject.put("msg", msg);
                                jsonObject.put("status", "error");
                            }
                        }

                    }
                }else{
                    msg = "No Pincode Registered on this spoke";
                    jsonObject.put("msg", msg);
                    jsonObject.put("status", "error");
                }
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            logger.error("registration error ",e);
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
        String password = null;
        try {

            String userName = "system";
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
            }
            if (userName != null) {
                String empName = request.getParameter("name");
                String empcode = request.getParameter("empcode");
                String mobileNo = request.getParameter("phone");
                String circlecode1 = request.getParameter("circle_office");
                String role = request.getParameter("empType");
                String spokeCode = request.getParameter("spoke_code");
                String password1 = request.getParameter("password");

                circleCode = Integer.parseInt(circlecode1);

                if (role.equalsIgnoreCase("DULD")) {
                    empcode = userService.getClientCode(role);

                }
                if (circleCode != 0) {
                    circleMastEntity = circleService.findByPrimaryKey(circleCode);
                }

                EmplogintableEntity emplogintableEntity1 = userService.getUserByEmpcode(empcode);
               if(emplogintableEntity1==null){

                EmplogintableEntity emplogintableEntity = new EmplogintableEntity();
                emplogintableEntity.setEmpCode(empcode);
                emplogintableEntity.setEmpName(empName);
                emplogintableEntity.setEmpPhone(mobileNo);
                emplogintableEntity.setEmpLeftStatus("N");

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(10000);
                String alphaPassCaps = RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                String alphaPassSpec = RandomStringUtils.random(1, "!@$%^&");
                String alphaPassNum = RandomStringUtils.randomNumeric(4);
                String alphaPassLower = RandomStringUtils.random(2, "abcdefghijklmnopqrstuvwxyz");
                password = alphaPassCaps + alphaPassLower + alphaPassSpec + alphaPassNum;
                logger.info(password);
                /*password = empcode.substring(0, 4) + "@" + randomInt;*/
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);

                emplogintableEntity.setEmpPassword(password);
                emplogintableEntity.setLastThreePassword(password);
                emplogintableEntity.setCircleMastByCirCode(circleMastEntity);
                emplogintableEntity.setRoleCode(role);
                emplogintableEntity.setEmpStatus(1);
                emplogintableEntity.setImportBy(userName);
                emplogintableEntity.setSpoke_code(spokeCode);
                Date expireDate = new Date();

                System.out.println("Current Date   " + expireDate);
                long expireTime = (long) 30 * 1000 * 60 * 60 * 24;
                expireDate.setTime(expireDate.getTime() + expireTime);

                System.out.println("Date After 30 Days  " + expireDate);

                emplogintableEntity.setImportDate(new Timestamp(new Date().getTime()));

                emplogintableEntity.setExpireDate(new Timestamp(expireDate.getTime()));

                msg = agentPaytmService.saveEmployee(emplogintableEntity, password);

                if ("done".equalsIgnoreCase(msg)) {
                    msg = "Succesfully Registered";
                    jsonObject.put("msg", msg);
                    jsonObject.put("status", "success");
                } else {
                    msg = "Not Registered, Number Already Exist ";
                    jsonObject.put("msg", msg);
                    jsonObject.put("status", "error");
                }
            }else {
                   msg = "User Already Exist ";
                   jsonObject.put("msg", msg);
                   jsonObject.put("status", "error");
            }
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Not Registered ";
            jsonObject.put("msg", msg);
            jsonObject.put("status", "error");
        }

        return jsonObject;
    }


    @RequestMapping(value = "/customerCalling", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject Calling(HttpServletRequest request) {

        JSONObject returnObj = new JSONObject();
        String userName = "system";
        String agentNo = "";
        String returnString = null;
        String customerId = "";
        String result = "";
        String alternatephone1 = "";
        String alternatephone2 = "";
        String alternateresult1 = "";
        String alternateresult2 = "";
        String customerphone = "";
        int cust_uid = 0;
        Integer cir_Code=4;
        HttpSession session = request.getSession(false);
        logger.info("calling to customer>>>>> wait");
        if (session != null) {
            userName = (String) session.getAttribute("name");
            agentNo = userService.getUserByEmpcode(userName).getEmpPhone();
        }
        try {
            if (userName != null) {
                String phonenumber = request.getParameter("customer_number");
                String custUid = request.getParameter("cust_uid");
                cust_uid=  Integer.parseInt(request.getParameter("cust_uid"));
                PaytmMastEntity paytmMastEntity=paytmMasterService.getPaytmMastDatas(cust_uid);

                    if (StringUtils.isNotBlank(phonenumber) && phonenumber != null) {
                        result = customerCalling(phonenumber, agentNo);
                    } else {
                        if (paytmMastEntity != null) {
                            customerphone = paytmMastEntity.getCustomerPhone();
                        }
                       result = customerCalling(customerphone, agentNo);
                    }


//2017-01-24
                if (result.equalsIgnoreCase("done")) {
                    returnString = "connected to Customer...";
                } else {
                    returnString = "Unable to connect Customer due to Network Connectivity";
                }
                returnObj.put("msg", returnString);
            } else {
                returnObj.put("authentication", "failed");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return returnObj;
    }


    @RequestMapping(value = "/updateAddress", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject updateAddress(HttpServletRequest request,HttpServletResponse response) {

        JSONObject returnObj = new JSONObject();
        String userName = "system";
        String changePincode="";
        String changeAddress="";
        String result="";

        int cust_uid = 0;
        HttpSession session = request.getSession(false);
        logger.info("calling to customer>>>>> wait");
        if (session != null) {
            userName = (String) session.getAttribute("name");
        }
        try {
            if (userName != null) {
                changePincode=request.getParameter("zipcode");
                changeAddress=request.getParameter("changeAddress");
                cust_uid=  Integer.parseInt(request.getParameter("cust_uid"));
                PaytmMastEntity paytmMastEntity=paytmMasterService.getPaytmMastDatas(cust_uid);
                if(paytmMastEntity!=null){
                    String lastAddress=paytmMastEntity.getAddress();
                    String lastpincode=paytmMastEntity.getPincode();
                    paytmMastEntity.setLastAddress(lastAddress);
                    paytmMastEntity.setLastPincode(lastpincode);
                    paytmMastEntity.setAddressStatus("U");
                    paytmMastEntity.setAddress(changeAddress);
                    paytmMastEntity.setPincode(changePincode);
                    result =paytmMasterService.updatePaytmMast(paytmMastEntity);
                    if(result.equalsIgnoreCase("done")){
                        result="success";
                    }
                }


            } else {
                returnObj.put("authentication", "authentication failed");
            }
        } catch (Exception e) {
            returnObj.put("authentication", "Technical error");
            logger.error("", e);
        }
        returnObj.put("status",result);
        return returnObj;
    }



    public static JSONObject uploadExcel(File path, String importBy, PaytmMasterService paytmMasterService, PaytmPinMasterService pinMasterService) {
        Row row = null;
        PaytmMastEntity paytmMastEntity = null;
        PaytmPinMaster paytmPinMaster = null;
        JSONObject jsonObject = new JSONObject();
        String result = "File Not Uploaded";

        JSONObject json1 = new JSONObject();
        int count = 0;
        int successCount = 0;
        int rejectCount = 0;
        FileInputStream file = null;
        boolean flag=false;
        boolean flag1=true;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {


            file = new FileInputStream(path);
            ArrayList<Map<String, String>> list = new ArrayList();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < 1; i++) {

                row = sheet.getRow(i);
                String customerID = row.getCell(0).getStringCellValue().trim();
                String co_id = row.getCell(1).getStringCellValue().trim();
                String co_status = row.getCell(2).getStringCellValue().trim();
                String ch_reason_desc = row.getCell(3).getStringCellValue().trim();
                String sim_no = row.getCell(4).getStringCellValue().trim();
                String sim = row.getCell(5).getStringCellValue().trim();
                String cellno = row.getCell(6).getStringCellValue().trim();
                String sim_plan_desc = row.getCell(7).getStringCellValue().trim();
                String dear = row.getCell(8).getStringCellValue().trim();
                String name = row.getCell(9).getStringCellValue().trim();
                String address1 = row.getCell(10).getStringCellValue().trim();
                String address2 = row.getCell(11).getStringCellValue().trim();
                String address3 = row.getCell(12).getStringCellValue().trim();
                String addressType = row.getCell(13).getStringCellValue().trim();
                String city = row.getCell(14).getStringCellValue().trim();
                String zip = row.getCell(15).getStringCellValue().trim();
                String contact = row.getCell(16).getStringCellValue().trim();
                String remarks = row.getCell(17).getStringCellValue().trim();
                String requestDate = row.getCell(18).getStringCellValue().trim();
                String lot_no = row.getCell(19).getStringCellValue().trim();
                //     String circle = row.getCell(10).getStringCellValue().trim();


            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                JSONObject json = new JSONObject();
                System.out.println("row start");
                row = sheet.getRow(i);
                System.out.println("get row start");
                if (row != null && row.getCell(0) != null) {
                    String pincode = null;
                    String mobileNumber = null;
                    String customerId = null;
                    String co_id = null;
                    String alternateNumber = null;
                    String alternateNumber1 = null;
                    String alternateNumber2 = null;
                    String request_date = null;
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    System.out.println(i);
                    if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                        customerId = row.getCell(0).getStringCellValue().trim();
                    } else {
                        customerId = NumberToTextConverter.toText(row.getCell(0).getNumericCellValue()).trim();
                    }
                    if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
                        co_id = row.getCell(1).getStringCellValue().trim();
                    } else {
                        co_id = NumberToTextConverter.toText(row.getCell(1).getNumericCellValue()).trim();
                    }
                    String co_status = row.getCell(2).getStringCellValue().trim();
                    String ch_reason_desc = row.getCell(3).getStringCellValue().trim();
                    String sim_type = row.getCell(5).getStringCellValue().trim();

                    //    String appointmantDate = row.getCell(1).getStringCellValue().trim();
                    //  String createdDate = row.getCell(2).getStringCellValue().trim();

                    //  String mobileNumber = row.getCell(4).getStringCellValue().trim();
                    if (row.getCell(6).getCellType() == Cell.CELL_TYPE_STRING) {
                        mobileNumber = row.getCell(6).getStringCellValue().trim();

                    } else {
                        mobileNumber = NumberToTextConverter.toText(row.getCell(6).getNumericCellValue()).trim();
                    }
                    String sim_plan_desc = row.getCell(7).getStringCellValue().trim();
                    String name = row.getCell(9).getStringCellValue().trim();

                    String address = row.getCell(10).getStringCellValue().trim() + " " + row.getCell(11).getStringCellValue().trim() + " " + row.getCell(12).getStringCellValue().trim();
                    String city = row.getCell(14).getStringCellValue().trim();

                    //String leadStage = row.getCell(6).getStringCellValue().trim();

                    //String leadSubStage = row.getCell(7).getStringCellValue().trim();

                    //  String pincode = row.getCell(8).getStringCellValue().trim();


                    if (row.getCell(15).getCellType() == Cell.CELL_TYPE_STRING) {
                        pincode = row.getCell(15).getStringCellValue().trim();

                    } else {
                        pincode = NumberToTextConverter.toText(row.getCell(15).getNumericCellValue()).trim();
                    }
                    if (row.getCell(16).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        alternateNumber = NumberToTextConverter.toText(row.getCell(16).getNumericCellValue()).trim();
                        //alternateNumber=row.getCell(16).getStringCellValue().trim();
                    } else {
                        alternateNumber = row.getCell(16).getStringCellValue().trim();
                    }
                    if(StringUtils.isNotBlank(alternateNumber)) {
                        String altNum = alternateNumber.replace("'", "");
                        String altNumbers = altNum.replace("-", " ");
                        String[] alternateNumbers = altNumbers.split("\\s+");
                        List<String> finalAltNumList = new ArrayList<String>();
                        for (String num : alternateNumbers) {
                            if (num.length() == 10 && StringUtils.isNumeric(num)) {
                                finalAltNumList.add(num);
                            }
                        }

                        if (alternateNumber1 == alternateNumber2) {
                            alternateNumber1 = finalAltNumList.get(0);
                        } else {
                            alternateNumber1 = finalAltNumList.get(0);
                            alternateNumber2 = finalAltNumList.get(1);
                        }
                    }
                    String remarks = row.getCell(17).getStringCellValue().trim();
                    Date reqDate = row.getCell(18).getDateCellValue();

                    if (row.getCell(18).getCellType() == Cell.CELL_TYPE_STRING) {
                        request_date = row.getCell(18).getStringCellValue().trim();
                        request_date = request_date.trim() + " 00:00:00";
                    } else if (row.getCell(18).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        //request_date=NumberToTextConverter.toText(row.getCell(18).getNumericCellValue()).trim();

                        request_date = df.format(reqDate).trim();
                    }
                    String lot_no = row.getCell(19).getStringCellValue().trim();

                    if (mobileNumber.length() == 10 && StringUtils.isNumeric(mobileNumber)) {
                        // paytmMastEntity = paytmMasterService.getPaytmMaster(mobileNumber);
                        /*DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
                        String requestDate=df.format(request_date);*/
                        paytmMastEntity = paytmMasterService.getPaytmMasterByDate(mobileNumber, request_date);
                        //paytmMastEntity=paytmMasterService.getPaytmMasterByDate(mobileNumber,newReqDate);
                        if(paytmMastEntity!=null){
                            Date date1=paytmMastEntity.getRequestDate();
                            Date date2=df.parse(request_date);
                            flag=date1.getTime()==date2.getTime();
                            if(paytmMastEntity.getFinalStatus()!=null) {
                                flag1 = paytmMastEntity.getFinalStatus().equalsIgnoreCase("close");
                            }
                        }


                    }
                    if ((pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                        int pincode1 = Integer.parseInt(pincode);
                        paytmPinMaster = pinMasterService.getByPincode(pincode1);

                    }
                    if (!flag && (pincode.length() == 6 && StringUtils.isNumeric(pincode) && flag1 && !StringUtils.isBlank(sim_type))) {
                        map.put("customerID", customerId);
                        map.put("name", name);
                        map.put("mobileNumber", mobileNumber);
                        map.put("address", address);
                        map.put("pincode", pincode);
                        map.put("city", city);
                        map.put("importBy", importBy);
                        map.put("co_id", co_id);
                        map.put("co_status", co_status);
                        map.put("ch_reason_desc", ch_reason_desc);
                        map.put("sim_type", sim_type);
                        map.put("sim_plan_desc", sim_plan_desc);
                        map.put("alternateNumber1", alternateNumber1);
                        map.put("alternateNumber2", alternateNumber2);
                        map.put("remarks", remarks);
                        map.put("request_date", request_date);
                        map.put("lot_no", lot_no);
                        list.add(map);
                        successCount++;

                        if (paytmPinMaster == null) {
                            json.put("CustomerID", customerId);
                            json.put("Resion", "This Customer Pincode not match with Softage Circles PinCode .");
                            json1.put("rejectedRecord" + count, json);
                        }
                    }else if(!flag && (pincode.length() == 6 && StringUtils.isNumeric(pincode) && flag1 && StringUtils.isBlank(sim_type))){
                        json.put("CustomerID", customerId);
                        json.put("Resion", "Sim Type is blank");
                        json1.put("rejectedRecord" + count, json);
                    } else if (flag) {
                        json.put("CustomerID", customerId);
                        json.put("Resion", "Duplicate Customers");
                        json1.put("rejectedRecord" + count, json);

                    } else if ((pincode.length() != 6 && !StringUtils.isNumeric(pincode))) {
                        json.put("CustomerID", customerId);
                        json.put("Resion", "PinCode not valid so please check It.");
                        json1.put("rejectedRecord" + count, json);

                    }


                }
                count++;
            }


            jsonObject.put("rejectedRecord", json1);
            System.out.println("list   " + list);
            rejectCount = count - successCount;

            jsonObject.put("status", "success");
            result = paytmMasterService.savePaytmMasterExcel(list);
            if ("done".equalsIgnoreCase(result)) {
                result = "Successfully Uploaded Customer  = " + successCount + " , Rejected Customer  =" + rejectCount;
            }
        } catch (Exception e) {
            result = "Technical error in uploading";
            logger.error("Customer Uploading", e);

        } finally {
            try {
                file.close();

            } catch (Exception e) {
                logger.error("",e);
            }
        }
        jsonObject.put("Message", result);
        return jsonObject;
    }

    public static JSONObject uploadExcelUP(File path, String importBy, PaytmMasterService paytmMasterService, PaytmPinMasterService pinMasterService) {
        Row row = null;
        PaytmMastEntity paytmMastEntity = null;
        PaytmPinMaster paytmPinMaster = null;
        JSONObject jsonObject = new JSONObject();
        String result = "File Not Uploaded";

        JSONObject json1 = new JSONObject();
        int count = 0;
        int successCount = 0;
        int rejectCount = 0;
        FileInputStream file = null;
        boolean flag=false;
        boolean flag1=true;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {


            file = new FileInputStream(path);
            ArrayList<Map<String, String>> list = new ArrayList();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < 1; i++) {

                row = sheet.getRow(i);
                String BILLCYCLE= row.getCell(0).getStringCellValue().trim();
                String SIM = row.getCell(1).getStringCellValue().trim();
                String MSISDN = row.getCell(2).getStringCellValue().trim();
                String TMCODE_DESC = row.getCell(3).getStringCellValue().trim();
                String TITLE = row.getCell(4).getStringCellValue().trim();
                String FIRST_NAME = row.getCell(5).getStringCellValue().trim();
                String MIDDLE_NAME = row.getCell(6).getStringCellValue().trim();
                String LAST_NAME = row.getCell(7).getStringCellValue().trim();
                String ADDRESS1 = row.getCell(8).getStringCellValue().trim();
                String ADDRESS2 = row.getCell(9).getStringCellValue().trim();
                String ADDRESS3 = row.getCell(10).getStringCellValue().trim();
                String CITY = row.getCell(11).getStringCellValue().trim();
                String ZIP = row.getCell(12).getStringCellValue().trim();
                String STATE = row.getCell(13).getStringCellValue().trim();
                String CONTACT_NO_2 = row.getCell(14).getStringCellValue().trim();
                String PRGDESC = row.getCell(15).getStringCellValue().trim();
            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                JSONObject json = new JSONObject();
                System.out.println("row start");
                row = sheet.getRow(i);
                System.out.println("get row start");
                if (row != null && row.getCell(0) != null) {
                    String pincode = null;
                    String mobileNumber = null;
                    String customerId = null;
                    String co_id = null;
                    String alternateNumber = null;
                    String alternateNumber1 = null;
                    String alternateNumber2 = null;
                    String request_date = null;
                    String sim_type="Normal";
                    customerId="";
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    System.out.println(i);

                    if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                        mobileNumber = row.getCell(2).getStringCellValue().trim();

                    } else {
                        mobileNumber = NumberToTextConverter.toText(row.getCell(2).getNumericCellValue()).trim();
                    }
                    String sim_plan_desc = row.getCell(3).getStringCellValue().trim();
                    String firstname=null;
                    String midname=null;
                    String lastname=null;
                    if(row.getCell(5).getCellType()==Cell.CELL_TYPE_NUMERIC){
                        firstname= NumberToTextConverter.toText(row.getCell(5).getNumericCellValue()).trim();
                    }else{
                        firstname=row.getCell(5).getStringCellValue().trim();
                    }
                    if(row.getCell(6)!=null) {
                        if (row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            midname = NumberToTextConverter.toText(row.getCell(6).getNumericCellValue()).trim();
                        }else{
                            midname=row.getCell(6).getStringCellValue().trim();
                        }
                    }else{
                        midname="";
                    }
                    if(row.getCell(7)!=null) {
                        if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            lastname = NumberToTextConverter.toText(row.getCell(7).getNumericCellValue()).trim();
                        }else{
                            lastname =row.getCell(7).getStringCellValue().trim();
                        }
                    }else{
                        lastname="";
                    }
                    String name = firstname+" "+midname+" "+lastname;

                    String address1=null;
                    String address2=null;
                    String address3=null;
                    if(row.getCell(8)!=null){
                        if(row.getCell(8).getCellType()==Cell.CELL_TYPE_STRING){
                            address1= row.getCell(8).getStringCellValue().trim();
                        }
                        else{
                            address1=NumberToTextConverter.toText(row.getCell(8).getNumericCellValue()).trim();
                        }
                    }else{
                        address1="";
                    }

                    if(row.getCell(9)!=null){
                        if(row.getCell(9).getCellType()==Cell.CELL_TYPE_STRING){
                            address2= row.getCell(9).getStringCellValue().trim();
                        }
                        else{
                            address2=NumberToTextConverter.toText(row.getCell(9).getNumericCellValue()).trim();
                        }
                    }else{
                        address2="";
                    }

                    if(row.getCell(10)!=null){
                        if(row.getCell(10).getCellType()==Cell.CELL_TYPE_STRING){
                            address3= row.getCell(10).getStringCellValue().trim();
                        }
                        else{
                            address3=NumberToTextConverter.toText(row.getCell(10).getNumericCellValue()).trim();
                        }
                    }else{
                        address3="";
                    }

                    String address =address1+" "+address2+" "+address3;
                    String city = row.getCell(11).getStringCellValue().trim();

                    //String leadStage = row.getCell(6).getStringCellValue().trim();

                    //String leadSubStage = row.getCell(7).getStringCellValue().trim();

                    //  String pincode = row.getCell(8).getStringCellValue().trim();


                    if (row.getCell(12).getCellType() == Cell.CELL_TYPE_STRING) {
                        pincode = row.getCell(12).getStringCellValue().trim();

                    } else {
                        pincode = NumberToTextConverter.toText(row.getCell(12).getNumericCellValue()).trim();
                    }
                    if(row.getCell(14)!=null) {
                        if (row.getCell(14).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            alternateNumber = NumberToTextConverter.toText(row.getCell(14).getNumericCellValue()).trim();
                            //alternateNumber=row.getCell(16).getStringCellValue().trim();
                        } else {
                            alternateNumber = row.getCell(14).getStringCellValue().trim();
                        }
                    }else{
                        alternateNumber="";
                    }
                    //String remarks = row.getCell(17).getStringCellValue().trim();
                    //Date reqDate = row.getCell(18).getDateCellValue();
                    Date reqDate =new Date();
                    request_date=df.format(reqDate);
                  /*  if (row.getCell(18).getCellType() == Cell.CELL_TYPE_STRING) {
                        request_date = row.getCell(18).getStringCellValue().trim();
                        request_date = request_date.trim() + " 00:00:00";
                    } else if (row.getCell(18).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        //request_date=NumberToTextConverter.toText(row.getCell(18).getNumericCellValue()).trim();

                        request_date = df.format(reqDate).trim();
                    }
                    String lot_no = row.getCell(19).getStringCellValue().trim();*/

                    if (mobileNumber.length() == 10 && StringUtils.isNumeric(mobileNumber)) {
                        // paytmMastEntity = paytmMasterService.getPaytmMaster(mobileNumber);
                        /*DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
                        String requestDate=df.format(request_date);*/
                        paytmMastEntity = paytmMasterService.getPaytmMasterByDate(mobileNumber, request_date);
                        //paytmMastEntity=paytmMasterService.getPaytmMasterByDate(mobileNumber,newReqDate);
                        if(paytmMastEntity!=null){
                            Date date1=paytmMastEntity.getRequestDate();
                            Date date2=df.parse(request_date);
                            flag=date1.getTime()==date2.getTime();
                            if(paytmMastEntity.getFinalStatus()!=null) {
                                flag1 = paytmMastEntity.getFinalStatus().equalsIgnoreCase("close");
                            }
                        }


                    }
                    if ((pincode.length() == 6 && StringUtils.isNumeric(pincode))) {
                        int pincode1 = Integer.parseInt(pincode);
                        paytmPinMaster = pinMasterService.getByPincode(pincode1);

                    }
                    if (!flag && (pincode.length() == 6 && StringUtils.isNumeric(pincode) && flag1 && !StringUtils.isBlank(sim_type))) {
                        map.put("customerID", customerId);
                        map.put("name", name);
                        map.put("mobileNumber", mobileNumber);
                        map.put("address", address);
                        map.put("pincode", pincode);
                        map.put("city", city);
                        map.put("importBy", importBy);
                        map.put("co_id", "");
                        map.put("co_status", "A");
                        map.put("ch_reason_desc", "");
                        map.put("sim_type",sim_type );
                        map.put("sim_plan_desc", sim_plan_desc);
                        map.put("alternateNumber1", alternateNumber);
                        map.put("alternateNumber2", "");
                        map.put("remarks", "ASIM");
                        map.put("request_date", request_date);
                        map.put("lot_no", "A");
                        list.add(map);
                        successCount++;

                        if (paytmPinMaster == null) {
                            //json.put("CustomerID", customerId);
                            json.put("CustomerID", mobileNumber);
                            json.put("Resion", "This Customer Pincode not match with Softage Circles PinCode .");
                            json1.put("rejectedRecord" + count, json);
                        }
                    }else if(!flag && (pincode.length() == 6 && StringUtils.isNumeric(pincode) && flag1 && StringUtils.isBlank(sim_type))){
                        json.put("CustomerID", mobileNumber);
                        json.put("Resion", "Sim Type is blank");
                        json1.put("rejectedRecord" + count, json);
                    } else if (flag || !flag1) {
                        json.put("CustomerID", mobileNumber);
                        json.put("Resion", "Duplicate Customers");
                        json1.put("rejectedRecord" + count, json);

                    } else if ((pincode.length() != 6 && !StringUtils.isNumeric(pincode))) {
                        json.put("CustomerID", mobileNumber);
                        json.put("Resion", "PinCode not valid so please check It.");
                        json1.put("rejectedRecord" + count, json);

                    }


                }
                count++;
            }


            jsonObject.put("rejectedRecord", json1);
            System.out.println("list   " + list);
            rejectCount = count - successCount;

            jsonObject.put("status", "success");
            result = paytmMasterService.savePaytmMasterExcel(list);
            if ("done".equalsIgnoreCase(result)) {
                result = "Successfully Uploaded Customer  = " + successCount + " , Rejected Customer  =" + rejectCount;
            }
        } catch (Exception e) {
            result = "Technical error in uploading";
            System.out.println("count is : "+count);
            logger.error("Customer Uploading", e);

        } finally {
            try {
                file.close();

            } catch (Exception e) {
                logger.error("",e);
            }
        }
        jsonObject.put("Message", result);
        return jsonObject;
    }

    public static JSONObject uploadAgent(File path, String importBy, AgentPaytmService agentPaytmService, int circleCode) {

        Row row = null;

        PaytmMastEntity paytmMastEntity = null;
        PaytmPinMaster paytmPinMaster = null;
        JSONObject jsonObject = new JSONObject();
        String result = "File Not Uploaded";

        JSONObject json1 = new JSONObject();
        int count = 0;
        int successCount = 0;
        int rejectCount = 0;
        FileInputStream file = null;

        try {
            file = new FileInputStream(path);

            ArrayList<Map<String, String>> list = new ArrayList();

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < 1; i++) {

                row = sheet.getRow(i);
                String agentName = row.getCell(0).getStringCellValue().trim();
                String agentCode = row.getCell(1).getStringCellValue().trim();
                String mobileNo = row.getCell(2).getStringCellValue().trim();
              //  String pinCode = row.getCell(3).getStringCellValue().trim();
                String circle = row.getCell(3).getStringCellValue().trim();

                String spoke = row.getCell(4).getStringCellValue().trim();

                String avialableSlot = row.getCell(5).getStringCellValue().trim();


            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                JSONObject json = new JSONObject();
                System.out.println("row start");
                String mobileNo = null;
                String pincode = null;

                row = sheet.getRow(i);
                System.out.println("get row start");
                if (row != null && row.getCell(0) != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    System.out.println(i);
                    String agentName = row.getCell(0).getStringCellValue().trim();
                    String agentCode = row.getCell(1).getStringCellValue().trim();
                    if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                        mobileNo = row.getCell(2).getStringCellValue().trim();

                    } else {
                        mobileNo = NumberToTextConverter.toText(row.getCell(2).getNumericCellValue());
                    }
                  /*  if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
                        pincode = row.getCell(3).getStringCellValue().trim();

                    } else {
                        pincode = NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
                    }*/
                    String circle = row.getCell(3).getStringCellValue().trim();
                    String spoke = row.getCell(4).getStringCellValue().trim();
                    String avialableSlot = row.getCell(5).getStringCellValue().trim();
                    if (((mobileNo.length() == 10 && StringUtils.isNumeric(mobileNo)))) {
                        map.put("agentName", agentName);
                        map.put("agentCode", agentCode);
                        map.put("mobileNo", mobileNo);
                        map.put("circle", circle);
                        map.put("pincode", pincode);
                        map.put("spoke", spoke);
                        map.put("avialableSlot", avialableSlot);
                        map.put("importBy", importBy);
                        list.add(map);
                        successCount++;

                    } else {
                        json.put("Resion", " Please check Mobile Number and PinCode for Agentcode " + agentCode);
                        json1.put("rejectedRecord" + count, json);

                    }

                }
                count++;
            }


            jsonObject.put("rejectedRecord", json1);

            System.out.println("list   " + list);
            rejectCount = count  - successCount;

            jsonObject.put("status", "success");

            result = agentPaytmService.saveBulkAgent(list, circleCode);


            if ("done".equalsIgnoreCase(result)) {
                result = "Successfully Uploded Agent  = " + successCount  + " Rejected Agent  =" + rejectCount;
            }else{
                result = "Agent Already Exist ";
            }
        } catch (Exception e) {
            result = "File Not Uploaded";
            logger.error("Agent Uploading", e);

        } finally {
            try {
                file.close();

            } catch (Exception e) {

            }
        }
        jsonObject.put("Message", result);
        return jsonObject;
    }

    @RequestMapping(value = "/getCircle",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getCircle(HttpServletRequest request,HttpSession session){
        JSONObject jsonObject=new JSONObject();
        Integer circle=(Integer)session.getAttribute("cirCode");
        jsonObject.put("circlecode",circle);
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
        String ftpPath = "";
        String url = "";
        String spoke = "";
        int circleCode = 4;
        String imagePath = "/F6Images/20072016/4/KYCNumber1445/";
        JSONObject json = new JSONObject();
        StringBuffer strbr = new StringBuffer("/");
        String customerNumber = request.getParameter("customer_Number");
        String scanID = request.getParameter("scanid");

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                userName = (String) session.getAttribute("name");
                circleCode = (Integer) session.getAttribute("cirCode");

            }

            if (circleCode != 0) {
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
                    System.out.println("pdfurl = " + ftpPath);

                }
            }
            json.put("authentication", "failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("status", "success");
        //    json.put("url","http:/www.sanface.com/pdf/test.pdf");
        json.put("url", ftpPath);
        return json;
    }

    public static JSONObject getDownloadedImages(String customerUID, String path) {
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> listOfPaths = new ArrayList<String>();
        customerUID = "1000000001";
        path = "D://" + customerUID + "//";
        File file = new File(path);
        String[] files = file.list();
        int len = files.length;
        if (len > 0) {
            for (String filename : files) {
                listOfPaths.add(filename);
            }
        }
        jsonObject.put("count", len + 1);
        jsonObject.put("pathList", listOfPaths);
        return jsonObject;
    }

    public static String generatePdfFromSftp(String path, String kycNumber, String localPath, String user, String password, String host) {
        String filename = null;
        Integer port = null;
        FileInputStream fis = null;
        JSch jSch = null;
        Session session = null;
        Channel channel = null;
        ChannelSftp sftpchannel = null;
        try {
            jSch = new JSch();
            session = jSch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            sftpchannel = (ChannelSftp) channel;
            sftpchannel.cd(path);


        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
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
            logger.error("", e);
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
                    logger.error("",f);
                }
            }


            return "";

        }

    }

    private String customerCalling(String mobileNo, String agentNumber) {
        String msg = null;
        String result = "done";
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
            result = "error";
            logger.error("calling", e);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }
        return result;
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
            if (circleCode != 0) {

                List<String> circles = circleService.getCirleList(circleCode);
                String circleName = circles.get(0);
                List<String> spokeList = circleService.getSpokeList(circleName);
                jsonObject.put("circles", circles);
                jsonObject.put("spokeList", spokeList);
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return jsonObject;
    }


    @RequestMapping(value = "/getSpokeByCircle", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getSpokeByCircle(HttpServletRequest request) {
        int circleCode = 4;
        JSONObject jsonObject = new JSONObject();
        try {

            String circode = request.getParameter("circleCode");
            List<String> spokeList = circleService.getSpokeList(circode);
            jsonObject.put("spokeList", spokeList);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getAvailableSlot", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getAvailableSlot(HttpServletRequest request, HttpSession session) {
        int circleCode = 4;
        int pincode1 = 0;
       /* String   dated="";*/
        JSONObject jsonObject = new JSONObject();
        JSONObject finalJson = new JSONObject();

        int timediff = 1;
        List<String> dateArray = new ArrayList<String>();
        session = request.getSession();
        String username = (String) session.getAttribute("name");
        try {
            if (username != null) {

                String pincode = request.getParameter("pincode");
                /* String datefetch = request.getParameter("19/01/2017");
                  dated = datefetch.substring(6, 10) + "-" + datefetch.substring(3, 5) + "-" + datefetch.substring(0, 2);*/

                List<String> agentList = agentPaytmService.getAgentPinMastList(pincode);
                System.out.println(" List Size " + agentList.size());
                Set<String> agentListUnique = new HashSet<String>(agentList);
                System.out.println(" SetSize Size " + agentListUnique.size());

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar date = Calendar.getInstance();
                String dateList1[] = new String[7];
                String dateList2[] = new String[3];
                List<String> dateListReject = new ArrayList<String>();




                for (int i = 0; i < 3; i++) {
                    if(i>0) {
                        date.add(Calendar.DATE, 1);
                    }

                    dateListReject.add(new SimpleDateFormat("dd-MM-yyyy").format(date.getTime()));
                }


                for (String date1 : dateListReject) {
                     /* date1 = date1.substring(6, 10) + "-" + date1.substring(3, 5) + "-" + date1.substring(0, 2);*/
                    dateArray.add(date1);
                }
                List<JSONObject> finalList = new ArrayList<JSONObject>();

                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                int hourOfDay = (cal.getTime().getMinutes()>40)? 3 : 2;
                cal.add(Calendar.HOUR_OF_DAY, hourOfDay);
                System.out.println("Today Date is " + new Date(cal.getTimeInMillis()).getHours());
                int currenttime = new Date(cal.getTimeInMillis()).getHours();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(today.getTime());
                System.out.println(currentDate);

                for (Integer i = 9; i <= 18; i = i + timediff) {
                    String time = i.toString();
                    Integer nextTime = i + timediff;
                    String strNextTime = nextTime.toString();
                    //   JSONArray jsonArray = new JSONArray();
                    List<JSONObject> jsonArray = new ArrayList<JSONObject>();
                    for (String date1 : dateListReject) {

                        System.out.println(" date   " + date1);
                        if (date1.equalsIgnoreCase(currentDate)) {
                            if (i >= currenttime) {
                                String date2 = date1.substring(6, 10) + "-" + date1.substring(3, 5) + "-" + date1.substring(0, 2);
                                JSONObject jsonObject1 = postCallingService.getAvailableslot(date2, agentListUnique, time, date1);
                                jsonArray.add(jsonObject1);

                            } else {
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put(date1, "NotAvailabe");
                                jsonArray.add(jsonObject1);
                            }


                        } else {
                            String date2 = date1.substring(6, 10) + "-" + date1.substring(3, 5) + "-" + date1.substring(0, 2);
                            JSONObject jsonObject1 = postCallingService.getAvailableslot(date2, agentListUnique, time, date1);
                            jsonArray.add(jsonObject1);
                        }


                    }
                    String timeKey = time + ":00-" + strNextTime + ":00";
                    jsonObject.put(i, jsonArray);
                    // finalList.add(jsonObject);
                }
                 /* }*/
                finalJson.put("slotList", jsonObject);
                finalJson.put("dateList", dateArray);
                finalJson.put("timedeff", timediff);

                //   finalArray.add(jsonObject);

            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }


        return finalJson;
    }

    @RequestMapping(value = "/getAvailableSlotByDate", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getAvailableSlotByDate(HttpServletRequest request, HttpSession session) {
        int circleCode = 4;
        int pincode1 = 0;
        int timediff = 1;
        String date1 = "";
        JSONObject jsonObject = new JSONObject();
        JSONObject finalJson = new JSONObject();

        List<String> timeList = new ArrayList<String>();

        session = request.getSession();
        String username = (String) session.getAttribute("name");

        try {

            if (username != null) {
                String pincode = request.getParameter("pincode");
                String datefetch = request.getParameter("date");
                date1 = datefetch.substring(6, 10) + "-" + datefetch.substring(3, 5) + "-" + datefetch.substring(0, 2);

                List<String> agentList = agentPaytmService.getAgentPinMastList(pincode);
                Set<String> agentListUnique = new HashSet<String>(agentList);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar todat = Calendar.getInstance();
                String todaytime = format.format(todat.getTime());

                if (todaytime.equals(datefetch)) {
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    int hourOfDay = (cal.getTime().getMinutes()>40)? 3 : 2;
                    cal.add(Calendar.HOUR_OF_DAY, hourOfDay);
                    System.out.println("Today Date is " + new Date(cal.getTimeInMillis()).getHours());
                    int currenttime = new Date(cal.getTimeInMillis()).getHours();
                    for (Integer i = currenttime; i <= 18; i = i + timediff) {
                        String time = i.toString();
                        JSONArray jsonArray = new JSONArray();
                        System.out.println(" date   " + date1);
                        System.out.println(" Timecurrent   " + time);
                        JSONObject jsonObject1 = postCallingService.getAvailableslot(date1, agentListUnique, time, date1);
                        String result = (String) jsonObject1.get(date1);
                        if (result.equalsIgnoreCase("Available")) {
                            timeList.add(time + ":00");
                        }
                    }
                } else {
                    for (Integer i = 9; i <= 18; i = i + timediff) {
                        String time = i.toString();
                        JSONArray jsonArray = new JSONArray();
                        System.out.println(" date   " + date1);
                        System.out.println(" Timecurrent   " + time);
                        JSONObject jsonObject1 = postCallingService.getAvailableslot(date1, agentListUnique, time, date1);
                        String result = (String) jsonObject1.get(date1);
                        if (result.equalsIgnoreCase("Available")) {
                            timeList.add(time + ":00");
                        }
                    }
                }
                finalJson.put("timeList", timeList);


                //   finalArray.add(jsonObject);
            } else {
                jsonObject.put("available", "failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }


        return finalJson;
    }


    @RequestMapping(value = "/getAllCircle", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getAllCircle(HttpServletRequest request) {
        int circleCode = 4;
        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                circleCode = (Integer) session.getAttribute("cirCode");
            }

            if (circleCode != 0) {

                List<CircleMastEntity> circles = circleService.getCircleList();
                for (CircleMastEntity circle : circles) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("name", circle.getCircleName());
                    jsonObject1.put("code", circle.getCirCode());
                    list.add(jsonObject1);
                }
            } else {
                jsonObject.put("authentication", "failed");
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

            if (importby != null) {
                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String custid = request.getParameter("customerID");
                int customerid = Integer.parseInt(custid);
                String coStatus = request.getParameter("coStatus");
                String number = request.getParameter("mobileNo");
                String area = request.getParameter("remarks");
                String emailId = request.getParameter("emailId");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String pinCode = request.getParameter("pincode");
                String simType = request.getParameter("simType");
                String visitDate = request.getParameter("visitDate");
                String visitTime1 = request.getParameter("visitTime");
                String status = request.getParameter("status");
                Map<String, String> map = new HashMap<String, String>();
                String[] visitTime = visitTime1.split(":");
                System.out.println(visitTime[0]);
                map.put("number", number);
                map.put("custId", custid);
                map.put("name", name);
                map.put("address", address);
                map.put("area", area);
                map.put("emailId", emailId);
                map.put("city", city);
                map.put("state", state);
                map.put("pinCode", pinCode);
                map.put("simType", simType);
                map.put("co_status", coStatus);
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
            } else {
                jsonObject.put("authentication", "failed");
            }
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
        String message = "";
        int cirCode = 0;
        String role = null;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");
            }
            if (cirCode != 0) {
                String mobileNo = request.getParameter("mobileNo");
                String callingDate = request.getParameter("visit_date");
                String callingTime = request.getParameter("visit_time");
                String dateTime = callingDate.substring(6, 10) + "-" + callingDate.substring(3, 5) + "-" + callingDate.substring(0, 2) + " " + callingTime + ":00";

            //    result = callTimeService.insertCallTimeDetails(mobileNo, dateTime, cirCode, importby);

                if (result.equals("done")) {
                    message = "success";
                } else {
                    message = "error";
                }
            } else {
                jsonObject.put("authentication", "failed");
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
        String message = "";
        String importType = "Admin";
        int cirCode = 0;
        String role = null;
        int cust_uid=0;
        String callingTime="";
        String callingDate="";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");
            }
            if (cirCode != 0) {
                if (role.equals("ADM")) {
                    importType = "Admin";
                }

                if (role.equals("A1")) {
                    importType = "Agent";
                }
                if (role.equals("HR")) {
                    importType = "HR";
                }
                importType = "Admin";
                String mobileNo = request.getParameter("mobileNo");
                String status = request.getParameter("status");
                callingDate = request.getParameter("visit_date");
                callingTime = request.getParameter("visit_time");
                String cust_uid1 = request.getParameter("customerID");
                cust_uid=Integer.parseInt(cust_uid1);


                if (status.equals("2-CB")) {
                    String dateTime = callingDate.substring(6, 10) + "-" + callingDate.substring(3, 5) + "-" + callingDate.substring(0, 2) + " " + callingTime.trim() + ":00";
                    result = callTimeService.insertCallTimeDetails(mobileNo, dateTime, cirCode, importby,cust_uid);
                }
                System.out.println("callingTime " + callingDate + "callingTime " + callingTime);

                map.put("cust_uid", cust_uid1);
                map.put("number", mobileNo);
                map.put("status", status);
                map.put("importby", importby);
                map.put("importType", importType);
                map.put("custId", cust_uid1);
                result = postCallingService.saveCallingData(map);
                if ("done".equalsIgnoreCase(result)) {
                    result = "success";
                } else {
                    result = "error";
                }
            } else {
                jsonObject.put("authentication", "failed");
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

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String type = request.getParameter("type");
        /*from = from.substring(6, 10) + "-" + from.substring(3, 5) + "-" + from.substring(0, 2);
        to = to.substring(6, 10) + "-" + to.substring(3, 5) + "-" + to.substring(0, 2);*/
        jsonObject = reportService.getReports(from, to, type);

        return jsonObject;
    }

    @RequestMapping(value = "/qcstatus", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject getQcStatus(HttpServletRequest request, HttpSession session) {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        String scanid_string = (String) request.getParameter("scanId");
        String empcode = (String) session.getAttribute("name");
        int scanid = Integer.parseInt(scanid_string);
        int audit_status = 0;
        String name_mathched = (String) request.getParameter("nameMatched");
        String photo_matched = (String) request.getParameter("photoMatched");
        String sign_matched = (String) request.getParameter("signMatched");
        String dob_matched = (String) request.getParameter("dobMatched");
        String other_reason = (String) request.getParameter("otherReason");
        String qcStatus = (String) request.getParameter("qcStatus");
        String updateStatusMessage = null;
        if (qcStatus.equalsIgnoreCase("Accepted")) {
            audit_status = 3;
        } else {
            audit_status = 2;
        }

        AuditStatusEntity auditStatusEntity = qcStatusService.getAuditStatusEntity(audit_status);

        String assignedToResult = qcStatusService.checkAssignedTo(scanid, empcode);

        if (assignedToResult.equalsIgnoreCase("assigned")) {
         /*   CircleAuditEntity circleAuditEntity = new CircleAuditEntity();
            circleAuditEntity.setNameMatched(name_mathched);
            circleAuditEntity.setPhotoMatched(photo_matched);
            circleAuditEntity.setSignMatched(sign_matched);
            circleAuditEntity.setDobMatched(dob_matched);
            circleAuditEntity.setOtherReason(other_reason);
            circleAuditEntity.setTblScan(tblScan);
            circleAuditEntity.setAuditStatus(audit_status);*/
            String msg=qcStatusService.insertCircleAuditValues(dob_matched,name_mathched,other_reason,photo_matched,sign_matched,scanid,audit_status);
        if(msg.equalsIgnoreCase("success")){
            jsonObject.put("message","Successfully updated audit status");
        }else if (msg.equalsIgnoreCase("exists")){
            jsonObject.put("message","This image has already been audited");
        }
        else{
            jsonObject.put("message","Unable to update the audit status");
        }
        }
        /*String msg = qcStatusService.saveCircleAuditEntity(circleAuditEntity);
        if (msg.equalsIgnoreCase("error")) {
            updateStatusMessage = "Unable to insert the audit values";
        } else {
            tblScan.setAuditStatusEntity(auditStatusEntity);
            updateStatusMessage = qcStatusService.updateTblSacnEntity(tblScan);
            if (updateStatusMessage.equalsIgnoreCase("success")) {

                jsonObject.put("message", "Successfully updated audit status");
            } else {
                jsonObject.put("message", "Unable to update the audit status");
            }
        }
    }*/else{
            jsonObject.put("message","This image has been assigned to another user");
        }
        return jsonObject;

    }


    @RequestMapping(value = "/getCustomer", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getCustomerMobileNumber(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //String spokecode="DELSOU205";   Currently spoke code set static -JIA Sarai
        Integer circle_code = (Integer) session.getAttribute("cirCode");
        String empcode = (String) session.getAttribute("name");
        //int imgCount=1;
        // String spokecode=(String)session.getAttribute("spoke_code");
        JSONObject jsonObject = new JSONObject();
        JSONObject ftpdetailsjson = new JSONObject();
        List<String> filepathList = new ArrayList<String>();
        JSONObject detailJson = qcStatusService.getMobileNumber(circle_code, empcode);
        String status = (String) detailJson.get("status");
        if (status.equals("Unavailable")) {
            jsonObject.put("auditStatus", "No Images To Audit");
        } else {
            jsonObject.put("auditStatus", "Available");

            String imagePath = (String) detailJson.get("imagePath");
            Integer custUID = (Integer) detailJson.get("custUID");
            Integer imgCount = (Integer) detailJson.get("imgCount");
            String projectPath = request.getServletContext().getRealPath("/");
            String localPath = projectPath + "/resources/ftpimages/" + custUID;
/*        String f1="../resources/ftpimages/1000000001/1000000001_1.jpg";
        String f2="../resources/ftpimages/1000000001/1000000001_2.jpg";
        filepathList.add(f1);
        filepathList.add(f2);*/
            String ftpImagespath = projectPath + "/resources/ftpimages";
            File ftpImagesFolder = new File(ftpImagespath);
            String[] filenames = ftpImagesFolder.list();
            if (filenames.length != 0) {
                for (String folders : filenames) {
                    File imgFolder = new File(ftpImagesFolder.getPath(), folders);
                    if (imgFolder.isDirectory() && !folders.equalsIgnoreCase(String.valueOf(custUID))) {
                        Boolean isAoAuditDone = qcStatusService.getAoAuditStatus(folders);
                        if (isAoAuditDone) {
                            String[] images = imgFolder.list();
                            for (String image : images) {
                                File imgpath = new File(imgFolder.getPath(), image);
                                imgpath.delete();
                            }
                            imgFolder.delete();
                        }
                    }
                }
            }
            ftpdetailsjson = qcStatusService.getFTPDetailsForUser(circle_code);
            downloadImages(imagePath, localPath, ftpdetailsjson);
            for (int i = 0; i < imgCount; i++) {
                String filepath = "../resources/ftpimages/" + custUID + "/" + custUID + "_" + (i + 1) + ".jpg";
                filepathList.add(filepath);
            }
            String mobNo = (String) detailJson.get("mobile");
            Integer scanid = (Integer) detailJson.get("scanID");
            String sim_no = (String) detailJson.get("simNo");
            String username = (String) detailJson.get("name");
            String address = (String) detailJson.get("address");
            Integer actualCount = (Integer) detailJson.get("actualCount");
            jsonObject.put("mobile", mobNo);
            jsonObject.put("scanID", scanid);
            jsonObject.put("simNo", sim_no);
            jsonObject.put("name", username);
            jsonObject.put("address", address);
            jsonObject.put("imgCount", imgCount);
            jsonObject.put("filePathList", filepathList);
            jsonObject.put("actualCount", actualCount);
            jsonObject.put("custuid", custUID);
        }
        //return qcStatusService.getMobileNumber(spokecode);
        return jsonObject;
    }

    public static void downloadImages(String imagePath, String localpath, JSONObject ftpDetailsJSON) {
        String sftpHost = (String) ftpDetailsJSON.get("ftphost");
        String sftpUsername = (String) ftpDetailsJSON.get("ftpusername");
        String sftpPassword = (String) ftpDetailsJSON.get("ftpPwd");
        //String sftpHost="172.25.37.216";
        Integer sftpPort = 22;
        //String sftpUsername="soft";
        // String sftpPassword="$oFt@ge@123456";
        //imagePath="/soft/home/simex";check how to handle the path
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setPassword(sftpPassword);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            String homeDirectory = channelSftp.getHome();
            String folderDirectory = imagePath.substring(0, imagePath.lastIndexOf("/"));
            String directory = homeDirectory + "/" + folderDirectory;
            channelSftp.cd(directory);
            //check whether path returned is full or just file name & If list is file type or string
            List<ChannelSftp.LsEntry> listOfImages = channelSftp.ls(directory);
            for (ChannelSftp.LsEntry lsEntry : listOfImages) {
                String filename = lsEntry.getFilename();
                if (filename.contains(".jpg")) {
                    byte[] buffer = new byte[1024];
                    BufferedInputStream bis = new BufferedInputStream(channelSftp.get(filename));
                    File file = new File(localpath);
                    if (!file.isDirectory()) {
                        file.mkdir();
                    }
                    File fileToBeDownloaded = new File(localpath + "//" + filename);
                    OutputStream os = new FileOutputStream(fileToBeDownloaded);
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    int readCount;
                    while ((readCount = bis.read(buffer)) > 0) {
                        System.out.println("Writing...");
                        bos.write(buffer, 0, readCount);
                    }
                    bis.close();
                    bos.close();

                }
            }
        } catch (Exception e) {
            logger.error("",e);
        }

    }

    @RequestMapping(value = "/getCustomerDetailsForAoAudit", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject getCustomerDetailsForAoAudit(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        String spokecode = (String) session.getAttribute("spoke_code");
        //String spokecode="DELSOU205"; Currently spoke code set static -JIA Sarai
        String empcode = (String) session.getAttribute("name");
        List<String> filepathList = new ArrayList<String>();
        JSONObject detailJson = aoAuditService.getAoAuditDetails(spokecode, empcode);
        String cirStatus=null;
        String status = (String) detailJson.get("status");
        Integer circleStatus=(Integer)detailJson.get("circleStatus");

        if (status.equals("Unavailable")) {
            jsonObject.put("auditStatus", "No Images To Audit");
        } else {
            jsonObject.put("auditStatus", "Available");

            if (circleStatus==2){
                cirStatus="Rejected";
            }else{
                cirStatus="Accepted";
            }

            // String imagePath = (String) detailJson.get("imagePath");
            Integer custUID = (Integer) detailJson.get("custUID");
            Integer imgCount = (Integer) detailJson.get("imgCount");
            String projectPath = request.getServletContext().getRealPath("/");
            String localPath = projectPath + "/resources/ftpimages/" + custUID;


            for (int i = 0; i < imgCount; i++) {
                String filepath = "../resources/ftpimages/" + custUID + "/" + custUID + "_" + (i + 1) + ".jpg";
                filepathList.add(filepath);
            }
            String mobNo = (String) detailJson.get("mobile");
            Integer scanid = (Integer) detailJson.get("scanID");
            String sim_no = (String) detailJson.get("simNo");
            String username = (String) detailJson.get("name");
            String address = (String) detailJson.get("address");
            Integer actualCount = (Integer) detailJson.get("actualCount");
            //String circleRemarks=(String)detailJson.get("circleRemarks");
            jsonObject.put("mobile", mobNo);
            jsonObject.put("scanID", scanid);
            jsonObject.put("simNo", sim_no);
            jsonObject.put("name", username);
            jsonObject.put("address", address);
            jsonObject.put("imgCount", imgCount);
            jsonObject.put("actualCount", actualCount);
            jsonObject.put("filePathList", filepathList);
            //jsonObject.put("circleRemarks",circleRemarks);
            jsonObject.put("custuid", custUID);
            jsonObject.put("cirStatus",cirStatus);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/aoAuditStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject aoAuditQcStatus(HttpServletRequest request) {

        int cust_uid=0;
        HttpSession session = request.getSession();
        String empcode = (String) session.getAttribute("name");
        int custuid=0;
        String result = "";
        JSONObject jsonObject = new JSONObject();
        String scanid_string = (String) request.getParameter("scanId");
        int scanid = Integer.parseInt(scanid_string);
        int audit_status = 0;
        String custUid = (String) request.getParameter("custUID");

        if (custUid!=null){
             custuid=Integer.parseInt(custUid);

        }
        String name_mathched = (String) request.getParameter("nameMatched");
        String photo_matched = (String) request.getParameter("photoMatched");
        String sign_matched = (String) request.getParameter("signMatched");
        String dob_matched = (String) request.getParameter("dobMatched");
        String other_reason = (String) request.getParameter("otherReason");
        String qcStatus = (String) request.getParameter("qcStatus");
        String updateStatusMessage = null;
        if (qcStatus.equalsIgnoreCase("Accepted")) {
            audit_status = 5;
        } else {
            audit_status = 4;
        }

    //    AuditStatusEntity auditStatusEntity = qcStatusService.getAuditStatusEntity(audit_status);
        String assignmentstatus=aoAuditService.checkAoAssignedTo(scanid,empcode);
        if(assignmentstatus.equalsIgnoreCase("assigned")) {
          /*  AoAuditEntity aoAuditEntity = new AoAuditEntity();
            aoAuditEntity.setPhotoMatched(photo_matched);
            aoAuditEntity.setNameMatched(name_mathched);
            aoAuditEntity.setDobMatched(dob_matched);
            aoAuditEntity.setSignMatched(sign_matched);
            aoAuditEntity.setOtherReason(other_reason);
            aoAuditEntity.setTblScan(tblScan);
            aoAuditEntity.setAuditStatus(audit_status);*/
            String msg=aoAuditService.insertAoAuditValues(dob_matched,name_mathched,other_reason,photo_matched,sign_matched,scanid,audit_status);
            if(msg.equalsIgnoreCase("success")){
                jsonObject.put("message","Successfully updated audit status");
            }else if(msg.equalsIgnoreCase("exists")){
                jsonObject.put("message","This image has already been audited");
            }
            else{
                jsonObject.put("message","Unable to update the audit status");
            }

            //String msg = aoAuditService.saveAuditEntity(aoAuditEntity);
        }
        /*if (msg.equalsIgnoreCase("error")) {
            updateStatusMessage = "Unable to insert the audit values";
        } else {
            tblScan.setAuditStatusEntity(auditStatusEntity);
            updateStatusMessage = qcStatusService.updateTblSacnEntity(tblScan);



            if (updateStatusMessage.equalsIgnoreCase("success")) {
                if(custUid!=null){
                    cust_uid = Integer.parseInt(custUid);
                    PaytmMastEntity paytmMastEntity =paytmMasterService.getPaytmMastDatas(cust_uid);
                    paytmMastEntity.setFinalStatus("close");
                    paytmMasterService.updatePaytmMast(paytmMastEntity);


                }


                jsonObject.put("message", "Successfully updated audit status");
            } else {
                jsonObject.put("message", "Unable to update the audit status");
            }
        }*/
    else{
            jsonObject.put("message","This image has been assigned to another user");

        }
        return jsonObject;
    }

    @RequestMapping(value = "/getFormRecievingDetails", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getFormRecievingDetails(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        String spokecode = (String) session.getAttribute("spoke_code");
        //String spokecode="DELSOU205";  Currently spoke code set static -JIA Sarai
        String customer_number = (String) request.getParameter("mobNo");
        JSONObject resultJson = aoAuditService.getFormRecievingDetails(customer_number, spokecode);
        Integer cirAuditStatus = 0;
        String circleAuditResult = null;
        String retValue = (String) resultJson.get("returned");
        if (retValue.equalsIgnoreCase("unavailable")) {
            jsonObject.put("retMessage", "No Record Found");
        } else {
            String bucket = null;
            String status = null;
            Integer statusID = (Integer) resultJson.get("status");
            Integer scanID = (Integer) resultJson.get("scanID");
            cirAuditStatus = (Integer) resultJson.get("cirStatus");
            if (cirAuditStatus == 2) {
                circleAuditResult = "Rejected";
            } else {
                circleAuditResult = "Accepted";
            }
            String circleRemarks = (String) resultJson.get("circleRemarks");
            if (statusID == 2) {
                bucket = "Circle Audit";
                status = "Rejected";
                jsonObject.put("bucket", bucket);
                jsonObject.put("user_status", status);
            } else if (statusID == 3) {
                bucket = "Circle Audit";
                status = "Accepted";
                jsonObject.put("bucket", bucket);
                jsonObject.put("user_status", status);
            } else if (statusID == 4) {
                bucket = "Ao Audit";
                status = "Rejected";
                jsonObject.put("bucket", bucket);
                jsonObject.put("user_status", status);
            } else if (statusID == 5) {
                bucket = "Ao Audit";
                status = "Accepted";
                jsonObject.put("bucket", bucket);
                jsonObject.put("user_status", status);
            } else {
                bucket = "No Records Available For This Number";
                status = "No Records Available For This Number";
                jsonObject.put("bucket", bucket);
                jsonObject.put("user_status", status);
            }
            jsonObject.put("retMessage", "Found");
            jsonObject.put("circleRemarks", circleRemarks);
            jsonObject.put("simNum", resultJson.get("simNo"));
            jsonObject.put("user_address", resultJson.get("address"));
            jsonObject.put("user_name", resultJson.get("user_name"));
            jsonObject.put("scanID", scanID);
            jsonObject.put("cirAuditStatus", circleAuditResult);
            System.out.println("The returned values are :" + jsonObject);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/formRecievingSubmit", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject formRecievingSubmit(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String scanID = (String) request.getParameter("scanID");
        Integer scanid = Integer.parseInt(scanID);
        TblScan scanEntity = qcStatusService.getScanTableEntity(scanid);
/*        AuditStatusEntity auditStatusEntity=qcStatusService.getAuditStatusEntity(6);
        scanEntity.setAuditStatusEntity(auditStatusEntity);*/
        scanEntity.setFormRecievingStatus("R");
        scanEntity.setDocumentRecieveDatetime(new Timestamp(new Date().getTime()));
        String message = qcStatusService.updateTblSacnEntity(scanEntity);
        if (message.equalsIgnoreCase("success")) {
            jsonObject.put("result", "Successfully Updated");
        } else {
            jsonObject.put("result", "Error in updating status");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getInwordFrom", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getInwordFrom(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String importby = null;
        int cirCode = 0;
        String role = null;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }

            String getinwardfrom = batchService.getinwardfrom(cirCode);
            json.put("inWordFrom", getinwardfrom);
        } catch (Exception e) {
            logger.error("", e);
        }
        return json;
    }

    @RequestMapping(value = "/createBatch", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject createBatch(HttpServletRequest request, HttpServletResponse response) {
        String inWordFrom = request.getParameter("inWordFrom");
        String inWordTo = request.getParameter("inWordTo");
        String importby = null;
        int cirCode = 0;
        String role = null;
        JSONObject jsonObject = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            if (importby != null) {
                int inFrom = Integer.parseInt(inWordFrom);
                int inTO = Integer.parseInt(inWordTo);
                int totaldoc = inTO - inFrom + 1;
                jsonObject = batchService.saveBatch(inFrom, inTO, totaldoc, cirCode, importby);

            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {

            logger.error("", e);
        }

        return jsonObject;
    }


    @RequestMapping(value = "/indexCustomerValidation", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject indexCustomerValidation(HttpServletRequest request, HttpServletResponse response) {
        String mobileno = request.getParameter("mobileno");
        String importby = null;
        int cirCode = 0;
        String role = null;
        JSONObject jsonObject = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");
            }
            if (importby != null) {
                jsonObject = batchService.getuserDetails(mobileno);
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {

            logger.error("", e);
        }

        return jsonObject;
    }


    @RequestMapping(value = "/getBatchDetails", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject getBatchDetails(HttpServletRequest request, HttpServletResponse response) {
        String importby = null;
        int cirCode = 0;
        String role = null;
        JSONObject jsonObject = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }
            if (importby != null) {
                jsonObject = batchService.getBatchDetails(cirCode);

                jsonObject.put("status", "done");
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {

            logger.error("", e);
        }

        return jsonObject;
    }


    @RequestMapping(value = "/updateindexing", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject updateindexing(HttpServletRequest request, HttpServletResponse response) {
        String importby = null;
        int cirCode = 0;
        String role = null;
        JSONObject jsonObject = null;
        try {


            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");

            }

            if (importby != null) {
                //    var data = '&mobileno=' + $scope.mobileno +'&status=N' + '&batchno=' + $scope.batchno +'&uid=' + $scope.uid+'&emp_name=' + $scope.emp_name+'&customerId=' + $scope.customerId+'&remarks=' + $scope.user_comment;
                String mobileNo = request.getParameter("mobileno");
                String status = request.getParameter("status");
                String batchno1 = request.getParameter("batchno");
                int batchno = Integer.parseInt(batchno1);
                String uid1 = request.getParameter("uid");
                int uid = Integer.parseInt(uid1);
                String name = request.getParameter("emp_name");
                String customerId = request.getParameter("customerId");
                String remarks = request.getParameter("remarks");
                jsonObject = batchService.updateBatch(mobileNo, status, batchno, uid, name, customerId, remarks, importby);
            } else {
                jsonObject.put("aithentication", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }

        return jsonObject;
    }


    @RequestMapping(value = "/searchIndexing", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject searchIndexing(HttpServletRequest request, HttpServletResponse response) {
        String importby = null;
        int cirCode = 0;
        String role = null;
        JSONObject jsonObject = null;
        int batchno = 0;
        int uid = 0;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                importby = (String) session.getAttribute("name");
                cirCode = (Integer) session.getAttribute("cirCode");
                role = (String) session.getAttribute("role");
            }
            if (importby != null) {
                //    var data = '&mobileno=' + $scope.mobileno +'&status=N' + '&batchno=' + $scope.batchno +'&uid=' + $scope.uid+'&emp_name=' + $scope.emp_name+'&customerId=' + $scope.customerId+'&remarks=' + $scope.user_comment;
                String mobileNo = request.getParameter("cust_number");
                if (mobileNo.equalsIgnoreCase("undefined")) {
                    mobileNo = "";
                }
                String batchno1 = request.getParameter("batchSearch");
                if (!batchno1.equalsIgnoreCase("undefined") && !batchno1.equalsIgnoreCase("") && !batchno1.isEmpty()) {
                    batchno = Integer.parseInt(batchno1);
                }
                String uid1 = request.getParameter("uidnoSearch");
                System.out.println(uid1.isEmpty());
                if (!uid1.equalsIgnoreCase("undefined") && !uid1.equalsIgnoreCase("") && !uid1.isEmpty()) {
                    uid = Integer.parseInt(uid1);
                }
                //     jsonObject=batchService.updateBatch(mobileNo,status,batchno,uid,name,customerId,remarks,importby);

                //we have

                jsonObject = batchService.searchindexng(mobileNo, batchno, uid, cirCode);
            } else {
                jsonObject.put("authentication", "failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }

        return jsonObject;
    }

    @RequestMapping(value = "/testTiff", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String TestTiff(HttpServletRequest request, HttpServletResponse response) {
        File file = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Document document = new Document();
        String localPath = "D:/PaytmProject/PehchaanSVN/attchment/tiffFile/";

        try {
            String[] IMAGES = {""};
            file = new File(localPath);

            IMAGES = file.list();


            String output = "D:/PaytmProject/PehchaanSVN/attchment/tiffFile.pdf";
            fos = new FileOutputStream(output);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            TiffWriter write = new TiffWriter();

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
        } catch (Exception e) {

        }

        return "";
    }


}