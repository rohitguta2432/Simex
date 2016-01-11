package com.softage.paytm.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0038 on 08-01-2016.
 */

@Component("multipartResolver")
public class PaytmMultiPartResolver extends StandardServletMultipartResolver {

    private HttpServletRequest request;

    public boolean isMultipartFile(HttpServletRequest request){
        request=request;
        return isMultipart(request);
    }
}
