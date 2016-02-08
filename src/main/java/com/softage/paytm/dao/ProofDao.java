package com.softage.paytm.dao;

import com.softage.paytm.models.ProofMastEntity;

import java.util.List;

/**
 * Created by SS0085 on 05-02-2016.
 */
public interface ProofDao {
    public List<ProofMastEntity> getProofMastEntity(String applicabe);
}
