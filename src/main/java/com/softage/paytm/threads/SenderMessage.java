package com.softage.paytm.threads;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by SS00857 on 03-01-2016.
 */
public class SenderMessage {


    public void demoService(){

        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());

    }
}
