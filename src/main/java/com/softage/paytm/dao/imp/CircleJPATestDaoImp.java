package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.CircleJPATestDao;
import com.softage.paytm.models.CircleMastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by SS0085 on 15-02-2016.
 */
@Repository
public class CircleJPATestDaoImp implements CircleJPATestDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory=null;

    @Override
    public <S extends CircleMastEntity> S save(S s) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg=null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(s);
            entityManager.flush();
            transaction.commit();
            msg="done";
        } catch (Exception e) {
            msg="err";
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return  s;
    }

    @Override
    public <S extends CircleMastEntity> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public CircleMastEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<CircleMastEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<CircleMastEntity> findAll(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(CircleMastEntity circleMastEntity) {

    }

    @Override
    public void delete(Iterable<? extends CircleMastEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
