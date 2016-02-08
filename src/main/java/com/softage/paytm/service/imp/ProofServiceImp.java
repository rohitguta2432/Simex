package com.softage.paytm.service.imp;

import com.softage.paytm.dao.ProofDao;
import com.softage.paytm.models.ProofMastEntity;
import com.softage.paytm.service.ProofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SS0085 on 05-02-2016.
 */

@Service
public class ProofServiceImp implements ProofService {
    @Autowired
    private ProofDao proofDao;
    @Override
    public List<ProofMastEntity> getProofMastEntity(String applicable) {
        return proofDao.getProofMastEntity(applicable);
    }
}
