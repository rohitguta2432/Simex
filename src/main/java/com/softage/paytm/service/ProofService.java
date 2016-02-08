package com.softage.paytm.service;

import com.softage.paytm.models.ProofMastEntity;

import java.util.List;

/**
 * Created by SS0085 on 05-02-2016.
 */
public interface ProofService {

    public List<ProofMastEntity> getProofMastEntity(String applicabe);
}
