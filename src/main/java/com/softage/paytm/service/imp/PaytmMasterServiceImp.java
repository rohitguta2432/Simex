package com.softage.paytm.service.imp;

import com.softage.paytm.dao.PaytmMasterDao;
import com.softage.paytm.models.PaytmMastEntity;
import com.softage.paytm.service.PaytmMasterService;
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

    @Override
    public void savePaytmMaster(List<Map<String, String>> paytmList) {

             List<PaytmMastEntity> custList=new ArrayList<PaytmMastEntity>();
             PaytmMastEntity paytmMastEntity=null;
        try {
            for (Map<String, String> map : paytmList) {
                paytmMastEntity = new PaytmMastEntity();
                paytmMastEntity.setKycRequestId(map.get("kycRequestId"));
                paytmMastEntity.setCustomerId(map.get("CustomerID"));
                paytmMastEntity.setUsername(map.get("Username"));
                paytmMastEntity.setCustomerPhone(map.get("CustomerPhone"));
                paytmMastEntity.setEmail(map.get("Email"));
                paytmMastEntity.setAddressId(map.get("AddressID"));
                paytmMastEntity.setTimeSlot(map.get("TimeSlot"));
                paytmMastEntity.setPriority(map.get("Priority"));
                paytmMastEntity.setAddressStreet1(map.get("AddressStreet1"));
                paytmMastEntity.setAddressStreet2(map.get("AddressStreet2"));
                paytmMastEntity.setCity(map.get("City"));
                paytmMastEntity.setState(map.get("State"));
                paytmMastEntity.setPincode(map.get("Pincode"));
                paytmMastEntity.setAddressPhone(map.get("AddressPhone"));
                paytmMastEntity.setVendorName(map.get("VendorName"));
                paytmMastEntity.setStageId(map.get("StageId"));
                paytmMastEntity.setSubStageId(map.get("SubStageId"));
                paytmMastEntity.setCreatedTimestamp(map.get("CreatedTimestamp"));
                paytmMastEntity.setImportDate(new Timestamp(new Date().getTime()));
                paytmMastEntity.setOtp("123");
                custList.add(paytmMastEntity);

            }
            paytmMasterDao.savePaytmMaster(custList);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public PaytmMastEntity getPaytmMastData() {
        PaytmMastEntity paytmMastEntity=new PaytmMastEntity();

        return  paytmMastEntity;

    }
}
