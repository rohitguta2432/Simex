package com.softage.paytm.spring.controllers;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SS0085 on 01-02-2016.
 */

@RestController
@RequestMapping(value = "/REST")
public class RestWebController {

    @RequestMapping(value = "/getTest", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject test(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("msg","this is JSON Testing");
        return jsonObject;

    }

}
