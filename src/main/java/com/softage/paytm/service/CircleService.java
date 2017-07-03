package com.softage.paytm.service;

import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.ReportMastEntity;

import java.util.List;

/**
 * Created by SS0085 on 31-12-2015.
 */
public interface CircleService {

    public List<String> getCirleList(int cirCode);
    public List<CircleMastEntity> getCircleList();
    public CircleMastEntity findByPrimaryKey(int circleCode);
    List<String> getSpokeList(String circleName,String empType);
    public List<ReportMastEntity> getReporttypes();
    public List<String> getBySpokeCode(String spokecode);
    public String getAospokeCode(String spokeCode);


}
