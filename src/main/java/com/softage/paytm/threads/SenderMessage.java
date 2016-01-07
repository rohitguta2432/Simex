package com.softage.paytm.threads;

import com.softage.paytm.service.PostCallingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by SS00857 on 03-01-2016.
 */

public class SenderMessage {
@Autowired
public PostCallingService postCallingService;


    public void demoService(){
        try {
            postCallingService.sendsmsService();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Method executed at every 5 minute. Current time is :: "+ new Date());

    }
}
