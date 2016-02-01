package com.softage.paytm.service.imp;

import com.softage.paytm.dao.ReportDao;
import com.softage.paytm.service.ReportService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 08-01-2016.
 */
@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    public ReportDao reportDao;


    @Override
    public JSONObject getReports(String from, String to, String type) {
     JSONObject jsonObject=new JSONObject();
              if("TeleCalling".equalsIgnoreCase(type)){

                  jsonObject= reportDao.getReports(from,to,type);
                  jsonObject= reportDao.getReOpenCalling(from,to,jsonObject);
              }
        return jsonObject;
    }
}
