package com.softage.paytm.service;

import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.ReportMastEntity;

import java.util.List;

/**
 * Created by SS0085 on 31-12-2015.
 */
public interface CircleService {
    public List<String> getCirleList();
    List<String> getSpokeList(String circleName);
    public List<ReportMastEntity> getReporttypes();

}
