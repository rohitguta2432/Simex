package com.softage.paytm.dao.imp;

import com.softage.paytm.dao.ReportDao;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS0085 on 08-01-2016.
 */
@Repository
public class ReportDaoImp implements ReportDao {
     @Autowired
     public EntityManagerFactory entityManagerFactory;


    @Override
    public JSONObject getReports(String from, String to, String type) {
        EntityManager entityManager = null;
        List list = new ArrayList<>();
        Query query=null;
        JSONObject jsonObject=new JSONObject();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            query= entityManager.createNativeQuery("{call sp_getreportTelecalling(?,?)}");
            query.setParameter(1, from);
            query.setParameter(2,to);
               List<Object[]> resultList = query.getResultList();
            int i=1;
            for (Object[] objects: resultList  ){
                if (objects.length>0){
                    JSONObject json=new JSONObject();
                    json.put("CustomerId",objects[0]);
                    json.put("MobileNumber",objects[1]);
                    json.put("CallStatus",objects[2]);
                    json.put("Attempts",objects[3]);
                    json.put("CallDateTime",objects[4]);
                    json.put("Tele-CallerName",objects[5]);
                    json.put("CustomerName",objects[6]);
                    json.put("AppointmentDate",objects[7]);
                    json.put("AppointmentTime",objects[8]);
                    json.put("Address",objects[9]);
                    json.put("Landmark",objects[10]);
                    json.put("City",objects[11]);
                    json.put("State",objects[12]);
                    json.put("Pincode",objects[13]);
                    json.put("Agent_Code",objects[14]);
                    jsonObject.put("record-"+i,json);
                    i++;
                }

               }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (entityManager != null && entityManager.isOpen())
            {
                entityManager.close();
            }
        }
        return jsonObject;
    }
}
