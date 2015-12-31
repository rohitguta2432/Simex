package com.softage.paytm.dao;

import com.softage.paytm.models.CircleMastEntity;

import java.util.List;

/**
 * Created by SS0085 on 29-12-2015.
 */
public interface CircleMastDao {

    public CircleMastEntity findByPrimaryKey(int circleCode);
    public List<String> getCircleList();
    List<String> getSpokeList(String circleName);

}
