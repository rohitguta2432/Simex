package com.softage.paytm.spring.controllers;

import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmdeviceidinfoEntity;
import com.softage.paytm.service.PaytmDeviceService;
import com.softage.paytm.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

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

    @RequestMapping(value = "/getTest", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject test(){
        JSONObject jsonObject=new JSONObject();
        JSONArray arr=new JSONArray();
        arr.add("hello");
        arr.add("Anil");
        jsonObject.put("msg","this is JSON Testing");
        jsonObject.put("identification",arr);
        return jsonObject;
    }

    @RequestMapping(value = "/validateEmployee", method = {RequestMethod.GET, RequestMethod.POST})
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


    @RequestMapping(value = "/UpdateDeviceInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String UpdateDeviceInfo(HttpServletRequest request,HttpServletResponse response){
        String msg="0";
        try {
            String loginId = request.getParameter("loginId");
            String deviceId = request.getParameter("deviceId");
            String importby = request.getParameter("importby");
            PaytmdeviceidinfoEntity paytmdeviceidinfoEntity = new PaytmdeviceidinfoEntity();
            paytmdeviceidinfoEntity.setDeviceId(deviceId);
            paytmdeviceidinfoEntity.setLoginId(loginId);
            paytmdeviceidinfoEntity.setImportBy(importby);
            paytmdeviceidinfoEntity.setImportDate(new Timestamp(new Date().getTime()));
            msg=  paytmDeviceService.saveDevice(paytmdeviceidinfoEntity);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("",e);
        }
        return msg;

    }



}