package com.softage.paytm.service.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SS0085 on 31-12-2015.
 */
@Service
public class CircleServiceImp implements CircleService {
    @Autowired
    public CircleMastDao circleMastDao;
    @Override
    public List<String> getCirleList() {
        List<String> circleList=circleMastDao.getCircleList();
        return circleList;
    }
}
