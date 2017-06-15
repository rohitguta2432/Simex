package com.softage.paytm.service.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.ReportMastEntity;
import com.softage.paytm.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 31-12-2015.
 */
@Service
public class CircleServiceImp implements CircleService {


    @Autowired
    public CircleMastDao circleMastDao;

    @Override
    public List<ReportMastEntity> getReporttypes() {
        return circleMastDao.getReporttypes();
    }

    @Override
    public List<String> getBySpokeCode(String spokecode) {
        return circleMastDao.getBySpokeCode(spokecode);
    }

    @Override
    public String getAospokeCode(String spokeCode) {
        return circleMastDao.getAospokeCode(spokeCode);
    }

    @Override
    public List<String> getCirleList(int circleCode) {
        List<String>  circleList =   new ArrayList<>();
        CircleMastEntity circle=circleMastDao.findByPrimaryKey(circleCode);
        circleList.add(circle.getCircleName());
        return circleList;
    }

    @Override
    public List<CircleMastEntity> getCircleList() {
        return circleMastDao.getCircleList();
    }


    @Override
    public CircleMastEntity findByPrimaryKey(int circleCode) {
        return circleMastDao.findByPrimaryKey(circleCode);
    }


    @Override
    public List<String> getSpokeList(String circleName,String empType) {
       List<String> list= circleMastDao.getSpokeList(circleName,empType);
        return list;
    }
}
