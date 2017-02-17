package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.ManualLeadDao;
import com.softage.paytm.models.EmplogintableEntity;
import com.softage.paytm.models.PaytmagententryEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0097 on 2/9/2017.
 */
@Repository
public class ManualLeadDaoImpl implements ManualLeadDao{
@Inject
    private EntityManagerFactory entityManagerFactory;
    @Override
    @Transactional
    public List getAgentDetails() {
        EntityManager entityManager=null;
JSONObject jsonresponse=new JSONObject();
        ArrayList<JSONObject> listArray=new ArrayList<JSONObject>();

try {

    entityManager=entityManagerFactory.createEntityManager();
   javax.persistence.Query query= entityManager.createNativeQuery("{call sp_ReAssignLeads()}");
    List<Object[]> result=query.getResultList();
    for(Object[] s:result)
      {
          JSONObject listjson=new JSONObject();
          listjson.put("jobid", s[0]);
        listjson.put("PhoneNumber", s[1]);
        listjson.put("customerid", s[2]);
        listjson.put("agentCode", s[3]);
        listjson.put("agentname", s[4]);
        listjson.put("customeraddress", s[5]);
          listjson.put("customername",s[6]);
      listArray.add(listjson);
    }
}

catch (Exception e){
    e.printStackTrace();
}

finally {
    if (entityManager != null && entityManager.isOpen())
    {
        entityManager.close();
    }
}

        return listArray;
    }

    @Override
    @Transactional
    public List<PaytmagententryEntity> GetAgentsCode() {
        EntityManager entityManager=null;
        List agent=new ArrayList();
        List<PaytmagententryEntity> ListAgentcode=null;
       try{
entityManager=entityManagerFactory.createEntityManager();
String hqlstr="select codeagents from PaytmagententryEntity codeagents";
           javax.persistence.Query query= entityManager.createQuery(hqlstr);
   ListAgentcode=query.getResultList();
       }
catch (Exception e){
e.printStackTrace();
}
       finally {
           if (entityManager != null && entityManager.isOpen()) {
               entityManager.close();
           }
       }
        return ListAgentcode;
    }

    @Override
    @Transactional
    public String updateAgentsByCustUid(int CustomerId, String agentCode,String lastAgent) {
EntityManager entityManager=null;
        String message="";
        try {
            entityManager=entityManagerFactory.createEntityManager();
            javax.persistence.Query query = entityManager.createNativeQuery("{call sp_ManualLeadAssign(?,?,?)}");
            query.setParameter(1, CustomerId);
            query.setParameter(2, agentCode);
            query.setParameter(3,lastAgent);
            message= (String)query.getSingleResult();
        }
        catch(Exception e){
e.printStackTrace();
        }
return message;
    }

}
