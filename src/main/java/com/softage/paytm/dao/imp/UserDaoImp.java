package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.UserDao;
import com.softage.paytm.models.ActivateLogEntity;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SS0085 on 09-01-2016.
 */
@Repository
public class UserDaoImp implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImp.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public EmplogintableEntity getUserByEmpcode(String empCode) {
        EntityManager entityManager = null;
        Query query = null;
        EmplogintableEntity emplogintableEntity = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
          //  transaction.begin();
            String strQuery = "select emp from EmplogintableEntity emp where emp.empCode=:empCode";
            query = entityManager.createQuery(strQuery);
            query.setParameter("empCode", empCode);
            emplogintableEntity = (EmplogintableEntity) query.getSingleResult();
          //  transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return emplogintableEntity;

    }

    @Override
    public EmplogintableEntity getUserByToken(String token) {
        EntityManager entityManager = null;
        Query query = null;
        EmplogintableEntity emplogintableEntity = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select emp from EmplogintableEntity emp where emp.token=:token";

            query = entityManager.createQuery(strQuery);
            query.setParameter("token", token);
            query.setMaxResults(1);
            emplogintableEntity = (EmplogintableEntity) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {

                entityManager.close();
            }
        }
        return emplogintableEntity;

    }

    @Override
    public EmplogintableEntity getUserByEmpNumber(String empNumber) {
        EntityManager entityManager = null;
        Query query = null;
        EmplogintableEntity emplogintableEntity = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select emp from EmplogintableEntity emp where emp.empPhone=:empNumber";
            query = entityManager.createQuery(strQuery);
            query.setParameter("empNumber", empNumber);
            emplogintableEntity = (EmplogintableEntity) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            ;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return emplogintableEntity;
    }


    @Override
    public JSONObject getEmpFtpDetailsDao(int circleCode) {
        EntityManager entityManager = null;
        Query query = null;
        JSONObject jsonObject = new JSONObject();
        EntityTransaction entityTransaction=null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction= entityManager.getTransaction();
            entityTransaction.begin();
            query = entityManager.createNativeQuery("{call usp_getEmployeeFtpDetails(?)}");
            query.setParameter(1, circleCode);
            Object[] object = (Object[]) query.getSingleResult();
            entityTransaction.commit();
            jsonObject.put("ftpIp", object[0]);
            jsonObject.put("userName", object[1]);
            jsonObject.put("password", object[2]);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getStatus(String mobileno) {
        JSONObject jsonObject = new JSONObject();
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        boolean status=false;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_empstatus(?)}");
            query.setParameter(1, mobileno);
            Object[] object = (Object[]) query.getSingleResult();
            transaction.commit();
            jsonObject.put("status", object[0].toString());
             System.out.println(object[0].getClass());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                }
            }
        }
        return jsonObject;
    }

    @Override
    public String updateAgentStatus(EmplogintableEntity emplogintableEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(emplogintableEntity);
            transaction.commit();
            msg = "done";
        } catch (Exception e) {
            msg = "err";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return msg;
    }

    @Override
    public EmplogintableEntity getUserByOldPassword(String oldpassword,String user) {
        EntityManager entityManager = null;
        Query query = null;
        EmplogintableEntity emplogintableEntity = null;
        try {
            String usercode="%"+user+"%";
            String updatedpassword="%"+oldpassword+"%";
            entityManager = entityManagerFactory.createEntityManager();
            String strQuery = "select emp from EmplogintableEntity emp where str(emp.empPassword)=:password and emp.empCode=:username";
            /*String strQuery = "select emp from EmplogintableEntity emp where str(emp.empPassword) and emp.lastThreePassword like:password and emp.empCode=:username";*/
            query = entityManager.createQuery(strQuery);
            query.setParameter("password", updatedpassword);
            query.setParameter("username", usercode);
            emplogintableEntity = (EmplogintableEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
               /* entityManager.flush();
                entityManager.clear();*/
                entityManager.close();
            }
        }
        return emplogintableEntity;
    }

    @Override
    public String UpdateLastThreePassword(EmplogintableEntity emplogintableEntity,String updatedpasword) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(emplogintableEntity);
            transaction.commit();
            msg = "done";
        } catch (Exception e) {
            msg = "err";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return msg;
    }

    @Override
    public String updateAttaptStatus(EmplogintableEntity emplogintableEntity) {
        String result=null;
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        boolean status=false;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createNativeQuery("{call usp_updateEmploginAttemptCount(?,?)}");
            query.setParameter(1, emplogintableEntity.getEmpCode());
            query.setParameter(2, emplogintableEntity.getAttamptCount());
            result = (String) query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                }
            }
        }
        return result;
    }

    @Override
    public EmplogintableEntity getClientCode(String role) {
        EntityManager entityManager = null;
        Query query = null;
        EmplogintableEntity emplogintableEntity = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction=entityManager.getTransaction();
            transaction.begin();
            String strQuery = "select emp from EmplogintableEntity emp where emp.roleCode=:role order by emp.importDate desc";
            query = entityManager.createQuery(strQuery);
            query.setParameter("role", role);
            query.setMaxResults(1);
            emplogintableEntity = (EmplogintableEntity) query.getSingleResult();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return emplogintableEntity;
    }

    @Override
    public String saveActivateEntity(ActivateLogEntity activateLogEntity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        String msg = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(activateLogEntity);
            transaction.commit();
            msg = "done";
        } catch (Exception e) {
            msg = "err";
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return msg;
    }
}

