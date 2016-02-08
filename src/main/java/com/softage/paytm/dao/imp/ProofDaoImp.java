package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.ProofDao;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import com.softage.paytm.models.ProofMastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 05-02-2016.
 */
@Repository
public class ProofDaoImp implements ProofDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public List<ProofMastEntity> getProofMastEntity(String applicable) {

        EntityManager entityManager=null;
        Query query=null;
        List<ProofMastEntity> proofMastEntities=new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select proof from ProofMastEntity proof where proof.applicable=:app1 or proof.applicable=:app2";
            query=entityManager.createQuery(strQuery);
            query.setParameter("app1","B");
            query.setParameter("app2",applicable);
            proofMastEntities = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();;
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return proofMastEntities;
    }
}
