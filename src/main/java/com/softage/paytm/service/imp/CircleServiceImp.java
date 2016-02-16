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
    @Override
    public List<ReportMastEntity> getReporttypes() {
        return circleMastDao.getReporttypes();
    }

    @Autowired
    public CircleMastDao circleMastDao;
    @Override
    public List<String> getCirleList(int circleCode) {
        List<String>  circleList =   new ArrayList<>();
        CircleMastEntity circle=circleMastDao.findByPrimaryKey(circleCode);
        circleList.add(circle.getCircleName());
        return circleList;
    }

    @Override
    public CircleMastEntity findByPrimaryKey(int circleCode) {
        return circleMastDao.findByPrimaryKey(circleCode);
    }


    @Override
    public List<String> getSpokeList(String circleName) {
       List<String> list= circleMastDao.getSpokeList(circleName);
        return list;
    }
}
