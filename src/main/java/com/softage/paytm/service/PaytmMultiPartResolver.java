package com.softage.paytm.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SS0038 on 08-01-2016.
 */

@Component("paytmMultiPartResolver")
public class PaytmMultiPartResolver extends StandardServletMultipartResolver {
    public boolean isMultiPartRequest(HttpServletRequest req){
        return this.isMultipart(req);
    }
}
