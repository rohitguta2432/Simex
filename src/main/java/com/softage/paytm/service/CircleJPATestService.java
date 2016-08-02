package com.softage.paytm.service;

import com.softage.paytm.models.CircleMastEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by SS0085 on 15-02-2016.
 */
public interface CircleJPATestService
{

    public CircleMastEntity getCircleEntity(Long cirId);
    public CircleMastEntity save(CircleMastEntity circleMastEntity);
}
