package com.softage.paytm.dao;

import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 08-01-2016.
 */
public interface ReportDao {

    public JSONObject getReports(String from,String to,String type);
    public JSONObject getReOpenCalling(String from,String to,JSONObject jsonObject);
}
