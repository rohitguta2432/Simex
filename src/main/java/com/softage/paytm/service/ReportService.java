package com.softage.paytm.service;

import org.json.simple.JSONObject;

/**
 * Created by SS0085 on 08-01-2016.
 */
public interface ReportService {
    public JSONObject getReports(String from,String to,String type);
}
