package com.softage.paytm.service.imp;

import com.softage.paytm.dao.CircleJPATestDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.service.CircleJPATestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SS0085 on 15-02-2016.
 */
@Service
public class CircleJPATestServiceImp implements CircleJPATestService {
    @Autowired
    private CircleJPATestDao circleJPATestDao;
    @Override
    public CircleMastEntity getCircleEntity(Long cirId) {
        return null;
    }

    @Override
    public CircleMastEntity save(CircleMastEntity circleMastEntity) {
        return circleJPATestDao.save(circleMastEntity);
    }
}
