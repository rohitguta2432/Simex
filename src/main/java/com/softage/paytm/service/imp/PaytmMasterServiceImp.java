package com.softage.paytm.service.imp;

import com.softage.paytm.dao.CircleMastDao;
import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.CallStatusMasterEntity;
import com.softage.paytm.models.CircleMastEntity;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.models.StateMasterEntity;
import com.softage.paytm.service.PaytmMasterService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by SS0085 on 22-12-2015.
 */
@Service
public class PaytmMasterServiceImp implements PaytmMasterService {
    @Autowired
    private PaytmMasterDao paytmMasterDao;

    @Autowired
    private CircleMastDao circleMastDao;

    @Override
    public void savePaytmMaster(List<Map<String, String>> paytmList) {

             List<PaytmMastEntity> custList=new ArrayList<PaytmMastEntity>();
             PaytmMastEntity paytmMastEntity=null;
             String circleCode=null;
             CircleMastEntity circleMastEntity=null;
        try {
            int i=1;
            for (Map<String, String> map : paytmList) {
                paytmMastEntity = new PaytmMastEntity();
                paytmMastEntity.setKycRequestId(map.get("kycRequestId"));
                System.out.println(map.get("kycRequestId"));
                paytmMastEntity.setCustomerId(map.get("CustomerID"));
                paytmMastEntity.setUsername(map.get("Username"));
                paytmMastEntity.setCustomerPhone(map.get("CustomerPhone"));
                paytmMastEntity.setEmail(map.get("Email"));
                paytmMastEntity.setAddressId(map.get("AddressID"));
                paytmMastEntity.setTimeSlot(map.get("TimeSlot"));
              //  paytmMastEntity.setPriority(map.get("Priority"));
                paytmMastEntity.setAddressStreet1(map.get("AddressStreet1"));
                paytmMastEntity.setAddressStreet2(map.get("AddressStreet2"));
                paytmMastEntity.setCity(map.get("City"));
                paytmMastEntity.setState(map.get("State"));
                paytmMastEntity.setPincode(map.get("Pincode"));
                String pincode=map.get("Pincode");
                String state=map.get("State");
                String pincode1=pincode.substring(0,2);
                String pincode2=pincode.substring(0,3);
                int pin1=Integer.parseInt(pincode1);
                int pin2=Integer.parseInt(pincode2);
                int circleCodevalue= getCircleCode(pin1,pin2,state);
                circleMastEntity= circleMastDao.findByPrimaryKey(circleCodevalue);
                if(circleMastEntity!=null){
                    paytmMastEntity.setCircleMastByCirCode(circleMastEntity);
                }
                paytmMastEntity.setAddressPhone(map.get("AddressPhone"));
                paytmMastEntity.setVendorName(map.get("VendorName"));
                paytmMastEntity.setStageId(map.get("StageId"));
                paytmMastEntity.setSubStageId(map.get("SubStageId"));
                paytmMastEntity.setCreatedTimestamp(map.get("CreatedTimestamp"));
                paytmMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                paytmMastEntity.setOtp("342"+i);
                paytmMastEntity.setRefCode(3424+i);
                paytmMastEntity.setImportBy("Afjal");
                custList.add(paytmMastEntity);
                i++;
            }
            paytmMasterDao.savePaytmMaster(custList);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public JSONObject getPaytmMastData(String mobileNo) {
        JSONObject jsonObject =paytmMasterDao.getPaytmMastData(mobileNo);
        return  jsonObject;

    }

    @Override
    public JSONObject telecallingScreen(String userName) {

       JSONObject json= paytmMasterDao.telecallingScreen(userName);
        return json;
    }

    @Override
    public List<StateMasterEntity> getStateList() {
       List<StateMasterEntity> listState=paytmMasterDao.getStatemaster();
        return listState;
    }

    @Override
    public List<CallStatusMasterEntity> getStatusList() {

           List<CallStatusMasterEntity>  statusList= paytmMasterDao.getStatusList();
           return statusList;
    }

    public int getCircleCode(int pin1,int pin2,String state){
        int circode = 0;
        String circleCode = "0";
        try {
            if (state.equalsIgnoreCase("GUJARAT") || (pin1 >= 36 && pin1 <= 39)) {
                circleCode = "1";
            } else if (state.equalsIgnoreCase("KARNATAKA") || (pin1 >= 56 && pin1 <= 59)) {
                circleCode = "2";
            } else if ((state.equalsIgnoreCase("BIHAR")) || (state.equalsIgnoreCase("JHARKHAND")) || (pin1 >= 80 && pin1 <= 85) || pin1 == 92) {
                circleCode = "3";
            } else if (state.equalsIgnoreCase("DELHI") || (pin1 == 11 || pin2 == 201 || pin2 == 222 || pin2 == 121)) {
                circleCode = "4";
            } else if (state.equalsIgnoreCase("HARYANA") || (pin2 > 122 && pin1 >= 12 && pin1 <= 13)) {
                circleCode = "5";
            } else if (state.equalsIgnoreCase("HIMANCHAL PRADESH") || pin1 == 17) {
                circleCode = "6";
            } else if (state.equalsIgnoreCase("RAJASTHAN") || (pin1 >= 30 && pin1 <= 34)) {
                circleCode = "7";
            } else if (state.equalsIgnoreCase("MADHYA PRADESH") || state.equalsIgnoreCase("CHHATISGARH") || (pin1 >= 45 && pin1 <= 49)) {
                circleCode = "8";
            } else if (state.equalsIgnoreCase("ASSAM") || (pin1 >= 78 && pin1 <= 79)) {
                circleCode = "9";
            } else if (state.equalsIgnoreCase("MAHARASHTRA") || (pin1 >= 40 && pin1 <= 44)) {
                circleCode = "10";
            } else if (state.equalsIgnoreCase("PUNJAB") || (pin1 >= 14 && pin1 <= 16)) {
                circleCode = "11";
            } else if (state.equalsIgnoreCase("WEST BENGAL") || state.equalsIgnoreCase("SIKKIM") || state.startsWith("ANDAMAN") || (pin1 >= 70 && pin2 <= 74)) {
                circleCode = "12";
            } else if (state.equalsIgnoreCase("UTTAR PARDESH") || (pin2 > 201 && pin1 >= 20 && pin1 <= 23)) {
                circleCode = "13";
            } else if (state.equalsIgnoreCase("UTTARAKHAND") || (pin1 >= 24 && pin1 <= 26)) {
                circleCode = "14";
            } else circleCode = "0";

            circode = Integer.parseInt(circleCode);
        }catch (Exception e){
          e.printStackTrace();
        }

      return  circode;
    }
}
